package ir.mehdisp.persiancalendar.interfaces

import ir.mehdisp.persiancalendar.models.Day
import ir.mehdisp.persiancalendar.models.PersianDate

interface PersianCalendarCallback {
    fun onEventUpdate()
    fun onDaysClick(day: Day)
    fun onDaysLongClick(day: Day)
    fun onMonthChanged(date: PersianDate)
}