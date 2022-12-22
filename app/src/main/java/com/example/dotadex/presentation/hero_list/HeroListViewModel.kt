package com.example.dotadex.presentation.hero_list

import androidx.lifecycle.ViewModel
import com.example.dotadex.domain.repository.HeroRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeroListViewModel(
    private val repository: HeroRepository
): ViewModel() {

    fun getHeroes() {
        CoroutineScope(Dispatchers.IO).launch{
            repository.getHeroes()
        }
    }

}