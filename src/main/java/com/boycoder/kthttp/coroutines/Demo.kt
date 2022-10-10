package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.*

/**
 * created by cly on 2022/9/15
 */
fun main() {
//    asyncTest()
//    runBlocking {
//        getUserInfo()
//    }

    GlobalScope.launch {
        val myException = CoroutineExceptionHandler { _, throwable ->
            println("catch exception: $throwable")
        }
        logX("start")
        val coroutineScope = CoroutineScope(Job() + myException)
        val job = coroutineScope.launch(myException) {
            logX("launch 1")
            val s: String? = null
            s!!.length
            logX("launch 2")
        }
        job.join()
        logX("end")
    }
    Thread.sleep(1000)
}

fun logX(msg: String) {
    println("$msg - ${Thread.currentThread().name}")
}

suspend fun getUserInfo(): String {
    withContext(Dispatchers.IO) {
        println("1")
//        delay(100)
    }
    return "bob"
}

fun asyncTest() {
    runBlocking(Dispatchers.IO) {
        println("in runBlocking:${Thread.currentThread().name}")

        val deferred = async {
            println("in async:${Thread.currentThread().name}")
            delay(1000)
            println("In async after delay!")
            return@async "task completed"
        }

        println("After async:${Thread.currentThread().name}")

        val result = deferred.await()
        println("result is:${result}")
        println("await finished")
    }
}

fun normal() {
    GlobalScope.launch(Dispatchers.Default) {
        println("Coroutine started:${Thread.currentThread().name}")
        delay(1000)
        println("hello world")
    }
    println("after launch:${Thread.currentThread().name}")
    Thread.sleep(2000)
}