package ir.mehdisp.routine.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mehdisp.routine.data.models.Note
import ir.mehdisp.routine.data.repository.NotesRepository
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    val notes = repository.notes()

    fun note(id: Int) = repository.note(id)

    fun insert(title: String, body: String) = liveData {
        val note = Note(0, title, body, System.currentTimeMillis(), System.currentTimeMillis())
        try {
            repository.insert(note)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }

    fun update(id: Int, title: String, body: String) = liveData {
        try {
            val note = repository.noteSync(id).apply {
                this.title = title
                this.body = body
                this.updatedAt = System.currentTimeMillis()
            }
            repository.update(note)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }

    fun delete(id: Int) = liveData {
        try {
            repository.delete(id)
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }

    fun saveNote(note: Note) = liveData {
        note.updatedAt = System.currentTimeMillis()
        try {
            if (note.id == 0) {
                repository.insert(note)
                emit(true)
            } else {
                repository.update(note)
                emit(true)
            }
        } catch (e: Exception) {
            emit(false)
        }
    }

}
