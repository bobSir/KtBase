package com.boycoder.kthttp.coroutines

import kotlinx.coroutines.*

/**
 * created by cly on 2022/10/12
 */
fun main() {
    logX("start")
//    runBlocking {
//        testCoroutine()
//    }

//    GlobalScope.launch {
//        testCoroutine()
//    }

//    GlobalScope.async {
//        testCoroutine()
//    }

    GlobalScope.launch {
        logX("00")
        getUserInfo2()
        logX("11")
    }

    GlobalScope.launch {
        logX("22")
        getFriendList("")
        logX("33")
    }

    GlobalScope.launch {
        logX("44")
        getFeedList("", "")
        logX("55")
    }


//    GlobalScope.launch {
//        withContext(Dispatchers.IO) {
//            logX("00")
//            getUserInfo2()
//            logX("11")
//        }
//
//        withContext(Dispatchers.Default) {
//            logX("22")
//            getFriendList("")
//            logX("33")
//        }
//
//        withContext(Dispatchers.IO) {
//            logX("44")
//            getFeedList("", "")
//            logX("55")
//        }
//    }

    logX("end - 0")
    Thread.sleep(5000L)
    logX("end - 1")
}

suspend fun testCoroutine() {
    logX("00")
    val user = getUserInfo2()
    logX("11")
    val friendList = getFriendList(user)
    logX("22")
    val feedList = getFeedList(user, friendList)
    logX(feedList)
}

suspend fun getFeedList(user: String, friendList: String): String {
    logX("getFeedList - 0")
    withContext(Dispatchers.IO) {
        logX("getFeedList - 1")
        delay(1000)
        logX("getFeedList - 2")
    }
    logX("getFeedList - 3")
    return "{feedList...}"
}

suspend fun getFriendList(user: String): String {
    logX("getFriendList - 0")
    withContext(Dispatchers.IO) {
        logX("getFriendList - 1")
        delay(1000)
        logX("getFriendList - 2")
    }
    logX("getFriendList - 3")
    return "Tom,Jack"
}

suspend fun getUserInfo2(): String {
    logX("getUserInfo2 - 0")
    withContext(Dispatchers.IO) {
        logX("getUserInfo2 - 1")
        delay(1000)
        logX("getUserInfo2 - 2")
    }
    logX("getUserInfo2 - 3")
    return "bob"
}

suspend fun test(): String {
    return "test"
}

