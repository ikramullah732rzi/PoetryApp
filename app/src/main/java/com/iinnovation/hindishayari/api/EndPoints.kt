package com.iinnovation.hindishayari.api

import com.iinnovation.hindishayari.api.modelclass.AdsModelClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ApiEndpoints {

    @GET("{tableName}")
    suspend fun getPermission(
        @Path("tableName") tableName : String,
        @Header("Authorization") authorization: String
    ): Response<AdsModelClass>
}