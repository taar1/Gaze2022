package net.gazeapp.callbacks

abstract class ListCallback<T> {
    abstract fun success(entityList: List<T>?)
    abstract fun fail(e: Exception?)
}