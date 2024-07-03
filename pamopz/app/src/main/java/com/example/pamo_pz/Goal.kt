package com.example.pamo_pz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    val name: String,
    val description: String,
    val value: Double,
    val target: Double,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)