package com.boycoder.kthttp.math

/**
 * created by cly on 2022/9/19
 */
fun main() {
    val equation = "x+5-3+x=6+x-2"
    Solution().solveEquation(equation)
}

class Solution {
    fun compareVersion(version1: String, version2: String): Int {
        val version1s = version1.split(".")
        val version2s = version2.split(".")
        for (i in 0..maxOf(version1s.size, version2s.size)) {
            var x = 0
            var y = 0
            if (i < version1s.size) x = version1s[i].toInt()
            if (i < version2s.size) y = version2s[i].toInt()
            if (x > y) {
                return 1
            }
            if (x < y) {
                return -1
            }
        }
        return 0
    }

    fun sort(array: IntArray): IntArray {
        for (end in (array.size - 1) downTo 1) {
            for (begin in 1..end) {
                if (array[begin - 1] > array[begin]) {
                    val temp = array[begin - 1]
                    array[begin - 1] = array[begin]
                    array[begin] = temp
                }
            }
        }
        return array
    }


    fun solveEquation(equation: String): String {
        // ① 分割等号
        val list = equation.split("=")

        var leftSum = 0
        var rightSum = 0

        val leftList = splitByOperator(list[0])
        println(leftList.toString())
        val rightList = splitByOperator(list[1])

        // ② 遍历左边的等式，移项，合并同类项
        leftList.forEach {
            if (it.contains("x")) {
                leftSum += xToInt(it)
                println("$it leftSum = $leftSum")
            } else {
                rightSum -= it.toInt()
                println("$it rightSum = $rightSum")
            }
        }

        // ③ 遍历右边的等式，移项，合并同类项
        rightList.forEach {
            if (it.contains("x")) {
                leftSum -= xToInt(it)
            } else {
                rightSum += it.toInt()
            }
        }

        // ④ 系数化为一,返回结果
        return when {
            leftSum == 0 && rightSum == 0 -> "Infinite solutions"
            leftSum == 0 && rightSum != 0 -> "No solution"
            else -> "x=${rightSum / leftSum}"
        }
    }

    // 根据“+”、“-”分割式子
    private fun splitByOperator(list: String): List<String> {
        val result = mutableListOf<String>()
        var temp = ""
        list.forEach {
            if (it == '+' || it == '-') {
                if (temp.isNotEmpty()) {
                    result.add(temp)
                }
                temp = it.toString()
            } else {
                temp += it
            }
        }

        result.add(temp)
        return result
    }

    // 提取x的系数：“-2x” ->“-2”
    private fun xToInt(x: String) =
        when (x) {
            "x",
            "+x" -> 1

            "-x" -> -1
            else -> x.replace("x", "").toInt()
        }
}