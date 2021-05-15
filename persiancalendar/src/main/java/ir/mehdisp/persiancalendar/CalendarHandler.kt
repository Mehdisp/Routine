package ir.mehdisp.persiancalendar

import android.content.Context
import ir.mehdisp.persiancalendar.exceptions.DayOutOfRangeException
import ir.mehdisp.persiancalendar.interfaces.PersianCalendarCallback
import ir.mehdisp.persiancalendar.interfaces.PersianCalendarEventHandler
import ir.mehdisp.persiancalendar.models.CivilDate
import ir.mehdisp.persiancalendar.models.Day
import ir.mehdisp.persiancalendar.models.PersianDate
import ir.mehdisp.persiancalendar.utils.Constants
import ir.mehdisp.persiancalendar.utils.DateConverter
import java.util.*

class CalendarHandler(val context: Context) {

    companion object {

        fun getToday(): PersianDate {
            val calendar = Calendar.getInstance()
            calendar.timeZone = TimeZone.getTimeZone("Asia/Tehran")
            calendar.time = Date()
            return DateConverter.civilToPersian(CivilDate(calendar))
        }

        fun getMonthName(date: PersianDate): String {
            return Constants.MONTH_NAMES[date.month - 1]
        }

        fun getDayInWeekName(date: PersianDate): String {
            val mDate = DateConverter.persianToCivil(date)
            return Constants.DAYS_OF_WEEK_NAME[mDate.dayOfWeek % 7]
        }

    }

    private val eventHandlers = mutableListOf<PersianCalendarEventHandler>()

    var config: Config = Config.Builder().build()
    var callback: PersianCalendarCallback? = null

    fun getDays(offset: Int): List<Day> {
        val days = mutableListOf<Day>()

        val persianDate: PersianDate = getToday()

        var month = persianDate.month - offset - 1
        var year = persianDate.year
        year += month / 12
        month %= 12

        if (month < 0) {
            year--
            month += 12
        }

        month++

        persianDate.month = month
        persianDate.year = year
        persianDate.dayOfMonth = 1

        var dayOfWeek = DateConverter.persianToCivil(persianDate).dayOfWeek % 7

        val today: PersianDate = getToday()
        try {
            for (i in 1..31) {
                persianDate.dayOfMonth = i

                val day = Day(
                    num = i.toString(),
                    dayOfWeek = dayOfWeek,
                    isHoliday = dayOfWeek == 6,
                    persianDate = persianDate.clone(),
                    isToday = persianDate == today,
                )

                day.isSelected = day.isToday

                days.add(day)
                dayOfWeek = (dayOfWeek + 1) % 7
            }
        } catch (e: DayOutOfRangeException) { }

        return days
    }

    fun addEventHandler(eventsHandler: PersianCalendarEventHandler) {
        val handler = eventHandlers.find { it.getUniqueId() == eventsHandler.getUniqueId() }
        removeEventHandler(handler)
        eventHandlers.add(eventsHandler)
    }

    fun removeEventHandler(eventsHandler: PersianCalendarEventHandler?) {
        eventsHandler ?: return
        eventHandlers.remove(eventsHandler)
    }

}

