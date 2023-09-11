package com.example.credibanco.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int ?= null,
    @ColumnInfo(name = "id_Transaction") val idTransaction: String?,
    @ColumnInfo(name = "commerce_Code") val commerceCode: String?,
    @ColumnInfo(name = "terminal_Code") val terminalCode: String?,
    @ColumnInfo(name = "amount") val amount: String?,
    @ColumnInfo(name = "card") val card: String?,
    @ColumnInfo(name = "receipt_Id") val receiptId: String?,
    @ColumnInfo(name = "rrn") val rrn: String?,
    @ColumnInfo(name = "authorization") val authorization: Boolean?,
    @ColumnInfo(name = "annulation") var annulation: Boolean?,
)



