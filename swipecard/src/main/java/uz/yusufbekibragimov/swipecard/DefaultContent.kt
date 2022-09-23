package uz.yusufbekibragimov.swipecard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * @sample DefaultContent class is only for test you can use your custom views instead of this
 * @param item it is bring dates for setting to UI
 *
 * @author Ibragimov Yusufbek
 * */
@Composable
fun DefaultContent(
    modifier: Modifier,
    item: TestDataModel
) {
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = item.sourceImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.text,
                    color = Color.White,
                    modifier = Modifier // disable the highlight of the text when dragging
                )
                Text(
                    text = item.subText,
                    color = Color.White,
                    modifier = Modifier // disable the highlight of the text when dragging
                )
            }
        }
    }
}