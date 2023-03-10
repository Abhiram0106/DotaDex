package com.example.dotadex.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dotadex.domain.model.Hero
import com.example.dotadex.domain.model.HeroDetail

@kotlinx.serialization.Serializable
@Entity(tableName = "heroes_table")
data class HeroItemDto(
    val `1_pick`: Int,
    val `1_win`: Int,
    val `2_pick`: Int,
    val `2_win`: Int,
    val `3_pick`: Int,
    val `3_win`: Int,
    val `4_pick`: Int,
    val `4_win`: Int,
    val `5_pick`: Int,
    val `5_win`: Int,
    val `6_pick`: Int,
    val `6_win`: Int,
    val `7_pick`: Int,
    val `7_win`: Int,
    val `8_pick`: Int,
    val `8_win`: Int,
    val agi_gain: Double,
    val attack_range: Int,
    val attack_rate: Double,
    val attack_type: String,
    val base_agi: Int,
    val base_armor: Double,
    val base_attack_max: Int,
    val base_attack_min: Int,
    val base_health: Int,
    val base_health_regen: Double,
    val base_int: Int,
    val base_mana: Int,
    val base_mana_regen: Double,
    val base_mr: Int,
    val base_str: Int,
    val cm_enabled: Boolean,
    val hero_id: Int,
    val icon: String,
    @PrimaryKey val id: Int,
    val img: String,
    val int_gain: Double,
    val legs: Int,
    val localized_name: String,
    val move_speed: Int,
    val name: String,
    val null_pick: Int,
    val null_win: Int,
    val primary_attr: String,
    val pro_ban: Int,
    val pro_pick: Int,
    val pro_win: Int,
    val projectile_speed: Int,
    val roles: List<String>,
    val str_gain: Double,
    val turbo_picks: Int,
    val turbo_wins: Int,
    val turn_rate: Double? = 0.0
)

fun HeroItemDto.toHero(): Hero {
    return Hero(
        hero_id = hero_id,
        id = id,
        img = img,
        localized_name = localized_name,
        pro_win = pro_win,
        primary_attr = primary_attr,
        pro_pick = pro_pick
    )
}

fun HeroItemDto.toHeroDetail(): HeroDetail {
    return HeroDetail(
        attack_type = attack_type,
        attack_range = attack_range,
        attack_rate = attack_rate,
        base_attack_max = base_attack_max,
        base_attack_min = base_attack_min,
        base_health = base_health,
        base_health_regen = base_health_regen,
        base_mana = base_mana,
        base_mana_regen = base_mana_regen,
        hero_id = hero_id,
        icon = icon,
        id = id,
        img = img,
        localized_name = localized_name,
        pro_ban = pro_ban,
        pro_pick = pro_pick,
        pro_win = pro_win,
        name = name,
        primary_attr = primary_attr,
        roles = roles,
        base_armor = base_armor,
        base_mr = base_mr,
        move_speed = move_speed,
        turn_rate = turn_rate,
        base_str = base_str,
        str_gain = str_gain,
        base_agi = base_agi,
        agi_gain = agi_gain,
        base_int = base_int,
        int_gain = int_gain
    )
}