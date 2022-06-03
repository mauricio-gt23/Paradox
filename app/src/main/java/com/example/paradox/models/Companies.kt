package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Companies (
    @SerializedName("content")
    val companies: List<Company> //todo: algo
)