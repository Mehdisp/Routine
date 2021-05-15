package ir.mehdisp.persiancalendar.ui

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.utils.SGridLayoutManager
import ir.mehdisp.persiancalendar.adapters.MonthAdapter

@SuppressLint("ViewConstructor")
class MonthView(handler: CalendarHandler) : RecyclerView(handler.context) {

    private val monthAdapter = MonthAdapter(handler)

    init {
        monthAdapter.setHasStableIds(true)
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setHasFixedSize(true)
        layoutManager = SGridLayoutManager(context, 7)
        adapter = monthAdapter
        overScrollMode = View.OVER_SCROLL_NEVER
        suppressLayout(true)
        isNestedScrollingEnabled = false

    }

    fun setOffset(offset: Int) {
        monthAdapter.setOffset(offset)
    }

}