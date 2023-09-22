plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    with(catalogGradle) {
        implementation(moko.mobileMultiplatform)
        implementation(mersey.gradlePlugins)
        implementation(android.gradle.stable)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(moko.resourcesGenerator)
        implementation(android.navigation.safeArgs)
        implementation(maven.publish.plugin)
    }
}