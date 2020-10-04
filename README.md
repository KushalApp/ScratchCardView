# ScratchCardView

[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

<p align="center">

A lightweight Android scratch card view. 


<img src="./screenshots/scratch_card_gif.gif" width="360" height="640" alt="android scratch card view" />

</p>



## Using in your projects

### Gradle

Add the ScratchCardView library dependency in your **app module's** `build.gradle`:

```groovy
dependencies {
    implementation 'com.stackhour.lib:libScratchView:1.1.0'
}
```


Make sure that you have `jcenter()` in the list of repositories in your **project level** `build.gradle`:

```
repository {
    jcenter()
}
```

### Usage Example

```
  <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="300dp"
        android:layout_height="300dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <!--   Content to be hidden (Image/Text/Drawable)-->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hidden World!"
            android:textSize="18sp"
            android:textStyle="bold"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <com.stackhour.lib.scratchcard.ScratchCardView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:scratchTouchWidth="25dp"
            app:scratchColor="@color/colorYellow"/>

    </androidx.constraintlayout.widget.ConstraintLayout>

```

### View Options

```
app:scratchTouchWidth="25dp"
app:scratchColor="@color/colorYellow"
app:scratchImage="@drawable/your_drawable"

```


License
-------

    Copyright 2020 Kushal (stackhour.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


