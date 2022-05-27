package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Skills (
    @SerializedName("content")
    val skills: List<Skill>
)