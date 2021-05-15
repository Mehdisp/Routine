package ir.mehdisp.persiancalendar.utils

object Constants {
    const val MONTHS_LIMIT = 12 // this should be an even number
    const val PERSIAN_COMMA = '،'

    val CIVIL_DAYS_IN_MONTH = arrayOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val FIRST_CHAR_OF_DAYS_OF_WEEK_NAME = arrayOf("ش", "ی", "د", "س", "چ", "پ", "ج")
    val ARABIC_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val PERSIAN_DIGITS = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')

    val DAYS_OF_WEEK_NAME = arrayOf("شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه")
    val MONTH_NAMES = arrayOf(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
    )
    val ISLAMIC_MONTH_NAMES = arrayOf(
        "محرم",
        "صفر",
        "ربیع‌الاول",
        "ربیع‌الثانی",
        "جمادی‌الاول",
        "جمادی‌الثانی",
        "رجب",
        "شعبان",
        "رمضان",
        "شوال",
        "ذیقعده",
        "ذیحجه",
    )
    val CIVIL_MONTH_NAMES = arrayOf(
        "ژانویه",
        "فوریه",
        "مارس",
        "آوریل",
        "مه",
        "ژوئن",
        "ژوئیه",
        "اوت",
        "سپتامبر",
        "اکتبر",
        "نوامبر",
        "دسامبر",
    )

}