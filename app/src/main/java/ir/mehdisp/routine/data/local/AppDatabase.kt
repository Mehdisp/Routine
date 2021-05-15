package ir.mehdisp.routine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.mehdisp.routine.data.models.Note
import ir.mehdisp.routine.data.models.Task

@Database(
    entities = [
        Task::class, Note::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    abstract fun notesDao(): NotesDao
}