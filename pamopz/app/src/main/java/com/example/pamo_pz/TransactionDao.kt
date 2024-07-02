package com.example.pamo_pz

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * TransactionDao provides methods for performing database operations on the transactions table.
 */
@Dao
interface TransactionDao {
    /**
     * Retrieves all transactions from the transactions table.
     *
     * @return A list of all transactions.
     */
    @Query("SELECT * from transactions")
    fun getAll(): List<Transaction>
    /**
     * Insert transaction into the transactions table.
     *
     * @param transaction Vararg parameter of transactions to be inserted.
     */
    @Insert
    fun insertAll(vararg transaction: Transaction)
    /**
     * Deletes a transaction from the transactions table.
     *
     * @param transaction The transaction to be deleted.
     */
    @Delete
    fun delete(transaction: Transaction)
    /**
     * Update transaction in the transactions table.
     *
     * @param transaction Vararg parameter of transactions to be updated.
     */
    @Update
    fun update(vararg transaction: Transaction)
}