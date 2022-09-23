package uz.yusufbekibragimov.swipecard

sealed class CardShadowSide {
    object ShadowStart : CardShadowSide()
    object ShadowEnd : CardShadowSide()
    object ShadowTop : CardShadowSide()
    object ShadowBottom : CardShadowSide()
}