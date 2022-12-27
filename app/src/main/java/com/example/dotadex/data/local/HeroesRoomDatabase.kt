package com.example.dotadex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dotadex.data.remote.dto.HeroItemDto

@Database(entities = [HeroItemDto::class], version = 1) //exportSchema in app gradle android{ javaCompileOptions{} }
@TypeConverters(Converter::class)
abstract class HeroesRoomDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao

    companion object {


        @Volatile
        private var INSTANCE: HeroesRoomDatabase? = null

        fun getDatabase(context: Context): HeroesRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HeroesRoomDatabase::class.java,
                    "heroes_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}