object LibraryDeps {
    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")
        val mavenCentralPublish = GradlePlugin(id = "publication.convention.publication")
        val mavenPublish = GradlePlugin(id = "maven-publish")

        val androidLibrary = GradlePlugin(
            id = "com.android.library",
            module = "com.android.tools.build:gradle:${LibraryVersions.Plugins.gradle}"
        )

        val kotlinMultiplatform = GradlePlugin(
            id = "org.jetbrains.kotlin.multiplatform",
            module = "org.jetbrains.kotlin:kotlin-gradle-plugin:${LibraryVersions.Plugins.kotlin}"
        )

        val kotlinSerialization = GradlePlugin(
            id = "kotlinx-serialization",
            module = "org.jetbrains.kotlin:kotlin-serialization:${LibraryVersions.Plugins.serialization}"
        )

        val resources = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:${LibraryVersions.Plugins.mokoResources}"
        )

        val sqldelight = GradlePlugin(
            id = "com.squareup.sqldelight",
            module = "com.squareup.sqldelight:gradle-plugin:${LibraryVersions.Plugins.sqlDelight}"
        )

        val kotlinParcelize = GradlePlugin(
            id = "kotlin-parcelize"
        )
    }

    object Libs {
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersions.Common.coroutines}"
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}"
        val appCompat = "androidx.appcompat:appcompat:${LibraryVersions.Libs.appCompat}"
        val material = "com.google.android.material:material:${LibraryVersions.Libs.material}"
        val fragment = "androidx.fragment:fragment:${LibraryVersions.Libs.fragment}"
        val recyclerView = "androidx.recyclerview:recyclerview:${LibraryVersions.Libs.recyclerView}"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:${LibraryVersions.Libs.constraintLayout}"
        val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersions.Libs.lifecycle}"
        val lifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${LibraryVersions.Libs.lifecycle}"
        val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersions.Libs.lifecycle}"
        val cardView = "androidx.cardview:cardview:${LibraryVersions.Libs.cardView}"
        val annotation = "androidx.annotation:annotation:${LibraryVersions.Libs.annotation}"
        val rxjava2 = "io.reactivex.rxjava2:rxjava:${LibraryVersions.Libs.rxjava2}"
        val paging = "androidx.paging:paging-runtime:${LibraryVersions.Libs.paging}"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:${LibraryVersions.kotlin}"
        val playCore = "com.google.android.play:core:${LibraryVersions.Libs.playCore}"
        val billing = "com.android.billingclient:billing:${LibraryVersions.Libs.billing}"
        val billingKtx = "com.android.billingclient:billing-ktx:${LibraryVersions.Libs.billing}"
        val publisher = "com.google.apis:google-api-services-androidpublisher:${LibraryVersions.Libs.publisher}"
        val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx:${LibraryVersions.Libs.firebaseFirestore}"
        val oauth2 = "com.google.auth:google-auth-library-oauth2-http:${LibraryVersions.Libs.auth}"
        val room = "androidx.room:room-runtime:${LibraryVersions.Libs.room}"
        val roomCompiler = "androidx.room:room-compiler:${LibraryVersions.Libs.room}"
        val roomKtx = "androidx.room:room-ktx:${LibraryVersions.Libs.room}"
        val koin = "io.insert-koin:koin-android:${LibraryVersions.Common.koin}"
        val koinExt = "io.insert-koin:koin-android-ext:${LibraryVersions.Common.koin}"
        val koinScope = "io.insert-koin:koin-android:${LibraryVersions.Common.koin}"
        val navigation = "androidx.navigation:navigation-fragment-ktx:${LibraryVersions.Libs.navigation}"
        val navigationUi = "androidx.navigation:navigation-ui-ktx:${LibraryVersions.Libs.navigation}"
        val keyboard = "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:${LibraryVersions.Libs.keyboard}"
        val worker = "androidx.work:work-runtime-ktx:${LibraryVersions.Libs.worker}"
        val gson = "com.google.code.gson:gson:${LibraryVersions.Libs.gson}"
        val coil = "io.coil-kt:coil:${LibraryVersions.Libs.coil}"
        val dagger = "com.google.dagger:dagger:${LibraryVersions.Libs.dagger}"
        val daggerCompiler = "com.google.dagger:dagger-compiler:${LibraryVersions.Libs.dagger}"
        val filemanager = "com.github.Merseyside:android-filemanager:${LibraryVersions.Libs.filemanager}"
        val typedDatastore = "androidx.datastore:datastore:${LibraryVersions.Libs.typedDataStore}"
        val mokoMvvmDatabinding = "dev.icerock.moko:mvvm-databinding:${LibraryVersions.Common.mokoMvvm}"
        val mokoMvvmViewbinding = "dev.icerock.moko:mvvm-viewbinding:${LibraryVersions.Common.mokoMvvm}"
        val location = "com.google.android.gms:play-services-location:${LibraryVersions.Libs.location}"

        object MerseyLibs {
            private const val base = "com.github.Merseyside.mersey-android-library"
            val archy = "$base:archy:${LibraryVersions.Common.merseyLibs}"
            val utils = "$base:utils:${LibraryVersions.Common.merseyLibs}"
        }

        object MultiPlatform {
            val kotlinStdLib = MultiPlatformLibrary(
                android = "org.jetbrains.kotlin:kotlin-stdlib:${LibraryVersions.Common.kotlinStdLib}",
                common = "org.jetbrains.kotlin:kotlin-stdlib-common:${LibraryVersions.Common.kotlinStdLib}"
            )
            val coroutines = MultiPlatformLibrary(
                android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersions.Common.coroutines}",
                common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibraryVersions.Common.coroutines}",
                iosX64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosx64:${LibraryVersions.Common.coroutines}",
                iosArm64 = "org.jetbrains.kotlinx:kotlinx-coroutines-core-iosarm64:${LibraryVersions.Common.coroutines}"
            )
            val serialization = MultiPlatformLibrary(
                common = "org.jetbrains.kotlinx:kotlinx-serialization-core:${LibraryVersions.Common.serialization}"
            )
            val serializationJson = MultiPlatformLibrary(
                common = "org.jetbrains.kotlinx:kotlinx-serialization-json:${LibraryVersions.Common.serialization}"
            )
            val serializationProtobuf = MultiPlatformLibrary(
                common = "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:${LibraryVersions.Common.serialization}"
            )
            val ktorClient = MultiPlatformLibrary(
                common = "io.ktor:ktor-client-core:${LibraryVersions.Libs.MultiPlatform.ktor}",
                android = "io.ktor:ktor-client-android:${LibraryVersions.Libs.MultiPlatform.ktor}",
                iosX64 = "io.ktor:ktor-client-ios:${LibraryVersions.Libs.MultiPlatform.ktor}",
                iosArm64 = "io.ktor:ktor-client-ios:${LibraryVersions.Libs.MultiPlatform.ktor}"
            )
            val mokoMvvm = MultiPlatformLibrary(
                common = "dev.icerock.moko:mvvm:${LibraryVersions.Common.mokoMvvm}",
                iosX64 = "dev.icerock.moko:mvvm-iosx64:${LibraryVersions.Common.mokoMvvm}",
                iosArm64 = "dev.icerock.moko:mvvm-iosarm64:${LibraryVersions.Common.mokoMvvm}"
            )
            val mokoResources = MultiPlatformLibrary(
                common = "dev.icerock.moko:resources:${LibraryVersions.Libs.MultiPlatform.mokoResources}",
                iosX64 = "dev.icerock.moko:resources-iosx64:${LibraryVersions.Libs.MultiPlatform.mokoResources}",
                iosArm64 = "dev.icerock.moko:resources-iosarm64:${LibraryVersions.Libs.MultiPlatform.mokoResources}"
            )
            val koin = MultiPlatformLibrary(
                common = "io.insert-koin:koin-core:${LibraryVersions.Common.koin}",
                android = "io.insert-koin:koin-android:${LibraryVersions.Common.koin}",
                iosX64 = "io.insert-koin:koin-core-iosx64:${LibraryVersions.Common.koin}",
                iosArm64 = "io.insert-koin:koin-core-iosarm64:${LibraryVersions.Common.koin}"
            )
            val sqlDelight = MultiPlatformLibrary(
                common = "com.squareup.sqldelight:runtime:${LibraryVersions.Libs.MultiPlatform.sqlDelight}",
                android = "com.squareup.sqldelight:android-driver:${LibraryVersions.Libs.MultiPlatform.sqlDelight}",
                iosArm64 = "com.squareup.sqldelight:native-driver:${LibraryVersions.Libs.MultiPlatform.sqlDelight}",
                iosX64 = "com.squareup.sqldelight:native-driver:${LibraryVersions.Libs.MultiPlatform.sqlDelight}"
            )
        }
    }
}