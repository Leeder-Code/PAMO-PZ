package com.example.pamo_pz

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * GoalDao provides methods for performing database operations on the goals table.
 */
@Dao
interface GoalDao {
    /**
     * Retrieves all goals from the goals table.
     *
     * @return A list of all goals.
     */
    @Query("SELECT * from goals")
    fun getAll(): List<Goal>
    /**
     * Insert goal into the goals table.
     *
     * @param goal Vararg parameter of goals to be inserted.
     */
    @Insert
    fun insertAll(vararg goal: Goal)
    /**
     * Deletes a goal from the goals table.
     *
     * @param goal The goal to be deleted.
     */
    @Delete
    fun delete(goal: Goal)
    /**
     * Update goal in the goals table.
     *
     * @param goal Vararg parameter of goals to be updated.
     */
    @Update
    fun update(vararg goal: Goal)
}