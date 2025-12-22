package com.example.german.data.repository.exercises

import android.util.Log
import kotlin.random.Random

import com.example.german.data.entities.exercises.DigitTranslateExercise

class ExerciseDigitTranslateRepository{

    fun getRandomNumbers(count:Int = 5, range: IntRange = 1..999): List<Int>{
        return List(count) {range.random()}
    }
    fun generateFakeNumber(correct: Int, existing: List<Int> = emptyList()): Int {
        var n: Int
        val dia = correct / 100
        do {
            n = (dia * 100..(dia + 1) * 100 - 1).random()
        } while (n == correct || existing.contains(n)) // проверяем, что оно не совпадает с правильным и уже существующими
        return n
    }

    fun generateExercises(digits: List<Int>): List<DigitTranslateExercise> {
        Log.e("DIGIT_", "repo")
        return digits.map { digit ->
            val germanTranslate = numberToGerman(digit)
            val fake1 = generateFakeNumber(digit)
            val fake2 = generateFakeNumber(digit, listOf(fake1))
            val variantsAnswer = listOf(digit, fake1, fake2).shuffled()
            DigitTranslateExercise(
                //id = digit,
                digit = digit,
                germanTranslate = germanTranslate,
                variantsAnswer = variantsAnswer,       // 3 варианта ответа (1 правильный + 2 случайных)
            )
        }
    }

    private fun numberToGerman(n: Int): String {
        val units = mapOf(
            1 to "eins", 2 to "zwei", 3 to "drei", 4 to "vier", 5 to "fünf",
            6 to "sechs", 7 to "sieben", 8 to "acht", 9 to "neun"
        )

        val teens = mapOf(
            10 to "zehn", 11 to "elf", 12 to "zwölf", 13 to "dreizehn",
            14 to "vierzehn", 15 to "fünfzehn", 16 to "sechzehn",
            17 to "siebzehn", 18 to "achtzehn", 19 to "neunzehn"
        )

        val tens = mapOf(
            20 to "zwanzig", 30 to "dreißig", 40 to "vierzig",
            50 to "fünfzig", 60 to "sechzig", 70 to "siebzig",
            80 to "achtzig", 90 to "neunzig"
        )

        return when {
            n == 0 -> "null"
            n in 1..12 -> units[n] ?: "null"
            n in 13..19 -> teens[n] ?: "null"
            n in 20..99 -> {
                val unit = n % 10
                val ten = n - unit
                if (unit == 0) tens[ten] ?: "null"
                else "${units[unit]}und${tens[ten]}"
            }
            n in 100..999 -> {
                val hundred = n / 100
                val tenUnit = n % 100
                val ten = tenUnit - (tenUnit % 10)
                val unit = tenUnit % 10

                when {
                    tenUnit in 10..19 -> {
                        if (unit == 0) "${units[hundred]} hundert ${teens[tenUnit]}"
                        else "${units[hundred]} hundert ${teens[tenUnit + unit]}"
                    }
                    unit == 0 && ten != 0 -> "${units[hundred]} hundert ${tens[ten]}"
                    ten == 0 && unit != 0 -> "${units[hundred]} hundert ${units[unit]}"
                    ten != 0 && unit != 0 -> "${units[hundred]} hundert ${units[unit]}und${tens[ten]}"
                    else -> "${units[hundred]} hundert"
                }
            }
            else -> "nicht unterstützt"
        }
    }

}
