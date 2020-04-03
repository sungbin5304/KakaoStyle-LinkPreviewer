![Logo](https://raw.githubusercontent.com/sungbin5304/KakaoStyle-LinkPreviewer/master/logo.png)

-----

# Preview [[APK]](https://github.com/sungbin5304/KakaoStyle-LinkPreviewer/raw/master/app-debug.apk)
<div>
<img src="https://raw.githubusercontent.com/sungbin5304/KakaoStyle-LinkPreviewer/master/KakaoTalk_20200403_223946952.png" width="400" height="650">
</div>

# Download [![](https://jitpack.io/v/sungbin5304/KakaoStyle-LinkPreviewer.svg)](https://jitpack.io/#sungbin5304/KakaoStyle-LinkPreviewer)

```Gradle
repositories {
  mavenCentral()
  google()
  maven { 
    url 'https://jitpack.io' 
  }
}
 
dependencies {
  implementation 'androidx.cardview:cardview:1.0.0'
  implementation 'com.github.sungbin5304:KakaoStyle-LinkPreviewer:{version}'
  
  //noinspection GradleCompatible
  implementation 'com.android.support:customtabs:28.0.0' (optional)
}
```

# Usage
## Init
```kotlin
val view = LinkPreviewer(this)
```

## Get View
```kotlin
val layout = view.getView("https://github.com", R.drawable.ic_launcher_foreground, 
            R.string.app_name,R.string.app_name, R.string.app_name)
```

## Create
```kotlin
setContentView(layout)
```

## All Methods
```kotlin
showLinkViewUnderLine()

setRadius(radius: Int)
setFontFamily(fontRes: Int)
setGravity(gravity: Int)
setUseChromeTab(tf: Boolean)

getView(link: String, defaultImageRes: Int, defaultTitleRes: Int,
       defaultDescriptionRes: Int, defaultUrlRes: Int): LinearLayout
getLinkView(): TextView
```

## You can Customize LinkView use `getLinkView()` method.
## You can Customize All Views use `getView(...)` method.

### If you use `setUseChromeTab` method true,<br>you need to implementation customtabs library.

# [[Example]](https://github.com/sungbin5304/KakaoStyle-LinkPreviewer/blob/master/app/src/main/java/com/sungbin/linkpreviewer/MainActivity.kt#L12)

-----

## License
```
                     Copyright 2020 SungBin

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   ```
