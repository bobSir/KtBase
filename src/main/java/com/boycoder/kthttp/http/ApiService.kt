package com.boycoder.kthttp.http

import kotlinx.coroutines.flow.Flow

/**
 * created by cly on 2022/9/13
 */
interface ApiService {
    @GET("/article/list/0/json")
    fun repos(@Field("cid") cid: Int): RepoList

    @GET("/article/list/0/json")
    fun reposV3(@Field("lang") cid: Int): KtCall<RepoList>

    @GET("/article/list/0/json")
    fun reposFlow(@Field("lang") cid: Int): Flow<RepoList>

    @GET("/article/list/0/json")
    suspend fun reposSuspend(@Field("lang") cid: Int): RepoList
}