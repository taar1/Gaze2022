package net.gazeapp.ui.contactview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.gazeapp.R

class MediaGalleryViewerActivityKt : AppCompatActivity(R.layout.media_gallery_viewer_activity_kt) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.media_gallery_viewer_activity_kt)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MediaGalleryViewerActivityKtFragment.newInstance(
                        intent.getIntExtra(CONTACT_ID, 0),
                        intent.getIntExtra(POSITION, 0)
                    )
                )
                .commitNow()
        }
    }

    companion object {
        private const val TAG = "MediaGalleryViewerActivityKt"
        const val CONTACT_ID = "contactId"
        const val POSITION = "position"
    }
}