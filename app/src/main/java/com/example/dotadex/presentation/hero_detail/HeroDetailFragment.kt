package com.example.dotadex.presentation.hero_detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.dotadex.R
import com.example.dotadex.databinding.FragmentHeroDetailBinding

class HeroDetailFragment : Fragment(R.layout.fragment_hero_detail) {

    var _binding: FragmentHeroDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeroDetailBinding.bind(view)

        postponeEnterTransition()

        ViewCompat.setTransitionName(binding.ivHeroImage, "detail_img")
        ViewCompat.setTransitionName(binding.tvHeroName, "detail_name")

        binding.ivHeroImage.setImageResource(R.drawable.ic_baseline_downloading_24)
        binding.tvHeroName.text = "TEST"


        startPostponedEnterTransition()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}