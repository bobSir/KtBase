package com.boycoder.kthttp.objectDemo

/**
 * created by cly on 2022/9/7
 */
open class BaseSingletonOne<in P, out T : Any>(private val creator: (P) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(params: P): T =
        instance ?: synchronized(this) {
            instance ?: creator(params).also { instance = it }
        }
}