package com.example.paradox.network

import com.example.paradox.models.Companies
import com.example.paradox.models.Company
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://movilesback.herokuapp.com/"

interface CompaniesInterface {
    //List
    @GET("api/employers/1/companies")
    fun getAllCompaniesByEmployerId(): Call<Companies>

    //Get
    @GET("api/employers/1/companies/{companyId}")
    fun getCompanyById(@Path("companyId") companyId: Int): Call<Company>

    //Add
    @POST("api/employers/1/companies")
    fun addCompany(@Body company: Company): Call<Company>

    //Edit
    @PUT("api/employers/1/companies/{companyId}")
    fun editCompany(@Path("companyId") companyId: Int, @Body company: Company): Call<Company>
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