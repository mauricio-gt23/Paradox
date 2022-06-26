package com.example.paradox.network

import com.example.paradox.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL3 = "https://movilesback.herokuapp.com/"

interface RegisterInterface {


    //Get
    @POST("/api/employeers")
    fun createEmployeer(@Body requestEmployeer: RequestEmployeer): Call<ResponseEmployeer>

    @POST("/api/postulants")
    fun createPostulant(@Body requestPostulant: RequestPostulant): Call<ResponsePostulant>

    @POST("/api/postulants/{profileId}/profiles")
    fun createPostulantProfile(@Path("profileId") profileId: Long,@Body requestPostulantPerfilCaro: RequestPostulantPerfilCaro): Call<ResponsePostulantPerfilCaro>


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