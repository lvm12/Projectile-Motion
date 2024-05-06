package co.uk.purpleeagle.projectilemotion.view

import co.uk.purpleeagle.projectilemotion.motion.items.Item
import co.uk.purpleeagle.projectilemotion.motion.items.Vector
import co.uk.purpleeagle.projectilemotion.motion.items.shapes.CircleItem

data class SetupState(
    val items: MutableList<Item> = mutableListOf(
        CircleItem(
            velocity = Vector(0.0,0.0),
            radius = 50.0
        )
    ),
    val gravity: Double = 9.8,
    val started: Boolean = false,
    val cor: Double = 1.0,
    val friction: Double = 0.0,
    val worldX: Double = 0.0,
    val worldY: Double = 0.0,
    val speedMultiplier: Double = 1.0,
    val recording: Boolean = true,
)
