import java.util.Base64

buildscript {
    repositories {
        gradlePluginPortal()
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()

        google()

        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://raw.githubusercontent.com/guardianproject/gpmaven/master") }
        maven { url = uri("https://maven.fabric.io/public") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    fun getExtraString(name: String) = ext[name]?.toString()

    // Stub secrets to let the project sync and build without the publication values set up
    ext["signing.keyId"] = null
    ext["signing.password"] = null
    ext["signing.signingKey"] = null
    ext["ossrhUsername"] = null
    ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
    val secretPropsFile = project.rootProject.file("local.properties")
    if (secretPropsFile.exists()) {
        secretPropsFile.reader().use {
            java.util.Properties().apply {
                load(it)
            }
        }.onEach { (name, value) ->
            ext[name.toString()] = value
        }
    } else {
        ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
        ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
        ext["signing.signingKey"] = System.getenv("GPG_KEY_CONTENT")
        ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
        ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    }

    val javadocJar by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
    }

    plugins.withId(LibraryDeps.Plugins.mavenPublish.id) {

        configure<PublishingExtension> {
            repositories {
                maven {
                    name = "sonatype"
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = getExtraString("ossrhUsername")
                        password = getExtraString("ossrhPassword")
                    }
                }
            }

            group = LibraryVersions.Application.groupId
            version = LibraryVersions.Application.version

            publications.withType<MavenPublication> {
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set("KMM Mersey library")
                    description.set("Contains arch stuff and useful utils and features")
                    url.set("https://github.com/Merseyside/mersey-kmp-library")

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("Merseyside")
                            name.set("Ivan Sablin")
                            email.set("ivanklessablin@gmail.com")
                        }
                    }
                    scm {
                        url.set("https://github.com/Merseyside/mersey-kmp-library")
                    }
                }
            }

            apply(plugin = LibraryDeps.Plugins.signing.id)

            configure<SigningExtension> {
                val signingKeyId: String? = getExtraString("signing.keyId")
                val signingPassword: String? = getExtraString("signing.password")
                val signingKey: String? = getExtraString("signing.signingKey")?.let { base64Key ->
                    String(Base64.getDecoder().decode(base64Key))
                }
                if (signingKeyId != null) {
                    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
                    sign(publications)
                }
            }
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
