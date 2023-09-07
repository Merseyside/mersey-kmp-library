package com.merseyside.merseyLib.utils.core.mappers.koin

import com.merseyside.merseyLib.utils.core.mappers.Mapper
import com.merseyside.merseyLib.utils.core.mappers.Mappers
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.onClose

fun Module.declareKoinMappers(
    qualifier: Qualifier
) {
    single(qualifier) { Mappers() }
}

fun Module.addMappers(
    qualifier: Qualifier,
    key: String,
    mapperList: () -> List<Mapper<out Any>>
) {
    var mappers: Mappers? = null
    single(
        qualifier = named("${qualifier.value}_$key"),
        createdAtStart = true
    ) {
        mappers = get<Mappers>(qualifier).also { mappers ->
            mappers.addMappers(mapperList())
        }
    }.onClose {
        mappers?.removeMappers(mapperList())
    }
}