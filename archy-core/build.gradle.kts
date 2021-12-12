plugins {
    id(Plugins.androidConvention)
    id(Plugins.kotlinMultiplatformConvention)
    id(Plugins.kotlinKapt)
    id(Plugins.mobileMultiplatform)
    id(Plugins.resources)
    id(Plugins.sqldelight)
    id(Plugins.iosFramework)
    `maven-publish-config`
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
}

multiplatformResources {
    multiplatformResourcesPackage = Application.applicationId // required
}

val mppLibs = listOf(
    multiplatformLibs.coroutines,
    multiplatformLibs.serialization,
    multiplatformLibs.moko.mvvm,
    multiplatformLibs.moko.mvvm.livedata,
    multiplatformLibs.koin
)

val mppModules = listOf(
    projects.utilsCore
)

dependencies {
    mppModules.forEach { module -> commonMainImplementation(module) }
    mppLibs.forEach { commonMainApi(it) }

    compileOnly("javax.annotation:jsr250-api:1.0")
}

framework {
    mppModules.forEach { export(it) }
    //mppLibs.forEach { export(it.toProvider()) }
}