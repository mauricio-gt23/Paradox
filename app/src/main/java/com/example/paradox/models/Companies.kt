package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class Companies (
    @SerializedName("content")
    val companies: List<Company>
)