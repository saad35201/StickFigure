package com.saadi.stickfigure.core

import com.saadi.stickfigure.utils.NetworkResult
import okio.IOException
import org.json.JSONObject
import retrofit2.Response

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                NetworkResult.Success(response.body()!!)
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                NetworkResult.Error(errorObj.getString("message"))
            } else {
                NetworkResult.Error("Something went wrong")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            NetworkResult.Error(ex.message)
        }catch (ex: Exception){
            ex.printStackTrace()
            NetworkResult.Error(null)
        }
    }

}