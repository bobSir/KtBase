package com.boycoder.kthttp.ext

/**
 * created by cly on 2022/9/7
 */
val Person.isAudit: Boolean
    get() = age >= 18

class Person {
    var name: String = ""
    var age: Int = 0
}