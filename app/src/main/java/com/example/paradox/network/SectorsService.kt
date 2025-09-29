package com.example.paradox.network

import com.example.paradox.models.Sectors
import com.example.paradox.models.Sector
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SectorsInterface {
    //List
    @GET("api/sectors")
    fun getAllSectors(): Call<Sectors>
}

object SectorsService {
    val sectorsInstance: SectorsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sectorsInstance = retrofit.create(SectorsInterface::class.java)
    }
}