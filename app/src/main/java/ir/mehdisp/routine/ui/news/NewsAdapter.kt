package ir.mehdisp.routine.ui.news

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ir.mehdisp.routine.R
import ir.mehdisp.routine.data.models.News
import ir.mehdisp.routine.databinding.LayoutNewsItemBinding
import ir.mehdisp.routine.utils.inflate

class NewsAdapter : PagingDataAdapter<News, NewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(o: News, n: News) = o.id == n.id
            override fun areContentsTheSame(o: News, n: News) = o == n
        }
    }

    private var onItemClick: (News) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_news_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    fun setOnItemClickListener(listener: (News) -> Unit) {
        this.onItemClick = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LayoutNewsItemBinding.bind(view)
        fun bind(item: News) {
            binding.title.text = item.title
            binding.desc.text = item.description
            binding.date.text = item.date
            binding.image.load(item.picture)
        }
    }

}
