package com.boycoder.kthttp.http

/**
 * created by cly on 2022/10/8
 */
interface Callback<T : Any> {
    fun onSuccess(data: T)
    fun onFail(throwable: Throwable)
}