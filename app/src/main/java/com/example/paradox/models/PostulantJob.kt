package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class PostulantJob (

    @SerializedName("directionJobOffer")
    val company: String,

    @SerializedName("begin_date_offerJobOffer")
    val subtitle: String,

    @SerializedName("titleJobOffer")
    val job: String,

    @SerializedName("typeJobOffer")
    val time: String,

    @SerializedName("aceppt")
    val status: Boolean
        )