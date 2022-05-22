@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        plugin(moko.multiplatform)
        id(mersey.kotlin.convention.id())
        id(mersey.android.convention.id())
        id(cocoapods.id())
    }
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
    }
}

val merseyModules = listOf(
    Modules.MultiPlatform.MerseyLibs.archy,
    Modules.MultiPlatform.MerseyLibs.utils
)

kotlinConvention {
    debug = true
//    setCompilerArgs(
//
//    )
}

val multiplatform = listOf(
    multiplatformLibs.koin
)

//val android = listOf(
//
//)

dependencies {
    commonMainApi(common.merseyLib.kotlin.ext)

    multiplatform.forEach { lib ->
        commonMainImplementation(lib)
    }


    merseyModules.forEach { module ->
        commonMainApi(project(module))
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    ios()
    // Add the ARM64 simulator target
    iosSimulatorArm64()

    sourceSets {
        val iosMain by getting
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependsOn(iosMain)
    }

    cocoapods {
        framework {
            summary = "KMM Mersey library"
            homepage = "https://github.com/Merseyside/mersey-kmp-library"
            baseName = "MultiPlatformLibrary"
            version = multiplatformLibs.versions.kmmLibrary.get()
            podfile = project.file("../ios-app/Podfile")

            export(common.merseyLib.kotlin.ext)
            merseyModules.forEach { module ->
                export(project(module))
            }
        }
    }
}