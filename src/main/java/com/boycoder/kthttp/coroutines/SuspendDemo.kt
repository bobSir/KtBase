package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume

/**
 * created by cly on 2022/10/13
 */

fun main() {
    runBlocking {
        val result = testSuspendCoroutine()
        println(result)
    }
}

private suspend fun testSuspendCoroutine() =
    suspendCoroutineUninterceptedOrReturn<String> { continuation ->
        thread {
            Thread.sleep(1000L)
            continuation.resume("hello")
        }
        return@suspendCoroutineUninterceptedOrReturn kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
    }