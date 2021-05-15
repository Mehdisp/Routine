package ir.mehdisp.routine.data.remote

import ir.mehdisp.routine.api.ParsijooService
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(
    private val service: ParsijooService
): BaseDataSource() {

    suspend fun searchCity(city: String) = getResult {
        service.citySearch(city)
    }

    suspend fun cityWeather(city: String) = getResult {
        service.cityWeather(city)
    }

    suspend fun latestNews(page: Int, count: Int) = getResult {
        service.latestNews(page, count)
    }

    suspend fun topNews(page: Int, count: Int) = getResult {
        service.topNews(page, count)
    }

}
