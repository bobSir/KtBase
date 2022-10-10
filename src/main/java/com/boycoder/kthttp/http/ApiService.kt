package com.boycoder.kthttp.http

/**
 * created by cly on 2022/9/13
 */
interface ApiService {
    @GET("/article/list/0/json")
    fun repos(@Field("cid") cid: Int): RepoList

    @GET("/article/list/0/json")
    fun reposV3(@Field("lang") cid: Int): KtCall<RepoList>

}