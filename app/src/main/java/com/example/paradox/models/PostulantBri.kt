package com.example.paradox.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class PostulantBri (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("firstname")
    val firstName: String = "",
    @SerializedName("lastname")
    val lastName: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("number")
    val number: Int = 1,
    @SerializedName("password")
    val password: String = "",
    @SerializedName("document")
    val document: String = "",
    @SerializedName("civil_status")
    val civilStatus: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("other")
    val other: String = "",
) : Parcelable