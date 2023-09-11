package com.example.credibanco.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.credibanco.data.database.entity.TransactionEntity
import com.example.credibanco.data.database.dao.TransactionDao

@Database(entities = [TransactionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val transactionDao: TransactionDao
}