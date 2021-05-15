package ir.mehdisp.routine.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

abstract class BasePagingSource<Value: Any>: PagingSource<Int, Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closest = state.closestPageToPosition(anchorPosition)
            return@let closest?.prevKey?.plus(1) ?: closest?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val position = params.key ?: 1
        return try {
            val repos = getRepos(position, params.loadSize)

            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    abstract suspend fun getRepos(position: Int, count: Int): List<Value>

}