@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.convention.id())
        id(mersey.kotlin.convention.id())
        plugin(kotlin.serialization)
        plugin(kotlin.kapt)
        plugin(moko.multiplatform)
        plugin(sqldelight)
    }
    `maven-publish-config`
}

android {
    namespace = "com.merseyside.merseyLib.archy.core"
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
}

kotlinConvention {
    debug = true
    setCompilerArgs(
        "-Xinline-classes",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xskip-prerelease-check"
    )
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
}

val mppLibs = listOf(
    multiplatformLibs.coroutines,
    multiplatformLibs.serialization,
    multiplatformLibs.moko.resources,
    multiplatformLibs.moko.mvvm,
    multiplatformLibs.moko.mvvm.livedata,
    multiplatformLibs.moko.resources,
    multiplatformLibs.koin
)

val mppModules = listOf(
    projects.utilsCore
)

dependencies {
    commonMainImplementation(common.kotlin.stdlib)
    commonMainImplementation(common.mersey.kotlin.ext)
    androidMainImplementation(androidLibs.mersey.utils)

    mppModules.forEach { module -> commonMainApi(module) }
    mppLibs.forEach { commonMainApi(it) }
}