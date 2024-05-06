package co.uk.purpleeagle.projectilemotion.motion.items.shapes

import androidx.compose.ui.geometry.Offset
import co.uk.purpleeagle.projectilemotion.motion.OffsetWorldState
import co.uk.purpleeagle.projectilemotion.motion.items.Item
import co.uk.purpleeagle.projectilemotion.motion.items.Vector

data class CircleItem(
    override var velocity: Vector,
    val radius: Double = 50.0,
    override var x: Double = radius + 1,
    override var y: Double = radius + 1,
    override var previous: MutableList<OffsetWorldState> = mutableListOf(
        OffsetWorldState(
            offset = Offset(
                x = radius.toFloat() + 1f,
                y = radius.toFloat() + 1f
            ),
            worldState = true
        )
    )
): Item(
    velocity = velocity,
    x = x,
    y = y,
    distance = radius,
    previous = previous,
)
