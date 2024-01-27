package com.example.project_task.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_task.Modals.Task_Modal
import com.example.project_task.R

class Adapter_task(private val context: Context, private val tasklist: ArrayList<Task_Modal>) :
    RecyclerView.Adapter<Adapter_task.ViewHolder>() {

    private var onTaskEditClickListener: ((Int) -> Unit)? = null
    private var onTaskDeleteClickListener: ((Int) -> Unit)? = null

    fun setOnTaskEditClickListener(listener: (Int) -> Unit) {
        onTaskEditClickListener = listener
    }

    fun setOnTaskDeleteClickListener(listener: (Int) -> Unit) {
        onTaskDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasklist[position])
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Use lateinit for better null safety
        private lateinit var title: TextView
        private lateinit var description: TextView
        private lateinit var date: TextView
        private lateinit var editButton: ImageView
        private lateinit var deleteButton: ImageView

        init {
            // Initialize views in the constructor
            title = itemView.findViewById(R.id.editTextTitle)
            description = itemView.findViewById(R.id.textViewDescription)
            date = itemView.findViewById(R.id.editTextDate)
            editButton = itemView.findViewById(R.id.editButton)
            deleteButton = itemView.findViewById(R.id.deleteButton)
        }

        fun bind(modelChat: Task_Modal) {
            title.text = modelChat.title
            description.text = modelChat.description
            date.text = modelChat.date

            editButton.setOnClickListener {
                onTaskEditClickListener?.invoke(adapterPosition)
            }

            deleteButton.setOnClickListener {
                onTaskDeleteClickListener?.invoke(adapterPosition)
            }
        }
    }
}


