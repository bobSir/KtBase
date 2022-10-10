package com.boycoder.kthttp.http

import com.boycoder.kthttp.demo.logX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

/**
 * created by cly on 2022/10/10
 */
fun main() {
    runBlocking {
        KtHttpV1.create(ApiService::class.java)
            .reposFlow(60)
            .flowOn(Dispatchers.IO)
            .catch { println("catch $it") }
            .collect {
                logX("collect -- $it")
            }
    }
}