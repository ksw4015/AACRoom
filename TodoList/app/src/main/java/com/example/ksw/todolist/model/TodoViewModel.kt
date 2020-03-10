package com.example.ksw.todolist.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ksw.todolist.Injection
import com.example.ksw.todolist.database.entity.Todo
import com.example.ksw.todolist.database.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {

    private val repository:  TodoRepository
    val allTodos: LiveData<List<Todo>>

    init {
        val todoDao = Injection.provideTodoResource(application.applicationContext)
        repository = TodoRepository(todoDao)
        allTodos = repository.allTodos
    }

    fun insert(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun update(todo: Todo) = viewModelScope.launch {
        repository.update(todo)
    }

    fun delete(todo: Todo) = viewModelScope.launch {
        repository.delete(todo)
    }

}