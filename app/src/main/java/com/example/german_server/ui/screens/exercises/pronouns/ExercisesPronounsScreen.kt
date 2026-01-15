package com.example.german_server.ui.screens.exercises.pronouns

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

import android.util.Log

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

import com.example.german_server.data.ui.viewModel.exercises.pronoun.ExercisesPronounsViewModel

@Composable
fun Exercises_pronouns_screen(
    navController: NavController,
    //userProfileViewModel: UserProfileViewModel,
    viewModel: ExercisesPronounsViewModel
) {

   // var login by remember { mutableStateOf("") }

    val context = LocalContext.current
    Log.e("PRONOUNS_", "SCREEN")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Упражнения с местоимениями",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {navController.navigate("exercise_pronoun_button_screen")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Выбор из вариантов")
        }
        Button(
            onClick = {navController.navigate("exercise_pronoun_enter_screen")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Написание")
        }


        Button (
            onClick = {
                navController.popBackStack("exercises_screen", false)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }


    }
}