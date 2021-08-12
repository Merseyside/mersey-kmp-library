plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.5.30-RC"
    kotlin("plugin.serialization") version "1.5.30-RC"
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    gradlePluginPortal()
}

val multiplatform = "0.12.0"
val kotlin = "1.5.30-RC"
val gradle = "4.2.1"
val resources = "0.15.1"
val sqldelight = "1.5.0"

dependencies {
    implementation("dev.icerock:mobile-multiplatform:$multiplatform")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
    implementation("com.android.tools.build:gradle:$gradle")
    implementation("org.jetbrains.kotlin:kotlin-serialization:$kotlin")
    implementation("dev.icerock.moko:resources-generator:$resources")
    implementation("com.squareup.sqldelight:gradle-plugin:$sqldelight")
}