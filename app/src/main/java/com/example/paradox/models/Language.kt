package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Language (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("level")
    val level: Int = 1
)