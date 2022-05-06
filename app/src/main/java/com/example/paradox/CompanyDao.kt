package com.example.paradox

import androidx.room.*

@Dao
interface CompanyDao {
    @Query("select * from company")
    fun getAll(): List<Company>

    @Insert
    fun insertCompany(vararg company: Company)

    @Delete
    fun deleteCompany(vararg company: Company)

    @Update
    fun updateCompany(vararg company: Company)
}