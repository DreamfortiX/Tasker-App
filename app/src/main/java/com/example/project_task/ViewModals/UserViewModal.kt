package com.example.project_task.ViewModals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.project_task.Constants.Constants
import com.example.project_task.Data.Repo
import com.example.project_task.Modals.Login_Modal
import com.example.project_task.Modals.Task_Modal
import com.example.project_task.Modals.task_user_modal

class UserViewModal: ViewModel() {

    private var repo =Repo(this)

   private var constant = Constants()

    fun addtask(modelTask: Task_Modal): LiveData<Boolean>
    {
        return repo.addTask(modelTask)
    }
    fun Add_User(model:task_user_modal): LiveData<Boolean>
    {
        return repo.Add_User(model)
    }

    fun retriveTask():LiveData<ArrayList<Task_Modal>?>
    {
        return repo.retriveTask()
    }

    fun del(clickedTask: Task_Modal): LiveData<Boolean> {
        return repo.deleteTask(clickedTask)
    }

    fun updateTask(updatedTask: Task_Modal): LiveData<Boolean> {
        return repo.updateTask(updatedTask)
    }
    fun get_user(model: Login_Modal):LiveData<Boolean>
    {
        return  repo.get_user(model)
    }

}