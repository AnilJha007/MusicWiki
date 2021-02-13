package com.mobile.musicwiki.customviews

interface DataTransform<T> {
    fun transfer(item: T): String
}