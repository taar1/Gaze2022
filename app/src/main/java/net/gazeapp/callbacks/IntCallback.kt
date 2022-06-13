package net.gazeapp.callbacks

abstract class IntCallback {
    abstract fun success(count: Int)
    abstract fun fail(e: Exception?)
}