package com.example.dotadex.presentation.hero_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotadex.data.remote.dto.toHero
import com.example.dotadex.data.remote.repository.HeroRepositoryImpl
import com.example.dotadex.domain.model.HeroListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HeroListViewModel(
    private val repository: HeroRepositoryImpl
): ViewModel() {

    private val _stateFlow = MutableStateFlow<HeroListUiState>(HeroListUiState.Empty)
    val stateFlow: StateFlow<HeroListUiState> = _stateFlow

    fun fetchHeroes() = viewModelScope.launch {
        _stateFlow.value = HeroListUiState.Loading

        try {
            val heroes = repository.getHeroes().map { it.toHero() } // convert HeroItemDto to Hero coz don't want to deal with extra data
            _stateFlow.value = HeroListUiState.Success(heroes)
        }catch (e: Exception) {
            _stateFlow.value = HeroListUiState.Error(e.message ?: "Unknown error message")
        }

    }

}