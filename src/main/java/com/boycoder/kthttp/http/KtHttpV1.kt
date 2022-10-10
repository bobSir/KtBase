package com.boycoder.kthttp.http

import com.boycoder.kthttp.demo.logX
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy

/**
 * created by cly on 2022/9/13
 */
object KtHttpV1 {
    private val okHttpClient: OkHttpClient by lazy { OkHttpClient() }
    private val gson: Gson by lazy { Gson() }

    var baseUrl = "https://www.wanandroid.com"

    /*
     类型实化
     */
    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java)
        ) { proxy, method, args ->
            return@newProxyInstance method.annotations
                .filterIsInstance<GET>()
                .takeIf { it.size == 1 }
                ?.let { invokeReal2("$baseUrl${it[0].value}", method, args) }
        } as T
    }

    fun invokeReal2(path: String, method: Method, args: Array<out Any>): Any? {
        return method.parameterAnnotations
            .takeIf { method.parameterAnnotations.size == args.size }
            ?.mapIndexed { index, annotations -> Pair(annotations, args[index]) }
            ?.fold(path, KtHttpV1::parseUrl)
            ?.let { Request.Builder().url(it).build() }
            ?.let { okHttpClient.newCall(it).execute().body?.string() }
            ?.let { gson.fromJson(it, method.genericReturnType) }
    }

    private fun parseUrl(acc: String, pair: Pair<Array<Annotation>, Any>) =
        pair.first.filterIsInstance<Field>()
            .first()
            .let { field ->
                if (acc.contains("?")) {
                    "$acc&${field.value}=${pair.second}"
                } else {
                    "$acc?${field.value}=${pair.second}"
                }
            }

    fun <T : Any> create(service: Class<T>): T {
        //调用Proxy.newProxyInstance创建接口的实例化对象
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service),
            object : InvocationHandler {
                override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any? {
                    val annotations = method.annotations
                    for (annotation in annotations) {
                        if (annotation is GET) {
                            val url = baseUrl + annotation.value
                            return invokeV3<T>(url, method, args)
                        }
                    }
                    return null
                }
            }
        ) as T
    }

    fun invokeReal(path: String, method: Method, args: Array<out Any>): Any? {
        if (method.parameterAnnotations.size != args.size) return null
        /*
        method.parameterAnnotations，它的作用是取出方法参数当中的所有注解，
        在我们这个案例当中，repos() 这个方法当中涉及到两个注解，它们分别是@Field("lang")、@Field("since")。
         */
        val parameterAnnotations = method.parameterAnnotations
        var url = path
        for (i in parameterAnnotations.indices) {
            for (parameterAnnotation in parameterAnnotations[i]) {
                if (parameterAnnotation is Field) {
                    val key = parameterAnnotation.value
                    val value = args[i].toString()
                    url += if (!url.contains("?")) {
                        "?$key=$value"
                    } else {
                        "&$key=$value"
                    }
                }
            }
        }

        val request = Request.Builder()
            .url(url)
            .build()
        val response = okHttpClient.newCall(request).execute()

        val genericReturnType = method.genericReturnType

        val body = response.body
        val json = body?.string()
        val result = gson.fromJson<Any?>(json, genericReturnType)
        return result
    }

    /**
     * sync
     */
    private fun <T : Any> invokeV3(path: String, method: Method, args: Array<out Any>): Any? {
        if (method.parameterAnnotations.size != args.size) return null
        /*
        method.parameterAnnotations，它的作用是取出方法参数当中的所有注解，
        在我们这个案例当中，repos() 这个方法当中涉及到两个注解，它们分别是@Field("lang")、@Field("since")。
         */
        val parameterAnnotations = method.parameterAnnotations
        var url = path
        for (i in parameterAnnotations.indices) {
            for (parameterAnnotation in parameterAnnotations[i]) {
                if (parameterAnnotation is Field) {
                    val key = parameterAnnotation.value
                    val value = args[i].toString()
                    url += if (!url.contains("?")) {
                        "?$key=$value"
                    } else {
                        "&$key=$value"
                    }
                }
            }
        }
        val request = Request.Builder()
            .url(url)
            .build()
        val call = okHttpClient.newCall(request)

        return when {
            isKtCallReturn(method) -> {
                val genericReturnType = getTypeArgument(method)
                KtCall<T>(call, gson, genericReturnType)
            }

            isFlowReturn(method) -> {
                flow<T> {
                    logX("start out")
                    val typeArgument = getTypeArgument(method)
                    val response = okHttpClient.newCall(request).execute()
                    val json = response.body?.string()
                    val result = gson.fromJson<T>(json, typeArgument)
                    logX("start emit")
                    emit(result)
                    logX("end emit")
                }
            }

            else -> {
                val response = call.execute()
                val genericReturnType = method.genericReturnType
                val json = response.body?.string()
                gson.fromJson<Any?>(json, genericReturnType)
            }
        }
    }

    private fun isKtCallReturn(method: Method) =
        `$Gson$Types`.getRawType(method.genericReturnType) == KtCall::class.java

    private fun isFlowReturn(method: Method) =
        `$Gson$Types`.getRawType(method.genericReturnType) == Flow::class.java

    /**
     * 拿到KtCall<RepoList>当中的RepoList类型
     */
    private fun getTypeArgument(method: Method) = (method.genericReturnType as ParameterizedType).actualTypeArguments[0]
}