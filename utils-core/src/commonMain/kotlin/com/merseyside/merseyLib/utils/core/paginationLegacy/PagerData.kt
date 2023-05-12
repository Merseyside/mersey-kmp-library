package com.merseyside.merseyLib.utils.core.paginationLegacy

@Deprecated("Use new pager data from com.merseyside.merseyLib.pagination")
open class PagerData<Data, Page>(
    val data: Data,
    val nextPage: Page?,
    val prevPage: Page? = null
)