package com.example.paradox.network

import com.example.paradox.models.Work
import com.example.paradox.models.Works
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JobOfferService {

    @GET("api/jobOffers")
    fun getAllJobOffers(): Call<Works>

    @GET("api/jobOffers/{jobOfferId}")
    fun getJobOfferById(@Path("jobOfferId") jobOfferId: Int): Call<Work>
}