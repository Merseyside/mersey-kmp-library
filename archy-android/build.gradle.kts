plugins {
    id(Plugins.androidConvention)
    id(Plugins.kotlinConvention)
    id(Plugins.kotlinKapt)
    id(Plugins.navigationArgs)
    `maven-publish-config`
}

android {
    buildFeatures {
        dataBinding = true
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
    implementation(androidLibs.bundles.navigation)
}

afterEvaluate {
    publishing.publications {
        create("release", MavenPublication::class.java) {
            from(components.getByName("release"))
        }
    }
}