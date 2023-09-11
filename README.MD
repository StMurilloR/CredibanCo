Android project with the transaction's library for CredibanCo APP

# CredibanCO App

CredibanCo is an App created to solve the client's needs regarding the operations involved in a banking transaction.
Within the APP we can authorize, cancel and consult the transactions.

## Requirements

The main requirements if you are importing the AAR are:
```
def daggerVersion = "2.41"
```

Add in your build.gradle at module level the following dependencies:
```
    implementation("androidx.room:room-runtime:2.3.0")
    annotationProcessor("androidx.room:room-compiler:2.3.0")
    kapt "androidx.room:room-compiler:2.3.0"
    implementation("androidx.room:room-ktx:2.3.0")
    implementation("androidx.room:room-rxjava2:2.3.0")
    implementation("androidx.room:room-rxjava3:2.3.0")
    implementation("androidx.room:room-guava:2.3.0")
    testImplementation("androidx.room:room-testing:2.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation "com.google.dagger:hilt-android:2.41"
    kapt "com.google.dagger:hilt-android-compiler:2.41"
    androidTestImplementation  'com.google.dagger:hilt-android-testing:2.47'
    kaptAndroidTest 'com.google.dagger:hilt-compiler:2.47'
    testImplementation 'com.google.dagger:hilt-android-testing:2.47'
    kaptTest 'com.google.dagger:hilt-compiler:2.47'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
    kapt('com.android.databinding:compiler:3.1.2')
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    
```

Add the KAPT and Android Plugins:

```
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
```

## Author

Sebastián Murillo R.