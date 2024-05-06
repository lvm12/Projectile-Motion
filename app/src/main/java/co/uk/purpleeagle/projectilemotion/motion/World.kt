package co.uk.purpleeagle.projectilemotion.motion

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.ui.geometry.Offset
import co.uk.purpleeagle.projectilemotion.motion.items.Item
import co.uk.purpleeagle.projectilemotion.motion.items.Vector
import co.uk.purpleeagle.projectilemotion.view.round
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

data class World(
    val x: Double = 0.0,
    val y: Double = 0.0,
    var objects: List<Item>,
    private var gravity: Double,
    var throttleTime: Int = 50,
    var started: Boolean = false,
    var cor: Double = 0.5,
    var friction: Double = 15.0,
    var speedMultiplier: Double = 1.0,
    var recording: Boolean = true,
){
    fun start(){
        val TAG = "World"
        recording = true
        started = true
        var x = 0
        CoroutineScope(Dispatchers.Main).launch {
            while (started) {
                val start = Clock.System.now().toEpochMilliseconds()
                Log.d(TAG, "(${objects[0].x},${objects[0].y})")
                val new = objects.map {
                    applyWorld(it, x)
                }
                objects = new
                x ++
                val end = Clock.System.now().toEpochMilliseconds()

                delay((throttleTime - (end - start)))
            }
        }
    }
    fun stop(){
        started = false
        recording = false
    }
    private fun applyWorld(item: Item, count: Int): Item {
        val topBounce = item.y + item.distance == y
        val bottomBounce = item.y - item.distance == 0.0
        val verticalBounce = topBounce || bottomBounce
        val startBounce = item.x - item.distance == 0.0
        val endBounce = item.x + item.distance == x
        val horizontalBounce = startBounce || endBounce
        val list = item.previous
        val TAG = "Apply"
        val t = throttleTime/1000.0
        val tx = count * (throttleTime/1000.0)
        Log.d(TAG, "Time passed: $tx")
        val ux = item.velocity.x
        Log.d(TAG, "ux: $ux")
        val uy = item.velocity.y
        Log.d(TAG, "uy: $uy")
        val vx: Double
        val vy: Double
        if (verticalBounce && !horizontalBounce){
            Log.d(TAG, "Bounced")
            vy = cor * -uy
            vx = cor* ux - friction*t
        }else{
            if(horizontalBounce && !verticalBounce){
                vy = cor * uy
                vx = cor * -ux
            }else {
                if (verticalBounce && horizontalBounce){
                    vx = cor * -ux
                    vy = cor * -uy
                }else {
                    vx = ux
                    vy = uy - gravity * t
                }
            }
        }
        val sx: Double = if (horizontalBounce){
            vx * t
        }else {
            (1.0/2.0) * (ux + vx) * t
        }
        val totalSx = if (horizontalBounce){
            if (startBounce) {
                item.distance + sx
            }else{
                x - item.distance - sx
            }
        }else{
            item.x + sx
        }
        Log.d(TAG, "vy: $vy")
        val sy: Double = if (verticalBounce) {
            vy*t
        }else{
            (1.0 / 2.0) * (uy + vy) * t
        }
        Log.d(TAG, "sy: $sy")
        val totalSy = if (verticalBounce) {
            if (bottomBounce) {
                sy + item.distance
            }else{
                y - item.distance - sy
            }
        }else{
            val newTotal = item.y + sy
            if (newTotal - item.distance<0) 0.0 + item.distance
            else newTotal
        }
        Log.d(TAG, "total sy: $totalSy")
        Log.d(TAG, "World state: $started")
        list.add(
            OffsetWorldState(
                offset = Offset(
                    x = totalSy.toFloat(),
                    y = totalSx.toFloat()
                ),
                worldState = started
            )
        )
        return item.apply {
            velocity = Vector(
                x = ux,
                y = vy
            )
            x = totalSx
            y = totalSy
            previous = list
        }
    }
}