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

class ReportsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportsBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private var transactions: List<Transaction> = listOf()
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(this, AppDatabase::class.java, "transactions").build()


        transactionAdapter = TransactionAdapter(transactions)
        binding.listViewTransactions.apply {
            layoutManager = LinearLayoutManager(this@ReportsActivity)
            adapter = transactionAdapter
        }

        fetchAll()

        // Setup spinners with data
        setupCategoryFilter()
        setupMonthFilter()
    }

    private fun setupCategoryFilter() {
        val categories =
            arrayOf("All", "Income") + resources.getStringArray(R.array.categories_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilterCategory.adapter = adapter

        // Handle filter changes
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

    private fun setupMonthFilter() {
        val months = resources.getStringArray(R.array.months_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilterMonth.adapter = adapter

        // Handle filter changes
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

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()

            runOnUiThread {
                transactionAdapter.setData(transactions)
                filterTransactions()
            }
        }
    }

    private fun updateTotalSum(filteredTransactions: List<Transaction>) {
        val expenseTransactions = filteredTransactions.filter {
            !it.isIncome
        }
        val totalSum = expenseTransactions.sumOf { it.amount }
        binding.textViewTotalSum.text = String.format("$%.2f", totalSum)
    }
}