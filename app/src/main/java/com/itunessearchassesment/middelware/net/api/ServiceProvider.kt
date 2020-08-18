package com.itunessearchassesment.middelware.net.api

import com.itunessearchassesment.BuildConfig
import com.itunessearchassesment.middelware.net.ServiceApi
import com.itunessearchassesment.middelware.net.error_handle.APIError
import com.itunessearchassesment.middelware.net.error_handle.ApiErrorEnum
import com.itunessearchassesment.middelware.utils.OkHttpUtils
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ServiceProvider {
    private val retrofitPublic: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpUtils.getHttpClient(null, OkHttpUtils.loggingInterceptor)
        )
        .build()

    val serviceApi: ServiceApi = retrofitPublic.create(
        ServiceApi::class.java
    )

    fun parseError(response: Response<*>): APIError? {
        return parseError(response.errorBody())
    }

    private fun parseError(responseBody: ResponseBody?): APIError? {
        val converter = retrofitPublic
            .responseBodyConverter<APIError>(
                APIError::class.java,
                arrayOfNulls(0)
            )
        val error: APIError?
        error = try {
            converter.convert(responseBody)
        } catch (e: IOException) {
            return APIError()
        }
        return error
    }

    fun handleError(t: Throwable): APIError? {
        var apiError: APIError? = null
        when (t) {
            is HttpException -> {
                apiError =
                    t.response()?.let { parseError(it) }
            }
            is UnknownHostException -> {
                apiError = APIError()
                val apiErrorEnum: ApiErrorEnum = ApiErrorEnum.INTERNET_ISSUE
                apiError.code = apiErrorEnum.errorCode
                apiError.message = apiErrorEnum.message
            }
            is SocketTimeoutException -> {
                apiError = APIError()
                val apiErrorEnum: ApiErrorEnum = ApiErrorEnum.TIMEOUT
                apiError.code = apiErrorEnum.errorCode
                apiError.message = apiErrorEnum.message
            }
            is InterruptedIOException -> {
                apiError = APIError()
                val apiErrorEnum: ApiErrorEnum = ApiErrorEnum.GENERIC_ERROR
                apiError.code = apiErrorEnum.errorCode
                apiError.message = apiErrorEnum.message
            }
            else -> {
                apiError = APIError()
                val apiErrorEnum: ApiErrorEnum = ApiErrorEnum.GENERIC_ERROR
                apiError.code = apiErrorEnum.errorCode
                apiError.message = apiErrorEnum.message
            }
        }
        return apiError
    }
}