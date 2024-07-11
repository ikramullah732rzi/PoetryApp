package com.iinnovation.hindishayari.api

import com.iinnovation.hindishayari.BASE_ID
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientApi {


    val url ="https://api.airtable.com/v0/$BASE_ID/"
    private var retrofit :Retrofit?=null
    private var okHttpClient :OkHttpClient=OkHttpClient.Builder().build()

    fun getApiClient():Retrofit{
        if(retrofit==null){
            retrofit=Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}