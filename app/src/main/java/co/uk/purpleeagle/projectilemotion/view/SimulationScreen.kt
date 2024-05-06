package co.uk.purpleeagle.projectilemotion.view

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import co.uk.purpleeagle.projectilemotion.motion.World
import co.uk.purpleeagle.projectilemotion.motion.items.shapes.CircleItem

@Composable
fun SimulationScreen(
    world: World,
    stop: () -> Unit,
    pause: () -> Unit,
    resume: () -> Unit,
) {
    val TAG = "SimulationScreen"
    val stateList = arrayOfNulls<State<Offset>>(world.objects.size)
    world.objects.forEachIndexed { index, obj ->
        stateList[index] =
            animateOffsetAsState(
                targetValue = Offset(
                    x = obj.y.toFloat()* 10 * world.speedMultiplier.toFloat()- (obj.distance*10*world.speedMultiplier).toFloat(),
                    y = obj.x.toFloat()* 10 * world.speedMultiplier.toFloat()- (obj.distance*10*world.speedMultiplier).toFloat()
                ),
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 200
                    },
                )
            )
        Log.d("SimulationScreen", "offset: ${stateList[index]!!.value}")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            world.objects.forEachIndexed { index, obj ->
                Log.d(TAG, "Number of Coords: ${obj.previous.size}")
                when (obj) {
                    is CircleItem -> {
                        obj.previous.forEachIndexed { index, value ->
                            if (value.offset.y.toDouble() == world.x + obj.distance + 1) obj.newTrailColor()
                            try {
                                if (value.worldState) {
                                    drawLine(
                                        color = obj.trailColour,
                                        start = Offset(
                                            x = value.offset.x*10*world.speedMultiplier.toFloat()- (obj.distance*10*world.speedMultiplier).toFloat(),
                                            y = value.offset.y*10*world.speedMultiplier.toFloat()- (obj.distance*10*world.speedMultiplier).toFloat()
                                        ),
                                        end = Offset(
                                            x = obj.previous[index + 1].offset.x*10*world.speedMultiplier.toFloat()- (obj.distance*10*world.speedMultiplier).toFloat(),
                                            y = obj.previous[index + 1].offset.y*10*world.speedMultiplier.toFloat()- (obj.distance*10*world.speedMultiplier).toFloat()
                                        )
                                    )
                                }
                            }catch (_:Exception){ }
                        }
                        drawCircle(
                            color = Color.Red,
                            radius = obj.radius.toFloat(),
                            center = if (stateList[index] != null) stateList[index]!!.value
                            else Offset(0f, 0f)
                        )
                    }
                }
            }
        }
        Row (modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { stop() },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .weight(1f)
            ) {
                Text(text = "Stop")
            }
            Button(
                onClick = {
                    if(world.started) pause()
                    else resume()
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .weight(1f)
            ) {
                Text(
                    text = if (world.started) "Pause" else "Resume",
                )
            }
        }
    }
}