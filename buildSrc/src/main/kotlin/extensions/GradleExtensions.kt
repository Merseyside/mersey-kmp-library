import dev.icerock.gradle.FrameworkConfig
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory
import org.gradle.api.provider.Provider
import org.gradle.internal.impldep.com.amazonaws.services.kms.model.NotFoundException

inline fun <reified T> Project.findTypedProperty(propertyName: String): T {

    val stringProperty = findProperty(propertyName) as? String

    return stringProperty?.let {
        when (T::class) {
            Boolean::class -> stringProperty.toBoolean()
            Int::class -> stringProperty.toInt()
            Float::class -> stringProperty
            else -> it
        }
    } as? T ?: throw Exception("Property $propertyName not found")
}

fun Project.isLocalDependencies(): Boolean =
    findTypedProperty("build.localDependencies")

fun Project.isLocalAndroidDependencies(): Boolean =
    findTypedProperty("build.localAndroidDependencies")

fun Project.isLocalKotlinExtLibrary(): Boolean =
    findTypedProperty("build.localKotlinExtLibrary")

fun FrameworkConfig.exportVersionCatalogLib(library: Any) {
    when(library) {
        is Provider<*> -> {
            if (library.get() is MinimalExternalModuleDependency) export(library as Provider<MinimalExternalModuleDependency>)
            else throw IllegalArgumentException()
        }

        is ExternalModuleDependencyFactory.DependencyNotationSupplier -> library.asProvider()
        else -> throw Exception()
    }
}