package com.example.german.ui.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color



import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

import com.example.german.data.ui.exercises.ExercisesViewModel
@Composable
fun Exercises_screen(
    navController: NavController,
    viewModel: ExercisesViewModel
) {

   // var login by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Exercises",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { /* TODO вход */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Перевод слов")
        }
        Button(
            onClick = { /* TODO вход */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Формы глаголов")
        }
        Button(
            onClick = { /* TODO вход */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Перевод чисел")
        }

        Button (
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
        Button (
            onClick = {
                (context as? Activity)?.finish()
                       },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Выйти")
        }

    }
}