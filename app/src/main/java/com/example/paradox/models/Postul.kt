package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class Postul (

    @SerializedName("idPostulant")
    val id: Int?,

    @SerializedName("firstnamePostulant")
    val firstname: String,

    @SerializedName("lastnamePostulant")
    val lastname: String,

    @SerializedName("documentPostulant")
    val document: String,

    @SerializedName("emailPostulant")
    val email: String,

    @SerializedName("numberPostulant")
    val number: Int?
    )