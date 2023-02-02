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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                val action =
                    HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(heroID)
                val extras = FragmentNavigatorExtras(
                    heroImg to "detail_img",
                    heroName to "detail_name",
                    primAtr to "detail_primary_atr",
                    proWinPercent to "detail_pro_win_percent"
                )
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
                when (it) {
                    is HeroListUiState.Success -> {
                        Log.d("HeroListUiState.Success", it.heroList.toString())
                        heroListAdapter.submitList(it.heroList)
                    }
                    is HeroListUiState.Error -> {
//                            Snackbar interferes with Fab testing
                        Snackbar.make(
                            view,
                            "${it.message}, loading local",
                            Snackbar.LENGTH_LONG
                        ).show()
                        Log.d("HeroListUiState.Error", it.message)
                    }
                    is HeroListUiState.Loading -> {
                        Log.d("HeroListUiState.Loading", "loading")
                        heroListAdapter.submitList(emptyList())
                    }
                    else -> Unit
                }

            }
        }

        binding.svSearchHeroes.setOnQueryTextListener(object : OnQueryTextListener {
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

        binding.rvHeroList.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            Log.d(
                "scroll",
                "scrollX=$scrollX, scrollY=$scrollY, oldScrollX=$oldScrollX, oldScrollY=$oldScrollY"
            )

            if (oldScrollY > 0) {
                binding.fabFilter.show()
            } else if (oldScrollY < 0 && binding.fabFilter.isShown) {
                binding.fabFilter.hide()
            }
        }

        binding.fabFilter.setOnClickListener {

            val selectedItems = mutableListOf<Int>()

            val dialogueBuilder = MaterialAlertDialogBuilder(requireContext())
            dialogueBuilder.setTitle(R.string.filter_dialogue_title)
                .setMultiChoiceItems(R.array.attributes, null) { dialog, which, isChecked ->
                    if (isChecked) {
                        // If the user checked the item, add it to the selected items
                        selectedItems.add(which)
                    } else if (selectedItems.contains(which)) {
                        // Else, if the item is already in the array, remove it
                        selectedItems.remove(which)
                    }
                }

                .setPositiveButton(R.string.ok) { dialog, id ->
                    val filter = mutableListOf<String>()
                    selectedItems.forEach {
                        when (it) {
                            0 -> filter.add("str")
                            1 -> filter.add("agi")
                            2 -> filter.add("int")
                        }
                    }
                    viewModel.filterList(filter)
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
//                    Nothing
                }

            dialogueBuilder.create().show()


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