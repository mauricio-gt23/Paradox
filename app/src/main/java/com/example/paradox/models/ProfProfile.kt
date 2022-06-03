package com.example.paradox.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class ProfProfile (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("ocupation")
    var ocupation: String = "",
    @SerializedName("video")
    var video: String = "",
    @SerializedName("description")
    var description: String = "",
)  : Parcelable