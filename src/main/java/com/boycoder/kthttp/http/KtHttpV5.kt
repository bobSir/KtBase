package com.boycoder.kthttp.http

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking

/**
 * created by cly on 2022/10/10
 */
fun <T : Any> KtCall<T>.asFlow(): Flow<T> = callbackFlow {
    val call = call(object : Callback<T> {
        override fun onSuccess(data: T) {
            // 1，变化在这里
            trySendBlocking(data)
                .onSuccess { close() }
                .onFailure { close(it) }
        }

        override fun onFail(throwable: Throwable) {
            close(throwable)
        }

    })

    awaitClose {
        call.cancel()
    }
}

fun main() {
    runBlocking {
        KtHttpV1.create(ApiService::class.java)
            .reposV3(cid = 60)
            .asFlow()
            .catch {
                println("catch $it")
            }
            .collect {
                println(it)
            }
    }
}