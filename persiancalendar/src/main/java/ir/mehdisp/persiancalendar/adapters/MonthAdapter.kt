package ir.mehdisp.persiancalendar.adapters

import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.R
import ir.mehdisp.persiancalendar.models.Day
import ir.mehdisp.persiancalendar.utils.Constants
import kotlin.concurrent.thread

class MonthAdapter(
    private val handler: CalendarHandler
) : ListAdapter<Day, MonthAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_DAY = 2
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Day>() {
            override fun areItemsTheSame(old: Day, new: Day): Boolean {
                return old == new
            }

            override fun areContentsTheSame(old: Day, new: Day): Boolean {
                return old == new
            }
        }
    }

    private val context = handler.context
    private val config = handler.config

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var mFirstDayOfWeek = -1
    private var mTotalDays: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = if (viewType == TYPE_HEADER) R.layout.item_header else R.layout.item_day
        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position + (6 - position % 7 * 2)

        if (isPositionHeader(pos)) {
            holder.mNum.text = Constants.FIRST_CHAR_OF_DAYS_OF_WEEK_NAME[pos]
            return
        }

        val dayIndex = pos - 7 - mFirstDayOfWeek

        if (dayIndex < 0 || mTotalDays <= dayIndex) {
            holder.mNum.visibility = View.GONE
            holder.mEvent?.visibility = View.GONE
            holder.mToday?.visibility = View.GONE
            return
        }

        holder.mNum.visibility = View.VISIBLE

        val day = getItem(dayIndex)
//        holder.itemView.isSelected = day.isSelected
        holder.mToday?.visibility = if (day.isToday) View.VISIBLE else View.GONE

        if (day.isToday) {
            holder.mNum.setTextColor(handler.config.colorToday)
            holder.mNum.setTypeface(holder.mNum.typeface, Typeface.BOLD)
        }

        holder.mNum.text = day.num
        holder.mNum.setTextColor(
            if (day.isHoliday) config.colorHoliday else config.colorNormalDay
        )

        holder.itemView.setOnClickListener {
            if (mTotalDays <= dayIndex || dayIndex < 0)
                return@setOnClickListener

            handler.callback?.onDaysClick(day)
            if (handler.config.isDaysSelectable) {
                currentList.forEach { it.isSelected = it == day }
                notifyDataSetChanged()
            }
        }

        holder.itemView.setOnLongClickListener {
            if (mTotalDays <= dayIndex || dayIndex < 0)
                return@setOnLongClickListener false

            handler.callback?.onDaysLongClick(day)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return 7 * 7
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_DAY
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position < 7
    }

    fun setOffset(offset: Int) {
        val days = handler.getDays(offset)
        submitList(days)
        mFirstDayOfWeek = days.firstOrNull()?.dayOfWeek ?: -1
        mTotalDays = days.size
        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mNum: AppCompatTextView = itemView.findViewById(R.id.num)
        var mToday: AppCompatImageView? = itemView.findViewById(R.id.today)
        var mEvent: View? = itemView.findViewById(R.id.event)
    }

}