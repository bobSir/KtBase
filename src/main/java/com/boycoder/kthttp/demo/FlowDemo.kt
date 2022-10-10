package com.boycoder.kthttp.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * created by cly on 2022/10/9
 */
fun main() {
    runBlocking {
        flow {
            logX("start")
            emit(1)
            logX("emit 1")
            emit(2)
            logX("emit 2")
            emit(3)
            logX("emit 3")
        }
            .flowOn(Dispatchers.IO)
            .filter {
                logX("Filter: $it")
                it > 2
            }
//            .collect {
//                logX("collect $it")
//            }
            .onEach {
                logX("onEach $it")
            }
            .launchIn(CoroutineScope(Dispatchers.Default))
        delay(100)
    }
}

fun logX(msg: String) {
    println("$msg -- ${Thread.currentThread().name}")
}