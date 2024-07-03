package com.example.pamo_pz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        goalsAdapter = GoalsAdapter(goals)
        binding.recyclerViewGoals.apply {
            layoutManager = LinearLayoutManager(this@GoalsActivity)
            adapter = goalsAdapter
        }
//        db = Room.databaseBuilder(this, AppDatabase::class.java, "test").build()

        // Set click listener for adding a new goal
        binding.buttonAddGoal.setOnClickListener {
            //            TODO: Add goal
            //            val intent = Intent(this, )
            //            startActivity(intent)
            println("Add goal")
        }
        // Fetch all goals from the database when the activity is created
        fetchAll()
    }

    /**
     * Fetches all goals from the database asynchronously.
     */
    private fun fetchAll() {
        GlobalScope.launch {
            goals = db.goalsDao().getAll()

            runOnUiThread {
                // goalsAdapter.setData(goals)

                // For now, mock data to visualize layout
                goalsAdapter.setData(
                    listOf(
                        Goal("Test", "Test opis", 480.0, 500.0),
                        Goal("test2", "test2 opis", 50.0, 250.0)
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data if any changes were made
        fetchAll()
        goalsAdapter.notifyDataSetChanged()

    }
}