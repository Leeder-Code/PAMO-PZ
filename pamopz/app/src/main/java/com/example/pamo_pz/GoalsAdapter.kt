package com.example.pamo_pz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pamo_pz.databinding.ItemSavingsGoalBinding

/**
 * Adapter for displaying savings goals in a RecyclerView.
 *
 * @property goals List of goals to display.
 */
class GoalsAdapter(private var goals: List<Goal>, private val context: Context) :
    RecyclerView.Adapter<GoalsAdapter.GoalViewHolder>() {
    /**
     * ViewHolder for displaying individual goals.
     *
     * @param binding View binding for the item layout.
     */
    inner class GoalViewHolder(private val binding: ItemSavingsGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds a goal to the ViewHolder.
         *
         * @param goal The goal to bind.
         */
        @SuppressLint("SetTextI18n")
        fun bind(goal: Goal) {
            binding.textViewGoalName.text = goal.name
            binding.textViewGoalDescription.text = goal.description
            binding.textViewAmountSaved.text = "$${goal.value} / $${goal.target}"
            binding.progressBarGoal.setProgress(((goal.value / goal.target) * 100).toInt())
            binding.root.setOnClickListener{
                val intent = Intent(context, GoalDetailsActivity::class.java)
                intent.putExtra("name", goal.name)
                intent.putExtra("description", goal.description)
                intent.putExtra("value", goal.value)
                intent.putExtra("monthlyDeposit", goal.monthlyDeposit)
                intent.putExtra("target", goal.target)
                intent.putExtra("id", goal.id)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding =
            ItemSavingsGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    /**
     * Updates the list of goals and notifies the adapter of the change.
     *
     * @param newGoals The new list of goals to update.
     */
    fun updateGoals(newGoals: List<Goal>) {
        goals = newGoals
        notifyDataSetChanged()
    }

    /**
     * Sets the data for the adapter with a new list of goals and notifies the adapter of the change.
     *
     * @param goals The list of goals to set.
     */
    fun setData(goals: List<Goal>) {
        this.goals = goals
        notifyDataSetChanged()
    }
}