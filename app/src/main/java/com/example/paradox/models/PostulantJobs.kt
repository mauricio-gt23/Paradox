package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class PostulantJobs (
    @SerializedName("content")
    val postulantJobs: List<PostulantJob>
        )