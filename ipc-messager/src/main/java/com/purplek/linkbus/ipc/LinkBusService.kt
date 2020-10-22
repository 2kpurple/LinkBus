package com.purplek.linkbus.ipc

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast

class LinkBusService : Service() {

  private val mMessenger: Messenger by lazy {
    Messenger(LinkBusHandler())
  }

  override fun onBind(intent: Intent): IBinder {
    return mMessenger.binder
  }

  inner class LinkBusHandler: Handler(Looper.getMainLooper()) {

    override fun handleMessage(msg: Message) {
      Toast.makeText(this@LinkBusService, "测试一下 ${Process.myPid()}", Toast.LENGTH_SHORT)
    }
  }
}