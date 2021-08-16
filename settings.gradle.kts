include(":archy-core")
include(":utils-core")
include(":archy-android")

private val isLocalDependencies = false

if (isLocalDependencies) {

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

include(":time")
project(":time").projectDir =
    File(rootDir.parent, "kmm-time-library/time")

rootProject.name = "kmm-support-library"
