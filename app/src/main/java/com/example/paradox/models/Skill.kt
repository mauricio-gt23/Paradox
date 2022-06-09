package com.example.paradox.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Skill (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = ""
) : Parcelable