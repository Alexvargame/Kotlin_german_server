package com.example.german_server.data.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import com.example.german_server.data.entities.exercises.AnswerDetailUi

@Composable
fun AnswerDetailsList(
    details: List<AnswerDetailUi>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(details) { detail ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = detail.leftText,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (detail.isCorrect) "✓ ${detail.correctAnswer}"
                    else "✗ ${detail.userAnswer ?: "-"} → ${detail.correctAnswer}",
                    color = if (detail.isCorrect) Color.Green else Color.Red,
                    fontSize = 18.sp
                )
            }
        }
    }
}