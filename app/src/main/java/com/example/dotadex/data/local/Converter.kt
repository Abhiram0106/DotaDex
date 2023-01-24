package com.example.dotadex.data.local

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromStringListToString(value: List<String>): String {
        return value.toString()
    }

    @TypeConverter
    fun fromStringToStringList(value: String): List<String> {
        return listOf(value)
    }
}