package com.boycoder.kthttp.http

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * created by cly on 2022/10/8
 */

suspend fun <T : Any> KtCall<T>.await(): T =
    suspendCoroutine { continuation ->
        val call = call(object : Callback<T> {
            override fun onSuccess(data: T) {
                println("Request success!")
                continuation.resumeWith(Result.success(data))
//                continuation.resume(data)
            }

            override fun onFail(throwable: Throwable) {
                println("Request fail!：$throwable")
                continuation.resumeWith(Result.failure(throwable))
//                continuation.resumeWithException(throwable)
            }

        })

        // 注意这里
//        continuation.invokeOnCancellation {
//            println("Call cancelled!")
//            call.cancel()
//        }
    }

fun main() {
    runBlocking {
        val start = System.currentTimeMillis()
        val deferred = async {
            val apiService = KtHttpV1.create(ApiService::class.java)
            val ktCall = apiService.reposV3(cid = 60)
            val repoList = ktCall.await()
        }
        deferred.invokeOnCompletion {
            println("invokeOnCompletion!")
        }
        delay(50L)
        deferred.cancel()
        println("Time cancel: ${System.currentTimeMillis() - start}")
        try {
            println(deferred.await())
        } catch (e: Exception) {
            println("Time exception: ${System.currentTimeMillis() - start}")
            println("Catch exception:$e")
        } finally {
            println("Time total: ${System.currentTimeMillis() - start}")
        }
    }
}


