package com.kappdev.notes.feature_notes.domain.util

import kotlin.random.Random

object IdGenerator {

    fun generateRandId(length: Int = DEFAULT_ID_LENGTH): String {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val random = Random(System.nanoTime())
        val id = StringBuilder()

        for (i in 0 until length) {
            val rIndex = random.nextInt(characterSet.length)
            id.append(characterSet[rIndex])
        }

        return id.toString()
    }

    private const val DEFAULT_ID_LENGTH = 30
}