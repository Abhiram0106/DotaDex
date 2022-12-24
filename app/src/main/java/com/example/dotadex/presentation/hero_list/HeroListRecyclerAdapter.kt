package com.example.dotadex.presentation.hero_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dotadex.R
import com.example.dotadex.common.Constants
import com.example.dotadex.databinding.HeroListItemBinding
import com.example.dotadex.domain.model.Hero

class HeroListRecyclerAdapter(
    private var heroClickListener: ( (hero: Hero) -> Unit )
) : ListAdapter<Hero, HeroListRecyclerAdapter.HeroesListViewHolder>(HeroesDiffUtilCallBack()) {

    inner class HeroesListViewHolder(private val binding: HeroListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) = binding.apply {
            tvHeroName.text = hero.localized_name
            tvPrimaryAttribute.text = hero.primary_attr
            tvWinRate.text = tvWinRate.context.getString(R.string.win_rate, hero.pro_win)
            sivHeroImage.load("${Constants.HERO_RESOURCE_PREPEND}${hero.img}"){
                placeholder(R.drawable.ic_baseline_downloading_24)
                crossfade(true)
                crossfade(400)
            }
            binding.root.setOnClickListener {
                heroClickListener(hero)
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