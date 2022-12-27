package com.example.dotadex.presentation.hero_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotadex.data.remote.dto.toHero
import com.example.dotadex.domain.model.HeroListUiState
import com.example.dotadex.domain.repository.HeroRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HeroListViewModel(
    private val repository: HeroRepository
): ViewModel() {

    private val _stateFlow = MutableStateFlow<HeroListUiState>(HeroListUiState.Empty)
    val stateFlow: StateFlow<HeroListUiState> = _stateFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HeroListUiState.Empty)
//    the .stateIn explained in https://youtu.be/fSB6_KE95bU?t=981

    init {
        fetchHeroes()
    }

    private fun fetchHeroes() = viewModelScope.launch {
        _stateFlow.value = HeroListUiState.Loading

            repository.getHeroes().catch { exception ->
                _stateFlow.value = HeroListUiState.Error(exception.message.toString())
                Log.d("HeroListUiState.Error", exception.message.toString())
            }.collect { heroesList ->
                _stateFlow.value = HeroListUiState.Success(heroesList.map { it.toHero() })
            }
    }

}