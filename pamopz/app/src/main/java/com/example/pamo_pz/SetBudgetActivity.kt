package com.example.pamo_pz

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pamo_pz.databinding.ActivitySetBudgetBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SetBudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetBudgetBinding

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

    private fun setupSpinner(spinner: Spinner) {
        val months = resources.getStringArray(R.array.months_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setBudget(transaction: Transaction) {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "transactions").build()
        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }
}