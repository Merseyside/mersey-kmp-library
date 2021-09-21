import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `android-convention`
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    `maven-publish-config`
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
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
    Modules.Android.MerseyLibs.archy,
    Modules.Android.MerseyLibs.utils
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