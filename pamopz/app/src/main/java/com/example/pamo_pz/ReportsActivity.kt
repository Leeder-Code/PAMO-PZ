package com.example.pamo_pz

import TransactionAdapter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.pamo_pz.databinding.ActivityReportsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * ReportsActivity is responsible for displaying a filtered list of transactions
 * and showing reports based on selected filters such as category and month.
 */
class ReportsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportsBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private var transactions: List<Transaction> = listOf()
    /**
     * Get database Singleton
     */
    private val db by lazy{ AppDatabase.getDatabase(this)}

    /**
     * Called when the activity is starting. Initializes the UI and database.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /**
         * Initialize the transaction adapter with the list of transactions
         */
        transactionAdapter = TransactionAdapter(transactions)
        binding.listViewTransactions.apply {
            layoutManager = LinearLayoutManager(this@ReportsActivity)
            adapter = transactionAdapter
        }
        /**
         * Fetch all transactions from the database
         */
        fetchAll()

        /**
         * Setup spinners with data
         */
        setupCategoryFilter()
        setupMonthFilter()
    }

    /**
     * Sets up the category filter spinner with data and handles selection changes.
     */
    private fun setupCategoryFilter() {
        val categories =
            arrayOf("All", "Income") + resources.getStringArray(R.array.categories_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilterCategory.adapter = adapter

        /**
         * Handle filter changes
         */
        binding.spinnerFilterCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterTransactions()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }
    }
    /**
     * Sets up the month filter spinner with data and handles selection changes.
     */
    private fun setupMonthFilter() {
        val months = resources.getStringArray(R.array.months_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilterMonth.adapter = adapter

        /**
         * Handle filter changes
         */
        binding.spinnerFilterMonth.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterTransactions()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Do nothing
                }
            }
    }
    /**
     * Filters the transactions based on the selected category and month.
     */
    private fun filterTransactions() {
        val selectedCategory = binding.spinnerFilterCategory.selectedItem.toString()
        val selectedMonth = binding.spinnerFilterMonth.selectedItem.toString()

        val filteredTransactions = transactions.filter {
            (selectedCategory == "All" || it.category == selectedCategory) &&
                    (selectedMonth == "All" || it.month.startsWith((selectedMonth)))
        }

        transactionAdapter.updateTransactions(filteredTransactions)
        updateTotalSum(filteredTransactions)
    }

    /**
     * Fetches all transactions from the database and updates the UI.
     */
    private fun fetchAll() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()

            runOnUiThread {
                transactionAdapter.setData(transactions)
                filterTransactions()
            }
        }
    }
    /**
     * Updates the total sum of expenses displayed in the UI.
     *
     * @param filteredTransactions The list of filtered transactions.
     */
    private fun updateTotalSum(filteredTransactions: List<Transaction>) {
        val expenseTransactions = filteredTransactions.filter {
            !it.isIncome
        }
        val totalSum = expenseTransactions.sumOf { it.amount }
        binding.textViewTotalSum.text = String.format("$%.2f", totalSum)
    }
}