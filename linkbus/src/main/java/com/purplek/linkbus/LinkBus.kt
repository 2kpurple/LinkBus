package com.purplek.linkbus

/**
 * LinkBus
 *
 * Created by 2kpurple 2020/10/20 4:41 PM
 */
object LinkBus {

  @JvmStatic
  fun <T: Any> get(eventType: Class<T>): Observable<T> {
    return LinkBusCore.instance().get(LinkBusCore.DEFAULT_KEY, eventType)
  }

  @JvmStatic
  fun <T: Any> get(key: String, eventType: Class<T>): Observable<T> {
    return LinkBusCore.instance().get(key, eventType)
  }
}