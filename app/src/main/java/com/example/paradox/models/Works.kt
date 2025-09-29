package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Works (
    @SerializedName("content")
    val works: List<Work>
    )