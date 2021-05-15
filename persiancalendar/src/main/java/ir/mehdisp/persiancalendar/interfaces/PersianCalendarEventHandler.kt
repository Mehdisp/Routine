package ir.mehdisp.persiancalendar.interfaces

import ir.mehdisp.persiancalendar.models.Day

interface PersianCalendarEventHandler {
    fun getUniqueId(): Int
    fun handle(day: Day)
}