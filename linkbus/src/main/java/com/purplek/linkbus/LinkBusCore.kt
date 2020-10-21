package com.purplek.linkbus

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import java.util.concurrent.ConcurrentHashMap

internal class LinkBusCore private constructor() {

  private val mObservableMap = ConcurrentHashMap<String, Observable<out Any>>()

  private val mMainHandler = Handler(Looper.getMainLooper())

  fun <T : Any> get(key: String, eventType: Class<T>): Observable<T> {
    val eventKey = "${key}_${eventType.name}"
    val observable = mObservableMap[eventKey]
    return if (observable == null) {
      val newObservable = LinkObservable<T>()
      mObservableMap[eventKey] = newObservable
      newObservable
    } else {
      observable as Observable<T>
    }
  }

  fun postDelay(block: () -> Unit, timeMills: Long = 0) {
    mMainHandler.postDelayed(block, timeMills)
  }

  companion object {

    const val DEFAULT_KEY = "default_key"

    private val INSTANCE by lazy {
      LinkBusCore()
    }

    fun instance() = INSTANCE
  }
}