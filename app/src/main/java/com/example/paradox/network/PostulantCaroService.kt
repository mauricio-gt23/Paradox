package com.example.paradox.network

import com.example.paradox.models.EmployeerCaro
import com.example.paradox.models.Postulant
import com.example.paradox.models.PostulantCaro
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL5 = "https://movilesback.herokuapp.com/"

interface PostulantInterface {


    //Get
    @GET("/api/postulants/{id}")
    fun getPostulant(@Path("id") email: Long): Call<PostulantCaro>


}

object PostulantCaroService {
    val postulantInstance: PostulantInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL5)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        postulantInstance = retrofit.create(PostulantInterface::class.java)
    }
}