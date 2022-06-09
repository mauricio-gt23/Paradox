package com.example.paradox.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Skills (
    @SerializedName("content")
    val skills: List<Skill>
) : Parcelable