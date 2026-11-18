package com.example.todo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class TodoViewModel : ViewModel() {
    companion object {
        private const val TAG = "TodoViewModel"
    }

    // _task i private -- only the ViewModel can modify it
    // This is a compose-aware mutable list that holds all current tasks
    private val _tasks = mutableStateListOf<Task>()

    // task is public -- the UI reads from the list but not modify it
    val tasks: List<Task> get() = _tasks


    // counter used for generate unique ids for each task
    private var nextId = 1


    // Adds a new task to the list
    // Does nothing if the title is blank
    fun addTask(title: String) {
        if (title.isBlank()) {
          //  Log.w(TAG, "addTask() called with blank title -- task was ignored")

            return
        }


        _tasks.add(Task(id = nextId++, title = title.trim()))
        // Log.d debug message useful during development to track the state of the app
       // Log.d(TAG, "Task added: '${title.trim()}' --totaltask : ${_tasks.size}")
    }
    // REMOVE THIS FUNCTION AFTER THE TESTS ARE COMPLETED
    //fun addTask(title: String) {
    // if (title.isBlank()) return
    // viewModelScope.launch is used to launch a coroutine
    // everything inside this block run on a background thread
    // viewModelScope.launch{
    // withContext switches the coroutine to the IO dispatcher
    // is designed for slow operations such as network requests or disk I/O
    // The main thread is completely free while other threads are blocked
    //   withContext(Dispatchers.IO){
    //   simulateSlowOperation() // now runs on a background thread -- UI is not affected
    //  }
    // }

    // _tasks.add(Task(id = nextId++, title = title.trim()))
    //  }}

    // Removes a task from the list
    fun removeTask(taskId: Int) {
        val taskToRemove = _tasks.find { it.id == taskId }
        if (taskToRemove != null) {
            _tasks.removeAll { it.id == taskId }
          //  Log.d(TAG, "Task removed: id= $taskId title = '${taskToRemove.title}'")
        } else {
            // Log.w warning message - removing a non-existent task
          //  Log.w(TAG, "removeTask() called with id=$taskId - task not found")
        }
    }


    // Returns the number of tasks in the list
    // Used in the unit test to check the number of tasks
    fun getTaskCount(): Int {
        return _tasks.size
    }

    fun containsTask(title: String): Boolean {
        return _tasks.any { it.title == title }
    }
}


