package com.boycoder.kthttp.coroutines

import kotlin.concurrent.thread
import kotlin.coroutines.*

/**F
 * created by cly on 2022/10/13
 */
fun main() {

//    runBlocking {
//        val lengthSuspend = getLengthSuspend("bob")
//        logX("$lengthSuspend")
//    }

    val func = ::getLengthSuspend as (String, Continuation<Int>) -> Any?

    func("bob", object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println(result.getOrNull())
        }

    })

}

suspend fun getLengthSuspend(text: String): Int = suspendCoroutine { continuation ->
    logX("getLength start 0")
    thread {
        logX("getLength start 1")
        Thread.sleep(1000L)
        continuation.resume(text.length)
        logX("getLength start 2")
    }
}
