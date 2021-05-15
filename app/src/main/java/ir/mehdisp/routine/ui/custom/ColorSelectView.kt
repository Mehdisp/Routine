package ir.mehdisp.routine.ui.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.core.view.children
import androidx.core.view.setMargins
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import ir.mehdisp.routine.utils.dp

class ColorSelectView: FlexboxLayout, View.OnClickListener {

    private val colors = arrayOf(
        Color.parseColor("#2196F3"),
        Color.parseColor("#607D8B"),
        Color.parseColor("#795548"),
        Color.parseColor("#4CAF50"),
        Color.parseColor("#3F51B5"),
        Color.parseColor("#FF9800"),
        Color.parseColor("#E91E63"),
        Color.parseColor("#9C27B0"),
        Color.parseColor("#F44336"),
        Color.parseColor("#FFEB3B")
    )

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?, d: Int) : this(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        flexWrap = FlexWrap.WRAP
        setColors(colors.asList())
    }

    override fun addView(child: View?) {
        super.addView(child)
        child?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        children.forEach { it.isSelected = it == v }
    }

    fun setColors(colors: List<Int>) {
        colors.forEach { color ->
            addView(CircleView(context, color).apply {
                tag = color
                layoutParams = LayoutParams(40.dp, 40.dp).apply {
                    setMargins(4.dp)
                }
            })
        }
        getChildAt(0).isSelected = true
    }

    fun getSelectedColor() = children.first { it.isSelected }.tag as Int

    fun selectColor(color: Int) {
        if (color == 0) return

        if (!colors.contains(color))
            return

        children.forEach {
            it.isSelected = (it.tag as Int) == color
        }
    }

}