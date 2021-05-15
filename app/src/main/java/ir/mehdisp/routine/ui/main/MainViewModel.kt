package ir.mehdisp.routine.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mehdisp.routine.data.models.*
import ir.mehdisp.routine.data.repository.ParsijooRepository
import ir.mehdisp.routine.data.repository.TasksRepository
import ir.mehdisp.routine.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val weatherRepository: ParsijooRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _weather = MutableLiveData<Resource<Hava>>()

    val weather: LiveData<Resource<Hava>>
        get() = _weather

    init {
        forecast()
    }

    fun tasksForDate(date: String) = tasksRepository.tasksForDate(date)

    fun forecast() = viewModelScope.launch(Dispatchers.IO) {
        val city = sharedPreferences.getString(Constants.CITY_NAME, "کاشان") ?: "کاشان"
        _weather.postValue(Resource.loading())

        val response = weatherRepository.cityWeather(city)

        val cityHava = response.data?.result?.hava

        if (cityHava == null)
            _weather.postValue(Resource.error(""))
        else
            _weather.postValue(Resource.success(cityHava))
    }

}