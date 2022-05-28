package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Languages (
    @SerializedName("content")
    val languages: List<Language>
    )