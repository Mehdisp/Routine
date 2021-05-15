package ir.mehdisp.routine.ui.task

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.routine.R
import ir.mehdisp.routine.data.models.Task
import ir.mehdisp.routine.databinding.LayoutTaskItemBinding
import ir.mehdisp.routine.utils.inflate

class TasksAdapter : ListAdapter<Task, TasksAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {

            override fun areItemsTheSame(old: Task, new: Task): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Task, new: Task): Boolean {
                return old.done == new.done && old.updatedAt == new.updatedAt
            }
        }
    }

    private var onItemClick: (Task) -> Unit = {}
    private var onDeleteClick: (Task) -> Unit = {}
    private var onCheckBoxClick: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_task_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position) ?: return
        holder.bind(task)
    }

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        this.onItemClick = listener
    }

    fun setOnCheckBoxClickListener(listener: (Task) -> Unit) {
        this.onCheckBoxClick = listener
    }

    fun setOnDeleteClickListener(listener: (Task) -> Unit) {
        this.onDeleteClick = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutTaskItemBinding.bind(view)
        fun bind(task: Task) {
            binding.colorView.setBackgroundColor(task.color)
            binding.text.text = task.body

            binding.checkbox.setImageResource(
                if (task.done)
                    R.drawable.ic_round_check_circle_24
                else
                    R.drawable.ic_round_circle_24
            )

            binding.root.setOnClickListener { onItemClick(task) }
            binding.checkbox.setOnClickListener { onCheckBoxClick(task) }
            binding.delete.setOnClickListener { onDeleteClick(task) }
        }
    }

}
