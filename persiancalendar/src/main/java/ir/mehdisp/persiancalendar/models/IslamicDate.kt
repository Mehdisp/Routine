package ir.mehdisp.persiancalendar.models

import ir.mehdisp.persiancalendar.exceptions.DayOutOfRangeException
import ir.mehdisp.persiancalendar.exceptions.MonthOutOfRangeException
import ir.mehdisp.persiancalendar.exceptions.YearOutOfRangeException
import ir.mehdisp.persiancalendar.utils.Constants

class IslamicDate(year: Int, month: Int, day: Int) : AbstractDate() {

    private var mDay: Int
    private var mMonth = 0
    private var mYear = 0

    init {
        mDay = 1
        setYear(year)
        setMonth(month)
        dayOfMonth = day
    }

    override fun getDayOfMonth(): Int {
        return mDay
    }

    override fun setDayOfMonth(day: Int) {
        if (day < 1 || day > 30)
            throw DayOutOfRangeException("Day $day is out of range")

        mDay = day
    }

    override fun getDayOfWeek(): Int {
        throw NotImplementedError()
    }

    override fun getMonth(): Int {
        return mMonth
    }

    override fun setMonth(month: Int) {
        if (month < 1 || month > 12)
            throw MonthOutOfRangeException("Month $month is out of range")

        dayOfMonth = mDay
        mMonth = month
    }

    override fun getWeekOfYear(): Int {
        throw NotImplementedError()
    }

    override fun getYear(): Int {
        return mYear
    }

    override fun setYear(year: Int) {
        if (year == 0)
            throw YearOutOfRangeException("Invalid year: $year")

        mYear = year
    }

    override fun rollDay(amount: Int, up: Boolean): IslamicDate {
        throw NotImplementedError()
    }

    override fun rollMonth(amount: Int, up: Boolean): IslamicDate {
        throw NotImplementedError()
    }

    override fun rollYear(amount: Int, up: Boolean): IslamicDate {
        throw NotImplementedError()
    }

    override fun getEvent(): String {
        throw NotImplementedError()
    }

    override fun getDayOfYear(): Int {
        throw NotImplementedError()
    }

    override fun getWeekOfMonth(): Int {
        throw NotImplementedError()
    }

    override fun isLeapYear(): Boolean {
        throw NotImplementedError()
    }

    override fun toString(): String {
        return String.format("%02d %s %04d", dayOfMonth, Constants.ISLAMIC_MONTH_NAMES[mMonth - 1], year)
    }

    override fun clone(): IslamicDate {
        return IslamicDate(year, month, dayOfMonth)
    }
}