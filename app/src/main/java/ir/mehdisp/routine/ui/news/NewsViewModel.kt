package ir.mehdisp.routine.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mehdisp.routine.data.repository.ParsijooRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: ParsijooRepository
) : ViewModel() {

    val latest = repository.latestNews().cachedIn(viewModelScope)

}