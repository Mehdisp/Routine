package ir.mehdisp.routine.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.mehdisp.routine.data.models.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun notes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun note(id: Int): LiveData<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun noteSync(id: Int): Note

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun delete(id: Int)

}