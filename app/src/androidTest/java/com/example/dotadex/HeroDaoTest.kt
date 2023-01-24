package com.example.dotadex

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.dotadex.data.local.HeroDao
import com.example.dotadex.data.local.HeroesRoomDatabase
import com.example.dotadex.data.remote.dto.HeroItemDto
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HeroDaoTest {

    private lateinit var heroDatabase: HeroesRoomDatabase
    private lateinit var heroDao: HeroDao

    @Before
    fun setUp() {
        heroDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HeroesRoomDatabase::class.java
        ).allowMainThreadQueries().build()

        heroDao = heroDatabase.heroDao()
    }

    @Test
    fun insertHero_expectedSingleHero() {
        val hero = HeroItemDto(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1.0,1,1.0,"a",1,1.0,1,1,1,1.0,1,1,1.0,1,1,true,1,"/apps/dota2/images/dota_react/heroes/icons/antimage.png?",1,"/apps/dota2/images/dota_react/heroes/antimage.png?",1.0,1,"test",1,"test hero",1,1,"non",1,1,1,1,
            listOf<String>("test","ing"),1.0,1,1,1.0)

        runTest {
            heroDao.insertHero(hero)
        }

        val result = heroDao.getHeroes()

        Assert.assertEquals(1, result.size)
        Assert.assertEquals("test hero", result[0].name)

    }

    @After
    fun tearDown() {
        heroDatabase.close()
    }
}