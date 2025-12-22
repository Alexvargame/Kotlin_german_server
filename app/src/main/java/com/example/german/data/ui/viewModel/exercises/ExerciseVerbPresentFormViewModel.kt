package com.example.german.data.ui.viewModel.exercises


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log


import com.example.german.data.entities.exercises.ExerciseVerbFormResult
import com.example.german.data.entities.exercises.VerbFormExercise
import com.example.german.data.repository.exercises.ExerciseVerbPresentFormRepoRepository

class ExercisesVerbPresentViewModel(
    private val repo: ExerciseVerbPresentFormRepoRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<VerbFormExercise>>(emptyList())
        private set

    fun loadExercises() {
        viewModelScope.launch {
            val verbs = repo.getRandomVerbs(5) //
            verbs.forEach {
                Log.d("Verb_forms_", "WordId: ${it.id}, Word: ${it.word}, ")
            }
            exercises = repo.generateExercises(verbs)
        }
    }
    fun selectAnswer(index: Int, answer: Long) {
        //exercises = exercises.toMutableList().also {
          //  it[index] = it[index].copy(selectedOption = answer)
       // }
    }
    fun checkAnswers(): ExerciseVerbFormResult {
        val correctCount = exercises.count { ex ->
            val userAnswer = ex.userAnswer?.trim()?.lowercase()
            val correct = ex.correctForm.trim().lowercase()
            userAnswer == correct
        }

        val wrongCount = exercises.count { ex ->
            val userAnswer = ex.userAnswer?.trim()?.lowercase()
            val correct = ex.correctForm.trim().lowercase()
            userAnswer != null && userAnswer != correct
        }

        return ExerciseVerbFormResult(
            correctCount = correctCount,
            wrongCount = wrongCount,
            totalQuestions = exercises.size
        )
    }

}
