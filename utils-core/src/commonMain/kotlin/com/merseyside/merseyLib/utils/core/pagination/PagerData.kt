package com.merseyside.merseyLib.utils.core.pagination

open class PagerData<Data, Page>(
    val data: Data,
    val nextPage: Page?,
    val prevPage: Page? = null
)