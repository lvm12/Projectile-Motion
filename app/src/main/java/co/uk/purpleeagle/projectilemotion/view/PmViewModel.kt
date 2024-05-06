package co.uk.purpleeagle.projectilemotion.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import co.uk.purpleeagle.projectilemotion.motion.OffsetWorldState
import co.uk.purpleeagle.projectilemotion.motion.World
import co.uk.purpleeagle.projectilemotion.motion.items.Vector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class PmViewModel(
    private val navHostController: NavHostController
): ViewModel(){
    private val TAG = "ViewModel"
    private val state = MutableStateFlow(SetupState())
    val world: StateFlow<World> = state.map {
        World(
            started = it.started,
            gravity = it.gravity,
            objects = it.items,
            cor = it.cor,
            x = it.worldX,
            y = it.worldY,
            speedMultiplier = it.speedMultiplier,
            recording = it.recording
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        World(
            gravity = 0.0,
            objects = state.value.items,
            throttleTime = 50,
            started = false,
        )
    )
    fun start() {
        navHostController.navigate("simulation")
        CoroutineScope(Dispatchers.Default).launch {
            state.update { it.copy(
                recording = true,
                started = true
            ) }
            world.value.start()
            Log.d(TAG, "State: ${state.value.started} World: ${world.value.started}")
        }
    }
    fun stop(){
        navHostController.navigate("start")
        CoroutineScope(Dispatchers.Default).launch {
            state.update {
                it.copy(
                    started = false,
                    recording = false
                )
            }
            world.value.stop()
            Log.d(TAG, "State: ${state.value.started} World: ${world.value.started}")
            state.update {
                it.copy(
                    items = mutableListOf(state.value.items[0].apply {
                        x = this.distance + 1
                        y = this.distance + 1
                    })
                )
            }
        }
    }
    fun pause(){
        CoroutineScope(Dispatchers.Default).launch {
            world.value.stop()
            state.update { it.copy(started = false) }
        }
    }
    fun resume(){
        CoroutineScope(Dispatchers.Default).launch {
            world.value.start()
            state.update { it.copy(started = true) }
        }
    }
    fun setDimensions(y: Double, x: Double) {
        state.update { it.copy(
            worldY = y,
            worldX = x
        )}
    }
    fun setDetails(
        angle: Double,
        newVelocity: Double,
        cor: Double,
        gravity: Double,
        friction: Double,
        speedMultiplier: Double
    ){
        state.update { it.copy(
            items = mutableListOf(state.value.items[0].apply {
                velocity = Vector(
                    x = newVelocity* cos(Math.toRadians(angle)),
                    y = newVelocity* sin(Math.toRadians(angle))
                )
            }),
            cor = cor,
            gravity = gravity,
            friction = friction,
            speedMultiplier = speedMultiplier
        ) }
    }
}