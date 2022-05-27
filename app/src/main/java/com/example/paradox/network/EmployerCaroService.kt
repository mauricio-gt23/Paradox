package com.example.paradox.network


import com.example.paradox.models.EmployeerCaro
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL4 = "https://movilesback.herokuapp.com/"

interface EmployeerInterface {


    //Get
    @GET("api/employeers/{id}")
    fun getEmployeer(@Path("id") email: Long): Call<EmployeerCaro>


}

object EmployeerService {
    val employeerInstance: EmployeerInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL4)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        employeerInstance = retrofit.create(EmployeerInterface::class.java)
    }
}