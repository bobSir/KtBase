package com.boycoder.kthttp.objectDemo

/**
 * created by cly on 2022/9/7
 */
class UserManagerFour private constructor(name: String) {

    companion object : BaseSingletonOne<String, UserManagerFour>({ UserManagerFour(it) })
}