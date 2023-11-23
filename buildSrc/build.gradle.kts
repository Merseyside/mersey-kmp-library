plugins {
    `kotlin-dsl`
}


dependencies {
    with(catalogGradle) {
        implementation(mersey.gradlePlugins)
        implementation(android.gradle.stable)
        implementation(kotlin.gradle)
        implementation(kotlin.serialization)
        implementation(moko.resourcesGenerator)
        implementation(android.navigation.safeArgs)
        implementation(maven.publish.plugin)
    }
}