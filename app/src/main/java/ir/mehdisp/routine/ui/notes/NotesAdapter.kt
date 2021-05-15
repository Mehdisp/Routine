package ir.mehdisp.routine.ui.notes

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.mehdisp.routine.R
import ir.mehdisp.routine.data.models.Note
import ir.mehdisp.routine.databinding.LayoutNoteItemBinding
import ir.mehdisp.routine.utils.inflate

class NotesAdapter : ListAdapter<Note, NotesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(old: Note, new: Note): Boolean {
                return old.id == new.id
            }

            override fun areContentsTheSame(old: Note, new: Note): Boolean {
                return old == new
            }
        }
    }

    private var onItemClick: (Note) -> Unit = {}
    private var onDeleteClick: (Note) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.layout_note_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position) ?: return
        holder.bind(note)
    }

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        this.onItemClick = listener
    }

    fun setOnDeleteClickListener(listener: (Note) -> Unit) {
        this.onDeleteClick = listener
    }

    fun removeItem(note: Note) {
        val list = currentList.toMutableList()
        list.remove(note)
        submitList(list)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = LayoutNoteItemBinding.bind(view)
        fun bind(note: Note) {
            binding.title.text = note.title
            binding.body.text = note.body

            binding.root.setOnClickListener { onItemClick(note) }
            binding.delete.setOnClickListener { onDeleteClick(note) }
        }
    }

}