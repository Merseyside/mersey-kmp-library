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
        implementation(android.gradle)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(moko.resourcesGenerator)
        implementation(sqldelight)
        implementation(nexusPublish)
        implementation(android.navigation.safeArgs)
    }
}