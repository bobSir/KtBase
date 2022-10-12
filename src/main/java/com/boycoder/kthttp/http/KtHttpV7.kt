package com.boycoder.kthttp.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * created by cly on 2022/10/12
 */
fun main() {
    runBlocking {
        withContext(Dispatchers.IO) {
            val repoList: RepoList = KtHttpV1.create(ApiService::class.java)
                .reposSuspend(60)
            println(repoList)
        }
    }

    Thread.sleep(5000L)
}