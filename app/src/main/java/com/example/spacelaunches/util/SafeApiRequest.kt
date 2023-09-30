package com.example.spacelaunches.util

import android.util.Log
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


object SafeApiRequest {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val responseErr = response.errorBody()?.string()
            val message = StringBuilder()
            responseErr?.let {
                try {
                    message.append(JSONObject(it).getString("error"))
                } catch (e: JSONException) {
                    message.append("Unknown error")
                }
            } ?: run {
                message.append("Response error body is null")
            }
            Log.d("TAG", "safeApiRequest: $message")
            throw Exception(message.toString())
        }
    }
}