package com.purplek.linkbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Observable<T> {

  fun post(value: T)

  fun postDelay(value: T, timeMills: Long)

  fun observe(onwer: LifecycleOwner, observer: Observer<T>)

  fun observeSticky(onwer: LifecycleOwner, observer: Observer<T>)

  fun observeForever(observer: Observer<T>)

  fun observeForeverSticky(observer: Observer<T>)
}