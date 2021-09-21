import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `android-convention`
    id(Plugins.kotlinMultiplatform)
    id(Plugins.kotlinKapt)
    id(Plugins.mobileMultiplatform)
    id(Plugins.kotlinSerialization)
    id(Plugins.iosFramework)
    `maven-publish-config`
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
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
    common.merseyLib.time,
    multiplatformLibs.serialization,
    multiplatformLibs.moko.resources,
    multiplatformLibs.ktor,
    multiplatformLibs.sqldelight
)

val android = listOf(
    androidLibs.sqldelight
)

val merseyLibs = listOf(
    androidLibs.merseyLib.utils,
)

dependencies {
    mppLibs.forEach { commonMainImplementation(it) }

    android.forEach { lib -> implementation(lib) }
    merseyLibs.forEach { lib -> implementation(lib) }
}

framework {
    mppLibs.forEach { export(it.toProvider()) }
}
