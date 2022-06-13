package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(primaryKeys = [PersistentObject.MEDIA_ID_FIELD_NAME, PersistentObject.TAG_ID_FIELD_NAME])
data class MediaTagCrossRef(
    @ColumnInfo(name = PersistentObject.MEDIA_ID_FIELD_NAME)
    var mediaId: Int = 0,

    @ColumnInfo(name = PersistentObject.TAG_ID_FIELD_NAME)
    var tagId: Int = 0
) : Parcelable