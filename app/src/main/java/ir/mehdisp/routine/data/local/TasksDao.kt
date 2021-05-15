package ir.mehdisp.routine.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.mehdisp.routine.data.models.Task

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks")
    fun tasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE for_date = :date")
    fun tasksForDate(date: String): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun task(id: Int): LiveData<Task>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Query("UPDATE tasks SET done = :done WHERE id = :id")
    suspend fun setAsDone(id: Int, done: Boolean)

}