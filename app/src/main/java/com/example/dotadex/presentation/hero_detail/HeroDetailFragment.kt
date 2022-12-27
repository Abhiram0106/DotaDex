package com.example.dotadex.presentation.hero_detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.dotadex.R
import com.example.dotadex.common.Constants
import com.example.dotadex.databinding.FragmentHeroDetailBinding
import com.example.dotadex.domain.model.HeroDetailUiState
import com.example.dotadex.presentation.hero_list.HeroListViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroDetailFragment : Fragment(R.layout.fragment_hero_detail) {

    private var _binding: FragmentHeroDetailBinding? = null
    private val binding get() = _binding!!

    private val args: HeroDetailFragmentArgs by navArgs()

    private val viewModel: HeroDetailViewModel by viewModel<HeroDetailViewModel>()

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


        lifecycleScope.launch {

            viewModel.getHeroById(args.heroId)

            viewModel.stateFlow.flowWithLifecycle(
                lifecycle = lifecycle,
                minActiveState = Lifecycle.State.STARTED
            ).collect {
                when (it) {
                    is HeroDetailUiState.Error -> {
                        Snackbar.make(view, it.message, Snackbar.LENGTH_SHORT).show()
                    }
                    is HeroDetailUiState.Loading -> {
                        Snackbar.make(view, "Loading", Snackbar.LENGTH_SHORT).show()
                    }
                    is HeroDetailUiState.Success -> {
                        binding.ivHeroImage.load("${Constants.HERO_RESOURCE_PREPEND}${it.hero.img}") {
                            placeholder(R.drawable.ic_baseline_downloading_24)
                            crossfade(true)
                            crossfade(400)
                        }
                        binding.tvHeroName.text = it.hero.localized_name
                    }
                    else -> Unit
                }
            }
        }


        startPostponedEnterTransition()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}