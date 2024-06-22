package com.example.pamo_pz

import TransactionAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.room.Room
import com.example.pamo_pz.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private var transactions: List<Transaction> = arrayListOf()
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionAdapter = TransactionAdapter(transactions)
        binding.listViewTransactions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = transactionAdapter
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                removeTransaction(transactions[viewHolder.adapterPosition])
            }
        }
        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(binding.listViewTransactions)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "transactions").build()

        // Set up buttons
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        binding.btnSetBudget.setOnClickListener {
            val intent = Intent(this, SetBudgetActivity::class.java)
            startActivity(intent)
        }

        binding.btnReports.setOnClickListener {
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
        }

        fetchAll()
//        updateTotalBalance()
//        updateBudgetAndExpense()
    }

    private fun updateTotalBalance() {
        val totalBalance = transactions.sumOf { if (it.isIncome) it.amount else -it.amount }
        binding.textViewTotalBalance.text = String.format("$%.2f", totalBalance)
    }

    private fun updateBudgetAndExpense() {
        val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
        val totalExpense = transactions.filter { !it.isIncome }.sumOf { it.amount }
        binding.textViewBudget.text = String.format("$%.2f", totalIncome)
        binding.textViewExpense.text = String.format("$%.2f", totalExpense)
    }

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()

            runOnUiThread {
                updateTotalBalance()
                updateBudgetAndExpense()
                transactionAdapter.setData(transactions)
            }
        }
    }

    fun removeTransaction(transaction: Transaction) {
        GlobalScope.launch {
            db.transactionDao().delete(transaction)
            fetchAll()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data if any changes were made
        fetchAll()
        transactionAdapter.notifyDataSetChanged()

    }
}