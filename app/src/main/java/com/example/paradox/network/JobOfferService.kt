package com.example.paradox.network

import com.example.paradox.models.Works
import retrofit2.Call
import retrofit2.http.GET

interface JobOfferService {

    @GET("api/jobOffers")
    fun getAllJobOffers(): Call<Works>
}