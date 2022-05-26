package com.example.paradox.network

import com.example.paradox.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL2 = "https://movilesback.herokuapp.com/"

interface UserInterface {


    //Get
    @GET("api/users/email/{email}/password/{password}")
    fun getAuth(@Path("email") email: String,@Path("password") password: String): Call<User>


}

object UserService {
    val userInstance: UserInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userInstance = retrofit.create(UserInterface::class.java)
    }
}