package ir.mehdisp.routine.utils

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val density: Float = Resources.getSystem().displayMetrics.density

val Float.dp: Float
    get() = density * this

val Int.dp: Int
    get() = (density * this).toInt()

fun ViewGroup.inflate(resId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(resId, this, attachToRoot)
}