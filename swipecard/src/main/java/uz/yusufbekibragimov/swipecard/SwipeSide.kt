package uz.yusufbekibragimov.swipecard

sealed class SwipeSide {
    object START : SwipeSide()
    object TOP : SwipeSide()
    object END : SwipeSide()
    object BOTTOM : SwipeSide()
}