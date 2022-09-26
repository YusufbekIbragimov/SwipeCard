package uz.yusufbekibragimov.swipecard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.ThresholdConfig
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sign

/**
 * Controller of the [draggableStack] modifier.
 *
 * @param screenWidth The width of the screen used to calculate properties such as rotation and scale
 * @param animationSpec The default animation that will be used to animate swipes.
 *
 * @author Ibragimov Yusufbek
 */
open class CardStackController(
    val scope: CoroutineScope,
    private val screenWidth: Float,
    private val screenHeight: Float,
    internal val animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
) {

    /**
     * Anchors
     */
    val right = Offset(screenWidth, 0f)
    val bottom = Offset(0f, screenHeight)
    val center = Offset(0f, 0f)

    /**
     * Threshold to start swiping
     */
    var threshold: Float = 0.0f

    /**
     * The current position (in pixels) of the First Card.
     */
    val offsetX = Animatable(0f)
    val offsetY = Animatable(0f)

    /**
     * The current rotation (in pixels) of the First Card.
     */
    val rotation = Animatable(0f)

    /**
     * The current scale factor (in pixels) of the Card before the first one displayed.
     */
    val scale = Animatable(0.9f)

    var onSwipe: (swipeSide: SwipeSide) -> Unit = {}

    fun swipeLeft() {
        scope.apply {
            launch {
                offsetX.animateTo(-screenWidth, tween(90))

                onSwipe(SwipeSide.START)

                // After the animation of swiping return back to Center to make it look like a cycle
                launch {
                    offsetX.snapTo(center.x)
                }
                launch {
                    offsetY.snapTo(0f)
                }
                launch {
                    rotation.snapTo(0f)
                }
                launch {
                    scale.snapTo(0.9f)
                }
            }

            launch {
                scale.animateTo(1f, tween(90))
            }
        }

    }

    fun swipeRight() {
        scope.apply {
            launch {
                offsetX.animateTo(screenWidth, tween(90))

                onSwipe(SwipeSide.END)

                // After the animation return back to Center to make it look like a cycle
                launch {
                    offsetX.snapTo(center.x)
                }
                launch {
                    offsetY.snapTo(0f)
                }
                launch {
                    scale.snapTo(0.9f)
                }
                launch {
                    rotation.snapTo(0f)
                }
            }

            launch {
                scale.animateTo(1f, tween(90))
            }
        }

    }

    fun swipeTop() {
        scope.apply {
            launch {
                offsetY.animateTo(-screenHeight * 6, tween(90))

                onSwipe(SwipeSide.TOP)

                // After the animation return back to Center to make it look like a cycle
                launch {
                    offsetX.snapTo(center.x)
                }
                launch {
                    offsetY.snapTo(0f)
                }
                launch {
                    scale.snapTo(0.9f)
                }
                launch {
                    rotation.snapTo(0f)
                }
            }

            launch {
                scale.animateTo(1f, tween(90))
            }
        }

    }

    fun swipeBottom() {
        scope.apply {
            launch {
                offsetY.animateTo(screenHeight * 6, tween(90))

                onSwipe(SwipeSide.BOTTOM)

                // After the animation return back to Center to make it look like a cycle
                launch {
                    offsetX.snapTo(center.x)
                }
                launch {
                    offsetY.snapTo(0f)
                }
                launch {
                    scale.snapTo(0.9f)
                }
                launch {
                    rotation.snapTo(0f)
                }
            }

            launch {
                scale.animateTo(1f, tween(90))
            }
        }

    }

    fun returnCenter() {
        scope.apply {
            launch {
                offsetX.animateTo(center.x, tween(90))
            }
            launch {
                offsetY.animateTo(center.y, tween(90))
            }
            launch {
                rotation.animateTo(0f, tween(90))
            }
            launch {
                scale.animateTo(0.9f, tween(90))
            }
        }
    }

}

/**
 * Create and [remember] a [CardStackController] with the default animation clock.
 *
 * @param animationSpec The default animation that will be used to animate to a new state.
 */
@Composable
fun rememberCardStackController(
    screenHeight: Dp,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
): CardStackController {

    val scope = rememberCoroutineScope()

    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    return remember {
        CardStackController(
            scope = scope,
            screenWidth = screenWidth,
            screenHeight = screenHeight.value,
            animationSpec = animationSpec
        )
    }
}


/**
 * Enable drag gestures between a set of predefined anchors defined in [controller].
 *
 * @param controller The controller of the [draggableStack].
 * @param thresholdConfig Specifies where the threshold between the predefined Anchors is. This is represented as a lambda
 * that takes two float and returns the threshold between them in the form of a [ThresholdConfig].
 */
@OptIn(ExperimentalMaterialApi::class)
fun Modifier.draggableStack(
    controller: CardStackController,
    orientation: Orientation,
    thresholdConfig: (Float, Float) -> ThresholdConfig,
): Modifier = composed {

    val density = LocalDensity.current
    val thresholds = { a: Float, b: Float ->
        with(thresholdConfig(a, b)) {
            density.computeThreshold(a, b)
        }
    }

    controller.threshold = thresholds(controller.center.x, controller.right.x)
    Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                if (orientation == Orientation.Horizontal) {
                    if (controller.offsetX.value <= 0f) {
                        if (controller.offsetX.value > -72) controller.returnCenter()
                        else controller.swipeLeft()
                    } else {
                        if (controller.offsetX.value < 72) controller.returnCenter()
                        else controller.swipeRight()
                    }
                } else {
                    if (controller.offsetY.value <= 0f) {
                        if (controller.offsetY.value > -72) controller.returnCenter()
                        else controller.swipeTop()
                    } else {
                        if (controller.offsetY.value < 72) controller.returnCenter()
                        else controller.swipeBottom()
                    }
                }
            },
            onDrag = { change, dragAmount ->
                controller.scope.apply {
                    launch {

                        controller.offsetX.snapTo(controller.offsetX.value + dragAmount.x)
                        controller.offsetY.snapTo(controller.offsetY.value + dragAmount.y)

                        if (orientation == Orientation.Horizontal) {

                            val targetRotation = normalize(
                                controller.center.x,
                                controller.right.x,
                                abs(controller.offsetX.value),
                                0f,
                                10f
                            )
                            controller.rotation.snapTo(targetRotation * controller.offsetX.value.sign)
                            controller.scale.snapTo(
                                normalize(
                                    controller.center.x,
                                    controller.right.x / 3,
                                    abs(controller.offsetX.value),
                                    0.9f
                                )
                            )

                        } else {
                            controller.scale.snapTo(
                                normalizeVertical(
                                    controller.center.y,
                                    controller.bottom.y / 3,
                                    abs(controller.offsetY.value),
                                    0.9f
                                )
                            )
                        }
                    }
                }
                change.consumePositionChange()
            }
        )
    }
}

/**
 * Min max normalization
 *
 * @param min Minimum of the range
 * @param max Maximum of the range
 * @param v Value to normalize in the given [min, max] range
 * @param startRange Transform the normalized value with a particular start range
 * @param endRange Transform the normalized value with a particular end range
 */
fun normalize(
    min: Float,
    max: Float,
    v: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) {
        "Start range is greater than End range"
    }
    val value = v.coerceIn(min, max)
    return (value - min) / (max - min) * (endRange - startRange) + startRange
}

/**
 * Min max normalization
 *
 * @param min Minimum of the range
 * @param max Maximum of the range
 * @param v Value to normalize in the given [min, max] range
 * @param startRange Transform the normalized value with a particular start range
 * @param endRange Transform the normalized value with a particular end range
 */
fun normalizeVertical(
    min: Float,
    max: Float,
    v: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) {
        "Start range is greater than End range"
    }
    val value = v.coerceIn(min, max)
    return (value - min) / (max - min) * (endRange - startRange) + startRange
}
