package com.boycoder.kthttp.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

/**
 * 函数式编程 处理并发编程
 * 之所以需要处理多线程同步问题，主要还是因为存在共享的可变状态。其实 共享可变状态，既不符合无副作用
 * 的特性，也不符合不变性的特征。借助函数式编程思想，实现无副作用和不变性以后，并发代码也会随之变得安全。
 * created by cly on 2022/10/10
 */
fun main() {
    runBlocking {
        val sum = (1..10).map {
            async(Dispatchers.Default) {
                var i = 0
                repeat(1000) {
                    i++
                }
                return@async i
            }
        }.awaitAll()
            .sum()
        println("i = $sum")
    }
}