package com.example.paradox.network

import com.example.paradox.models.PostulantJobs
import com.example.paradox.models.Postulantz
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PostulantJobService {

    @GET("api/postulants/{postulantId}/postulantjobs")
    fun getAllPostulantJobByPostulantId(@Path("postulantId") postulantId: Int): Call<PostulantJobs>

    @GET("api/joboffers/{jobofferId}/postulantjobs")
    fun getAllPostulantJobsByJobOfferId(@Path("jobofferId") jobofferId: Int): Call<Postulantz>
}