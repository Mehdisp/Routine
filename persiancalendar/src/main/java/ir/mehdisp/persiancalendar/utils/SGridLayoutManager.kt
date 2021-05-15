package ir.mehdisp.persiancalendar.utils

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager

class SGridLayoutManager: GridLayoutManager {
    constructor(c: Context?, a: AttributeSet?, da: Int, dr: Int) : super(c, a, da, dr)
    constructor(c: Context?, s: Int) : super(c, s)
    constructor(c: Context?, s: Int, o: Int, r: Boolean) : super(c, s, o, r)

    override fun canScrollVertically() = false

    override fun canScrollHorizontally() = false

}