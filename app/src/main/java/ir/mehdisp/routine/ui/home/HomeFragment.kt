package ir.mehdisp.routine.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ir.mehdisp.routine.R
import ir.mehdisp.routine.databinding.FragmentHomeBinding
import ir.mehdisp.routine.ui.base.BaseFragment
import ir.mehdisp.routine.ui.main.MainViewModel
import ir.mehdisp.routine.utils.Constants
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.models.CivilDate
import ir.mehdisp.persiancalendar.utils.DateConverter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val civilDate = CivilDate()
        val persianDate = DateConverter.civilToPersian(civilDate)
        val islamicDate = DateConverter.civilToIslamic(civilDate, -1)

        binding.day.text = persianDate.dayOfMonth.toString()
        binding.monthYear.text = buildString {
            appendLine(persianDate.monthName)
            append(persianDate.year)
        }

        binding.dayOfWeek.text = CalendarHandler.getDayInWeekName(persianDate)
        binding.islamic.text = islamicDate.toString()
        binding.civil.text = civilDate.toString()

        setupOnClicks()

        viewModel.tasksForDate(persianDate.toString2()).observe(viewLifecycleOwner) { notes ->
            val done = notes.filter { it.done }.size
            binding.tasks.text = String.format("%d/%d", notes.size, done)
        }

        syncCityWeather()
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == Constants.CITY_NAME) {
                viewModel.forecast()
                syncCityWeather()
            }
        }

        viewModel.weather.observe(viewLifecycleOwner) {
            val hava = it.data ?: return@observe
            binding.weatherTemp.text = "${hava.summary.temp}°"
            binding.weatherDesc.text = hava.summary.condition
            val icon = if (hava.summary.currentSymbol.isEmpty())
                hava.dayList.firstOrNull { i -> i.symbol.isNotEmpty() }?.symbol
            else hava.summary.currentSymbol

            binding.weatherIcon.load("file:///android_asset/weathericons/$icon.svg") {
                error(R.drawable.ic_error_outline_white_24dp)
            }
        }

    }

    private fun setupOnClicks() {
        binding.homeCalendar.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToCalendar()
            findNavController().navigate(action)
        }

        binding.tasksLayout.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToTasks(null)
            findNavController().navigate(action)
        }

        binding.weather.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToLocations()
            findNavController().navigate(action)
        }

        binding.notes.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToNotes()
            findNavController().navigate(action)
        }

        binding.news.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToNews()
            findNavController().navigate(action)
        }
    }

    private fun syncCityWeather() {
        binding.weatherTitle.text = String.format(
            "آب و هوای شهر %s (برای تغییر کلیک کنید)",
            sharedPreferences.getString(Constants.CITY_NAME, "کاشان")
        )
    }

}
