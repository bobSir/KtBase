package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.*

/**
 * created by cly on 2022/10/14
 */
fun main() {
    testScope()
}


// 代码段3
private fun testScope() {
    val scope = CoroutineScope(Job())
    scope.launch {
        launch {
            delay(1000000L)
            logX("Inner")
        }
        logX("Hello!")
        delay(1000000L)
        logX("World!")  // 不会执行
    }

    scope.launch {
        launch {
            delay(1000000L)
            logX("Inner！！！")
        }
        logX("Hello！！!")
        delay(1000000L)
        logX("World1!！！")  // 不会执行
    }
    Thread.sleep(500L)
    scope.cancel()
}