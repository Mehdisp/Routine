package ir.mehdisp.routine.ui.weather

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.mehdisp.routine.R
import ir.mehdisp.routine.data.models.City
import ir.mehdisp.routine.databinding.LayoutCityItemBinding
import ir.mehdisp.routine.utils.inflate

class LocationsAdapter : ListAdapter<City, LocationsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(old: City, new: City): Boolean {
                return old.name == new.name
            }

            override fun areContentsTheSame(old: City, new: City): Boolean {
                return old == new
            }
        }
    }

    private var onItemClick: (City) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_city_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = getItem(position) ?: return
        holder.bind(city)
        holder.itemView.setOnClickListener { onItemClick(city) }
    }

    fun setOnItemClickListener(listener: (City) -> Unit) {
        this.onItemClick = listener
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = LayoutCityItemBinding.bind(view)
        fun bind(city: City) {
            binding.city.text = city.name
            val icon = if (city.symbol.isEmpty()) "wi-na" else city.symbol
            binding.icon.load("file:///android_asset/weathericons/$icon.svg") {
                error(R.drawable.ic_round_add_location_24)
            }
        }
    }

}
