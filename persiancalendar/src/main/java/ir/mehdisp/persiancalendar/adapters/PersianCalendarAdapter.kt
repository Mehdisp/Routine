package ir.mehdisp.persiancalendar.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.ui.MonthView
import ir.mehdisp.persiancalendar.utils.Constants

class PersianCalendarAdapter(
    private val calendarHandler: CalendarHandler
) : RecyclerView.Adapter<PersianCalendarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MonthView(calendarHandler))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOffset(position - Constants.MONTHS_LIMIT / 2)
    }

    override fun getItemCount() = Constants.MONTHS_LIMIT

    class ViewHolder(val view: MonthView) : RecyclerView.ViewHolder(view)

}
