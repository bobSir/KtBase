package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.*

/**
 * created by cly on 2022/10/13
 */

fun main() {


    CoroutineScope(Job())
        .launch {
            delay(1000L)
        }


//    val i = (1 shl 21) - 2
//    println(i)

    Thread.sleep(2000L)
}