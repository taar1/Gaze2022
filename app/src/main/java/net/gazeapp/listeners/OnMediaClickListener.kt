package net.gazeapp.listeners

import net.gazeapp.data.model.Media

interface OnMediaClickListener {
    fun onMediaClicked(mediaList: List<Media>, position: Int)
}