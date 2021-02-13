package com.github.tommykw.tagview

import androidx.annotation.IntDef

class Annotations {
    companion object {
        const val SORT_TYPE_NONE = 0
        const val SORT_TYPE_ASC = 1
        const val SORT_TYPE_DESC = 2
    }

    @IntDef(SORT_TYPE_NONE, SORT_TYPE_ASC, SORT_TYPE_DESC)
    annotation class SortType
}