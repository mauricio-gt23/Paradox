package com.example.paradox.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Company (
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("logo")
    val logo: String,

    @SerializedName("ruc")
    val ruc: Int,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName("idEmployeer")
    val idEmployeer: Int,

    @SerializedName("idSector")
    val idSector: Int,

    @SerializedName("nameSector")
    val nameSector: String
)

data class RequestCompany (
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("logo")
    val logo: String,

    @SerializedName("ruc")
    val ruc: Int,

    @SerializedName("direccion")
    val direccion: String
)