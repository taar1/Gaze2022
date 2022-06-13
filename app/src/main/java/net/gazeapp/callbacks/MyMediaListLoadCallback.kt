package net.gazeapp.callbacks

import net.gazeapp.data.GazeImage

interface MyMediaListLoadCallback {
    fun success(mediaList: ArrayList<GazeImage>)
    fun fail(e: Exception?)
    fun empty()
}