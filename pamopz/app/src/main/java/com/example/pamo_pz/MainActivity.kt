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

/**
 * MainActivity is the main activity of the application, which displays a list of transactions,
 * allows adding new transactions, setting a budget, and go to reports. The activity also manages the transactions database.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private var transactions: List<Transaction> = arrayListOf()

    /**
     * Get database Singleton
     */
    private val db by lazy { AppDatabase.getDatabase(this) }

    /**
     * Called when the activity is starting. Initializes the UI and database.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Initialize view binding
         */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Initialize the transaction adapter with the list of transactions
         */
        transactionAdapter = TransactionAdapter(transactions)
        binding.listViewTransactions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = transactionAdapter
        }

        /**
         * Create item touch helper to delete transaction on swipe right
         */

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

        /**
         * Setup buttons and their click listeners
         */
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
        binding.btnSavings.setOnClickListener {
            val intent = Intent(this, GoalsActivity::class.java)
            startActivity(intent)
        }
        /**
         * Fetch all transactions from the database
         */
        fetchAll()
    }

    /**
     * Updates the total balance displayed in the UI.
     */
    private fun updateTotalBalance() {
        val totalBalance = transactions.sumOf { if (it.isIncome) it.amount else -it.amount }
        binding.textViewTotalBalance.text = String.format("$%.2f", totalBalance)
    }

    /**
     * Updates the budget and expense amounts displayed in the UI.
     */
    private fun updateBudgetAndExpense() {
        val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
        val totalExpense = transactions.filter { !it.isIncome }.sumOf { it.amount }
        binding.textViewBudget.text = String.format("$%.2f", totalIncome)
        binding.textViewExpense.text = String.format("$%.2f", totalExpense)
    }

    /**
     * Fetches all transactions from the database and updates the UI.
     */
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

    /**
     * Removes a transaction from the database.
     *
     * @param transaction The transaction to be removed.
     */
    fun removeTransaction(transaction: Transaction) {
        GlobalScope.launch {
            db.transactionDao().delete(transaction)
            fetchAll()
        }
    }

    /**
     * Called when the activity will start interacting with the user.
     */
    override fun onResume() {
        super.onResume()
        // Refresh data if any changes were made
        fetchAll()
        transactionAdapter.notifyDataSetChanged()

    }
}