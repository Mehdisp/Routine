package ir.mehdisp.routine.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ir.mehdisp.routine.data.paging.NewsPagingSource
import ir.mehdisp.routine.data.remote.ApiRemoteDataSource
import ir.mehdisp.routine.utils.performRemoteGetOperation
import javax.inject.Inject

class ParsijooRepository @Inject constructor(
    private val remote: ApiRemoteDataSource
) {

    suspend fun cityWeather(city: String) = remote.cityWeather(city)

    fun searchCities(city: String) = performRemoteGetOperation {
        remote.searchCity(city)
    }

    fun latestNews() = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { NewsPagingSource(true, remote) }
    ).flow

    fun topNews() = Pager(
        config = PagingConfig(10),
        pagingSourceFactory = { NewsPagingSource(false, remote) }
    ).flow

}