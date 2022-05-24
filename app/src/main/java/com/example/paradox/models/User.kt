package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    val id: Int,

    @SerializedName("firstname")
    val firstname: String,

    @SerializedName("lastname")
    val lastname: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("number")
    val number: Int,

    @SerializedName("password")
    val password: String,

    @SerializedName("document")
    val document: Int,


)