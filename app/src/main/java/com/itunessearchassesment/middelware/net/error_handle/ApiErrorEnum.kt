package com.itunessearchassesment.middelware.net.error_handle

import com.itunessearchassesment.middelware.net.error_handle.APIError.ApiErrorCode

enum class ApiErrorEnum(val errorCode: Int, val message: String) {
    INTERNET_ISSUE(ApiErrorCode.INTERNET_ISSUE, "Please check your internet connection."),
    TIMEOUT(
        ApiErrorCode.TIMEOUT,
        "Server Timeout."
    ),
    GENERIC_ERROR(
        ApiErrorCode.TIMEOUT,
        "Something went wrong with the network connection, Please try again later."
    );

}