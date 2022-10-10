package com.boycoder.kthttp.demo

import kotlinx.coroutines.*

/**
 * 使用 CoroutineExceptionHandler 处理复杂结构的协程异常，它仅在顶层协程中起作用。
 * 我们都知道，传统的 try-catch 在协程当中并不能解决所有问题，尤其是在协程嵌套层级较深的情况下。
 * 这时候，Kotlin 官方为我们提供了 CoroutineExceptionHandler 作为补充。
 * 有了它，我们可以轻松捕获整个作用域内的所有异常。
 * created by cly on 2022/10/10
 */

fun main() {
    runBlocking {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("Catch exception : $throwable")
        }

        val scope = CoroutineScope(coroutineContext + Job() + coroutineExceptionHandler)

        scope.launch {
            async {
                delay(100L)
            }
            launch {
                delay(100L)

                launch {
                    delay(100L)
                    1 / 0
                }
            }
            delay(100L)
        }
        delay(1000L)
        println("end")
    }
}