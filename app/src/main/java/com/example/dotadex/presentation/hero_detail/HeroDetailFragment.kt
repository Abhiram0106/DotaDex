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
import com.example.dotadex.domain.model.HeroDetail
import com.example.dotadex.domain.model.HeroDetailUiState
import com.google.android.material.snackbar.Snackbar
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
        ViewCompat.setTransitionName(binding.tvPrimaryAttribute, "detail_primary_atr")
        ViewCompat.setTransitionName(binding.tvWinPercentage, "detail_pro_win_percent")


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
                        setUI(it.hero)
                    }
                    else -> Unit
                }
            }
        }


        startPostponedEnterTransition()
    }

    private fun setUI(hero: HeroDetail) {
        binding.apply {

            ivHeroImage.load("${Constants.HERO_RESOURCE_PREPEND}${hero.img}") {
                placeholder(R.drawable.ic_baseline_downloading_24)
                crossfade(true)
                crossfade(400)
            }

            ivHeroIcon.load("${Constants.HERO_RESOURCE_PREPEND}${hero.icon}") {
                placeholder(R.drawable.ic_baseline_downloading_24)
                crossfade(true)
                crossfade(400)
            }

            tvHeroName.text = getString(R.string.hero_name, hero.localized_name)

            val hp = hero.base_health + (hero.base_str*20)
            tvHealthBase.text = getString(R.string.base_health, hp)
            val hpRegen = hero.base_health_regen + (hero.base_str*0.1)
            tvHealthRegen.text = getString(R.string.base_health_regen, hpRegen)

            val mana = hero.base_mana + (hero.base_int*12)
            tvManaBase.text = getString(R.string.base_mana, mana)
            val manaRegen = hero.base_mana_regen + (hero.base_int*0.05)
            tvManaRegen.text = getString(R.string.base_mana_regen, manaRegen)

            tvPrimaryAttribute.text = getString(R.string.primary_attribute, when(hero.primary_attr) {
                "str" -> "Strength"
                "agi" -> "Agility"
                "int" -> "Intelligence"
                else -> "Error"
            })
            tvRoles.text = getString(R.string.roles, hero.roles)

            tvStrBase.text = getString(R.string.base_str, hero.base_str)
            tvStrGain.text = getString(R.string.str_gain, hero.str_gain)

            tvAgiBase.text = getString(R.string.base_agi, hero.base_agi)
            tvAgiGain.text = getString(R.string.agi_gain, hero.agi_gain)

            tvIntBase.text = getString(R.string.base_int, hero.base_int)
            tvIntGain.text = getString(R.string.int_gain, hero.int_gain)

            tvAttackDmg.text = getString(R.string.attack_dmg, hero.base_attack_min, hero.base_attack_max)
            tvAttackRange.text = getString(R.string.attack_range, hero.attack_range)
            tvAttackRate.text = getString(R.string.attack_rate, hero.attack_rate)

            tvArmour.text = getString(R.string.armour, hero.base_armor)
            tvMargicRes.text = getString(R.string.magic_res, hero.base_mr)

            tvMoveSpeed.text = getString(R.string.move_speed, hero.move_speed)
            tvTurnRate.text = getString(R.string.turn_rate, hero.turn_rate)

            tvProPicks.text = getString(R.string.pro_pick, hero.pro_pick)
            tvProWins.text = getString(R.string.pro_wins, hero.pro_win)
            tvProBans.text = getString(R.string.pro_ban, hero.pro_ban)

            val winPercent = ((hero.pro_win.toDouble() / hero.pro_pick.toDouble()) * 100)
            tvWinPercentage.text = getString(R.string.pro_winPercentage, winPercent)
            if(winPercent >= 50) {
                tvWinPercentage.setTextColor(tvWinPercentage.context.getColor(R.color.health_start))
            } else {
                tvWinPercentage.setTextColor(tvWinPercentage.context.getColor(R.color.dota_red))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}