package com.iinnovation.hindishayari.domain

import com.iinnovation.hindishayari.api.ApiEndpoints
import com.iinnovation.hindishayari.api.modelclass.AdsModelClass

class Repository(val apiEndpoints: ApiEndpoints) {

    suspend fun getAirTableData( tableName:String,authorization : String) : AdsModelClass?{
        val result =  apiEndpoints.getPermission( tableName = tableName, authorization = authorization ).body()
        return  result
    }


}