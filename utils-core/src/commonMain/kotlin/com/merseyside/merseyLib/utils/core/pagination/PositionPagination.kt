package com.merseyside.merseyLib.utils.core.pagination

abstract class PositionPagination<Data>(initPage: Int) :
    Pagination<PositionPagerData<Data>, Data, Int>(initPage)