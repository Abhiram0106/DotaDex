package com.example.dotadex.domain.model

sealed class HeroDetailUiState {

    data class Success(val hero: Hero) : HeroDetailUiState()
    data class Error(val message: String) : HeroDetailUiState()
    object Loading : HeroDetailUiState()
    object Empty : HeroDetailUiState()

}