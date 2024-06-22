package com.example.pamo_pz

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pamo_pz.databinding.ActivityAddExpenseBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val months = resources.getStringArray(R.array.months_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMonth.adapter = adapter

        val categories = resources.getStringArray(R.array.categories_array)
        val adapterCategories = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerExpenseCategory.adapter = adapterCategories

        binding.buttonSubmitExpense.setOnClickListener {
            val name = binding.editTextExpenseName.text.toString()
            val date = binding.spinnerMonth.selectedItem.toString()
            val category = binding.spinnerExpenseCategory.selectedItem.toString()
            val amount = binding.editTextExpenseAmount.text.toString().toDoubleOrNull() ?: 0.0

            val transaction = Transaction(name, date, category, amount, false)
            insert(transaction)
        }
    }

    private fun insert(transaction: Transaction) {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "transactions").build()
        GlobalScope.launch {
            db.transactionDao().insertAll(transaction)
            finish()
        }
    }

}