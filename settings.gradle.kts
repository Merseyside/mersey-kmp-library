enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

private val isLocalAndroidDependencies = false
private val isLocalKotlinExtLibrary = false

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        google()
    }

    val catalogVersions = "1.6.1"
    val group = "io.github.merseyside"
    versionCatalogs {
        val multiplatformLibs by creating {
            from("$group:catalog-version-multiplatform:$catalogVersions")
        }

        val androidLibs by creating {
            from("$group:catalog-version-android:$catalogVersions")
        }

        val common by creating {
            from("$group:catalog-version-common:$catalogVersions")
        }

        val catalogPlugins by creating {
            from("$group:catalog-version-plugins:$catalogVersions")
        }
    }
}

include(":archy-core")
include(":utils-core")
include(":archy-android")

include(":sample:mpp-library")
include(":sample:androidApp")

if (isLocalAndroidDependencies) {

    include(":utils")
    project(":utils").projectDir =
        File(rootDir.parent, "mersey-android-library/utils")

    include(":archy")
    project(":archy").projectDir =
        File(rootDir.parent, "mersey-android-library/archy")

    include(":adapters")
    project(":adapters").projectDir =
        File(rootDir.parent, "mersey-android-library/adapters")

    include(":animators")
    project(":animators").projectDir =
        File(rootDir.parent, "mersey-android-library/animators")

}

if (isLocalKotlinExtLibrary) {
    include(":kotlin-ext")
    project(":kotlin-ext").projectDir =
        File(rootDir.parent, "mersey-kotlin-ext/kotlin-ext")
}

rootProject.name = "kmm-support-library"
