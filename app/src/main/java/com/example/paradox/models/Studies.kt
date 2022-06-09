package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Studies (
    @SerializedName("content")
    val studies: List<Study>
    )