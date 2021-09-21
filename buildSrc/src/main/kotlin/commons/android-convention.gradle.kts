plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(Application.compileSdk)

    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
    }

    android.buildFeatures.dataBinding = true

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
    }

    lintOptions {
        lintConfig = rootProject.file(".lint/config.xml")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
    }
}