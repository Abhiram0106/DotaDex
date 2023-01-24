package com.example.dotadex.presentation.hero_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.remote.dto.toHeroDetail
import com.example.dotadex.domain.model.HeroDetailUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HeroDetailViewModel(
    private val heroDao: HeroDao
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<HeroDetailUiState>(HeroDetailUiState.Empty)
    val stateFlow: StateFlow<HeroDetailUiState> = _stateFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        HeroDetailUiState.Empty
    )


    fun getHeroById(heroID: Int) = viewModelScope.launch {
        _stateFlow.value = HeroDetailUiState.Loading

        heroDao.getHeroById(heroID).catch { exception ->
            _stateFlow.value = HeroDetailUiState.Error(exception.message.toString())
        }.collect { heroItemDto ->
            _stateFlow.value = HeroDetailUiState.Success(heroItemDto.toHeroDetail())
        }
    }
}