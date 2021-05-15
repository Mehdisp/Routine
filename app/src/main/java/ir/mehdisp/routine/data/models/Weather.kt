package ir.mehdisp.routine.data.models

import com.squareup.moshi.Json

class WeatherCitiesResponse : BaseResponse<WeatherCities>()

data class WeatherCities(
    @field:Json(name = "cityList") val cityList: List<City>
)

class CityWeatherResponse: BaseResponse<CityHava>()

data class CityHava(
    @field:Json(name = "hava") val hava: Hava
)

data class Hava(
    @field:Json(name = "city") val city: String,
    @field:Json(name = "state") val state: String,
    @field:Json(name = "summary") val summary: HavaSummary,
    @field:Json(name = "dayList") val dayList: List<City>
)

data class HavaSummary(
    @field:Json(name = "condition") val condition: String,
    @field:Json(name = "windSpeed") val windSpeed: String,
    @field:Json(name = "windDir") val windDir: String,
    @field:Json(name = "humidity") val humidity: String,
    @field:Json(name = "pressure") val pressure: String,
    @field:Json(name = "date") val date: String,
    @field:Json(name = "update") val update: String,
    @field:Json(name = "temp") val temp: String,
    @field:Json(name = "precipitation") val precipitation: String,
    @field:Json(name = "currentSymbol") val currentSymbol: String
)

data class City(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "max") val max: String,
    @field:Json(name = "min") val min: String,
    @field:Json(name = "symbol") val symbol: String,
    @field:Json(name = "condition") val condition: String
)