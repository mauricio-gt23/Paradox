package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class Work (

    @SerializedName("direction")
    val company: String,

    @SerializedName("begin_date_offer")
    val subtitle: String,

    @SerializedName("title")
    val job: String,

    @SerializedName("type")
    val time: String,

    @SerializedName("description")
    val info: String,

    )