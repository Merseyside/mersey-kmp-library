package com.merseyside.merseyLib.utils.core.pagination

class PositionPagerData<Data>(
    data: Data,
    currentPage: Int
) : PagerData<Data, Int>(data, currentPage + 1, currentPage - 1)