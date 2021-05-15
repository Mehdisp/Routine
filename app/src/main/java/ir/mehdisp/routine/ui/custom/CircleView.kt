package ir.mehdisp.routine.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ir.mehdisp.routine.R
import ir.mehdisp.routine.utils.ImageUtils
import ir.mehdisp.routine.utils.dp

class CircleView : View {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val wPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    constructor(context: Context?, attrs: AttributeSet?, d: Int) : this(context, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, 0)
    constructor(context: Context?, color: Int) : super(context) {
        paint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val mWidth = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            else -> 40.dp
        }

        val mMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY)

        setMeasuredDimension(mMeasureSpec, mMeasureSpec)

    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val center = width / 2f
        canvas?.drawCircle(center, center, center, paint)
        if (isSelected) {
            val bitmap = ImageUtils.getBitmap(context, R.drawable.ic_round_check_24)!!
            val x = center - (bitmap.width / 2)
            canvas?.drawBitmap(bitmap, x, x, wPaint)
        }
    }

}