package com.example.ksw.todolist.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ksw.todolist.R
import com.example.ksw.todolist.database.entity.Todo
import org.jetbrains.anko.find

class TodoListAdapter(
    private val context: Context
): BaseAdapter() {

    private var list = emptyList<Todo>()

    override fun getCount(): Int {
       return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)
            viewHolder = ViewHolder()
            viewHolder.dateTextView = view.find(R.id.todoDate)
            viewHolder.todoTextView = view.find(R.id.todoTitle)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        if(list != null) {
            viewHolder.todoTextView?.text = list[position].title
            viewHolder.dateTextView?.text = DateFormat.format("yyyy/MM/dd", list[position].date)
        }

        return view
    }

    public fun setTodos(todos: List<Todo>) {
        list = todos
        notifyDataSetChanged()
    }

    companion object {
        private class ViewHolder {
            var todoTextView: TextView? = null
            var dateTextView: TextView? = null
        }
    }

}