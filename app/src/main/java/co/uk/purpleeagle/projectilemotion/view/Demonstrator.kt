package co.uk.purpleeagle.projectilemotion.view

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Demonstrator(
    angle: Double,
    velocity: Double,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier){
        drawLine(
            color = Color.Red,
            start = Offset(0f, 0f),
            end = Offset(
                x = (velocity * cos(Math.toRadians(90 - angle))).toFloat(),
                y = (velocity * sin(Math.toRadians(90 - angle))).toFloat()
            ),
            strokeWidth = 10f
        )
    }
}