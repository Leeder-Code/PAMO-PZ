package com.example.pamo_pz

import TransactionAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pamo_pz.databinding.ActivityGoalDetailsBinding
import com.example.pamo_pz.databinding.ActivitySavingsGoalsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class GoalDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalDetailsBinding
    /**
     * Database instance for accessing goals data.
     */
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var filteredTransactions: List<Transaction>
    private val db by lazy { AppDatabase.getDatabase(this) }
    private lateinit var name: String
    private lateinit var description: String
    private var value by Delegates.notNull<Double>()
    private var target by Delegates.notNull<Double>()
    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("name").toString()
        description = intent.getStringExtra("description").toString()
        value = intent.getDoubleExtra("value", 0.00)
        target = intent.getDoubleExtra("target", 0.00)
        id = intent.getIntExtra("id", 0)

        binding.textViewGoalName.text = name
        binding.textViewProgress.text = getProgress()
        binding.textViewRemainingAmount.text = "$${(target - value)}"
        binding.textViewSavedAmount.text = "$$value"
        getTransactions()
        transactionAdapter = TransactionAdapter(listOf())
        binding.listViewTransactions.apply {
            layoutManager = LinearLayoutManager(this@GoalDetailsActivity)
            adapter = transactionAdapter
        }
        binding.textViewMonthlyDepositAmount.setOnClickListener{
            updateGoal()
        }
    }
    private fun getTransactions() {
        GlobalScope.launch {
            val transactions = db.transactionDao().getAll()
            filteredTransactions = transactions.filter{it.category == name}
            runOnUiThread {
                transactionAdapter.setData(filteredTransactions)
            }
        }
    }
    private fun updateGoal(){
        GlobalScope.launch {
            if(target<value+50){
                return@launch
            }
            value=value+50
            runOnUiThread {
                binding.textViewSavedAmount.text = value.toString()
                binding.textViewProgress.text = getProgress()
            }
            val goal = Goal(name, description, value, target, id)
            db.goalsDao().update(goal)
        }
    }
    private fun getProgress(): String{
        return "Progress: ${(value/target*100).toInt()}%"
    }
}