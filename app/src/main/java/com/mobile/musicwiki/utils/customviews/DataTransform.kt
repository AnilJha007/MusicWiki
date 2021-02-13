package com.mobile.musicwiki.utils.customviews

interface DataTransform<T> {
    fun transfer(item: T): String
}