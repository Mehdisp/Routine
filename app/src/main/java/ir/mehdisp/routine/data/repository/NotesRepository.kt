package ir.mehdisp.routine.data.repository

import ir.mehdisp.routine.data.local.AppDatabase
import ir.mehdisp.routine.data.models.Note
import javax.inject.Inject

class NotesRepository @Inject constructor(database: AppDatabase) {

    private val notesDao = database.notesDao()

    fun notes() = notesDao.notes()

    fun note(id: Int) = notesDao.note(id)

    suspend fun noteSync(id: Int) = notesDao.noteSync(id)

    suspend fun insert(note: Note) = notesDao.insert(note)

    suspend fun update(note: Note) = notesDao.update(note)

    suspend fun delete(id: Int) = notesDao.delete(id)


}