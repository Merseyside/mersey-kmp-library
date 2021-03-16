import extensions.isLocalDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(LibraryDeps.Plugins.androidLibrary)
    plugin(LibraryDeps.Plugins.kotlinMultiplatform)
    plugin(LibraryDeps.Plugins.kotlinKapt)
    plugin(LibraryDeps.Plugins.mobileMultiplatform)
    plugin(LibraryDeps.Plugins.mavenPublish)
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
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

kotlin {
    android()

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
    LibraryDeps.Libs.MultiPlatform.koin
)

val androidLibraries = listOf(
    LibraryDeps.Libs.appCompat,
    LibraryDeps.Libs.fragment,
    LibraryDeps.Libs.lifecycleViewModelSavedState,
    LibraryDeps.Libs.annotation,
    LibraryDeps.Libs.koinViewModels,
    LibraryDeps.Libs.koinExt
)

val merseyModules = listOf(
    LibraryModules.archy,
    LibraryModules.utils
)

val merseyLibs = listOf(
    LibraryDeps.Libs.MerseyLibs.archy,
    LibraryDeps.Libs.MerseyLibs.utils
)

val mppModules = listOf(
    LibraryModules.MultiPlatform.utils
)

dependencies {
    mppModules.forEach { module -> mppModule(module) }
    mppLibs.forEach { mppLibrary(it) }
    androidLibraries.forEach { lib -> implementation(lib) }

    if (isLocalDependencies()) {
        merseyModules.forEach { module -> implementation(project(module)) }
    } else {
        merseyLibs.forEach { lib -> implementation(lib) }
    }

    compileOnly("javax.annotation:jsr250-api:1.0")
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}

publishing {
    repositories.maven("https://api.bintray.com/maven/merseysoft/mersey-library/kmp-clean-mvvm-arch/;publish=1") {
        name = "bintray"

        credentials {
            username = System.getProperty("BINTRAY_USER")
            password = System.getProperty("BINTRAY_KEY")
        }
    }
}

repositories {
    mavenCentral()
}