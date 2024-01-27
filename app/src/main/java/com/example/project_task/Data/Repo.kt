package com.example.project_task.Data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project_task.Constants.Constants
import com.example.project_task.Modals.Login_Modal
import com.example.project_task.Modals.Task_Modal
import com.example.project_task.Modals.task_user_modal
import com.example.project_task.ViewModals.UserViewModal
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore

class Repo(private val viewModel: UserViewModal) {



 var db=Firebase.firestore

    var auth = FirebaseAuth.getInstance()

    var constant=Constants()
    var TaskCollection=db.collection(constant.TASK_COLLECTION)




    fun Add_User(modal: task_user_modal): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val userCollection = db.collection(constant.CREATE_USER)

        // Check if user with the given email already exists
        userCollection.whereEqualTo("email", modal.email)
            .get()
            .addOnCompleteListener { querySnapshot ->
                if (querySnapshot.isSuccessful) {
                    if (querySnapshot.result?.isEmpty == false) {
                        result.value = false
                    } else {
                        addUserToCollection(userCollection, modal, result)
                    }
                } else {
                    result.value = false
                }
            }

        return result
    }

    private fun addUserToCollection(
        userCollection: CollectionReference,
        modal: task_user_modal,
        result: MutableLiveData<Boolean>
    ) {
        // Add the user to the collection
        userCollection.add(modal)
            .addOnCompleteListener { documents ->
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }
    }

    fun get_user(modal: Login_Modal): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val userCollection = db.collection(constant.CREATE_USER)

        userCollection.whereEqualTo("email", modal.email)
            .whereEqualTo("password", modal.Password)
            .get()
            .addOnSuccessListener { documents ->
                    result.value = true
            }
            .addOnFailureListener { exception ->
                result.value = false
            }

        return result
    }







    fun addTask(modelTask: Task_Modal):LiveData<Boolean>
    {
        var result= MutableLiveData<Boolean>()
        TaskCollection.add(modelTask).addOnCompleteListener {
            result.value=true
        }

            .addOnFailureListener {
                result.value=false
            }

        return  result
    }
    @SuppressLint("SuspiciousIndentation")
    fun retriveTask(): MutableLiveData<ArrayList<Task_Modal>?> {
        val result = MutableLiveData<ArrayList<Task_Modal>?>()

        db.collection("Task").get().addOnSuccessListener { documents ->
            val listlist: ArrayList<Task_Modal> = arrayListOf()

            for (document in documents) {
                val modelUser = document.toObject(Task_Modal::class.java)
                listlist.add(modelUser)
            }

            result.value = listlist
        }.addOnFailureListener { e ->
            result.value = null // You can handle the failure case differently if needed
        }

        return result
    }


    fun deleteTask(clickedTask: Task_Modal): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        // Build a query to find the document with matching id and title
        val query = db.collection("Task")
            .whereEqualTo("id", clickedTask.id)
            .whereEqualTo("title", clickedTask.title)

        // Execute the query
        query.get().addOnSuccessListener { documents ->
            // Counter to track the number of documents deleted
            var deletedCount = 0

            for (document in documents) {
                // Delete each matching document
                db.collection("Task")
                    .document(document.id)
                    .delete()
                    .addOnSuccessListener {
                        // Increment the deleted count
                        deletedCount++

                        // Check if all documents have been deleted
                        if (deletedCount == documents.size()) {
                            // Update the LiveData once all deletions are complete
                            result.value = true
                        }
                    }
                    .addOnFailureListener { e ->
                        // Set result to false if any deletion fails
                        result.value = false
                    }
            }

            // Check if there were no documents to delete
            if (documents.isEmpty()) {
                result.value = false
            }
        }.addOnFailureListener { e ->
            // Query failed
            result.value = false
        }

        return result
    }



    fun updateTask(updatedTask: Task_Modal): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        // Build a query to find the document with matching id and title
        val query = db.collection("Task")
            .whereEqualTo("id", updatedTask.id)
            .whereEqualTo("title", updatedTask.title)

        // Execute the query
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                // Found a matching document, update it
                db.collection("Task")
                    .document(document.id)
                    .set(updatedTask)
                    .addOnSuccessListener {
                        result.value = true
                    }
                    .addOnFailureListener { e ->
                        result.value = false
                    }
            }
        }.addOnFailureListener { e ->
            // Query failed
            result.value = false
        }

        return result
    }


}


