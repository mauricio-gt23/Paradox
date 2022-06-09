package com.example.paradox.network

import com.example.paradox.models.RequestEmployeer
import com.example.paradox.models.ResponseEmployeer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL3 = "https://movilesback.herokuapp.com/"

interface RegisterInterface {


    //Get
    @POST("/api/employeers")
    fun createEmployeer(@Body requestEmployeer: RequestEmployeer): Call<ResponseEmployeer>


}

object RegisterService {
    val registerInstance: RegisterInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL3)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        registerInstance = retrofit.create(RegisterInterface::class.java)
    }
}