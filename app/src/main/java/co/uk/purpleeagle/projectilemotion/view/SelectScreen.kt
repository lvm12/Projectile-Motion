package co.uk.purpleeagle.projectilemotion.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

fun Double.round(decimals: Int): Double {
    val string = toString()
    val pre = string.split(".")[0]
    val post = string.split(".")[1]
    val newPost = post.take(decimals)
    return "$pre.$newPost".toDouble()
}
fun Float.round(decimals: Int): Float {
    val string = toString()
    val pre = string.split(".")[0]
    val post = string.split(".")[1]
    val newPost = post.take(decimals)
    return "$pre.$newPost".toFloat()
}

@Composable
fun SelectScreen(
    start: () -> Unit,
    setDetails: (angle: Double, newVelocity: Double, cor: Double, gravity: Double, friction: Double, speedMultiplier: Double) -> Unit
) {
    val TAG = "SelectScreen"
    var angle by remember { mutableDoubleStateOf(45.0) }
    var newVelocity by remember { mutableDoubleStateOf(25.0) }
    var cor by remember { mutableDoubleStateOf(1.0) }
    var newGravity by remember { mutableDoubleStateOf(9.8) }
    var newFriction by remember { mutableDoubleStateOf(25.0) }
    var speedMultiplier by remember { mutableDoubleStateOf(1.0) }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Set angle (${angle.round(2)}):")
        Slider(
            value = angle.toFloat(),
            onValueChange = {
                Log.d(TAG, "angle: $it")
                angle = it.toDouble()
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            valueRange = 0.0f..90f,
        )
        Text(text = "Set velocity (${newVelocity.round(2)}):")
        Slider(
            value = newVelocity.toFloat(),
            onValueChange = {
                Log.d(TAG, "newVelocity: $it")
                newVelocity = it.toDouble()
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            valueRange = 0f..100f
        )
        Text(text = "Set COR (${cor.round(2)}):")
        Slider(
            value = cor.toFloat(),
            onValueChange = {
                Log.d(TAG, "cor: $it")
                cor = it.toDouble()
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            valueRange = 0.0f..1.5f
        )
        Text(text = "Set gravity (${newGravity.round(2)}):")
        Slider(
            value = newGravity.toFloat(),
            onValueChange = {
                Log.d(TAG, "newGravity: $it")
                newGravity = it.toDouble()
            },
            modifier = Modifier
                .fillMaxWidth(0.9f),
            valueRange = 0.0f..50f
        )
        Text(text = "Set friction (${newFriction.round(2)}):")
        Slider(
            value = newFriction.toFloat(),
            onValueChange = {newFriction = it.toDouble()},
            modifier = Modifier
                .fillMaxWidth(0.9f),
            valueRange = 0f..100f
        )
        Text(text = "Set speed multiplier (${speedMultiplier.round(2)}):")
        Slider(
            value = speedMultiplier.toFloat(),
            onValueChange = {speedMultiplier = it.toDouble()},
            modifier = Modifier
                .fillMaxWidth(0.9f),
            valueRange = 0f..50f
        )
        Demonstrator(
            angle = angle,
            velocity = newVelocity,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.2f)
                .rotate(270f)
        )
        Spacer(modifier = Modifier.height(
            100.dp
        ))
        Button(
            onClick = {
                Log.d(TAG, "Velocity: $newVelocity")
                setDetails(angle, newVelocity, cor, newGravity, newFriction, speedMultiplier)
                start()
            }
        ) {
            Text(text = "Start")
        }
    }
}