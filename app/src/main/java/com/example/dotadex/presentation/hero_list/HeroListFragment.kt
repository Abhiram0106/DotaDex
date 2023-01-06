package com.example.dotadex.presentation.hero_list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dotadex.R
import com.example.dotadex.databinding.FragmentHeroListBinding
import com.example.dotadex.domain.model.HeroListUiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class
HeroListFragment : Fragment(R.layout.fragment_hero_list) {

    private var _binding: FragmentHeroListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeroListViewModel by viewModel<HeroListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeroListBinding.bind(view)

        postponeEnterTransition()

        val heroListAdapter = HeroListRecyclerAdapter(
            heroClickListener = { heroID, heroImg, heroName, primAtr, proWinPercent ->
                Snackbar.make(view, heroID.toString(), Snackbar.LENGTH_SHORT).show()
                val action = HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(heroID)
                val extras = FragmentNavigatorExtras(heroImg to "detail_img", heroName to "detail_name", primAtr to "detail_primary_atr", proWinPercent to "detail_pro_win_percent")
                findNavController().navigate(
                    action,
                    extras
                )
            }
        )

//        lifecycleScope.launchWhenStarted {} ctrl click on .launchWhenStarted
        lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            Here, .repeatOnLifecycle(Lifecycle.State.STARTED) is useful when collecting multiple flows
//            But for single, .flowWithLifecycle is suitable
//            https://youtu.be/fSB6_KE95bU?t=717
            viewModel.stateFlow.flowWithLifecycle(
                lifecycle = lifecycle,
                minActiveState = Lifecycle.State.STARTED
            ).collect {
                viewModel.stateFlow.collect {
                    when (it) {
                        is HeroListUiState.Success -> {
                            Log.d("HeroListUiState.Success", it.heroList.toString())
                            heroListAdapter.submitList(it.heroList)
                        }
                        is HeroListUiState.Error -> {
                            Snackbar.make(view, it.message, Snackbar.LENGTH_SHORT).show()
                            Log.d("HeroListUiState.Error", it.message)
                        }
                        is HeroListUiState.Loading -> {
                            Log.d("HeroListUiState.Loading","loading")
                            heroListAdapter.submitList(emptyList())
                        }
                        else -> Unit
                    }
                }
            }
        }

        binding.svSearchHeroes.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.fetchHeroByName(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.fetchHeroByName(newText)
                return true
            }
        })


        binding.rvHeroList.apply {
            layoutManager =
                LinearLayoutManager(binding.rvHeroList.context, RecyclerView.VERTICAL, false)
            hasFixedSize()
            adapter = heroListAdapter
        }

        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}