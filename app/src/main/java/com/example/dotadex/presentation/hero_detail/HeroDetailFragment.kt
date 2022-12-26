package com.example.dotadex.presentation.hero_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dotadex.R
import com.example.dotadex.databinding.FragmentHeroDetailBinding

class HeroDetailFragment : Fragment(R.layout.fragment_hero_detail) {

    var _binding: FragmentHeroDetailBinding? = null
    val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeroDetailBinding.bind(view)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}