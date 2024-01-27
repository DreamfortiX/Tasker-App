package com.example.project_task.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.project_task.Modals.Task_Modal
import com.example.project_task.R
import com.example.project_task.ViewModals.UserViewModal
import com.example.project_task.databinding.ActivityAddTaskBinding
import com.example.project_task.databinding.ActivityUpdateTaskBinding

class update_task : AppCompatActivity() {


    private lateinit var viewModel: UserViewModal

    private lateinit var binding: ActivityUpdateTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(UserViewModal::class.java)




        val task = intent.getParcelableExtra<Task_Modal>("task")
        if (task != null) {
            // Populate UI fields with task data
            binding.editTextTitle.setText(task.title)
            binding.editTextDescription.setText(task.description)
            binding.editTextDate.setText(task.date)
            binding.editTextid.setText(task.id)

        } else {
            Toast.makeText(this, "Null data occure", Toast.LENGTH_SHORT).show()
        }


        binding.updateTask.setOnClickListener()
        {
            var title = binding.editTextTitle.text.toString()
            var id = binding.editTextid.text.toString()
            var description = binding.editTextDescription.text.toString()
            var date = binding.editTextDate.text.toString()

            if (title.isEmpty() && description.isEmpty() && date.isEmpty()) {

                binding.editTextTitle.error = "Title cannot be empty"
                binding.editTextDescription.error = "Description cannot be empty"
                binding.editTextDate.error="Date cannot be empty"
                binding.editTextid.error="ID is empty"

            }
            else{
                var modal = Task_Modal(title,description,date,id)
                viewModel.updateTask(modal).observe(this){result->
                    if (result==true)
                    {
                        Toast.makeText(this, "data updated", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this, "some thing went wrong", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}