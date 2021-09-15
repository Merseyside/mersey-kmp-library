object Plugins {
    val androidApplication = GradlePlugin(id = "com.android.application")
    val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
    val kotlinAndroid = GradlePlugin(id = "kotlin-android")
    val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
    val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")
    val mavenCentralPublish = GradlePlugin(id = "publication.convention.publication")
    val mavenPublish = GradlePlugin(id = "maven-publish")
    val signing = GradlePlugin(id = "signing")
    val swiftPackage = GradlePlugin("com.chromaticnoise.multiplatform-swiftpackage", version = "2.0.3")

    val androidLibrary = GradlePlugin(
        id = "com.android.library",
    )

    val kotlinMultiplatform = GradlePlugin(
        id = "org.jetbrains.kotlin.multiplatform",
    )

    val kotlinSerialization = GradlePlugin(
        id = "kotlinx-serialization",
    )

    val resources = GradlePlugin(
        id = "dev.icerock.mobile.multiplatform-resources",
    )

    val sqldelight = GradlePlugin(
        id = "com.squareup.sqldelight",
    )

    val kotlinParcelize = GradlePlugin(
        id = "kotlin-parcelize"
    )
}