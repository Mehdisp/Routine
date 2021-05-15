package ir.mehdisp.routine.api

import ir.mehdisp.routine.data.models.CityWeatherResponse
import ir.mehdisp.routine.data.models.NewsResponse
import ir.mehdisp.routine.data.models.WeatherCitiesResponse
import ir.mehdisp.routine.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ParsijooService {

    @Headers("api-key: ${Constants.NEWS_TOKEN}")
    @GET("news?type=top-latest&mode=latest")
    suspend fun latestNews(
        @Query("page") page: Int,
        @Query("nrpp") pageCount: Int
    ): Response<NewsResponse>

    @Headers("api-key: ${Constants.NEWS_TOKEN}")
    @GET("news?type=top-latest&mode=top")
    suspend fun topNews(
        @Query("page") page: Int,
        @Query("nrpp") pageCount: Int
    ): Response<NewsResponse>

    @Headers("api-key: ${Constants.WEATHER_TOKEN}")
    @GET("weather?type=search")
    suspend fun citySearch(@Query("city") city: String): Response<WeatherCitiesResponse>

    @Headers("api-key: ${Constants.WEATHER_TOKEN}")
    @GET("weather?type=search")
    suspend fun cityWeather(@Query("city") city: String): Response<CityWeatherResponse>

}