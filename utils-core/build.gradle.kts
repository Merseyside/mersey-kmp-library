@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.serialization)
        plugin(kotlin.kapt)
        //id(cocoapods.id())
    }
    `maven-publish-plugin`
}

android {
    namespace = "com.merseyside.merseyLib.utils.core"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
    }
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()
//
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
//
//        ios.deploymentTarget = "15.0"
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
    common.serialization,
    multiplatformLibs.moko.resources,
    multiplatformLibs.ktor,
    multiplatformLibs.sqldelight,
    multiplatformLibs.koin,
    multiplatformLibs.settings
)

val android = listOf(
    androidLibs.recyclerView,
    androidLibs.sqldelight,
    androidLibs.lifecycleLiveDataKtx,
    androidLibs.koin,
    androidLibs.navigation
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

    commonMainImplementation(common.mersey.time)
}