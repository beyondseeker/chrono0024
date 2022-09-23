package com.objectfanatics.composesample

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty

// おまけ。
// MutableStateFlow へのアクセスに by を使う場合に使える。以下例：
// val _name = MutableStateFlow("")
// var name by _name

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> StateFlow<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value

@Suppress("NOTHING_TO_INLINE")
inline operator fun <T> MutableStateFlow<T>.setValue(thisObj: Any?, property: KProperty<*>, value: T) {
    this.value = value
}