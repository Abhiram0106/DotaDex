package com.example.dotadex.presentation.hero_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotadex.data.remote.dto.toHero
import com.example.dotadex.domain.model.Hero
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
            _stateFlow.value = HeroListUiState.Error("fetchHeroes ${exception.message}")
            Log.d("HeroListUiState.Error", "fetchHeroes ${exception.message}")
        }.collect { heroesList ->
            _stateFlow.value = HeroListUiState.Success(heroList = heroesList.map { it.toHero() })
        }
    }

    fun fetchHeroByName(heroName: String?) = viewModelScope.launch {
        _stateFlow.value = HeroListUiState.Loading

        if(heroName.isNullOrEmpty()) {
            repository.getHeroesOffline().catch {  exception ->
                _stateFlow.value = HeroListUiState.Error("fetchHeroByName ${exception.message}")
                Log.d("HeroListUiState.Error", "fetchHeroByName ${exception.message}")
            }.collect{ heroList ->
                _stateFlow.value = HeroListUiState.Success(heroList = heroList.map { it.toHero() })
            }
            return@launch
        }

        repository.getHeroByName(heroName).catch { exception ->
            _stateFlow.value = HeroListUiState.Error("fetchHeroByName ${exception.message}")
            Log.d("HeroListUiState.Error", "fetchHeroByName ${exception.message}")
        }.collect{ heroList ->
            _stateFlow.value = HeroListUiState.Success(heroList = heroList.map { it.toHero() })
        }
    }

}