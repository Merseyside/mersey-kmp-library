plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    gradlePluginPortal()
}

val multiplatform = "0.12.0"
val kotlin = "1.6.0"
val gradle = "7.0.3"
val resources = "0.15.1"
val sqldelight = "1.5.3"
val nexus = "1.1.0"
val navigation = "2.4.0-beta02"

dependencies {
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("dev.icerock.moko:resources-generator:$resources")
    implementation("com.squareup.sqldelight:gradle-plugin:$sqldelight")
    implementation("io.github.gradle-nexus:publish-plugin:$nexus")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:$navigation")
}