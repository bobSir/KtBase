package com.boycoder.kthttp.objectDemo

/**
 * 抽象类模板
 * created by cly on 2022/9/6
 */
abstract class BaseSingleton<in P, out T> {
    @Volatile
    private var instance: T? = null

    protected abstract val creator: (P) -> T

    fun getInstance(param: P): T =
        instance ?: synchronized(this) {
            instance ?: creator(param).also { instance = it }
        }
}