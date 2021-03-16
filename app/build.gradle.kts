import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import extensions.androidImplementation

plugins {
    plugin(LibraryDeps.Plugins.androidApplication)
    plugin(LibraryDeps.Plugins.kotlinAndroid)
    plugin(LibraryDeps.Plugins.kotlinKapt)
    plugin(LibraryDeps.Plugins.kotlinSerialization)
}

android {
    compileSdkVersion(LibraryVersions.Android.compileSdk)

    defaultConfig {
        minSdkVersion(LibraryVersions.Android.minSdk)
        targetSdkVersion(LibraryVersions.Android.targetSdk)
        versionCode = LibraryVersions.Application.versionCode
        versionName = LibraryVersions.Application.version

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        dataBinding = true
    }

    sourceSets.getByName("main") {
        res.srcDir("src/main/res/")
        res.srcDir("src/main/res/layouts/fragment")
        res.srcDir("src/main/res/layouts/activity")
        res.srcDir("src/main/res/layouts/dialog")
        res.srcDir("src/main/res/layouts/view")
        res.srcDir("src/main/res/value/values-light")
        res.srcDir("src/main/res/value/values-night")
    }
}

val androidLibs = listOf(
    LibraryDeps.Libs.coroutines,
    LibraryDeps.Libs.serialization,
    LibraryDeps.Libs.appCompat,
    LibraryDeps.Libs.material
)


dependencies {
    androidLibs.forEach { lib -> implementation(lib) }

    implementation(LibraryDeps.Libs.filemanager) {
        exclude(group = "com.github.Merseyside.mersey-android-library", module = "utils")
    }

    compileOnly("javax.annotation:jsr250-api:1.0")
}