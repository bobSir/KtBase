package com.boycoder.kthttp.demo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

/**
 * created by cly on 2022/10/9
 */
fun main() {
    runBlocking {
        suspend fun getCacheInfo(productId: String): Product? {
            logX("getCacheInfo")
            delay(100L)
            return Product(productId, 9.9)
        }

        suspend fun getNetworkInfo(productId: String): Product? {
            logX("getNetworkInfo")
            delay(200L)
            return Product(productId, 9.8)
        }

        fun updateUi(product: Product) {
            println("${product.productId}==${product.price}")
        }

        val startTime = System.currentTimeMillis()
        val productId = "xxxId"
        //1.缓存和网络，并发执行
        val cacheDeferred = async(Dispatchers.Default) { getCacheInfo(productId) }
        val latestDeferred = async(Dispatchers.Default) { getNetworkInfo(productId) }
        //2. 在缓存和网络中间，选择最快的结果
        val product = select<Product?> {
            cacheDeferred.onAwait {
                it?.copy(isCache = true)
            }

            latestDeferred.onAwait {
                it?.copy(isCache = false)
            }
        }

        //3. 更新ui
        if (product != null) {
            updateUi(product)
            println("Time cost: ${System.currentTimeMillis() - startTime}")
        }

        //4. 如果当前结果是缓存，那么再取最新的网络服务结果
        if (product != null && product.isCache) {
            val latest = latestDeferred.await() ?: return@runBlocking
            updateUi(latest)
            println("Time cost: ${System.currentTimeMillis() - startTime}")
        }
    }
}

data class Product(
    val productId: String,
    val price: Double,
    val isCache: Boolean = false
)