package ir.mehdisp.routine.ui.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.mehdisp.routine.databinding.LayoutDatePickerSheetBinding
import ir.mehdisp.routine.ui.base.BaseBottomSheetDialog
import ir.mehdisp.persiancalendar.models.CivilDate
import ir.mehdisp.persiancalendar.models.PersianDate
import ir.mehdisp.persiancalendar.utils.DateConverter
import java.util.*

class DatePickerSheet(
    private val onDoneClick: (PersianDate?) -> Unit
): BaseBottomSheetDialog() {

    private var dismissed = true
    private lateinit var binding: LayoutDatePickerSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = LayoutDatePickerSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.done.setOnClickListener {
            dismissed = false
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"))
            calendar.time = binding.datePicker.displayDate
            val persianDate = DateConverter.civilToPersian(CivilDate(calendar))
            onDoneClick(persianDate)
            dismissAllowingStateLoss()
        }

    }

    override fun onDestroyView() {
        if (dismissed)
            onDoneClick(null)
        super.onDestroyView()
    }

}