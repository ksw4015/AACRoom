package com.example.ksw.todolist.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ksw.todolist.database.entity.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    suspend fun getAllTodoList(): List<Todo>

    @Query("SELECT * FROM todo_table")
    fun getAllTodoListForLiveData(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE date = :date")
    suspend fun getTodoByDate(date: Long): Todo

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo): Long

    @Delete
    suspend fun deleteTodo(todo: Todo): Int

    @Update
    suspend fun updateTodo(todo: Todo): Int

}