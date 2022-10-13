package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * created by cly on 2022/10/13
 */
fun main() {
    testStartCoroutine()
    Thread.sleep(2000L)
}

val block = suspend {
    println("hello")
    delay(1000L)
    println("world")
    "result"
}

private fun testStartCoroutine() {
    val continuation = object : Continuation<String> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {
            println("result is ${result.getOrNull()}")
        }
    }
    block.startCoroutine(continuation)
}