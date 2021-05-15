package ir.mehdisp.persiancalendar.models

import ir.mehdisp.persiancalendar.exceptions.DayOutOfRangeException
import ir.mehdisp.persiancalendar.exceptions.MonthOutOfRangeException
import ir.mehdisp.persiancalendar.exceptions.YearOutOfRangeException
import ir.mehdisp.persiancalendar.utils.Constants
import ir.mehdisp.persiancalendar.utils.DateConverter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PersianDate : AbstractDate {

    private var mYear = 0
    private var mMonth = 0
    private var mDay: Int

    constructor(year: Int, month: Int, day: Int) {
        setYear(year)
        mDay = 1
        setMonth(month)
        dayOfMonth = day
    }

    override fun clone(): PersianDate {
        return PersianDate(year, month, dayOfMonth)
    }

    override fun getDayOfMonth(): Int {
        return mDay
    }

    @Throws(DayOutOfRangeException::class)
    override fun setDayOfMonth(day: Int) {
        if (day < 1)
            throw DayOutOfRangeException("Day $day is out of date")
        if (mMonth <= 6 && day > 31)
            throw DayOutOfRangeException("Day $day is out of date")
        if (mMonth in 7..12 && day > 30)
            throw DayOutOfRangeException("Day $day is out of date")
        if (isLeapYear && mMonth == 12 && day > 30)
            throw DayOutOfRangeException("Day $day is out of date")
        if (!isLeapYear && mMonth == 12 && day > 29)
            throw DayOutOfRangeException("Day $day is out of date")

        mDay = day
    }

    override fun getMonth(): Int {
        return mMonth
    }

    val monthName: String
        get() = Constants.MONTH_NAMES[mMonth - 1]

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

    fun rollDay(amount: Int) = rollDay(amount, true)

    override fun rollDay(amount: Int, up: Boolean): PersianDate {
        val civilDate = DateConverter.persianToCivil(this)
        civilDate.rollDay(amount, up)
        val persianDate = DateConverter.civilToPersian(civilDate)
        setDate(persianDate.year, persianDate.month, persianDate.dayOfMonth)
        return this
    }

    override fun rollMonth(amount: Int, up: Boolean): PersianDate {
        mMonth += amount * if (up) 1 else -1
        return this
    }

    override fun rollYear(amount: Int, up: Boolean): PersianDate {
        mYear += amount * if (up) 1 else -1
        return this
    }

    override fun getEvent(): String {
        throw NotImplementedError()
    }

    override fun getDayOfWeek(): Int {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = mYear
        cal[Calendar.MONTH] = mMonth - 1
        cal[Calendar.DAY_OF_MONTH] = mDay
        return cal[Calendar.DAY_OF_WEEK]
    }

    fun dayOfWeekName() = Constants.DAYS_OF_WEEK_NAME[dayOfWeek]

    override fun getDayOfYear(): Int {
        throw NotImplementedError()
    }

    override fun getWeekOfMonth(): Int {
        throw NotImplementedError()
    }

    override fun isLeapYear(): Boolean {
        val y = if (mYear > 0) mYear - 474 else 473
        return (y % 2820 + 474 + 38) * 682 % 2816 < 682
    }

    override fun equals(other: Any?): Boolean {
        return other is PersianDate && dayOfMonth == other.dayOfMonth && month == other.month && (year == other.year || year == -1)
    }

    override fun toString(): String {
        return "$mDay ${Constants.MONTH_NAMES[mMonth - 1]} $mYear"
    }

    fun toString2(): String {
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