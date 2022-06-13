package net.gazeapp.callbacks

import net.gazeapp.data.model.Media

interface MediaListLoadCallback {
    fun success(mediaList: List<Media?>?)
    fun fail(e: Exception?)
    fun empty()
}