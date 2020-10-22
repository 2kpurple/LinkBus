package com.purplek.linkbus.ipc

class LinkBusServiceManager private constructor() {

  fun <T> postRemote() {

  }

  companion object {

    private val INSTANCE by lazy {
      LinkBusServiceManager()
    }

    fun instance() = INSTANCE
  }
}