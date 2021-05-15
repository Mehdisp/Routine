package ir.mehdisp.routine.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "body") var body: String,
    @ColumnInfo(name = "done") var done: Boolean,
    @ColumnInfo(name = "color") var color: Int,
    @ColumnInfo(name = "for_date") var forDate: String,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
    @ColumnInfo(name = "created_at") var createdAt: Long,
) : Parcelable {
    companion object {
        val EMPTY: Task
            get() = Task(0, "", false, 0, "", 0L, 0L)
    }
}