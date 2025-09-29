package com.example.paradox.models

import com.google.gson.annotations.SerializedName

data class Sector (
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String
) {
    override fun toString(): String {
        return name
    }
}