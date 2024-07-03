package com.example.pamo_pz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * AppDatabase is the main database of the application, which holds the transactions table.
 * It provides access to the DAO (Data Access Object) for performing database operations.
 *
 * @property transactionDao Provides access to the transaction data access object.
 */
@Database(entities = [Transaction::class, Goal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Returns the DAO for accessing transaction data.
     *
     * @return The TransactionDao for performing database operations on transactions.
     */
    abstract fun transactionDao(): TransactionDao
    /**
     * Returns the DAO for accessing goals data.
     *
     * @return The GoalDao for performing database operations on goals.
     */
    abstract fun goalsDao(): GoalDao
    /**
     * Create singleton
     */
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "transactions_and_goals"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}