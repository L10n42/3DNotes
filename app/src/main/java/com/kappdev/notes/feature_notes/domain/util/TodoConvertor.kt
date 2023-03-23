package com.kappdev.notes.feature_notes.domain.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kappdev.notes.feature_notes.domain.model.Todo

class TodoConvertor {

    @TypeConverter
    fun todoListToJson(list: List<Todo>): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToTodoList(json: String): List<Todo> = Gson().fromJson(json, object : TypeToken<List<Todo>>() {}.type)
}