package com.example.pamo_pz

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pamo_pz.databinding.ActivitySetBudgetBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * SetBudgetActivity is responsible for setting a budget for a selected month.
 * Users can input a budget amount and select a month from a dropdown menu.
 */
class SetBudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetBudgetBinding

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
        binding = ActivitySetBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner(binding.spinnerBudgetMonth)

        binding.buttonSubmitBudget.setOnClickListener {
            val budgetAmount = binding.editTextBudgetAmount.text.toString().toDoubleOrNull()
            val selectedMonth = binding.spinnerBudgetMonth.selectedItem.toString()

            if (budgetAmount != null && selectedMonth.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "Budget of $$budgetAmount set for $selectedMonth",
                    Toast.LENGTH_SHORT
                ).show()
                val transaction = Transaction("Budget", selectedMonth, "Income", budgetAmount, true)
                setBudget(transaction)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Please enter a valid amount and select a month",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Sets up the spinner with month options from a predefined array resource.
     *
     * @param spinner The Spinner UI component to be set up.
     */
    private fun setupSpinner(spinner: Spinner) {
        val months = resources.getStringArray(R.array.months_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    /**
     * Inserts the budget transaction into the database.
     *
     * @param transaction The Transaction object representing the budget to be set.
     */
    private fun setBudget(transaction: Transaction) {
        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }
}
