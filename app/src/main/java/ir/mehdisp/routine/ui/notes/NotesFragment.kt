package ir.mehdisp.routine.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.mehdisp.routine.databinding.FragmentNotesBinding
import ir.mehdisp.routine.ui.shared.AppAlertDialog

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModels()
    private val notesAdapter = NotesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = notesAdapter
        }

        binding.add.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesToNewNote(null)
            findNavController().navigate(action)
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            binding.emptyLayout.isVisible = it.isNullOrEmpty()
            notesAdapter.submitList(it)
        }

        notesAdapter.setOnItemClickListener {
            val action = NotesFragmentDirections.actionNotesToNewNote(it)
            findNavController().navigate(action)
        }

        notesAdapter.setOnDeleteClickListener { note ->
            AppAlertDialog.Builder(requireActivity())
                .setTitle("حذف یادداشت")
                .setMessage("آیا برای حذف یادداشت مطمئنید؟")
                .setPositiveButton("بله") {
                    viewModel.delete(note.id).observe(viewLifecycleOwner) {
                        if (it == true)
                            notesAdapter.removeItem(note)
                    }
                }
                .show()
        }

    }

}