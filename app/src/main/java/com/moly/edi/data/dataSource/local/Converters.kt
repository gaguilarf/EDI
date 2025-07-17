package com.moly.edi.data.dataSource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moly.edi.domain.model.Project

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromProjectList(value: List<Project>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toProjectList(value: String): List<Project> {
        val listType = object : TypeToken<List<Project>>() {}.type
        return Gson().fromJson(value, listType)
    }
}

