import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(LibraryDeps.Plugins.androidLibrary)
    plugin(LibraryDeps.Plugins.kotlinMultiplatform)
    plugin(LibraryDeps.Plugins.kotlinKapt)
    plugin(LibraryDeps.Plugins.mobileMultiplatform)
    plugin(LibraryDeps.Plugins.mavenCentralPublish)
    plugin(LibraryDeps.Plugins.resources)
    plugin(LibraryDeps.Plugins.sqldelight)
    plugin(LibraryDeps.Plugins.iosFramework)
}

group = LibraryVersions.Application.groupId
version = LibraryVersions.Application.version

android {
    compileSdkVersion(LibraryVersions.Application.compileSdk)

    defaultConfig {
        minSdkVersion(LibraryVersions.Application.minSdk)
        targetSdkVersion(LibraryVersions.Application.targetSdk)
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/*.kotlin_module")
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
    multiplatformResourcesPackage = LibraryVersions.Application.applicationId // required
}

val mppLibs = listOf(
    LibraryDeps.Libs.MultiPlatform.kotlinStdLib,
    LibraryDeps.Libs.MultiPlatform.coroutines,
    LibraryDeps.Libs.MultiPlatform.serializationJson,
    LibraryDeps.Libs.MultiPlatform.mokoMvvm,
    LibraryDeps.Libs.MultiPlatform.mokoMvvmLiveData,
    LibraryDeps.Libs.MultiPlatform.koin
)

val mppModules = listOf(
    LibraryModules.MultiPlatform.utilsCore
)

dependencies {
    mppModules.forEach { module -> mppModule(module) }
    mppLibs.forEach { mppLibrary(it) }

    compileOnly("javax.annotation:jsr250-api:1.0")
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}