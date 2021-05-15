package ir.mehdisp.routine.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import ir.mehdisp.routine.R
import ir.mehdisp.routine.data.models.Task
import ir.mehdisp.routine.databinding.FragmentAddTaskBinding
import ir.mehdisp.routine.ui.base.BaseFragment
import ir.mehdisp.routine.ui.shared.DatePickerSheet
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.models.PersianDate
import java.util.*

@AndroidEntryPoint
class AddTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private val viewModel: TasksViewModel by activityViewModels()

    private var selectedDate = CalendarHandler.getToday()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments ?: Bundle.EMPTY
        val args = AddTaskFragmentArgs.fromBundle(bundle)

        args.task?.let { task ->
            binding.toolbar.title = "ویرایش تسک"
            binding.body.editText?.setText(task.body)
            binding.colorView.selectColor(task.color)

            val date = task.forDate.split('-').map { it.toInt() }
            selectedDate = PersianDate(date[0], date[1], date[2])

            val today = CalendarHandler.getToday()
            val pos = when (task.forDate) {
                today.toString2() -> 0
                today.rollDay(1).toString2() -> 1
                today.rollDay(2).toString2() -> 2
                today.rollDay(4).toString2() -> 3
                else -> 4
            }

            binding.singleSelect.selectItemAt(pos)

            if (pos == 4)
                binding.singleSelect.getSelectedItem().text = today.toString2()

        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.singleSelect.setOnSelectedChangeListener {
            val options = resources.getStringArray(R.array.task_times)
            val today = CalendarHandler.getToday()
            when (options.indexOf(it.text)) {
                // امروز
                0 -> selectedDate = today
                // فردا
                1 -> selectedDate = today.rollDay(1)
                // سه روز بعد
                2 -> selectedDate = today.rollDay(3)
                // هفته بعد
                3 -> selectedDate = today.rollDay(7)
                // انتخاب تاریخ
                else -> DatePickerSheet { date ->
                    it.text = date?.toString() ?: options.last()
                    if (date == null)
                        binding.singleSelect.selectItemAt(0)
                    else
                        selectedDate = date
                }.show(requireActivity())
            }
        }

        binding.save.setOnClickListener {
            val body = binding.body.editText?.text?.toString()
            binding.body.error = null
            if (body.isNullOrEmpty()) {
                binding.body.error = "عنوان تسک را وارد کنید"
                return@setOnClickListener
            }

            val task = args.task ?: Task.EMPTY
            task.body = body
            task.color = binding.colorView.getSelectedColor()
            task.forDate = selectedDate.toString2()

            if (args.task == null) {
                viewModel.addTask(task)
                Toasty.success(requireActivity(), "تسک ایجاد شد").show()
            } else {
                viewModel.updateTask(task)
                Toasty.success(requireActivity(), "تسک ویرایش شد").show()
            }

            findNavController().navigateUp()
        }

    }

}