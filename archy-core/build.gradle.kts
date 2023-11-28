@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.serialization)
        plugin(kotlin.kapt)
    }
    `maven-publish-plugin`
}

android {
    namespace = "com.merseyside.merseyLib.archy.core"
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

//    sourceSets {
//        val iosMain by getting
//        val iosSimulatorArm64Main by getting
//        iosSimulatorArm64Main.dependsOn(iosMain)
//    }
}

kotlinExtension {
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
    common.serialization,
    multiplatformLibs.coroutines,
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
    if (isLocalKotlinExtLibrary()) {
        commonMainImplementation(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainImplementation(common.mersey.kotlin.ext)
    }

    if (isLocalAndroidDependencies()) {
        implementation(project(Modules.Android.MerseyLibs.utils))
    } else {
        implementation(androidLibs.mersey.utils)
    }

    mppModules.forEach { module -> commonMainApi(module) }
    mppLibs.forEach { commonMainApi(it) }
}