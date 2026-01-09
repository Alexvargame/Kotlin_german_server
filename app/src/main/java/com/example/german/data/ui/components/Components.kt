package com.example.german.data.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GreetingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = modifier
            .border(2.dp, Color.Black, RoundedCornerShape(50))
            .background(Color.White, RoundedCornerShape(50))
            .padding(10.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            textAlign = TextAlign.Center
        )
    }
}
