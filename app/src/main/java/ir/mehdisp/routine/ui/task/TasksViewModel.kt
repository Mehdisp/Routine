package ir.mehdisp.routine.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mehdisp.routine.data.models.Task
import ir.mehdisp.routine.data.repository.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
): ViewModel() {

    fun tasksForDate(date: String) = tasksRepository.tasksForDate(date)

    fun addTask(task: Task) = viewModelScope.launch {
        tasksRepository.insert(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        tasksRepository.update(task)
    }

    fun setAsDone(id: Int, done: Boolean) = viewModelScope.launch {
        tasksRepository.setAsDone(id, done)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        tasksRepository.delete(task)
    }

}