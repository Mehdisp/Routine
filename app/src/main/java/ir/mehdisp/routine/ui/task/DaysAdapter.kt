package ir.mehdisp.routine.ui.task

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.routine.R
import ir.mehdisp.routine.databinding.LayoutDayBinding
import ir.mehdisp.routine.utils.inflate
import ir.mehdisp.persiancalendar.models.Day
import ir.mehdisp.persiancalendar.utils.Constants

class DaysAdapter : ListAdapter<Day, DaysAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Day>() {
            override fun areItemsTheSame(old: Day, new: Day): Boolean {
                return old == new
            }

            override fun areContentsTheSame(old: Day, new: Day): Boolean {
                return old == new || old.isSelected == new.isSelected
            }
        }
    }

    private var onItemClick: (Day) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_day))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = getItem(position) ?: return
        holder.bind(day)
        holder.itemView.setOnClickListener {
            currentList.forEach { item -> item.isSelected = item == day }
            notifyDataSetChanged()
            onItemClick(day)
        }
    }

    fun setOnItemClickListener(listener: (Day) -> Unit) {
        this.onItemClick = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutDayBinding.bind(view)
        fun bind(day: Day) {
            binding.root.isActivated = day.isToday

            binding.root.isSelected = day.isSelected
            binding.num.isSelected = day.isSelected
            binding.dayOfWeek.isSelected = day.isSelected

            binding.num.text = day.num
            binding.dayOfWeek.text = Constants.DAYS_OF_WEEK_NAME[day.dayOfWeek]
        }
    }

}
