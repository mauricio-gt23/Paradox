package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Skill (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = ""
)