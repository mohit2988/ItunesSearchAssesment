package com.itunessearchassesment.middelware.net

import com.itunessearchassesment.middelware.model.ITunesResultResponse
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {

    @GET("search?term=all")
    fun getITunesResults(): Call<ITunesResultResponse>
}