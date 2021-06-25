import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(LibraryDeps.Plugins.androidLibrary)
    plugin(LibraryDeps.Plugins.kotlinAndroid)
    plugin(LibraryDeps.Plugins.kotlinKapt)
    plugin(LibraryDeps.Plugins.mavenPublish)
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

    buildFeatures {
        dataBinding = true
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xinline-classes", "-Xopt-in=org.mylibrary.OptInAnnotation")
    }
}

val androidLibraries = listOf(
    LibraryDeps.Libs.appCompat,
    LibraryDeps.Libs.fragment,
    LibraryDeps.Libs.lifecycleViewModelSavedState,
    LibraryDeps.Libs.annotation,
    LibraryDeps.Libs.koin,
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

dependencies {
    api(project(LibraryModules.MultiPlatform.archyCore.name))

    if (isLocalAndroidDependencies()) {
        merseyModules.forEach { module -> implementation(project(module)) }
    } else {
        merseyLibs.forEach { lib -> implementation(lib) }
    }

    androidLibraries.forEach { lib -> implementation(lib) }
}

afterEvaluate {
    publishing.publications {
        create("release", MavenPublication::class.java) {
            from(components.getByName("release"))
        }
    }
}