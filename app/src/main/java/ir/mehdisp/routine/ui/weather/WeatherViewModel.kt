package ir.mehdisp.routine.ui.weather

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mehdisp.routine.data.repository.ParsijooRepository
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: ParsijooRepository
) : ViewModel() {

    fun search(query: String) = repository.searchCities(query)

}
