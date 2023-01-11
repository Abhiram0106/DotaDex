package com.example.dotadex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dotadex.data.remote.dto.HeroItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes_table ORDER BY hero_id DESC")
    fun getHeroes(): List<HeroItemDto>

    @Query("SELECT * FROM heroes_table WHERE id = :heroID")
    fun getHeroById(heroID: Int): Flow<HeroItemDto>

    @Query("SELECT * FROM heroes_table WHERE localized_name LIKE :name || '%'")
    fun getHeroByName(name: String): Flow<List<HeroItemDto>>

    @Query("SELECT * FROM heroes_table WHERE primary_attr IN (:filter)")
    fun filterByAttribute(filter: List<String>): Flow<List<HeroItemDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHero(heroItemDto: HeroItemDto)

}