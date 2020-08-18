package com.itunessearchassesment.middelware.net.error_handle

import com.google.gson.annotations.SerializedName

class APIError(
    @SerializedName("errors")
    var error: Errors = Errors(),
    @SerializedName("status")
    var code: Int = 0,
    @SerializedName("title")
    var message: String = "",
    @SerializedName("traceId")
    var traceId: String = "",
    @SerializedName("type")
    var type: String = ""
) {
    data class Errors(
        @SerializedName("gearTypeId")
        var gearTypeId: List<String> = listOf()
    )

    object ApiErrorCode {
        const val INTERNET_ISSUE = 9001
        const val TIMEOUT = 9002
        const val DEFAULT = 9003
    }
}