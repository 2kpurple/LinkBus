package com.purplek.linkbus

import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LinkObservable<T>: Observable<T> {

  private val mInternalData = MutableLiveData<LinkEventWrapper<T>>()

  private var mVersion: Int = INIT_VERSION

  override fun post(value: T) {
    runOnMain {
      val wrapper = LinkEventWrapper(mVersion, value)
      mInternalData.value = wrapper
    }
  }

  override fun postDelay(value: T, timeMills: Long) {
    LinkBusCore.instance().postDelay({
      post(value)
    }, timeMills)
  }

  override fun observe(onwer: LifecycleOwner, observer: Observer<T>) {
    runOnMain {
      observe(onwer, observer, false)
    }
  }

  override fun observeSticky(onwer: LifecycleOwner, observer: Observer<T>) {
    runOnMain {
      observe(onwer, observer, true)
    }
  }

  override fun observeForever(observer: Observer<T>) {
    runOnMain {
      observeForever(observer, false)
    }
  }

  override fun observeForeverSticky(observer: Observer<T>) {
    runOnMain {
      observeForever(observer, true)
    }
  }

  @MainThread
  private fun observe(
    onwer: LifecycleOwner,
    observer: Observer<T>,
    sticky: Boolean
  ) {
    val version = if (sticky) -1 else mVersion++
    mInternalData.observe(onwer, LinkObserverWrapper(version, observer, sticky))
  }

  @MainThread
  private fun observeForever(
    observer: Observer<T>,
    sticky: Boolean
  ) {
    val version = if (sticky) -1 else mVersion++
    mInternalData.observeForever(LinkObserverWrapper(version, observer, sticky))
  }

  private fun runOnMain(block: () -> Unit) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      // Main Thread
      block()
    } else {
      LinkBusCore.instance().postDelay(block)
    }
  }

  inner class LinkObserverWrapper(
    private val version: Int,
    private val observer: Observer<T>,
    private val sticky: Boolean = false
  ) : Observer<LinkEventWrapper<T>> {

    override fun onChanged(wrapper: LinkEventWrapper<T>) {
      if (sticky || version > wrapper.version) {
        observer.onChanged(wrapper.value)
      } else {
        Log.d(TAG, "Observer version < event.version can not dispatch changed.")
      }
    }
  }

  companion object {

    private const val TAG = "LinkData"

    const val INIT_VERSION = 0
  }
}