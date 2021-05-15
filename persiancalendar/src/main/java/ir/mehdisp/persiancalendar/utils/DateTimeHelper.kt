package ir.mehdisp.persiancalendar.utils

import ir.mehdisp.persiancalendar.models.CivilDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {

    const val DAY_TIME_IN_MILLIS: Long = 86400000
    const val WEEK_TIME_IN_MILLIS: Long = 604800000

    fun dateToMs(strDate: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(strDate) ?: return 0L
        return date.time
    }

    fun calculateMillisecondsFromNowToDate(endDate: String?): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return try {
            val date = sdf.parse(endDate)
            val endDateMillis = date.time
            val currentMillis = System.currentTimeMillis()

            //System.out.println(endDateMillis);
            //System.out.println(currentMillis);
            //System.out.println(result);
            endDateMillis - currentMillis
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }

/*
        Date date2 = new Date();
      long ff= date2.getTime();

        long calc = (ff - millis);
        return (int) calc;*/
    }

    fun calcDayToMs(days: Int): Long {
        return days * DAY_TIME_IN_MILLIS
    }

    fun calcDayToWeek(days: Int): String {
        val weekCount = days / 7
        val remain = days % 7
        return weekCount.toString() + "هفته و " + remain + " روز "
    }

    fun calcMsToDay(ms: Long): Long {
        return ms / DAY_TIME_IN_MILLIS
    }

    fun isDayBetweenRange(strCivilDate: String, firstDateMs: Long, lastDateMs: Long): Boolean {
        return dateToMs(strCivilDate) in (firstDateMs + 1) until lastDateMs
    }

    fun dateToStr(civilDate: CivilDate): String {
        return "${civilDate.year}-${civilDate.month}-${civilDate.dayOfMonth}"
    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getCivilDate(milliSeconds: Long): CivilDate {
        val date = getDate(milliSeconds, "yyyy-MM-dd")
            .split('-')
            .map { it.toInt() }

        return CivilDate(date[0], date[1], date[2])
    }

}