package com.example.ksw.todolist

import android.content.Context
import com.example.ksw.todolist.database.TodoDatabase
import com.example.ksw.todolist.database.dao.TodoDao

object Injection {

    fun provideTodoResource(context: Context): TodoDao {
        val database = TodoDatabase.getInstance(context)
        return database.todoDao()
    }

}