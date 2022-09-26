package uz.yusufbekibragimov.swipecard

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

fun Modifier.shadowPadding(
    shadowSide: CardShadowSide,
    betweenMargin: Dp,
    cardStackController: CardStackController,
    layer:Float
) = this.padding(
    bottom = if (shadowSide == CardShadowSide.ShadowBottom) ((layer - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
    start = if (shadowSide == CardShadowSide.ShadowStart) ((layer - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
    top = if (shadowSide == CardShadowSide.ShadowTop) ((layer - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
    end = if (shadowSide == CardShadowSide.ShadowEnd) ((layer - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
)

fun Modifier.shadowPaddingLayerThree(
    shadowSide: CardShadowSide,
    betweenMargin: Dp
) = this.padding(
    bottom = if (shadowSide==CardShadowSide.ShadowBottom) betweenMargin * 2 else 0.dp,
    end = if (shadowSide==CardShadowSide.ShadowEnd) betweenMargin * 2 else 0.dp,
    start = if (shadowSide==CardShadowSide.ShadowStart) betweenMargin * 2 else 0.dp,
    top = if (shadowSide==CardShadowSide.ShadowTop) betweenMargin * 2 else 0.dp,
)

fun Modifier.shadowHorizontalPadding(
    margin: Dp,
    cardStackController: CardStackController,
    layer: Float
) = this.padding(
    horizontal = ((layer + (10 - cardStackController.scale.value * 10)) * margin.value).dp
)

fun <E> MutableList<E>.fistToLast() {
    val item = this[this.size - 1]
    this.removeAt(this.size - 1)
    this.add(0, item)
}