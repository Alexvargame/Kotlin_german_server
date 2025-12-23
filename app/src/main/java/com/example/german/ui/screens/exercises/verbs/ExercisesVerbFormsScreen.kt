package com.example.german.ui.screens.exercises.verbs

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

import com.example.german.data.ui.viewModel.exercises.verb.ExercisesVerbFormsViewModel

@Composable
fun Exercises_verb_forms_screen(
    navController: NavController,
    //userProfileViewModel: UserProfileViewModel,
    viewModel: ExercisesVerbFormsViewModel
) {

   // var login by remember { mutableStateOf("") }

    val context = LocalContext.current
    Log.e("DIGIT_", "SCREEN")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Формы глагола",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {navController.navigate("exercise_verb_present_form_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Präsens")
        }
        Button(
            onClick = { navController.navigate("exercise_verb_perfect_form_screen")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Perfekt")
        }
        Button(

            onClick = { navController.navigate("exercise_verb_prateritum_form_screen")  },
            modifier = Modifier.fillMaxWidth(),

            ) {
            Text("Präteritum")
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