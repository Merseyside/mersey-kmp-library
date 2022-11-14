@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.android)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
        plugin(android.navigation.args)
    }
    `android-maven-publish-config`
}

android {
    namespace = "com.merseyside.merseyLib.archy.android"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
    }
    
    buildFeatures {
        dataBinding = true
    }
}

kotlinExtension {
    debug = true
    setCompilerArgs(
        "-Xinline-classes",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xskip-prerelease-check"
    )
}

val androidLibraries = listOf(
    common.serialization,
    androidLibs.appCompat,
    androidLibs.fragment,
    androidLibs.lifecycleViewModelSavedState,
    androidLibs.koin
)

val merseyModules = listOf(
    Modules.Android.MerseyLibs.archy,
    Modules.Android.MerseyLibs.utils
)

val merseyLibs = listOf(
    androidLibs.mersey.archy,
    androidLibs.mersey.utils
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