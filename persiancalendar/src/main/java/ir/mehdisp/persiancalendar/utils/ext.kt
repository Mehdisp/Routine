package ir.mehdisp.persiancalendar.utils

import android.content.res.Resources
import ir.mehdisp.persiancalendar.models.CivilDate
import java.lang.Exception

fun Float.dp(): Float {
    return Resources.getSystem().displayMetrics.density * this
}

fun String?.toCivilDate(): CivilDate? {
    if (isNullOrEmpty() || !contains('-'))
        return null

    return try {
        val split = split("-").map { it.toInt() }
        CivilDate(split[0], split[1], split[2])
    } catch (e: Exception) {
        null
    }
}
