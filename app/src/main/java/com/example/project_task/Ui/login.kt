package com.example.project_task.Ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.project_task.Modals.Login_Modal
import com.example.project_task.ViewModals.UserViewModal
import com.example.project_task.databinding.ActivityLoginBinding

class login : AppCompatActivity() {
    private lateinit var taskUserModal: UserViewModal
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskUserModal = ViewModelProvider(this).get(UserViewModal::class.java)

        binding.ClickHere.setOnClickListener()
        {
            startActivity(Intent(this,signup::class.java))
        }
        binding.BtnLogin.setOnClickListener(){

            val emailEditText = binding.editTextEmail
            val passwordEditText = binding.editTextPassword

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (isValidEmail(email)) {
                if (isValidPassword(password)) {

                    var modal = Login_Modal(email,password)
                    taskUserModal.get_user(modal).observe(this){result->
                        if (result== true)
                        {
                            Toast.makeText(this, "Login Successfull", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Email and password doesn't match", Toast.LENGTH_SHORT).show()
                        }


                    }
                } else {
                    // Password is invalid
                    passwordEditText.error = "Invalid password"
                    passwordEditText.requestFocus()
                }
            } else {
                emailEditText.error = "Invalid email address"
                emailEditText.requestFocus()
            }


        }
    }
        private fun isValidEmail(email: String): Boolean {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            return email.matches(emailPattern.toRegex())
        }
        private fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }
}