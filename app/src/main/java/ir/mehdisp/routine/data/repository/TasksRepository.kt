package ir.mehdisp.routine.data.repository

import ir.mehdisp.routine.data.local.AppDatabase
import ir.mehdisp.routine.data.models.Task
import javax.inject.Inject

class TasksRepository @Inject constructor(database: AppDatabase) {

    private val dao = database.tasksDao()

    fun tasks() = dao.tasks()

    fun tasksForDate(date: String) = dao.tasksForDate(date)

    fun task(id: Int) = dao.task(id)

    suspend fun insert(task: Task) {
        val time = System.currentTimeMillis()
        task.updatedAt = time
        task.createdAt = time
        dao.insert(task)
    }

    suspend fun update(task: Task) {
        task.updatedAt = System.currentTimeMillis()
        dao.update(task)
    }

    suspend fun delete(task: Task) = dao.delete(task)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun setAsDone(id: Int, done: Boolean) = dao.setAsDone(id, done)

}
