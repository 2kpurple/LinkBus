package com.purplek.linkbus

class LinkEventWrapper<T>(
  val version: Int,
  val value: T
) {

  override fun toString(): String {
    return "LinkEventWrapper(version=$version, value=$value)"
  }
}