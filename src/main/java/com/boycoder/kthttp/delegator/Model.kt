package com.boycoder.kthttp.delegator

/**
 * 委托
 * created by cly on 2022/9/8
 */
class Model {
    val data: List<String> by ::_data
    private val _data: MutableList<String> = mutableListOf()

    fun load() {
        _data.add("hello")
    }
}