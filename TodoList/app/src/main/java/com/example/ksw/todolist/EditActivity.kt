package com.example.ksw.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import com.example.ksw.todolist.database.dao.TodoDao
import com.example.ksw.todolist.database.entity.Todo
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.item_todo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.lang.IllegalArgumentException
import java.util.*

const val EDIT_ACTIVITY_LOG_TAG = "EditActivity"
const val MODE_UPDATE = "update"
const val MODE_INSERT = "insert"
const val MODE_DELETE = "delete"

class EditActivity: AppCompatActivity() {

//    private val dataSource by lazy {
//        Injection.provideTodoResource(this)
//    }
    private val mCalendar by lazy {
        Calendar.getInstance()
    }
    private var mTodo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        mTodo = intent.getParcelableExtra<Todo?>("item")
        if(mTodo == null) {
            Log.d(EDIT_ACTIVITY_LOG_TAG, "INSERT MODE")
            deleteFab.hide()
        } else {
            Log.d(EDIT_ACTIVITY_LOG_TAG, "UPDATE MODE")
            showSelectedTodo(mTodo)
            deleteFab.show()
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, month)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        doneFab.setOnClickListener {
            val mode = updateTodo()
            completeTodoUpdate(mode)
        }

        deleteFab.setOnClickListener{
            completeTodoUpdate(MODE_DELETE)
        }

    }

    private fun showSelectedTodo(todo: Todo?) {
        todo?.let {
            todoEditText.setText(todo.title)
            calendarView.date = todo.date
        }
    }

    private fun updateTodo(): String {
        val title = todoEditText.text.toString()
        val date = mCalendar.timeInMillis

        return when(mTodo == null) {
            true -> {
                mTodo = Todo(
                    title = title,
                    date = date
                )
                MODE_INSERT
            }
            false -> {
                mTodo?.title = title
                mTodo?.date = date
                MODE_UPDATE
            }
        }
    }

    private fun completeTodoUpdate(mode: String) {
        Log.d(EDIT_ACTIVITY_LOG_TAG, "$mode, Item $mTodo")
        val intent = Intent()
        intent.putExtra("item", mTodo)
        intent.putExtra("mode", mode)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}