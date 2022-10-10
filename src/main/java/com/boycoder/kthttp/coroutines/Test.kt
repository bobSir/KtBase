package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * created by cly on 2022/10/10
 */
 // 代码段3
val fixedDispatcher = Executors.newFixedThreadPool(2) {
    Thread(it, "MyFixedThread").apply { isDaemon = false }
}.asCoroutineDispatcher()

fun main() = runBlocking {
    // 父协程
    val parentJob = launch(fixedDispatcher) {

        // 1，注意这里
        launch(Job()) { // 子协程1
            var i = 0
            while (isActive) {
                Thread.sleep(500L)
                i++
                println("First i = $i")
            }
        }

        launch { // 子协程2
            var i = 0
            while (isActive) {
                Thread.sleep(500L)
                i++
                println("Second i = $i")
            }
        }
    }

    delay(2000L)

    parentJob.cancel()
    parentJob.join()

    println("End")
}

/*
输出结果
First i = 1
Second i = 1
First i = 2
Second i = 2
Second i = 3
First i = 3
First i = 4
Second i = 4
End
First i = 5
First i = 6
// 子协程1永远不会停下来
*/