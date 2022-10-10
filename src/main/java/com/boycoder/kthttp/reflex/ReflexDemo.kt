package com.boycoder.kthttp.reflex

/**
 * created by cly on 2022/9/9
 */
fun main() {
    val student = Student("tom", 99.1, 160)
    val school = School("PKU", "Beijing")

    readMembers(student)
    readMembers(school)
}

fun readMembers(obj: Any) {

}

data class Student(
    val name: String,
    val score: Double,
    val height: Int,
)

data class School(
    val name: String,
    val address: String
)
