package com.example.pamo_pz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    val name: String,
    val month: String,
    val category: String,
    val amount: Double,
    val isIncome: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)