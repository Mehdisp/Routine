package ir.mehdisp.routine.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

class NewsResponse: BaseResponse<NewsResult>()

data class NewsResult(
    @field:Json(name = "items") val items: List<News>
)

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @field:Json(name = "picture") val picture: String,
    @field:Json(name = "category") val category: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "source") val source: String,
    @field:Json(name = "rutitr") val rutitr: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "date") val date: String
)
