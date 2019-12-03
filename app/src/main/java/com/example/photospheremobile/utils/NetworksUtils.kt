package com.example.photospheremobile.utils

import com.example.photospheremobile.service.ImageSetService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {


    /** Retorna uma Instância do Client Retrofit para Requisições
     * @param path Caminho Principal da API
     */
    fun getRetrofitInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://10.7.128.18:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun imageSetService(): ImageSetService {
        return getRetrofitInstance().create(ImageSetService::class.java)
    }

}