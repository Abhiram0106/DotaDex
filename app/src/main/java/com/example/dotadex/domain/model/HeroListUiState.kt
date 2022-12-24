package com.example.dotadex.domain.model

sealed class HeroListUiState {
    data class Success(val heroList: List<Hero>) : HeroListUiState()
    data class Error(val message: String) : HeroListUiState()
    object Loading : HeroListUiState()
    object Empty : HeroListUiState()
}
