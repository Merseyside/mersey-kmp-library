@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        plugin(moko.multiplatform)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.serialization)
        plugin(kotlin.kapt)
        //id(cocoapods.id())
    }
    `maven-publish-config`
}

android {
    namespace = "com.merseyside.merseyLib.utils.core"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    ios()
    iosSimulatorArm64()

    sourceSets {
        val iosMain by getting
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)
    }

//    cocoapods {
//
//        framework {
//            summary = "A Kotlin multiplatform mobile library with useful utils"
//            homepage = "https://github.com/Merseyside/mersey-kmp-library/tree/master/utils-core"
//
//            version = multiplatformLibs.versions.mersey.kmm.get()
//        }
//
//        // https://github.com/tonymillion/Reachability
//        pod("Reachability") {
//            version = "3.2"
//        }
//    }
}

kotlinExtension {
    debug = true
    setCompilerArgs(
        "-Xcontext-receivers",
        "-Xinline-classes",
        "-Xskip-prerelease-check",
        "-opt-in=kotlin.RequiresOptIn"
    )
}

val mppLibs = listOf(
    common.mersey.time,
    multiplatformLibs.serialization,
    multiplatformLibs.moko.resources,
    multiplatformLibs.ktor,
    multiplatformLibs.sqldelight,
    multiplatformLibs.koin
)

val android = listOf(
    androidLibs.sqldelight,
    androidLibs.lifecycleLiveDataKtx,
    androidLibs.koin
)

val merseyLibs = listOf(
    androidLibs.mersey.utils
)

dependencies {
    if (isLocalKotlinExtLibrary()) {
        commonMainApi(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainApi(common.mersey.kotlin.ext)
    }
    commonMainApi(multiplatformLibs.bundles.moko.mvvm)
    mppLibs.forEach { commonMainImplementation(it) }

    android.forEach { lib -> implementation(lib) }

    if (isLocalAndroidDependencies()) {
        implementation(project(Modules.Android.MerseyLibs.utils))
    } else {
        merseyLibs.forEach { lib -> implementation(lib) }
    }
}