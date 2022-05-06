package com.example.paradox

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "ruc")
    var ruc: String?,
    @ColumnInfo(name = "address")
    var address: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "logo")
    var logo: String?
)