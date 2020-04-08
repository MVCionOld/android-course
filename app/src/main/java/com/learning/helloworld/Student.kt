package com.learning.helloworld

import java.io.File

data class Student(val firstName: String, val lastName: String)

object StudentLoader {
    fun loadStudents(file: File): List<Student> {
        if (!file.exists()) {
            val students = generateStudents()
            saveStudents(students, file)
            return students
        }
        return file.readLines()
            .filter(String::isNotEmpty)
            .map(::loadStudent)
    }

    private fun loadStudent(str: String): Student {
        val parts = str.split(':')
        return Student(parts[0], parts[1])
    }

    private fun generateStudents()
            : List<Student> = generateSequence(1, { i ->
                if (i < 1000) i + 1 else null }
            ).map { i ->
                Student("имя$i", "фамилия$i")
            }.toList()

    private fun saveStudents(students: List<Student>, file: File) {
        file.writeText("")
        students.map(::saveStudent).forEach {
            file.appendText(it + "\n")
        }
    }

    private fun saveStudent(student: Student)
            : String = "${student.firstName}:${student.lastName}"
}