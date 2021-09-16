enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    val catalogVersions = "1.0.4"
    val group = "io.github.merseyside"
    versionCatalogs {
        create("multiplatformLibs") {
            from("$group:catalog-version-multiplatform:$catalogVersions")
        }

        create("common") {
            from("$group:catalog-version-common:$catalogVersions")
        }

        create("androidLibs") {
            from("$group:catalog-version-android:$catalogVersions")
        }
    }
}

include(":archy-core")
include(":utils-core")
include(":archy-android")

private val isLocalAndroidDependencies = false

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

rootProject.name = "kmm-support-library"
