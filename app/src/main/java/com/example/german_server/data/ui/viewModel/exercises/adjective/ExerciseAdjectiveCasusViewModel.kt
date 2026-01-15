package com.example.german_server.data.ui.viewModel.exercises.adjective


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log



import com.example.german_server.data.entities.exercises.ExerciseAdjectiveCasusResult
import com.example.german_server.data.entities.exercises.ExerciseAdjectiveCasus
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveCasusRepository

data class UiButtonState(
    val value: String,
    val isSelected: Boolean = false,
    val isCorrect: Boolean? = null // null = не проверяли
)

class ExercisesAdjectiveCasusViewModel(
    private val repo: ExerciseAdjectiveCasusRepository
) : ViewModel() {

    var exercise by mutableStateOf<ExerciseAdjectiveCasus?>(null)
        private set
    var articleButtons by mutableStateOf<List<UiButtonState>>(emptyList())
        private set

    var adjectiveButtons by mutableStateOf<List<UiButtonState>>(emptyList())
        private set

    var nounButtons by mutableStateOf<List<UiButtonState>>(emptyList())
        private set

    fun loadExercises() {

        viewModelScope.launch {
            val nounForm = repo.getRandomNounDeclensionsForm(1)[0]
            val adjectiveForm = repo.getRandomAdjectiveForm(1)[0]
            exercise = repo.generateExercises(nounForm, adjectiveForm)

            articleButtons = exercise?.articleOptions?.map { UiButtonState(it) } ?: emptyList()
            adjectiveButtons = exercise?.adjectiveOptions?.map { UiButtonState(it) } ?: emptyList()
            nounButtons = exercise?.nounOptions?.map { UiButtonState(it) } ?: emptyList()

        }
    }

    fun selectAnswer(column: ColumnTypeAdjective, value: String) {
        val ex = exercise ?: return

        when(column) {
            ColumnTypeAdjective.ARTICLE -> {
                articleButtons = articleButtons.map {
                    if(it.value == value) it.copy(
                        isSelected = true,
                        isCorrect = value == ex.correctArticle
                    )
                    else it.copy(isSelected = false)
                }
            }

            ColumnTypeAdjective.ADJECTIVE -> {
                adjectiveButtons = adjectiveButtons.map {
                    if(it.value == value) it.copy(
                        isSelected = true,
                        isCorrect = value == ex.correctAdjectiveForm
                    )
                    else it.copy(isSelected = false)
                }
            }

            ColumnTypeAdjective.NOUN -> {
                nounButtons = nounButtons.map {
                    if(it.value == value) it.copy(
                        isSelected = true,
                        isCorrect = value == ex.correctNounForm
                    )
                    else it.copy(isSelected = false)
                }
            }
        }
    }


    fun checkAnswers(): ExerciseAdjectiveCasusResult {
        val correctCount = 1//exercises.count { it.selectedOption == it.article }
        val wrongCount =
            1//exercises.count { it.selectedOption != null && it.selectedOption != it.article }
        return ExerciseAdjectiveCasusResult(correctCount, wrongCount, 1)
    }
    fun isAllCorrect(): Boolean {
        return articleButtons.any { it.isCorrect == true } &&
                adjectiveButtons.any { it.isCorrect == true } &&
                nounButtons.any { it.isCorrect == true }
    }

}


enum class ColumnTypeAdjective {
    ARTICLE,
    ADJECTIVE,
    NOUN
}
