package com.example.pamo_pz

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pamo_pz.databinding.ActivityAddExpenseBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * AddExpenseActivity is responsible for adding a new expense transaction.
 * Users can input expense details such as name, date, category, and amount.
 */
class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding
    /**
     * Get database Singleton
     */
    private val db by lazy{ AppDatabase.getDatabase(this)}

    /**
     * Called when the activity is starting. Initializes the UI components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMonthSpinner()
        setupCategorySpinner()

        binding.buttonSubmitExpense.setOnClickListener {
            handleSubmitExpense()
        }
    }

    /**
     * Sets up the spinner with month options from a predefined array resource.
     */
    private fun setupMonthSpinner() {
        val months = resources.getStringArray(R.array.months_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMonth.adapter = adapter
    }

    /**
     * Sets up the spinner with category options from a predefined array resource.
     */
    private fun setupCategorySpinner() {
        val categories = resources.getStringArray(R.array.categories_array)
        val adapterCategories = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerExpenseCategory.adapter = adapterCategories
    }

    /**
     * Handles the submission of the expense by creating a Transaction object
     * and inserting it into the database.
     */
    private fun handleSubmitExpense() {
        val name = binding.editTextExpenseName.text.toString()
        val date = binding.spinnerMonth.selectedItem.toString()
        val category = binding.spinnerExpenseCategory.selectedItem.toString()
        val amount = binding.editTextExpenseAmount.text.toString().toDoubleOrNull() ?: 0.0

        val transaction = Transaction(name, date, category, amount, false)
        insert(transaction)
    }

    /**
     * Inserts the transaction into the database.
     *
     * @param transaction The Transaction object to be inserted into the database.
     */
    private fun insert(transaction: Transaction) {
        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }
}
