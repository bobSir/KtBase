package com.boycoder.kthttp.demo

import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * kotlin提供非阻塞式的锁 Mutex
 * created by cly on 2022/10/10
 */
fun main() {
    runBlocking {
        val mutex = Mutex()

        var i = 0
        val jobs = arrayListOf<Job>()

        repeat(10) {
            val job = launch {
                repeat(1000) {
                    mutex.withLock {
                        i++
                    }
                }
            }
            jobs.add(job)
        }
        jobs.joinAll()

        println("i = $i")
    }
}