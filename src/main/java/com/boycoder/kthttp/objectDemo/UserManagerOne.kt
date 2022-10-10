package com.boycoder.kthttp.objectDemo

/**
 * 伴生对象Double Check
 * created by cly on 2022/9/6
 */
class UserManagerOne private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: UserManagerOne? = null

        fun getInstance(): UserManagerOne {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserManagerOne().also { INSTANCE = it }
            }
        }
    }
}