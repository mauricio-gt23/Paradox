package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class Postulantz (
    @SerializedName("content")
    val postulantz: List<Postul>
        )