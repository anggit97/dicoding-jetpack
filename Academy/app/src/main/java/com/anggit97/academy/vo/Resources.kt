package com.anggit97.academy.vo

import androidx.annotation.Nullable
import com.anggit97.academy.vo.Status.*


/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */
class Resource<T>(
    val status: Status, @param:Nullable @field:Nullable
    val data: T?, @param:Nullable @field:Nullable
    val message: String?
) {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as Resource<*>?

        if (status !== resource!!.status) {
            return false
        }
        if (if (message != null) message != resource!!.message else resource!!.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    companion object {

        fun <T> success(@Nullable data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, @Nullable data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
