package com.boycoder.kthttp.demo

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * created by cly on 2022/10/9
 */
fun main() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val channel1 = produce<String> {
            send("1")
            delay(200L)
            send("2")
            delay(200L)
            send("3")
            delay(200L)
        }

        val channel2 = produce {
            delay(100L)
            send("a")
            delay(200L)
            send("b")
            delay(200L)
            send("c")
        }

        suspend fun selectChannel(
            channel1: ReceiveChannel<String>,
            channel2: ReceiveChannel<String>
        ): String {
            return select<String> {
                //1. 选择channel1
                channel1.onReceiveCatching {
                    it.getOrNull() ?: "channel1 is closed"
                }
//                2. 选择channel2
                channel2.onReceiveCatching {
                    it.getOrNull() ?: "channel2 is closed"
                }
            }
        }

        repeat(6) {// 3. 选择6次结果
            val result = selectChannel(channel1, channel2)
            println(result)
        }
        channel1.cancel()
        channel2.cancel()

        println("Time cost: ${System.currentTimeMillis() - startTime}")
    }
}