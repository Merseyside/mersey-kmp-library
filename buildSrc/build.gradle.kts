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
        //implementation("io.github.merseyside:plugins:1.0.2")
        implementation(mersey.gradlePlugins)
        implementation(android.gradle)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(moko.resourcesGenerator)
        implementation(sqlDelight)
        implementation(nexusPublish)
        implementation(android.navigation.safeArgs)
    }
}