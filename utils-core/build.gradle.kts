plugins {
    id(Plugins.androidConvention)
    id(Plugins.kotlinMultiplatformConvention)
    id(Plugins.mobileMultiplatform)
    id(Plugins.kotlinSerialization)
    id(Plugins.kotlinKapt)
    id(Plugins.iosFramework)
    `maven-publish-config`
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
}

val mppLibs = listOf(
    common.merseyLib.time,
    multiplatformLibs.serialization,
    multiplatformLibs.moko.resources,
    multiplatformLibs.ktor,
    multiplatformLibs.sqldelight
)

val android = listOf(
    androidLibs.sqldelight,
    androidLibs.lifecycleLiveDataKtx
)

val merseyLibs = listOf(
    androidLibs.merseyLib.utils
)

dependencies {
    if (isLocalKotlinExtLibrary()) {
        commonMainApi(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainApi(common.merseyLib.kotlin.ext)
    }
    commonMainImplementation(multiplatformLibs.bundles.moko.mvvm)
    mppLibs.forEach { commonMainImplementation(it) }

    android.forEach { lib -> implementation(lib) }
    merseyLibs.forEach { lib -> implementation(lib) }
}

framework {
    //mppLibs.forEach { export(it.toProvider()) }
}
