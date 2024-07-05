package com.example.pamo_pz

import TransactionAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pamo_pz.databinding.ActivityGoalDetailsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlin.properties.Delegates

open class GoalDetailsActivity : AppCompatActivity() {
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
    private var monthlyDeposit by Delegates.notNull<Double>()
    private var target by Delegates.notNull<Double>()
    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("name").toString()
        description = intent.getStringExtra("description").toString()
        value = intent.getDoubleExtra("value", 0.00)
        monthlyDeposit = intent.getDoubleExtra("monthlyDeposit", 0.00)
        target = intent.getDoubleExtra("target", 0.00)
        id = intent.getIntExtra("id", 0)

        binding.textViewGoalName.text = name
        binding.textViewProgress.text = getProgress(value, target)
        binding.textViewRemainingAmount.text = getRemainingValue(target, value)
        binding.textViewSavedAmount.text = "$$value"
        binding.textViewMonthsLeft.text = getMonthsLeft(target, value, monthlyDeposit)
        binding.progressBarGoal.setProgress((value/target*100).toInt())

        getTransactions()
        transactionAdapter = TransactionAdapter(listOf())
        binding.listViewTransactions.apply {
            layoutManager = LinearLayoutManager(this@GoalDetailsActivity)
            adapter = transactionAdapter
        }
        binding.buttonMonthlyDeposit.setOnClickListener {
            updateGoal()
        }
    }

    private fun getTransactions() {
        GlobalScope.launch {
            val transactions = db.transactionDao().getAll()
            filteredTransactions = transactions.filter { it.category == name }
            runOnUiThread {
                transactionAdapter.setData(filteredTransactions)
            }
        }
    }

    fun updateGoal() {
        GlobalScope.launch {
            if (target < value + monthlyDeposit) {
                return@launch
            }
            value = value + monthlyDeposit
            runOnUiThread {
                binding.textViewSavedAmount.text = "$${value}"
                binding.textViewProgress.text = getProgress(target, value)
                binding.textViewMonthsLeft.text = getMonthsLeft(target, value, monthlyDeposit)
                binding.textViewRemainingAmount.text = getRemainingValue(target, value)
                binding.progressBarGoal.setProgress((value/target*100).toInt())
            }
            val goal = Goal(name, description, value, monthlyDeposit, target, id)
            db.goalsDao().update(goal)
            val currentDate = LocalDate.now()
            val currentMonthFullName =
                currentDate.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            db.transactionDao().insertAll(
                Transaction(
                    "Saving goal $name",
                    currentMonthFullName,
                    name,
                    monthlyDeposit
                )
            )
            getTransactions()
        }
    }

    fun getProgress(target: Double, value: Double): String {
        return "Progress: ${(value / target * 100).toInt()}%"
    }

    fun getMonthsLeft(target: Double, value: Double, monthlyDeposit: Double): String {
        return "${((target-value) / monthlyDeposit).toInt()} months"
    }
    fun getRemainingValue(target: Double, value: Double): String{
        return "$${(target - value)}"
    }
}