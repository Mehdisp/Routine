package ir.mehdisp.routine.ui.task

import androidx.recyclerview.widget.DiffUtil
import ir.mehdisp.routine.data.models.Task

class TaskDiffUtil : DiffUtil.ItemCallback<Task>() {

    companion object {
        val instance = TaskDiffUtil()
    }

    override fun areItemsTheSame(old: Task, new: Task): Boolean {
        return old == new
    }

    override fun areContentsTheSame(old: Task, new: Task): Boolean {
        return old == new
    }
}
