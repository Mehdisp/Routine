package ir.mehdisp.routine.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import ir.mehdisp.routine.databinding.FragmentNewsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentNewsBinding
    private val newsAdapter = NewsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        newsAdapter.addLoadStateListener {
            binding.progress.isVisible = it.source.refresh == LoadState.Loading
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        lifecycleScope.launch {
            viewModel.latest.collectLatest {
                newsAdapter.submitData(it)
            }
        }

        newsAdapter.setOnItemClickListener {
            val action = NewsFragmentDirections.actionNewsToWeb(it.url)
            findNavController().navigate(action)
        }

    }

}