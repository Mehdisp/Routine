package ir.mehdisp.persiancalendar.models

import android.graphics.Color

data class CalendarEvent(
    var title: String,
    val textColor: Int = Color.WHITE,
    val eventDrawable: Int
)