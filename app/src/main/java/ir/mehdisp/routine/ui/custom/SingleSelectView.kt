package ir.mehdisp.routine.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.setMargins
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import ir.mehdisp.routine.R
import ir.mehdisp.routine.utils.dp

class SingleSelectView : FlexboxLayout, View.OnClickListener {

    private var onSelectedChange: (AppCompatTextView) -> Unit = {}

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?, d: Int) : this(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        flexWrap = FlexWrap.WRAP

        val ta = context?.obtainStyledAttributes(attrs, R.styleable.SingleSelectView)
        val id = ta?.getResourceId(R.styleable.SingleSelectView_entries, 0) ?: 0

        if (id != 0)
            setItems(resources.getStringArray(id).asList())

        ta?.recycle()
    }

    override fun onClick(v: View?) {
        children.forEach { it.isSelected = it == v }
        onSelectedChange(getSelectedItem())
    }

    override fun addView(child: View?) {
        super.addView(child)
        child?.setOnClickListener(this)
    }

    fun setOnSelectedChangeListener(listener: (AppCompatTextView) -> Unit) {
        this.onSelectedChange = listener
    }

    fun setItems(items: List<String>) {
        items.forEach { item ->
            addView(AppCompatTextView(context).apply {
                layoutParams =
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                        setMargins(4.dp)
                    }
                setBackgroundResource(R.drawable.selectable_rect)
                setTextColor(ContextCompat.getColorStateList(context, R.color.black_white_select))
                text = item
            })
        }

        if (childCount > 0)
            getChildAt(0).isSelected = true
    }

    fun getSelectedItem() = children.first { it.isSelected } as AppCompatTextView

    fun selectItemAt(position: Int) {
        children.forEachIndexed { index, view ->
            view.isSelected = index == position
        }
        onSelectedChange(getSelectedItem())
    }

}