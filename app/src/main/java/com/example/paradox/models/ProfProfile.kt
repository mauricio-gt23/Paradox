package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class ProfProfile (
    @SerializedName("id")
    val id: Int,
    @SerializedName("ocupation")
    val ocupation: String,
    @SerializedName("video")
    val video: String,
    @SerializedName("description")
    val description: String,
)