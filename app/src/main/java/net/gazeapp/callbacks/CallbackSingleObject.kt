package net.gazeapp.callbacks

import net.gazeapp.data.model.DataModel

abstract class CallbackSingleObject {
    abstract fun success(entityList: DataModel?)
    abstract fun fail(e: Exception?)
}