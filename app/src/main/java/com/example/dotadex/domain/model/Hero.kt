package com.example.dotadex.domain.model

data class Hero(
    val hero_id: Int,
    val id: Int,
    val img: String,
    val localized_name: String,
    val primary_attr: String,
    val pro_win: Int,
    val pro_pick: Int
)
