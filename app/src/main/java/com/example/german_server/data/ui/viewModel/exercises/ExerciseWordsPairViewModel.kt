package com.example.german_server.data.ui.viewModel.exercises


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import androidx.compose.ui.graphics.Color

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


import com.example.german_server.data.entities.exercises.WordsPairExercise
import com.example.german_server.data.repository.exercises.ExerciseWordsPairRepository

data class UiButtonState(
    val wordId: Long,
    val text: String,
    var color: Color
)
enum class ColumnType {
    LEFT, RIGHT
}

class ExercisesWordsPairViewModel(
    private val repo: ExerciseWordsPairRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<WordsPairExercise>>(emptyList())
        private set
    var leftButtons by mutableStateOf<List<UiButtonState>>(emptyList())
        private set
    var rightButtons by mutableStateOf<List<UiButtonState>>(emptyList())

    var selectedLeft: UiButtonState? = null
    var selectedRight: UiButtonState? = null

    var showRepeatButton by mutableStateOf(false)
        private set

    var showBackButton by mutableStateOf(false)
        private set
    var errorCount by mutableStateOf(0)
        private set

    // Флаг завершения упражнения
    var exerciseFinished by mutableStateOf(false)
        private set

    fun loadExercises() {
        viewModelScope.launch {
            val words = repo.getRandomWords(5)
            leftButtons = words.map { UiButtonState(it.id, it.german, Color.LightGray) }
            rightButtons =
                words.shuffled().map { UiButtonState(it.id, it.russian, Color.LightGray) }
        }
    }

    // Функция для левой колонки — возвращает актуальный список

    // обработка клика (пока заглушка)
    fun onButtonClick(wordId: Long, column: ColumnType) {
        when (column) {
            ColumnType.LEFT -> {
                // Нельзя кликать дважды в одной колонке
                if (selectedLeft != null) return
                Log.e("WORD_PAIR_", "${selectedLeft}")
                // Выбираем кнопку из левой колонки и подсвечиваем светло-зелёным
                selectedLeft = leftButtons.find { it.wordId == wordId }
                leftButtons = leftButtons.map {
                    if (it.wordId == wordId) it.copy(color = Color.Blue) else it
                }
            }

            ColumnType.RIGHT -> {
                if (selectedRight != null) return

                selectedRight = rightButtons.find { it.wordId == wordId }
                rightButtons = rightButtons.map {
                    if (it.wordId == wordId) it.copy(color = Color.Blue) else it
                }
            }
        }

        // Если обе кнопки выбраны, проверяем соответствие
        if (selectedLeft != null && selectedRight != null) {
            val left = selectedLeft!!
            val right = selectedRight!!
            Log.e("WORD_PAIR_", "${left.wordId} / ${right.wordId}")
            if (left.wordId == right.wordId) {
                // Пара совпадает — делаем темно-зелёными
                leftButtons = leftButtons.map { btn ->
                    if (btn.wordId == left.wordId) btn.copy(color = Color.Green) else btn
                }

                rightButtons = rightButtons.map { btn ->
                    if (btn.wordId == right.wordId) btn.copy(color = Color.Green) else btn
                }
                checkExerciseFinished()
            } else {
                leftButtons = leftButtons.map { btn ->
                    if (btn.wordId == left.wordId) btn.copy(color = Color.Red) else btn
                }

                rightButtons = rightButtons.map { btn ->
                    if (btn.wordId == right.wordId) btn.copy(color = Color.Red) else btn
                }
                errorCount++
                // Пара не совпадает — мигаем красным
                // Через небольшую задержку возвращаем в исходное состояние
                viewModelScope.launch {
                    delay(100) // полсекунды
                    leftButtons = leftButtons.map { btn ->
                        if (btn.wordId == left.wordId) btn.copy(color = Color.LightGray) else btn
                    }

                    rightButtons = rightButtons.map { btn ->
                        if (btn.wordId == right.wordId) btn.copy(color = Color.LightGray) else btn
                    }
                }
            }

            // Сброс выбранных кнопок
            selectedLeft = null
            selectedRight = null

            // Проверка завершения упражнения
        }
    }
    private fun checkExerciseFinished() {
        val finished =
            leftButtons.all { it.color == Color.Green} &&
                    rightButtons.all { it.color == Color.Green}

        if (finished) {
            exerciseFinished = true
        }
    }

}


