package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Postulant (
    @SerializedName("id")
    val id: Int,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("document")
    val document: String,
    @SerializedName("civil_status")
    val civilStatus: String,
)