package com.example.paradox.network

import com.example.paradox.models.Companies
import com.example.paradox.models.Company
import com.example.paradox.models.RequestCompany
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://movilesback.herokuapp.com/"

interface CompaniesInterface {
    //List
    @GET("api/employeers/{employeerId}/companys")
    fun getAllCompaniesByEmployerId(@Path("employeerId") employeerId: Int): Call<Companies>

    //Get
    @GET("api/companys/{companyId}")
    fun getCompanyById(@Path("companyId") companyId: Int): Call<Company>

    //Edit
    @PUT("api/employeers/{employeerId}/sector/{sectorId}/companys")
    fun editCompany(@Path("employeerId") employeerId: Int, @Path("sectorId") sectorId: Int, @Body requestCompany: RequestCompany): Call<Company>

    //Add
    @POST("api/employeers/{employeerId}/sector/{sectorId}/companys")
    fun addCompany(@Path("employeerId") employeerId: Int, @Path("sectorId") sectorId: Int, @Body requestCompany: RequestCompany): Call<Company>
}

object CompaniesService {
    val companiesInstance: CompaniesInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        companiesInstance = retrofit.create(CompaniesInterface::class.java)
    }
}