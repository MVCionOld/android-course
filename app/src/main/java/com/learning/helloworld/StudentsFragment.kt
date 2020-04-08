package com.learning.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.lang.ref.WeakReference

class StudentsFragment : Fragment() {

    private var studentsView: RecyclerView? = null
    private var onStudentClick : ((Student) -> Unit)? = null
    private var students = emptyList<Student>()
    private var studentsAdapter : StudentsAdapter? = null
    private var studentsLoadTask : StudentsLoadTask? = null
    private var listener : StudentsLoadTask.UIListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students, container, false)

        val studentsFile = File(context?.filesDir, "students.txt")
        listener = object : StudentsLoadTask.UIListener {
            override fun onStudentsLoaded(students: List<Student>) {
                studentsAdapter?.students = students
                studentsAdapter?.notifyDataSetChanged()
            }
        }

        studentsLoadTask = StudentsLoadTask(WeakReference(listener!!))
        studentsLoadTask?.execute(studentsFile)

        studentsView = view.findViewById(R.id.students)
        studentsView?.layoutManager = LinearLayoutManager(context)
        studentsAdapter = StudentsAdapter(students) { student ->
            onStudentClick?.invoke(student)
        }
        studentsView?.adapter = studentsAdapter

        return view
    }

    override fun onDestroy() {
        studentsLoadTask?.cancel(true)
        super.onDestroy()
    }

    companion object StudentsFragmentFactory {

        fun newInstance(onStudentClick: (Student) -> Unit): StudentsFragment
        = StudentsFragment().apply {
            this.onStudentClick = onStudentClick
        }
    }
}