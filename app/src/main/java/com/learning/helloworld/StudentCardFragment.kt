package com.learning.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class StudentCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView: View = inflater.inflate(
            R.layout.fragment_student_info,
            container,
            false
        )
        newView.findViewById<TextView>(R.id.studentFirstName)
            .apply {
                text = arguments?.getString(KEY_FIRST_NAME)
            }
        newView.findViewById<TextView>(R.id.studentLastName)
            .apply{
                text = arguments?.getString(KEY_LAST_NAME)
            }
        return newView
    }

    companion object StudentCardFragmentFactory {

        const val KEY_FIRST_NAME: String = "firstName"
        const val KEY_LAST_NAME: String = "lastName"

        fun newInstance(student: Student): StudentCardFragment = StudentCardFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_FIRST_NAME, student.firstName)
                putString(KEY_LAST_NAME, student.lastName)
            }
        }
    }
}
