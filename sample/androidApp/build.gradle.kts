@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.application)
        plugin(kotlin.android)
        id(mersey.android.extension.id())
        id(mersey.kotlin.extension.id())
        plugin(kotlin.kapt)
    }
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk

        applicationId = Application.applicationId

        versionCode = 1
        versionName = "0.1.0"
    }

    buildFeatures.dataBinding = true


    lint {
        lintConfig = rootProject.file(".lint/config.xml")
    }

    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/*.kotlin_module",
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt"
            )
        )
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

val android = listOf(
    androidLibs.appCompat,
    androidLibs.material,
    androidLibs.koin
)

val merseyLibs = listOf(
    androidLibs.mersey.archy,
    androidLibs.mersey.utils
)

val merseyModules = listOf(
    Modules.Android.MerseyLibs.archy,
    Modules.Android.MerseyLibs.utils
)

dependencies {
    implementation(project(":sample:mpp-library"))
    android.forEach { lib -> implementation(lib) }

    if (isLocalAndroidDependencies()) {
        merseyModules.forEach { module -> implementation(project(module)) }
    } else {
        merseyLibs.forEach { lib -> implementation(lib) }
    }

    implementation(projects.archyCore)
    implementation(projects.utilsCore)
    implementation(projects.archyAndroid)
}
