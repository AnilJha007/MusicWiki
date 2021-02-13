package com.mobile.musicwiki.utils

interface DataTransform<T> {
    fun transfer(item: T): String
}