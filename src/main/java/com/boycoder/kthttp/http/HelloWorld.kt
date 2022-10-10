package com.boycoder.kthttp.http

fun main() {
    //V1
//    val api: ApiService = KtHttpV1.create(ApiService::class.java)
//    val repos = api.repos(cid = 60)

    //V2
//    val api1: ApiService by lazy { KtHttpV1.create() }
//    val repos = api1.repos(cid = 60)
//    println(repos)

    //V3 sync
    val apiService = KtHttpV1.create(ApiService::class.java)

    val repoList = apiService.repos(cid = 60)
    println("sync")
    println(repoList)

    apiService.reposV3(cid = 60)
        .call(object : Callback<RepoList> {
            override fun onSuccess(data: RepoList) {
                println(data)
            }

            override fun onFail(throwable: Throwable) {
                println(throwable)
            }
        })
}