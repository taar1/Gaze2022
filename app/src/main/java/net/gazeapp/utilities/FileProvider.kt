package net.gazeapp.utilities

import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider

/**
 * http://stackoverflow.com/questions/24195674/image-share-intent-works-for-gmail-but-crashes-fb-and-twitter/25020642#25020642
 * Created by taar1 on 20.02.2017.
 */
//class FileProvider : FileProvider() {
//    override fun query(
//        uri: Uri,
//        projection: Array<String>?,
//        selection: String?,
//        selectionArgs: Array<String>?,
//        sortOrder: String?
//    ): Cursor {
//        val source = super.query(uri, projection, selection, selectionArgs, sortOrder)
//        val columnNames = source.columnNames
//        val newColumnNames = columnNamesWithData(columnNames)
//        val cursor = MatrixCursor(newColumnNames, source.count)
//        source.moveToPosition(-1)
//        while (source.moveToNext()) {
//            val row = cursor.newRow()
//            for (i in columnNames.indices) {
//                row.add(source.getString(i))
//            }
//        }
//        return cursor
//    }
//
//    private fun columnNamesWithData(columnNames: Array<String>): Array<String> {
//        for (columnName in columnNames) if (MediaStore.MediaColumns.DATA == columnName) return columnNames
//        val newColumnNames = columnNames.copyOf(columnNames.size + 1)
//        newColumnNames[columnNames.size] = MediaStore.MediaColumns.DATA
//        return newColumnNames.requireNoNulls()
//    }
//}

fun FileProvider.columnNamesWithData(columnNames: Array<String>): Array<String> {
    for (columnName in columnNames) if (MediaStore.MediaColumns.DATA == columnName) return columnNames
    val newColumnNames = columnNames.copyOf(columnNames.size + 1)
    newColumnNames[columnNames.size] = MediaStore.MediaColumns.DATA
    return newColumnNames.requireNoNulls()
}


fun FileProvider.query(
    uri: Uri,
    projection: Array<String>?,
    selection: String?,
    selectionArgs: Array<String>?,
    sortOrder: String?
): Cursor {
    val source = query(uri, projection, selection, selectionArgs, sortOrder)
    val columnNames = source.columnNames
    val newColumnNames = columnNamesWithData(columnNames)
    val cursor = MatrixCursor(newColumnNames, source.count)
    source.moveToPosition(-1)
    while (source.moveToNext()) {
        val row = cursor.newRow()
        for (i in columnNames.indices) {
            row.add(source.getString(i))
        }
    }
    return cursor
}

