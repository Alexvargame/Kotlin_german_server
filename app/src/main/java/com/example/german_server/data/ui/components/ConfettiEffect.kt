package com.example.german_server.data.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

data class ConfettiParticle(
    val radius: Float,
    val color: Color,
    val speedY: Float,
    var x: Float,
    var y: Float
)

@Composable
fun ConfettiEffect(play: Boolean, modifier: Modifier = Modifier, count: Int = 100) {
    if (!play) return

    var particles by remember {
        mutableStateOf(
            List(count) {
                ConfettiParticle(
                    x = Random.nextFloat() * 1000f,
                    y = Random.nextFloat() * 1600f,
                    radius = Random.nextInt(6, 14).toFloat(),
                    color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f),
                    speedY = Random.nextFloat() * 8 + 2
                )
            }
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            particles = particles.map { p ->
                var newY = p.y + p.speedY
                if (newY > 1600f) newY = -p.radius
                p.copy(y = newY) // создаём новый объект
            }
            delay(16L)
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            drawCircle(
                color = p.color,
                radius = p.radius,
                center = Offset(p.x, p.y)
            )
        }
    }
}