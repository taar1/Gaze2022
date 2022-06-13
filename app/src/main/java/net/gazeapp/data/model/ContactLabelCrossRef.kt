package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(primaryKeys = [PersistentObject.CONTACT_ID_FIELD_NAME, PersistentObject.LABEL_ID_FIELD_NAME])
data class ContactLabelCrossRef(
    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,

    @ColumnInfo(name = PersistentObject.LABEL_ID_FIELD_NAME)
    var labelId: Int = 0
) : Parcelable