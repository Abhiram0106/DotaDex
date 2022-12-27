package com.example.dotadex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dotadex.data.remote.dto.HeroItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Query("SELECT * FROM heroes_table ORDER BY id ASC")
    fun getHeroes(): Flow<List<HeroItemDto>>

    @Query("SELECT * FROM heroes_table WHERE id = :heroID")
    fun getHeroById(heroID: Int): Flow<HeroItemDto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHero(heroItemDto: HeroItemDto)

}