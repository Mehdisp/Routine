package ir.mehdisp.routine.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import ir.mehdisp.routine.data.models.Task
import ir.mehdisp.routine.databinding.FragmentTasksBinding
import ir.mehdisp.routine.ui.base.BaseFragment
import ir.mehdisp.routine.ui.shared.AppAlertDialog
import ir.mehdisp.persiancalendar.CalendarHandler
import ir.mehdisp.persiancalendar.models.Day

@AndroidEntryPoint
class TasksFragment : BaseFragment() {

    private lateinit var binding: FragmentTasksBinding
    private val viewModel: TasksViewModel by activityViewModels()
    private var taskLiveData: LiveData<List<Task>>? = null

    private val todayDate = CalendarHandler.getToday()
    private val thisMonthDays: List<Day> by lazy {
        CalendarHandler(requireActivity()).getDays(0)
    }

    private val tasksAdapter = TasksAdapter()
    private val daysAdapter = DaysAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments ?: Bundle.EMPTY
        val args = TasksFragmentArgs.fromBundle(bundle)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        daysAdapter.submitList(thisMonthDays) {
            binding.daysRecycler.scrollToPosition(thisMonthDays.indexOfFirst { it.isToday })
        }

        binding.daysRecycler.apply {
            setHasFixedSize(true)
            isVisible = args.showDate == null
            adapter = daysAdapter
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = tasksAdapter
        }

        tasksAdapter.setOnItemClickListener {
            val action = TasksFragmentDirections.actionTasksToAddTask(it)
            findNavController().navigate(action)
        }

        tasksAdapter.setOnCheckBoxClickListener { task ->
            viewModel.setAsDone(task.id, !task.done)
        }

        tasksAdapter.setOnDeleteClickListener {
            AppAlertDialog.Builder(requireActivity())
                .setTitle("حذف تسک")
                .setMessage("آیا برای اینکار مطمئنید؟")
                .setPositiveButton("بله") {
                    viewModel.deleteTask(it)
                    Toasty.success(requireActivity(), "تسک حذف شد").show()
                }
                .show()
        }

        binding.addTask.setOnClickListener {
            val action = TasksFragmentDirections.actionTasksToAddTask(null)
            findNavController().navigate(action)
        }

        daysAdapter.setOnItemClickListener {
            getTasks(it.persianDate.toString2())
        }

        getTasks(args.showDate ?: todayDate.toString2())
    }

    private fun getTasks(date: String) {
        taskLiveData?.removeObservers(viewLifecycleOwner)

        taskLiveData = viewModel.tasksForDate(date)

        taskLiveData?.observe(viewLifecycleOwner) { tasks ->
            binding.emptyLayout.isVisible = tasks.isEmpty()
            tasksAdapter.submitList(tasks)
        }
    }

}