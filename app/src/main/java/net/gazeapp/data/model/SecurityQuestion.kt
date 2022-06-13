package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SecurityQuestion(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,
    // 1 = first question, 2 = second question etc.
    var sortOrder: Int = 0,
    var question: String = "",
    var answer: String = ""
) : Parcelable