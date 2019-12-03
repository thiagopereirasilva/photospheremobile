package com.example.photospheremobile.service

import android.media.Image
import com.example.photospheremobile.models.ImageSet
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ImageSetService {

    @GET("image-sets")
    fun getImageSets(): Call<List<ImageSet>>

    @GET("images/hdr")
    fun getTest(): Call<Any?>

    @POST("images/upload")
    @Headers("Content-type:image/jpeg")
    fun uploadImage(
        @Header("phone_img_name") imageName: String,
        @Header("phone_UUID") uuid: String,
        @Body imageContent: String
    ): Call<Map<String, Objects>>

}
