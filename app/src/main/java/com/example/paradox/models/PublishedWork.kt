package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class PublishedWork (

    @SerializedName("id")
    val id: Int?,

    @SerializedName("begin_date_offer")
    val subtitle: String,

    @SerializedName("title")
    val job: String,

    @SerializedName("type")
    val time: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("direction")
    val address: String,

    @SerializedName("salary")
    val salary: Int?
    )