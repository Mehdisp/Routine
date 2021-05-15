package ir.mehdisp.persiancalendar.models

import ir.mehdisp.persiancalendar.exceptions.DayOutOfRangeException
import ir.mehdisp.persiancalendar.exceptions.MonthOutOfRangeException
import ir.mehdisp.persiancalendar.exceptions.YearOutOfRangeException
import ir.mehdisp.persiancalendar.utils.Constants
import java.lang.Exception
import java.util.*

class CivilDate : AbstractDate {

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 1

    constructor(calendar: Calendar = Calendar.getInstance()) {
        init(calendar)
    }

    constructor(year: Int, month: Int, day: Int) : this() {
        setYear(year)
        mDay = 1
        setMonth(month)
        dayOfMonth = day
    }

    private fun init(calendar: Calendar) {
        mYear = calendar[Calendar.YEAR]
        mMonth = calendar[Calendar.MONTH] + 1
        mDay = calendar[Calendar.DAY_OF_MONTH]
    }

    override fun getDayOfMonth(): Int {
        return mDay
    }

    override fun setDayOfMonth(day: Int) {
        if (day < 1)
            throw DayOutOfRangeException("Day: $day is out of range")

        if (mMonth != 2 && day > Constants.CIVIL_DAYS_IN_MONTH[mMonth])
            throw DayOutOfRangeException("Day: $day is out of range")

        if (mMonth == 2 && isLeapYear && day > 29)
            throw DayOutOfRangeException("Day: $day is out of range")

        if (mMonth == 2 && !isLeapYear && day > 28)
            throw DayOutOfRangeException("Day: $day is out of range")

        mDay = day
    }

    override fun getDayOfWeek(): Int {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = mYear
        cal[Calendar.MONTH] = mMonth - 1
        cal[Calendar.DAY_OF_MONTH] = mDay
        return cal[Calendar.DAY_OF_WEEK]
    }

    override fun getDayOfYear(): Int {
        throw NotImplementedError()
    }

    override fun getEvent(): String {
        throw NotImplementedError()
    }

    override fun getMonth(): Int {
        return mMonth
    }

    override fun setMonth(month: Int) {
        if (month < 1 || month > 12)
            throw MonthOutOfRangeException("Month $month is out of range")

        dayOfMonth = dayOfMonth
        mMonth = month
    }

    override fun getWeekOfMonth(): Int {
        throw NotImplementedError()
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

    override fun isLeapYear(): Boolean {
        if (mYear % 400 == 0)
            return true
        else if (mYear % 100 == 0)
            return false

        return mYear % 4 == 0
    }

    override fun rollDay(amount: Int, up: Boolean): CivilDate {
        init(getCalendar().apply {
            add(Calendar.DAY_OF_MONTH, amount * if (up) 1 else -1)
        })
        return this
    }

    override fun rollMonth(amount: Int, up: Boolean): CivilDate {
        init(getCalendar().apply {
            add(Calendar.MONTH, amount * if (up) 1 else -1)
        })
        return this
    }

    override fun rollYear(amount: Int, up: Boolean): CivilDate {
        init(getCalendar().apply {
            add(Calendar.YEAR, amount * if (up) 1 else -1)
        })
        return this
    }

    override fun equals(other: Any?): Boolean {
        return other is CivilDate && dayOfMonth == other.dayOfMonth && month == other.month && year == other.year
    }

    override fun clone(): CivilDate {
        return CivilDate(year, month, dayOfMonth)
    }

    fun getCalendar() = Calendar.getInstance().apply {
        set(year, month - 1, dayOfMonth)
    }

    override fun toString(): String {
        return String.format(
            Locale.ENGLISH,
            "%02d %s %04d",
            mDay,
            Constants.CIVIL_MONTH_NAMES[mMonth - 1],
            mYear
        )
    }

    fun toDateString(): String {
        return String.format(Locale.ENGLISH, "%04d-%02d-%02d", mYear, mMonth, mDay)
    }

    fun toDateTimeString(): String {
        return String.format(Locale.ENGLISH, "%04d-%02d-%02dT00:00:00", mYear, mMonth, mDay)
    }

    override fun hashCode(): Int {
        var result = mYear
        result = 31 * result + mMonth
        result = 31 * result + mDay
        return result
    }
}

