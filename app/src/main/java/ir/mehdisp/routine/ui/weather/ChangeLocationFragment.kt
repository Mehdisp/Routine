package ir.mehdisp.routine.ui.weather

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import ir.mehdisp.routine.databinding.FragmentChangeLocationBinding
import ir.mehdisp.routine.ui.base.BaseFragment
import ir.mehdisp.routine.utils.Constants
import javax.inject.Inject

@AndroidEntryPoint
class ChangeLocationFragment : BaseFragment() {

    private lateinit var binding: FragmentChangeLocationBinding
    private val viewModel: WeatherViewModel by activityViewModels()
    @Inject lateinit var sharedPreferences: SharedPreferences

    private val locationsAdapter = LocationsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = FragmentChangeLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = binding.searchEt.text?.toString() ?: "ایران"
                viewModel.search(text).observe(viewLifecycleOwner) {
                    binding.progress.isVisible = it.isLoading()
                    val cities = it.data?.result?.cityList ?: emptyList()

                    binding.empty.isVisible = !it.isLoading() && cities.isEmpty()
                    locationsAdapter.submitList(cities)
                }
            }

            return@setOnEditorActionListener false
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = locationsAdapter
        }

        locationsAdapter.setOnItemClickListener { city ->
            sharedPreferences.edit {
                putString(Constants.CITY_NAME, city.name)
            }
            Toasty.success(requireActivity(), "موقعیت با موفقیت تغییر کرد").show()
            findNavController().navigateUp()
        }
    }


}