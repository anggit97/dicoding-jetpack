package com.anggit97.academy.data.source.remote

import androidx.annotation.Nullable
import com.anggit97.academy.data.source.remote.StatusResponse.*


/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */
class ApiResponse<T>(
    val status: StatusResponse, @param:Nullable @field:Nullable
    val body: T, @param:Nullable @field:Nullable
    val message: String?
) {
    companion object {

        fun <T> success(@Nullable body: T): ApiResponse<T> {
            return ApiResponse(SUCCESS, body, null)
        }

        fun <T> empty(msg: String, @Nullable body: T): ApiResponse<T> {
            return ApiResponse(EMPTY, body, msg)
        }

        fun <T> error(msg: String, @Nullable body: T): ApiResponse<T> {
            return ApiResponse(ERROR, body, msg)
        }
    }
}
