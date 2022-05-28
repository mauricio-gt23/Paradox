package com.example.paradox.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ProfProfile (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("ocupation")
    val ocupation: String = "",
    @SerializedName("video")
    val video: String = "",
    @SerializedName("description")
    val description: String = "",
)  : Parcelable