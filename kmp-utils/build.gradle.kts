import extensions.isLocalDependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(LibraryDeps.Plugins.androidLibrary)
    plugin(LibraryDeps.Plugins.kotlinMultiplatform)
    plugin(LibraryDeps.Plugins.kotlinKapt)
    plugin(LibraryDeps.Plugins.mobileMultiplatform)
    plugin(LibraryDeps.Plugins.mavenPublish)
    plugin(LibraryDeps.Plugins.iosFramework)
}

group = LibraryVersions.Application.applicationId
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
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xinline-classes")
    }
}

kotlin {
    android()

    android {
        publishAllLibraryVariants()
        publishLibraryVariantsGroupedByFlavor = true
    }
}

val mppLibs = listOf(
    LibraryDeps.Libs.MultiPlatform.kotlinStdLib,
    LibraryDeps.Libs.MultiPlatform.serializationJson,
    LibraryDeps.Libs.MultiPlatform.mokoResources,
    LibraryDeps.Libs.MultiPlatform.ktorClient,
    LibraryDeps.Libs.MultiPlatform.sqlDelight
)

val androidLibraries = listOf(
    LibraryDeps.Libs.appCompat,
    LibraryDeps.Libs.publisher,
    LibraryDeps.Libs.oauth2,
    LibraryDeps.Libs.billing,
    LibraryDeps.Libs.billingKtx
)

val merseyModules = listOf(
    LibraryModules.utils
)

val merseyLibs = listOf(
    LibraryDeps.Libs.MerseyLibs.utils
)

dependencies {
    mppLibs.forEach { mppLibrary(it) }
    androidLibraries.forEach { lib -> implementation(lib) }

    if (isLocalDependencies()) {
        merseyModules.forEach { module -> implementation(project(module)) }
    } else {
        merseyLibs.forEach { lib ->  implementation(lib) }
    }
}

framework {
    mppLibs.forEach { export(it) }
}

publishing {
    repositories.maven("https://api.bintray.com/maven/merseysoft/mersey-library/kmp-utils/;publish=1") {
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
