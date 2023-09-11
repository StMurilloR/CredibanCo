package com.example.credibanco.data.database.dao

import androidx.room.*
import com.example.credibanco.data.database.entity.TransactionEntity

@Dao
interface TransactionDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM 'transaction' WHERE authorization = 1")
    suspend fun getAll():List<TransactionEntity>

    @Update
    suspend fun updateTransaction(vararg users: TransactionEntity)
}