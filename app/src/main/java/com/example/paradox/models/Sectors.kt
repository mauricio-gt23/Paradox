package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Sectors (
    @SerializedName("content")
    val sectors: List<Sector>
)