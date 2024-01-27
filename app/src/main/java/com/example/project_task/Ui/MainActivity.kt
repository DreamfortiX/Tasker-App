package com.example.project_task.Ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_task.Adapters.Adapter_task
import com.example.project_task.Modals.Task_Modal
import com.example.project_task.ViewModals.UserViewModal
import com.example.project_task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModal
    private var listlist: ArrayList<Task_Modal> = arrayListOf()
    private val taskListAdapter = Adapter_task(this, listlist)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UserViewModal::class.java)

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = taskListAdapter

        taskListAdapter.setOnTaskEditClickListener { position ->
            val clickedTask = listlist[position]
            // Start an activity with the data of the clicked task for editing
            startActivity(Intent(this, update_task::class.java).putExtra("task", clickedTask))
        }

        taskListAdapter.setOnTaskDeleteClickListener { position ->
            val clickedTask = listlist[position]
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Are you sure you want to delete the task?")
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                viewModel.del(clickedTask)
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ ->

            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        viewModel.retriveTask().observe(this) { taskList ->
            if (taskList != null) {
                listlist.clear()
                listlist.addAll(taskList)
                taskListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.floting.setOnClickListener {
            startActivity(Intent(this, Add_Task::class.java))
        }
    }
}
