package ir.mehdisp.routine.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import ir.mehdisp.routine.data.models.Note
import ir.mehdisp.routine.databinding.FragmentNewNoteBinding

@AndroidEntryPoint
class NewNoteFragment : Fragment() {

    private lateinit var binding: FragmentNewNoteBinding
    private val viewModel: NotesViewModel by viewModels()
    private val args: NewNoteFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        args.note?.let { note ->
            binding.title.editText?.setText(note.title)
            binding.body.editText?.setText(note.body)
        }

        binding.save.setOnClickListener {
            val title = binding.title.editText?.text?.trim()?.toString()
            val body = binding.body.editText?.text?.trim()?.toString()

            binding.title.error = null
            binding.body.error = null

            if (title.isNullOrEmpty()) {
                binding.title.error = "عنوان یادداشت الزامی است"
                return@setOnClickListener
            }

            if (body.isNullOrEmpty()) {
                binding.body.error = "متن یادداشت الزامی است"
                return@setOnClickListener
            }

            val note = args.note ?: Note.EMPTY
            note.title = title
            note.body = body

            viewModel.saveNote(note).observe(viewLifecycleOwner) {
                if (it) {
                    Toasty.success(requireActivity(), "یادداشت ذخیره شد").show()
                    findNavController().navigateUp()
                } else
                    Toasty.error(requireActivity(), "خطا در ذخیره سازی").show()
            }
        }

    }

}