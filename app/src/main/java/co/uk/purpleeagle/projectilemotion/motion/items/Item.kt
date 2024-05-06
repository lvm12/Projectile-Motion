package co.uk.purpleeagle.projectilemotion.motion.items

import androidx.compose.ui.graphics.Color
import co.uk.purpleeagle.projectilemotion.motion.OffsetWorldState
import kotlin.random.Random

abstract class Item(
    open var velocity: Vector,
    open var x: Double,
    open var y: Double,
    open var distance: Double,
    open var previous: MutableList<OffsetWorldState>,
    open var trailColour: Color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
){
    fun newTrailColor(){
        trailColour = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}