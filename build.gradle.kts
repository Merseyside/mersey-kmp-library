buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    `nexus-config`
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()

        google()

        maven { url = uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
