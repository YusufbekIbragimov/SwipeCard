package uz.yusufbekibragimov.swipecard

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/**
 * A stack of cards that can be dragged.
 *
 * @param list Cards to show in the stack.
 * @param heightCard Card's height for setting.
 * @param betweenMargin it is for set margin Card's between.
 * @param onSwipeLeft Lambda that executes when the animation of swiping left is finished.
 * @param onSwipeRight Lambda that executes when the animation of swiping right is finished.
 * @param onSwipeTop Lambda that executes when the animation of swiping top is finished.
 * @param onSwipeBottom Lambda that executes when the animation of swiping bottom is finished.
 * @param orientation it for set scroll orientation.
 * @param shadowSide it for set side background items.
 *
 * @param thresholdConfig Specifies where the threshold between the predefined Anchors is. This is represented as a lambda
 * that takes two float and returns the threshold between them in the form of a [ThresholdConfig].
 *
 * @author Ibragimov Yusufbek
 */
@ExperimentalMaterialApi
@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    list: List<Any>,
    heightCard: Dp = 100.dp,
    betweenMargin: Dp = 18.dp,
    onSwipeLeft: (item: Any) -> Unit = {},
    onSwipeRight: (item: Any) -> Unit = {},
    onSwipeTop: (item: Any) -> Unit = {},
    onSwipeBottom: (item: Any) -> Unit = {},
    onEmptyStack: (lastItem: Any) -> Unit = {},
    orientation: Orientation = Orientation.Horizontal,
    shadowSide: CardShadowSide = CardShadowSide.ShadowBottom,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    content: @Composable ((item: Any) -> Unit)
) {

    val itemsList by remember { mutableStateOf(arrayListOf<Any>()) }
    itemsList.addAll(list)

    val currentIndex by remember { mutableStateOf(list.size - 1) }
    val cardStackController = rememberCardStackController((heightCard + (betweenMargin * 3)))

    if (currentIndex == -1) onEmptyStack(list.last())

    cardStackController.onSwipeTop = {
        onSwipeTop(itemsList[currentIndex])
        val item = itemsList[list.size - 1]
        itemsList.removeAt(list.size - 1)
        itemsList.add(0, item)
    }

    cardStackController.onSwipeBottom = {
        onSwipeBottom(itemsList[currentIndex])
        val item = itemsList[list.size - 1]
        itemsList.removeAt(list.size - 1)
        itemsList.add(0, item)
    }

    cardStackController.onSwipeLeft = {
        onSwipeLeft(itemsList[currentIndex])
        val item = itemsList[list.size - 1]
        itemsList.removeAt(list.size - 1)
        itemsList.add(0, item)
    }

    cardStackController.onSwipeRight = {
        onSwipeRight(itemsList[currentIndex])
        val item = itemsList[list.size - 1]
        itemsList.removeAt(list.size - 1)
        itemsList.add(0, item)
    }

    Box(
        modifier = modifier
            .height((heightCard + (betweenMargin * 3)))
    ) {

        Box(
            modifier = Modifier
                .padding(
                    bottom = if(shadowSide == CardShadowSide.ShadowBottom) ((1f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                    start = if(shadowSide == CardShadowSide.ShadowStart) ((1f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                    top = if(shadowSide == CardShadowSide.ShadowTop) ((1f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                    end = if(shadowSide == CardShadowSide.ShadowEnd) ((1f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                )
                .padding(horizontal = ((1f + (10 - cardStackController.scale.value * 10)) * 16f).dp)
                .align(Alignment.Center)
                .height(heightCard)
                .shadow(0.dp, RoundedCornerShape(8.dp))
        ) {
            content(
                if (currentIndex < 2) itemsList[list.size + currentIndex - 2] else itemsList[abs(
                    currentIndex - 2
                )]
            )
        }

        Box(
            modifier = Modifier
                .padding(
                    bottom = if (shadowSide == CardShadowSide.ShadowBottom) ((2f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                    start = if (shadowSide == CardShadowSide.ShadowStart) ((2f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                    end = if (shadowSide == CardShadowSide.ShadowEnd) ((2f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                    top = if (shadowSide == CardShadowSide.ShadowTop) ((2f - (10 - cardStackController.scale.value * 10)) * betweenMargin.value).dp else 0.dp,
                )
                .padding(horizontal = ((0f + (10 - cardStackController.scale.value * 10)) * 16f).dp)
                .align(Alignment.Center)
                .height(heightCard)
                .shadow(0.dp, RoundedCornerShape(8.dp))
        ) { content(itemsList[abs(currentIndex - 1)]) }

        Box(
            modifier = Modifier
                .padding(
                    bottom = if (shadowSide==CardShadowSide.ShadowBottom) betweenMargin * 2 else 0.dp,
                    end = if (shadowSide==CardShadowSide.ShadowEnd) betweenMargin * 2 else 0.dp,
                    start = if (shadowSide==CardShadowSide.ShadowStart) betweenMargin * 2 else 0.dp,
                    top = if (shadowSide==CardShadowSide.ShadowTop) betweenMargin * 2 else 0.dp,
                )
                .align(Alignment.Center)
                .height(heightCard)
                .draggableStack(
                    controller = cardStackController,
                    orientation = orientation,
                    thresholdConfig = thresholdConfig
                )
                .moveTo(
                    x = if(orientation == Orientation.Horizontal) cardStackController.offsetX.value else 0f,
                    y = if(orientation == Orientation.Horizontal) 0f else cardStackController.offsetY.value
                )
                .graphicsLayer(
                    rotationZ = cardStackController.rotation.value,
                )
                .shadow(0.dp, RoundedCornerShape(8.dp))
        ) { content(itemsList[currentIndex]) }

    }

}