package com.example.dotadex.presentation.hero_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dotadex.R
import com.example.dotadex.common.Constants
import com.example.dotadex.databinding.HeroListItemBinding
import com.example.dotadex.domain.model.Hero

class HeroListRecyclerAdapter(
    private var heroClickListener: ((heroID: Int, heroImg: ImageView, heroName: TextView, primAtr: TextView, proWinPercent: TextView) -> Unit)
) : ListAdapter<Hero, HeroListRecyclerAdapter.HeroesListViewHolder>(HeroesDiffUtilCallBack()) {

    inner class HeroesListViewHolder(private val binding: HeroListItemBinding) :

        RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) = binding.apply {
            tvHeroName.text = hero.localized_name

            tvPrimaryAttribute.text = when (hero.primary_attr) {
                "agi" -> tvPrimaryAttribute.context.getString(R.string.agility)
                "str" -> tvPrimaryAttribute.context.getString(R.string.strength)
                "int" -> tvPrimaryAttribute.context.getString(R.string.intelligence)
                else -> tvPrimaryAttribute.context.getString(R.string.error)
            }

            val winPercent = ((hero.pro_win.toDouble() / hero.pro_pick.toDouble()) * 100)
            tvWinRate.text = tvWinRate.context.getString(R.string.pro_winPercentage, winPercent)
            if (winPercent >= 50) {
                tvWinRate.setTextColor(tvWinRate.context.getColor(R.color.health_start))
            } else {
                tvWinRate.setTextColor(tvWinRate.context.getColor(R.color.DotaRed))
            }

            sivHeroImage.load("${Constants.HERO_RESOURCE_PREPEND}${hero.img}") {
                crossfade(true)
                crossfade(400)
            }

            ViewCompat.setTransitionName(sivHeroImage, "hero_img_${hero.id}")
            ViewCompat.setTransitionName(tvHeroName, "hero_name_${hero.id}")
            ViewCompat.setTransitionName(tvPrimaryAttribute, "hero_primary_atr_${hero.id}")
            ViewCompat.setTransitionName(tvWinRate, "hero_pro_win_percent_${hero.id}")

            binding.root.setOnClickListener {
                heroClickListener(hero.id, sivHeroImage, tvHeroName, tvPrimaryAttribute, tvWinRate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HeroListItemBinding.inflate(layoutInflater, parent, false)
        return HeroesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroesListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HeroesDiffUtilCallBack : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean {
            return oldItem == newItem
        }
    }


}