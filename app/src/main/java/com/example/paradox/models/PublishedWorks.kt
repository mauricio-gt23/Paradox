package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class PublishedWorks (
    @SerializedName("content")
    val publishedWorks: List<PublishedWork>
        )