package com.boycoder.kthttp.highOrder

/**
 * created by cly on 2022/9/7
 */
class Demo {

    /**
     * 带接受者的函数类型 ： 成员函数类型
     */
    fun Demo.test1(block: (demo: Demo) -> Unit) {}
    fun Demo.test2(block: Demo.() -> Unit) {}


    /**
    run：调用一个函数，作用仅仅限于构建lambda方便一点
    带receiver的run：调用一个带receiver的函数，把this传给这个函数

    with：用第一个参数调用第二个带receiver的函数，把this设定为第一个参数

    apply：带receiver的函数高阶函数，参数为带receiver的函数，接受一个对象，把这个对象作为this传给参数并调用，返回this

    also：和apply类似，但是参数是带一个参数的函数，接受对象传给参数，其余和apply一样

    let：和apply类似，但是返回值不是this，而是函数的返回

    takeIf：带receiver的函数高阶函数，参数是一个判断函数，结果判断结果为真就返回this，否者null

    takeUnless：和take相反

    repeat：参数为次数和函数，for循环执行函数
     */
    fun test1() {
        val s: String? = null

        s.takeIf {
            it == ""
        }

        s.also { }

        with(s) {

        }

        s.run {

        }

        s?.apply {
            toString()
        }

        s.let {
            it == "1"
        }
    }

}