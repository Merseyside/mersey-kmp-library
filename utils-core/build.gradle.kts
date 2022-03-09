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
    commonMainApi(multiplatformLibs.bundles.moko.mvvm)
    mppLibs.forEach { commonMainImplementation(it) }

    android.forEach { lib -> implementation(lib) }
    merseyLibs.forEach { lib -> implementation(lib) }
}

framework {
    export(multiplatformLibs.moko.mvvm.asProvider())
    export(multiplatformLibs.moko.mvvm.livedata)
    export(multiplatformLibs.moko.resources)
}
