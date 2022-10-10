package com.boycoder.kthttp.objectDemo

/**
 * 如果我们的单例占用内存很小，并且对内存不敏感，不需要传参，直接使用 object 定义的单例即可。
 * 如果我们的单例占用内存很小，不需要传参，但它内部的属性会触发消耗资源的网络请求和数据库查询，我们可以使用 object 搭配 by lazy 懒加载。
 * 如果我们的工程很简单，只有一两个单例场景，同时我们有懒加载需求，并且 getInstance() 需要传参，我们可以直接手写 Double Check。
 * 如果我们的工程规模大，对内存敏感，单例场景比较多，那我们就很有必要使用抽象类模板 BaseSingleton 了。
 * created by cly on 2022/9/6
 */
class Demo {
    fun test() {
        val s = ""
        s.also {
            it.toString()
        }

        s.let {
            it.toString()
        }

        s.apply {
            this.toString()
        }

    }
}