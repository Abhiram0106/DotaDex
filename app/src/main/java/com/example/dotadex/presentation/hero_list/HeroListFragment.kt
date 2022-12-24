package com.example.dotadex.presentation.hero_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dotadex.databinding.FragmentHeroListBinding
import com.example.dotadex.domain.model.Hero
import com.example.dotadex.domain.model.HeroListUiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroListFragment : Fragment() {

    private var _binding: FragmentHeroListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeroListViewModel by viewModel<HeroListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeroListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val heroListAdapter = HeroListRecyclerAdapter(
            heroClickListener = { hero ->  
                Snackbar.make(view, hero.localized_name, Snackbar.LENGTH_SHORT).show()
            }
        )

        val tempList = listOf(
            Hero("1",1,1,1,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",1,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png","1","1","1",1,
                listOf<String>("1"))
        )
        heroListAdapter.submitList(tempList)

        binding.fabHero.setOnClickListener {
            viewModel.fetchHeroes()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect {
                when(it) {
                    is HeroListUiState.Success -> {
                        Log.d("data", it.heroList.toString())
                        heroListAdapter.submitList(it.heroList)
                    }
                    is HeroListUiState.Error -> {
                        Snackbar.make(view, it.message, Snackbar.LENGTH_SHORT).show()
                        Log.d("data", it.message)
                    }
                    is HeroListUiState.Loading -> {
                        Snackbar.make(view, "Loading", Snackbar.LENGTH_SHORT).show()
                        Log.d("data", "Loading")
                    }
                    else -> Unit
                }
            }
        }


        binding.rvHeroList.apply {
            layoutManager = LinearLayoutManager(binding.rvHeroList.context, RecyclerView.VERTICAL, false)
            hasFixedSize()
            adapter = heroListAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}