package com.example.ksw.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ksw.todolist.adapter.TodoListAdapter
import com.example.ksw.todolist.database.entity.Todo
import com.example.ksw.todolist.model.TodoViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivityForResult

const val LOG_TAG = "TodoMainActivity"
const val REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {

    private val dataSource by lazy {
        Injection.provideTodoResource(this)
    }
    private lateinit var mTodoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = TodoListAdapter(this)
        todoListView.adapter = adapter
        mTodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        mTodoViewModel.allTodos.observe(this, Observer {
            todo ->
                 todo?.let {
                    adapter.setTodos(it)
                 }
        })

        addFab.setOnClickListener {
            moveEditActivity(null)
        }

        todoListView.setOnItemClickListener { parent, view, position, id ->
            val todo = parent.getItemAtPosition(position) as Todo
            moveEditActivity(todo)
        }

    }

//    private fun setTodoListAdapter() {
//        GlobalScope.launch(Dispatchers.Main) {
//            val todoList = async(Dispatchers.IO) {
//                dataSource.getAllTodoList()
//            }
//            makeTodoListView(todoList.await())
//        }
//
//    }
//
//    private fun makeTodoListView(list: List<Todo>) {
//        val adapter = TodoListAdapter(this)
//        todoListView.adapter = adapter
//    }

    private fun moveEditActivity(todo: Todo?) {
        startActivityForResult<EditActivity>(
            REQUEST_CODE,
            "item" to todo
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE -> {
                    val todo = data?.getParcelableExtra<Todo>("item")
                    val mode = data?.getStringExtra("mode")
                    if(todo != null) {
                        Log.d(LOG_TAG, "$mode, Item : $todo")
                        when(mode) {
                            MODE_INSERT -> mTodoViewModel.insert(todo)
                            MODE_UPDATE -> mTodoViewModel.update(todo)
                            MODE_DELETE -> mTodoViewModel.delete(todo)
                            else -> Log.d(LOG_TAG, "Unknown mode...")
                        }
                    }
                }
            }
        }
    }

}
