package com.boycoder.kthttp.objectDemo

/**
 * created by cly on 2022/9/6
 */
class UserManagerTwo private constructor(name: String) {
    companion object : BaseSingleton<String, UserManagerTwo>() {
        override val creator = ::UserManagerTwo
    }
}