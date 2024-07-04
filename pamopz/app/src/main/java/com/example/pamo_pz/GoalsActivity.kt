package com.example.pamo_pz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pamo_pz.databinding.ActivitySavingsGoalsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Activity for managing savings goals.
 */
class GoalsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavingsGoalsBinding
    private lateinit var goalsAdapter: GoalsAdapter

    /**
     * Database instance for accessing goals data.
     */
    private val db by lazy { AppDatabase.getDatabase(this) }

    /**
     * List of goals.
     */
    private var goals: List<Goal> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySavingsGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the goals adapter and bind it to the RecyclerView
        goalsAdapter = GoalsAdapter(goals, this)
        binding.recyclerViewGoals.apply {
            layoutManager = LinearLayoutManager(this@GoalsActivity)
            adapter = goalsAdapter
        }

        // Set click listener for adding a new goal
        binding.buttonAddGoal.setOnClickListener {
            val intent = Intent(this, AddGoalActivity::class.java)
            startActivity(intent)
        }

        // Fetch all goals from the database when the activity is created
        fetchAll()

        // Set up swipe-to-delete functionality
        setupSwipeToDelete()
    }

    /**
     * Fetches all goals from the database asynchronously.
     */
    private fun fetchAll() {
        GlobalScope.launch {
            goals = db.goalsDao().getAll()

            runOnUiThread {
                goalsAdapter.setData(goals)
            }
        }
    }

    /**
     * Sets up swipe-to-delete functionality for goals.
     */
    private fun setupSwipeToDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                target: androidx.recyclerview.widget.RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                removeGoal(goals[viewHolder.adapterPosition])
            }
        }
        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(binding.recyclerViewGoals)
    }

    /**
     * Removes a goal from the database.
     *
     * @param goal The goal to be removed.
     */
    private fun removeGoal(goal: Goal) {
        GlobalScope.launch {
            db.goalsDao().delete(goal)
            fetchAll()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data if any changes were made
        fetchAll()
        goalsAdapter.notifyDataSetChanged()
    }
}
