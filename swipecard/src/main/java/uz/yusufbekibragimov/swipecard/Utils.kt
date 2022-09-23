package uz.yusufbekibragimov.swipecard

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import kotlin.math.roundToInt

fun Modifier.moveTo(
    x: Float,
    y: Float
) = this.then(Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x.roundToInt(), y.roundToInt())
    }
})