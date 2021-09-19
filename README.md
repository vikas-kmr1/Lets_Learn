# Lets_Learn



![5](https://user-images.githubusercontent.com/73611313/133928141-d9a0ead1-a2e4-4cd3-a2c7-e3662f5e471c.PNG)
![4](https://user-images.githubusercontent.com/73611313/133928144-247d943b-3024-4d39-a0fd-94d863334531.PNG)

![3](https://user-images.githubusercontent.com/73611313/133928207-cabb92e8-8554-455f-8d6d-6819e12f7171.PNG)
![2](https://user-images.githubusercontent.com/73611313/133928214-a2f24123-ce51-4443-905e-ff4ad23ae247.PNG)
![1](https://user-images.githubusercontent.com/73611313/133928217-86009844-c22b-4e51-8ca4-cba0670cd3a0.PNG)



#plugins {
    id 'com.android.application'
}



android {
    compileSdk 30

    defaultConfig {
        applicationId "com.miwok"
        minSdk 21
        targetSdk 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}




