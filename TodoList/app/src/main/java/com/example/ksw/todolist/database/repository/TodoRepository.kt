package com.example.ksw.todolist.database.repository

import androidx.lifecycle.LiveData
import com.example.ksw.todolist.database.dao.TodoDao
import com.example.ksw.todolist.database.entity.Todo

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Todo>> =
        todoDao.getAllTodoListForLiveData()

    suspend fun getTodoById(id: Int) =
        todoDao.getTodoById(id)

    suspend fun insert(todo: Todo) =
        todoDao.insertTodo(todo)

    suspend fun update(todo: Todo) =
        todoDao.updateTodo(todo)

    suspend fun delete(todo: Todo) =
        todoDao.deleteTodo(todo)

}