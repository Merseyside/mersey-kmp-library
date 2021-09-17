import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(Plugins.androidLibrary)
    plugin(Plugins.kotlinMultiplatform)
    plugin(Plugins.kotlinKapt)
    plugin(Plugins.mobileMultiplatform)
    plugin(Plugins.resources)
    plugin(Plugins.sqldelight)
    plugin(Plugins.iosFramework)
    `maven-publish-config`
}

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
        languageVersion = "1.4"
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=org.mylibrary.OptInAnnotation")
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
}

multiplatformResources {
    multiplatformResourcesPackage = Application.applicationId // required
}

val mppLibs = listOf(
    multiplatformLibs.coroutines,
    multiplatformLibs.serialization,
    multiplatformLibs.moko.mvvm,
    multiplatformLibs.moko.livedata,
    multiplatformLibs.koin
)

val mppModules = listOf(
    projects.utilsCore
)

dependencies {
    mppModules.forEach { module -> commonMainImplementation(module) }
    mppLibs.forEach { commonMainApi(it) }

    compileOnly("javax.annotation:jsr250-api:1.0")
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}