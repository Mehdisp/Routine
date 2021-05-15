package ir.mehdisp.routine.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "body") var body: String,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
    @ColumnInfo(name = "created_at") var createdAt: Long
) : Parcelable {
    companion object {
        val EMPTY: Note
            get() = Note(0, "", "", System.currentTimeMillis(), System.currentTimeMillis())
    }
}
