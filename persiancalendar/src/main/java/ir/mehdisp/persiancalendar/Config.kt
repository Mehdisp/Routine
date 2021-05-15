package ir.mehdisp.persiancalendar

import android.graphics.Color
import android.graphics.Typeface

class Config private constructor(builder: Builder) {

    val colorToday = builder.colorTodayDay
    val colorTodaySelected = builder.colorTodayDaySelected

    val colorHoliday = builder.colorHoliday
    val colorHolidaySelected = builder.colorHolidaySelected

    val colorNormalDay = builder.colorNormalDay
    val colorNormalDaySelected = builder.colorNormalDaySelected

    val colorBackground = builder.colorBackground

    val isDaysSelectable = builder.isDaysSelectable

    class Builder {

        var typeface: Typeface? = null
        var headerTypeface: Typeface? = null

        var colorTodayDay: Int = Color.BLACK
        var colorTodayDaySelected: Int = Color.BLACK

        var colorNormalDay: Int = Color.GRAY
        var colorNormalDaySelected: Int = Color.DKGRAY

        var colorHoliday: Int = Color.RED
        var colorHolidaySelected: Int = Color.RED

        var colorBackground: Int = Color.WHITE

        var daysFontSize: Int = 20
        var headersFontSize: Int = 26

        var isDaysSelectable: Boolean = false

        fun build(): Config {
            return Config(this)
        }

        fun setTypeface(typeface: Typeface?) = apply { this.typeface = typeface }

        fun setHeaderTypeface(typeface: Typeface?) = apply { headerTypeface = typeface }

        fun setDaysFontSize(size: Int) = apply { this.daysFontSize = size }

        fun setHeadersFontSize(size: Int) = apply { this.headersFontSize = size }

        fun setSelectedDayBackground(resourceId: Int): Builder {
            return this
        }

        fun setColorDayName(color: Int): Builder {
            return this
        }

        fun setColorHolidaySelected(color: Int) = apply { this.colorHolidaySelected = color }
        fun setColorHoliday(color: Int) = apply { this.colorHoliday = color }

        fun setColorNormalDaySelected(color: Int) = apply { this.colorNormalDaySelected = color }
        fun setColorNormalDay(color: Int) = apply { this.colorNormalDay = color }

        fun setColorTodayDay(color: Int) = apply { this.colorTodayDay = color }
        fun setColorTodayDaySelected(color: Int) = apply { this.colorTodayDaySelected = color }

        fun setColorBackground(color: Int) = apply { this.colorBackground = color }

        fun setIsDaysSelectable(selectable: Boolean) = apply { isDaysSelectable = selectable }
    }
}