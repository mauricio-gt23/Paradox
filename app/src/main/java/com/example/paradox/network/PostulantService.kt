package com.example.paradox.network

import com.example.paradox.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
const val BASE_URL_BRI = "https://movilesback.herokuapp.com/api/"

interface PostulantInterface2 {
    @GET("postulants/{id}")
    fun getPostulantById(@Path("id") format: Int) : Call<PostulantBri>

    @PUT("postulants/{id}")
    fun editPostulant(@Path("id") postulantId: Int, @Body postulantBri: PostulantBri): Call<PostulantBri>

    @GET("postulants/{postulantId}/profiles/{profileId}")
    fun getProfileByIdAndPostulantId(@Path("postulantId") postulantId: Int, @Path("profileId") profileId: Int): Call<ProfProfile>

    @GET("postulants/{postulantId}/profiles")
    fun getProfileIdByUserId(@Path("postulantId") postulantId: Int): Call<ProfProfiles>

    @GET("profiles/{profileId}/studies")
    fun getStudiesByProfileId(@Path("profileId") postulantId: Int): Call<Studies>

    @GET("profiles/{profileId}/skills")
    fun getSkillsByProfileId(@Path("profileId") postulantId: Int): Call<Skills>

    @GET("profiles/{profileId}/languages")
    fun getLanguagesByProfileId(@Path("profileId") postulantId: Int): Call<Languages>

    @GET("languages")
    fun getAllLanguages(): Call<Languages>

    @GET("skills")
    fun getAllSkills(): Call<Skills>

    @GET("studies")
    fun getAllStudies(): Call<Studies>

    @PUT("postulants/{postulantId}/profiles/{profileId}")
    fun editProfileOfSpecificPostulant(@Path("postulantId") postulantId: Int, @Path("profileId") profileId: Int, @Body profProfile: ProfProfile): Call<ProfProfile>

    @GET("studies/{studyId}/profiles")
    fun getStudiesByProfile(@Path("studyId") studyId: Int): Call<Studies>

    @GET("studies/{skillId}/profiles")
    fun getSkillsByProfile(@Path("skillId") skillId: Int): Call<Skills>

    @GET("languages/{languageId}/profiles")
    fun getLanguagesByProfile(@Path("languageId") languageId: Int): Call<Languages>

}

object PostulantService {
    val postulantInstance: PostulantInterface2
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_BRI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        postulantInstance = retrofit.create(PostulantInterface2::class.java)
    }
}