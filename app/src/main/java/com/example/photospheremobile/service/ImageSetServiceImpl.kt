package com.example.photospheremobile.service

import android.util.Log
import com.example.photospheremobile.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ImageSetServiceImpl {


    fun uploadImage(imageName: String, uuid: String, imageContent: String) {
        val call = NetworkUtils().imageSetService().uploadImage(imageName, uuid, imageContent)
        call.enqueue(object : Callback<Map<String, Objects>?> {
            override fun onResponse(
                call: Call<Map<String, Objects>?>?,
                response: Response<Map<String, Objects>?>?
            ) {
                response?.let {
                    val body = it.body()
                    Log.i("onFailure error", body.toString())
                }
            }

            override fun onFailure(call: Call<Map<String, Objects>?>?, t: Throwable?) {

            }
        })
    }

    fun getTest() {
        val call = NetworkUtils().imageSetService().getTest()
        call.enqueue(object : Callback<Any?> {
            override fun onResponse(
                call: Call<Any?>?,
                response: Response<Any?>?
            ) {
                response?.let {
                    val body = it.body()
                }
            }

            override fun onFailure(call: Call<Any?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

}