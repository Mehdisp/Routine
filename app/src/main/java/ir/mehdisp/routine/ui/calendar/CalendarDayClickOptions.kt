package ir.mehdisp.routine.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ir.mehdisp.routine.databinding.LayoutCalendarClickOptionsBinding
import ir.mehdisp.routine.ui.base.BaseBottomSheetDialog
import ir.mehdisp.persiancalendar.models.Day

class CalendarDayClickOptions(
    private val day: Day
): BaseBottomSheetDialog() {

    private lateinit var binding: LayoutCalendarClickOptionsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = LayoutCalendarClickOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = day.persianDate.toString()

        binding.addTask.setOnClickListener {
            val date = day.persianDate.toString2()
            val directions = CalendarFragmentDirections.actionCalendarToTasks(date)
            findNavController().navigate(directions)
            dismissAllowingStateLoss()
        }

    }

}