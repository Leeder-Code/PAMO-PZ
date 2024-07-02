package com.example.pamo_pz

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * AppDatabase is the main database of the application, which holds the transactions table.
 * It provides access to the DAO (Data Access Object) for performing database operations.
 *
 * @property transactionDao Provides access to the transaction data access object.
 */
@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the DAO for accessing transaction data.
     *
     * @return The TransactionDao for performing database operations on transactions.
     */
    abstract fun transactionDao(): TransactionDao
}