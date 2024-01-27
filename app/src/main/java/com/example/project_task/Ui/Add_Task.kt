package com.example.project_task.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.project_task.Modals.Task_Modal
import com.example.project_task.ViewModals.UserViewModal
import com.example.project_task.databinding.ActivityAddTaskBinding

class Add_Task : AppCompatActivity() {
    private lateinit var viewModel: UserViewModal

    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UserViewModal::class.java)





        binding.cancelTask.setOnClickListener()
        {
          finish()
        }

        binding.AddTask.setOnClickListener() {
            var title = binding.editTextTitle.text.toString()
            var id = binding.editTextid.text.toString()
            var description = binding.editTextDescription.text.toString()
            var date = binding.editTextDate.text.toString()

            if (title.isEmpty() && description.isEmpty() && date.isEmpty()) {

              binding.editTextTitle.error = "Title cannot be empty"
                binding.editTextDescription.error = "Description cannot be empty"
                binding.editTextDate.error="Date cannot be empty"

            }
            else {
                val modal = Task_Modal(title, description, date, id)
                var result=true
                viewModel.addtask(modal).observe(this) { result ->
                    if (result == true) {
                        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
}