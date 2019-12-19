package com.example.photospheremobile.service

import android.media.Image
import com.example.photospheremobile.models.ImageSet
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ImageSetService {

    @GET("imageset")
    fun getImageSets(): Call<MutableList<ImageSet>>

    @POST("images/upload")
    @Headers("Content-type:image/jpeg")
    fun uploadImage(
        @Header("phone_img_name") imageName: String,
        @Header("phone_UUID") uuid: String,
        @Body imageContent: String
    ): Call<Map<String, Objects>>

    @POST("imageset")
    @Headers("Content-type:application/json")
    fun saveImageSet(@Body imageSet: ImageSet): Call<Map<String, Objects>>

}
