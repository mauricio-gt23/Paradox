package com.example.paradox.models

import com.google.gson.annotations.SerializedName

class Study (
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("degree")
    val degree: Int = 1
){
    override fun toString(): String {
        return name
    }
}