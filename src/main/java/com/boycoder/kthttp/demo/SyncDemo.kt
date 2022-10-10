package com.boycoder.kthttp.demo

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * created by cly on 2022/10/10
 */
fun main() {
    runBlocking {
        val singleDispatcher =
            Executors.newSingleThreadExecutor { Thread(it, "singleThread").apply { isDaemon = true } }
                .asCoroutineDispatcher()

        var i = 0
        val jobs = arrayListOf<Job>()

        repeat(10) {
            val job = launch(singleDispatcher) {
                repeat(1000) {
                    i++
                }
            }
            jobs.add(job)
        }

        jobs.joinAll()

        println("i =  $i")
    }
}