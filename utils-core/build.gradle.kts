@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        plugin(moko.multiplatform)
        id(mersey.android.convention.id())
        id(mersey.kotlin.convention.id())
        plugin(kotlin.serialization)
        plugin(kotlin.kapt)
        id(cocoapods.id())
    }
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

    ios()
    iosSimulatorArm64()

    sourceSets {
        val iosMain by getting
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)
    }

    cocoapods {

        framework {
            summary = "A Kotlin multiplatform mobile library with useful utils"
            homepage = "https://github.com/Merseyside/mersey-kmp-library/tree/master/utils-core"

            version = multiplatformLibs.versions.kmmLibrary.get()
        }

        // https://github.com/tonymillion/Reachability
        pod("Reachability") {
            version = "3.2"
        }
    }
}

kotlinConvention {
    debug = true
    setCompilerArgs(
        "-Xinline-classes",
        "-Xskip-prerelease-check",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xbinary=memoryModel=experimental"
    )
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

    if (isLocalAndroidDependencies()) {
        implementation(project(Modules.Android.MerseyLibs.utils))
    } else {
        merseyLibs.forEach { lib -> implementation(lib) }
    }
}