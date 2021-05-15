package ir.mehdisp.routine.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.mehdisp.routine.databinding.FragmentCalendarBinding
import ir.mehdisp.routine.ui.base.BaseFragment
import ir.mehdisp.persiancalendar.interfaces.PersianCalendarCallback
import ir.mehdisp.persiancalendar.models.CivilDate
import ir.mehdisp.persiancalendar.models.Day
import ir.mehdisp.persiancalendar.models.PersianDate
import ir.mehdisp.persiancalendar.utils.DateConverter

@AndroidEntryPoint
class CalendarFragment : BaseFragment(), PersianCalendarCallback {

    private lateinit var binding: FragmentCalendarBinding
    private val todayDate = DateConverter.civilToPersian(CivilDate())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.today.hide()
        binding.calendar.setCallback(this)

        binding.month.text = todayDate.monthName

        binding.nextMonth.setOnClickListener {
            binding.calendar.goToNextMonth()
        }

        binding.prevMonth.setOnClickListener {
            binding.calendar.goToPreviousMonth()
        }

        binding.today.setOnClickListener {
            binding.calendar.goToToday()
        }

    }

    override fun onEventUpdate() {

    }

    override fun onDaysClick(day: Day) {
        CalendarDayClickOptions(day).show(requireActivity())
    }

    override fun onDaysLongClick(day: Day) {

    }

    override fun onMonthChanged(date: PersianDate) {
        binding.month.text = date.monthName

        if (date.month == todayDate.month && date.year == todayDate.year)
            binding.today.hide()
        else
            binding.today.show()
    }

}