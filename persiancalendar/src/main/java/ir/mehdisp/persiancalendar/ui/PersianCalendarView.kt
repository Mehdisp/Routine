package ir.mehdisp.persiancalendar.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.Config
import ir.mehdisp.persiancalendar.R
import ir.mehdisp.persiancalendar.adapters.PersianCalendarAdapter
import ir.mehdisp.persiancalendar.interfaces.PersianCalendarCallback
import ir.mehdisp.persiancalendar.interfaces.PersianCalendarEventHandler
import ir.mehdisp.persiancalendar.models.PersianDate
import ir.mehdisp.persiancalendar.utils.Constants

class PersianCalendarView : RecyclerView {

    private val handler: CalendarHandler
    private val pagerAdapter: PersianCalendarAdapter
    private val mLayoutManager: LinearLayoutManager

    val currentPosition: Int
        get() = mLayoutManager.findFirstCompletelyVisibleItemPosition()

    private var pagerPosition = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?, d: Int) : this(context, attrs)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        layoutDirection = View.LAYOUT_DIRECTION_LTR
        handler = CalendarHandler(context)
        handler.config = createConfig(context, attrs)
        pagerAdapter = PersianCalendarAdapter(handler)
        mLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        setItemViewCacheSize(0)

        layoutManager = mLayoutManager
        adapter = pagerAdapter


        val pagerHelper = PagerSnapHelper()
        pagerHelper.attachToRecyclerView(this)

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE)
                    callOnMonthChanged()
            }
        })

        scrollToPosition(Constants.MONTHS_LIMIT / 2)
    }

    private fun callOnMonthChanged() {
        pagerPosition = currentPosition - Constants.MONTHS_LIMIT / 2
        val persianDate = CalendarHandler.getToday()
        var month: Int = persianDate.month - pagerPosition - 1
        var year: Int = persianDate.year

        year += month / 12
        month %= 12
        if (month < 0) {
            year -= 1
            month += 12
        }

        month += 1
        persianDate.month = month
        persianDate.year = year
        persianDate.dayOfMonth = 1

        handler.callback?.onMonthChanged(persianDate)
    }

    private fun createConfig(context: Context, attrs: AttributeSet?): Config {
        val builder = Config.Builder()

        if (attrs == null)
            return builder.build()

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PersianCalendarView, 0, 0)

        if (typedArray.hasValue(R.styleable.PersianCalendarView_pcv_typefacePath)) {
            val typeface = Typeface.createFromAsset(
                context.assets,
                typedArray.getString(R.styleable.PersianCalendarView_pcv_typefacePath)
            )
            if (typeface != null) builder.setTypeface(typeface)
        }

        if (typedArray.hasValue(R.styleable.PersianCalendarView_pcv_headersTypefacePath)) {
            val typeface = Typeface.createFromAsset(
                context.assets,
                typedArray.getString(R.styleable.PersianCalendarView_pcv_headersTypefacePath)
            )
            if (typeface != null) builder.setHeaderTypeface(typeface)
        }
        builder.setDaysFontSize(
            typedArray.getDimensionPixelSize(
                R.styleable.PersianCalendarView_pcv_fontSize, 25
            )
        )
        builder.setHeadersFontSize(
            typedArray.getDimensionPixelSize(
                R.styleable.PersianCalendarView_pcv_headersFontSize, 20
            )
        )
        builder.setSelectedDayBackground(
            typedArray.getResourceId(
                R.styleable.PersianCalendarView_pcv_selectedDayBackground, 0
            )
        )
        builder.setColorDayName(
            typedArray.getColor(
                R.styleable.PersianCalendarView_pcv_colorDayName, Color.DKGRAY
            )
        )
        builder.setColorHolidaySelected(
            typedArray.getColor(
                R.styleable.PersianCalendarView_pcv_colorHolidaySelected, Color.BLACK
            )
        )
        builder.setColorHoliday(
            typedArray.getColor(
                R.styleable.PersianCalendarView_pcv_colorHoliday, Color.RED
            )
        )
        builder.setColorNormalDaySelected(
            typedArray.getColor(
                R.styleable.PersianCalendarView_pcv_colorNormalDaySelected, Color.GRAY
            )
        )
        builder.setColorNormalDay(
            typedArray.getColor(
                R.styleable.PersianCalendarView_pcv_colorNormalDay, Color.LTGRAY
            )
        )
        builder.setIsDaysSelectable(
            typedArray.getBoolean(R.styleable.PersianCalendarView_pcv_isDaysSelectable, false)
        )

        val backgroundColor = typedArray.getColor(
            R.styleable.PersianCalendarView_pcv_colorBackground, Color.WHITE
        )

        builder.setColorBackground(backgroundColor)
        setBackgroundColor(backgroundColor)
        typedArray.recycle()
        return builder.build()
    }

    private fun bringTodayYearMonth() {
        if (currentPosition != Constants.MONTHS_LIMIT / 2)
            smoothScrollToPosition(Constants.MONTHS_LIMIT / 2)
    }

    fun bringDate(date: PersianDate) {
        val today = getToday()
        pagerPosition = (today.year - date.year) * 12 + today.month - date.month
        smoothScrollToPosition(pagerPosition + Constants.MONTHS_LIMIT / 2)
    }

    fun update() {
        adapter = pagerAdapter
        scrollToPosition(Constants.MONTHS_LIMIT / 2)
    }

    fun getToday() = CalendarHandler.getToday()

    fun goToDate(date: PersianDate) {
        bringDate(date)
    }

    fun goToToday() {
        bringDate(getToday())
    }

    fun goToNextMonth() {
        changeMonth(-1)
    }

    fun goToPreviousMonth() {
        changeMonth(1)
    }

    private fun changeMonth(position: Int) {
        smoothScrollToPosition(currentPosition + position)
    }

    fun setCallback(callback: PersianCalendarCallback) {
        handler.callback = callback
    }

    fun addEventsHandler(eventsHandler: PersianCalendarEventHandler) = apply {
        handler.addEventHandler(eventsHandler)
        update()
    }

    fun removeEventsHandler(eventsHandler: PersianCalendarEventHandler) = apply {
        handler.removeEventHandler(eventsHandler)
        update()
    }

    fun getCalendarHandler() = handler

}