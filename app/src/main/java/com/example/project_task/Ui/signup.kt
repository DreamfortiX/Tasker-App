package com.example.project_task.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.project_task.Modals.task_user_modal
import com.example.project_task.ViewModals.UserViewModal
import com.example.project_task.databinding.ActivitySignupBinding

class signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var taskUserModal:UserViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskUserModal = ViewModelProvider(this).get(UserViewModal::class.java)

        binding.ClickHere.setOnClickListener()
        {
            startActivity(Intent(this,login::class.java))
        }
binding.BtnRegister.setOnClickListener(){

    val emailEditText = binding.editTextEmail
    val passwordEditText = binding.editTextPassword
    val confirmPasswordEditText = binding.editTextCPassword

    val email = emailEditText.text.toString()
    val password = passwordEditText.text.toString()
    val confirmPassword = confirmPasswordEditText.text.toString()

    if (!isValidEmail(email)) {
        emailEditText.error = "Invalid email address"
        emailEditText.requestFocus()
    } else if (password != confirmPassword) {
        confirmPasswordEditText.error = "Passwords do not match"
        confirmPasswordEditText.requestFocus()
    } else {
        var result = true
        val modal = task_user_modal(email,password,confirmPassword)
        taskUserModal.Add_User(modal).observe(this){result->
            if(result==true)
            {
                Toast.makeText(this, "Signup successfull", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,login::class.java))

            }
            else
            {
                Toast.makeText(this, "Email Already Exist", Toast.LENGTH_SHORT).show()
            }

        }
    }
}

    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}


