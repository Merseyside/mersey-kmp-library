import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    plugin(Plugins.androidLibrary)
    plugin(Plugins.kotlinAndroid)
    plugin(Plugins.kotlinKapt)
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
    common.serialization,
    androidLibs.appCompat,
    androidLibs.fragment,
    androidLibs.lifecycleViewModelSavedState,
    androidLibs.annotation,
    androidLibs.koin
)

val merseyModules = listOf(
    ":archy",
    ":utils"
)

val merseyLibs = listOf(
    androidLibs.merseyLib.archy,
    androidLibs.merseyLib.utils
)

dependencies {
    api(projects.utilsCore)
    api(projects.archyCore)

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