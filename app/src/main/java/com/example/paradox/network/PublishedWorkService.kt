package com.example.paradox.network

import com.example.paradox.models.PublishedWorks
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PublishedWorkService {

    @GET("api/employeers/{employeerId}/joboffers")
    fun getAllJobOffersByEmployeerId(@Path("employeerId") employeerId: Int): Call<PublishedWorks>
}