![Release](https://jitpack.io/v/Nurdiyor/CardScanLib.svg)
 
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
//Scan Card
implementation 'com.github.Nurdiyor:CardScanLib:latest_version'
//TensorFlow
implementation 'org.tensorflow:tensorflow-lite:2.9.0'
```

## Step 3. Create Application class
```
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ScanBaseActivity.warmUp(this)
    }
}
```
## Step 4. Modify your App Activity
```
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardData.setOnClickListener {
            startScanActivity()
        }
    }

    private fun startScanActivity() {

        val intent = ScanActivity.buildIntent(
            this,
            true,
            null,
            "Locate your card in box",
            AppCompatResources.getDrawable(this,R.drawable.ic_flash_on),
            AppCompatResources.getDrawable(this,R.drawable.ic_flash_off),
        )

        activityLauncher.launch(intent)
        //or 
          /* val intent = ScanActivity.buildIntent(
                this,
                true,
                null,
                "Поместите карту в рамку",
                null, //    AppCompatResources.getDrawable(this,R.drawable.ic_flash_on),
                null, //    AppCompatResources.getDrawable(this,R.drawable.ic_flash_off),
            )
            startActivityForResult(intent,ScanActivity.SCAN_REQUEST_CODE)
            */

    }

   private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val scanResult = ScanActivity.creditCardFromResult(intent)
                binding.cardData.text = scanResult.toString()
            }
        }
        
        //or
        /*  
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ScanActivity.isScanResult(requestCode)) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val scanResult = ScanActivity.creditCardFromResult(data)
                val resultMap = mutableMapOf<String, String?>()
                resultMap["card_number"] = scanResult?.number
                resultMap["expiry_month"] = scanResult?.expiryMonth
                resultMap["expiry_year"] = scanResult?.expiryYear
                binding.cardData.text = scanResult.toString()
            }
        }
    }
    */
}
```

For bugs, feature requests, and discussion, please use GitHub Issues. For general questions ONLY, please contact via Telegram.
