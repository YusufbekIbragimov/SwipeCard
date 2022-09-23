package uz.yusufbekibragimov.swipecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = listOf(
            TestDataModel(R.drawable.theme4, "Highlight 1", "Description for the highlight one"),
            TestDataModel(R.drawable.theme5, "Highlight 2", "Description for the highlight two"),
            TestDataModel(R.drawable.theme7, "Highlight 3", "Description for the highlight three"),
        )

        setContent {
            Column(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SwipeCard(
                    modifier = Modifier,
                    list = list,
                    shadowSide = CardShadowSide.ShadowStart,
                    orientation = Orientation.Horizontal
                ) {

                    (it as TestDataModel)

                    DefaultContent(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 8.dp),
                        item = TestDataModel(it.sourceImage, it.text, it.subText)
                    )

                }
            }
        }

    }

}