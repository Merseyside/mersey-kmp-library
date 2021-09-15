import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(Plugins.androidLibrary)
    plugin(Plugins.kotlinMultiplatform)
    plugin(Plugins.kotlinKapt)
    plugin(Plugins.mobileMultiplatform)
    plugin(Plugins.kotlinSerialization)
    plugin(Plugins.mavenPublish)
    plugin(Plugins.iosFramework)
    `maven-publish-config`
}

group = Application.groupId
version = Application.version

android {
    compileSdkVersion(Application.compileSdk)

    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xinline-classes", "-Xopt-in=org.mylibrary.OptInAnnotation")
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
}

val mppLibs = listOf(
    multiplatformLibs.serialization,
    multiplatformLibs.moko.resources,
    multiplatformLibs.ktor,
    multiplatformLibs.sqldelight
)

val merseyModules = listOf(
    ":utils"
)

val android = listOf(
    androidLibs.sqldelight
)

val merseyLibs = listOf(
    androidLibs.merseyLib.utils,
)

dependencies {
    commonMainApi(projects.time)
    //commonMainApi(LibraryDeps.Libs.MerseyLibs.time)
    mppLibs.forEach { commonMainImplementation(it) }

    android.forEach { lib -> implementation(lib) }
    merseyLibs.forEach { lib -> implementation(lib) }
}

framework {
    mppLibs.forEach { export(it) }
}
