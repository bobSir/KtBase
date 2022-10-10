package com.boycoder.kthttp.training

/**
 * created by cly on 2022/9/7
 */

fun main() {
    val text = "kotlin is my favorite language, i love kotlin!"
    val processText = TextProcessorV1().processText(text)
    println(processText)
}

class TextProcessorV1 {
    fun processText(text: String): List<WordFreq> {
//        val trim = text.replace("[^A-Za-z]".toRegex(), " ").trim()
//        val stringList = trim.split(" ")
//        val hashMapOf = hashMapOf<String, Int>()
//        for (word in stringList) {
//            if (word.isEmpty()) continue
//            val trim1 = word.trim()
//            val count = hashMapOf.getOrDefault(trim1, 0)
//            hashMapOf[trim1] = count + 1
//        }
//        val arrayListOf = arrayListOf<com.boycoder.kthttp.training.WordFreq>()
//        for (entry in hashMapOf) {
//            val freq = com.boycoder.kthttp.training.WordFreq(entry.key, entry.value)
//            arrayListOf.add(freq)
//        }
//        arrayListOf.sortByDescending { it.frequency }
//        return arrayListOf

//        return text.clear()
//            .split(" ")
//            .getWordCount()
//            .mapToList { com.boycoder.kthttp.training.WordFreq(it.key, it.value) }
//            .sortedByDescending { it.frequency }


        return text.clear()
            .split(" ")
            .filter { it != "" }
            .groupBy { it }
            .map { WordFreq(it.key, it.value.size) }
            .sortedByDescending { it.frequency }
    }

    private fun String.clear(): String {
        return replace("[^A-Za-z]".toRegex(), " ").trim()
    }

    private fun List<String>.getWordCount(): Map<String, Int> {
        val hashMapOf = hashMapOf<String, Int>()
        for (word in this) {
            if (word.isEmpty()) continue
            val trim1 = word.trim()
            val count = hashMapOf.getOrDefault(trim1, 0)
            hashMapOf[trim1] = count + 1
        }
        return hashMapOf
    }

    private fun Map<String, Int>.sortByFrequency(): ArrayList<WordFreq> {
        val arrayListOf = arrayListOf<WordFreq>()
        for (entry in this) {
            val freq = WordFreq(entry.key, entry.value)
            arrayListOf.add(freq)
        }
        arrayListOf.sortByDescending { it.frequency }
        return arrayListOf
    }

    private inline fun <T> Map<String, Int>.mapToList(transform: (Map.Entry<String, Int>) -> T): ArrayList<T> {
        val list = arrayListOf<T>()
        for (entry in this) {
            val freq = transform(entry)
            list.add(freq)
        }
        return list
    }
}

data class WordFreq(
    val word: String,
    val frequency: Int
)