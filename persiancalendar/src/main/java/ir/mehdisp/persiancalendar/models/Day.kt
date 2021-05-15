package ir.mehdisp.persiancalendar.models


data class Day(
    var num: String?,
    var isHoliday: Boolean,
    var isToday: Boolean,
    var dayOfWeek: Int,
    var persianDate: PersianDate
) {
    var isSelected: Boolean = false
}