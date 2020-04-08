package com.learning.helloworld

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class StudentViewHolder(
    itemView: ConstraintLayout,
    val onClick : (Student) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private var nameTextView : TextView? = itemView.findViewById(R.id.name)

    @SuppressLint("SetTextI18n")
    fun bind(student: Student) {
        nameTextView?.text = "${student.firstName} ${student.lastName}"
        nameTextView?.setOnClickListener {
            onClick(student)
        }
    }
}

class StudentsAdapter(
    var students: List<Student>,
    private val onClick : (Student) -> Unit
) : RecyclerView.Adapter<StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder =
        StudentViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.student, parent, false)
                    as ConstraintLayout,
            onClick
        )

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }
}