![Release](https://jitpack.io/v/YusufbekIbragimov/SwipeCard.svg)
 
# SwipeCard
To get a Git project into your build:

## Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
   repositories {
     ..
     maven { url 'https://jitpack.io'}
     ..
  }
}
```
## Step 2. Add the dependency Gradle:

```
//Swipe Card
implementation 'com.github.YusufbekIbragimov:SwipeCard:#latest_version'
```

## Step 3. Modify your App Activity or Fragment
```
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
```

For bugs, feature requests, and discussion, please use GitHub Issues. For general questions ONLY, please contact via Telegram.
