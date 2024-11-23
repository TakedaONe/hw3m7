package com.projectx.homework3_7month.presentation.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.projectx.homework3_7month.databinding.ItemTaskBinding
import com.projectx.homework3_7month.presentation.model.TaskUI

class TaskAdapter(
    private var taskList: List<TaskUI>,
    private val onItemClick: (id: Int) -> Unit,
    private val onTaskDeleted: (TaskUI) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(taskList[position].id)
                }
            }
        }

        fun bind(taskUI: TaskUI) = with(binding) {
            tvTask.text = taskUI.taskName
            tvDate.text = taskUI.taskDate
        }
    }

    fun updateTasks(newTasks: List<TaskUI>) {
        val diffResult = DiffUtil.calculateDiff(TaskDiffCallback(taskList, newTasks))
        taskList = newTasks
        diffResult.dispatchUpdatesTo(this)
    }

    fun attachSwipeToRecyclerView(recyclerView: RecyclerView) {
        val itemTouchHelper = createItemTouchHelper(onTaskDeleted)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun createItemTouchHelper(onTaskDeleted: (TaskUI) -> Unit): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val taskToDelete = taskList[position]
                onTaskDeleted(taskToDelete)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    private class TaskDiffCallback(
        private val oldList: List<TaskUI>,
        private val newList: List<TaskUI>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
