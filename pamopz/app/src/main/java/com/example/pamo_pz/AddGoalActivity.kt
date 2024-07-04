package com.example.pamo_pz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo_pz.databinding.ActivityAddGoalBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * AddGoalActivity is responsible for adding a new savings goal.
 * Users can input goal details such as name, description, monthly amount set aside, and the target amount.
 */
class AddGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGoalBinding

    /**
     * Get database Singleton
     */
    private val db by lazy { AppDatabase.getDatabase(this) }

    /**
     * Called when the activity is starting. Initializes the UI components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this contains the data it most recently supplied. Otherwise, it is null.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSubmitGoal.setOnClickListener {
            handleSubmitGoal()
        }
    }

    /**
     * Handles the submission of the goal by creating a Goal object
     * and inserting it into the database.
     */
    private fun handleSubmitGoal() {
        val name = binding.editTextGoalName.text.toString()
        val description = binding.editTextGoalDescription.text.toString()
        val monthlyAmount = binding.editTextMonthlyAmount.text.toString().toDoubleOrNull() ?: 0.0
        val targetAmount = binding.editTextTargetAmount.text.toString().toDoubleOrNull() ?: 0.0

        val goal = Goal(name, description, 0.00, monthlyAmount, targetAmount)
        insert(goal)
    }

    /**
     * Inserts the goal into the database.
     *
     * @param goal The Goal object to be inserted into the database.
     */
    private fun insert(goal: Goal) {
        GlobalScope.launch {
            db.goalsDao().insertAll(goal)
            finish()
        }
    }
}
