package com.example.dotadex.domain.model

data class Hero(
    val attack_type: String,
    val base_health: Int,
    val base_mana: Int,
    val hero_id: Int,
    val icon: String,
    val id: Int,
    val img: String,
    val localized_name: String,
    val name: String,
    val primary_attr: String,
    val pro_win: Int,
    val roles: List<String>
)
