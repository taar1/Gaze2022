package net.gazeapp.utilities

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import net.gazeapp.GazeApplication
import net.gazeapp.callbacks.MyMediaListLoadCallback
import net.gazeapp.data.GazeImage
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.Media
import net.gazeapp.helpers.Const
import java.io.*
import java.sql.SQLException
import java.util.*
import javax.inject.Inject

class MediaTools @Inject constructor() {

    companion object {
        private const val TAG = "MediaTools"
    }

    // TODO FIXME ist das der korrekte: absolutePath?
    val absolutePath = Environment.getDataDirectory().absolutePath

    // TODO FIXME an den Application  GazeApplication.instance.applicationContext rankommen (in GazeApplication)

    /**
     * Copies a file to the private storage using the next unique file name.
     *
     * @param originalFileOnExternalStorage
     * @param media
     * @return Used file name or NULL.
     */
    fun copyFileToPrivateStorage(originalFileOnExternalStorage: File, media: Media): String? {
        if (!originalFileOnExternalStorage.canRead()) {
            return null
        }

        val dstFile = createDestinationFile(media)
        return try {
            val inputStream: InputStream = FileInputStream(originalFileOnExternalStorage)
            saveFileToInternalStorage(inputStream, dstFile!!)
        } catch (e: IOException) {
            // IOException, could not write file to private storage.
            Log.e(TAG, "" + e.localizedMessage)
            null
        } catch (e: SQLException) {
            // SQLException, could not insert entry to database
            Log.e(TAG, "" + e.localizedMessage)
            null
        }
    }

    fun copyFileToPrivateStorage(file: File): String? {
        if (!file.canRead()) {
            return null
        }

        // TODO
        // TODO
        // TODO

//        return try {
//            val inputStream: InputStream = FileInputStream(dstFile)
//            saveFileToInternalStorage(inputStream, dstFile)
//        } catch (e: IOException) {
//            // IOException, could not write file to private storage.
//            Log.e(TAG, "" + e.localizedMessage)
//            null
//        } catch (e: SQLException) {
//            // SQLException, could not insert entry to database
//            Log.e(TAG, "" + e.localizedMessage)
//            null
//        }
        return null
    }

    fun copyFileToPrivateStorageFromUri(uri: Uri, media: Media): String? {
        val dstFile = createDestinationFile(media)
        return try {
            val inputStream =
                GazeApplication.instance.applicationContext.contentResolver?.openInputStream(uri)
            saveFileToInternalStorage(inputStream!!, dstFile!!)
        } catch (e: IOException) {
            // IOException, could not write file to private storage.
            // SQLException, could not insert entry to database
            Log.e(TAG, "$e.localizedMessage")
            null
        } catch (e: SQLException) {
            Log.e(TAG, "$e.localizedMessage")
            null
        }
    }

    fun copyFileToPrivateStorage(file: ByteArray?, media: Media): String? {
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, getGalleryPath(media)) // i.e. /20/
        if (!storeInternal.exists()) {
            storeInternal.mkdirs()
        }
        val dstFile = File(storeInternal, media.usedFileName)
        return try {
            val os: OutputStream = FileOutputStream(dstFile)
            os.write(file)
            os.close()
            dstFile.name
        } catch (e: IOException) {
            // IOException, could not write file to private storage.
            Log.e(TAG, "$e.localizedMessage")
            null
        }
    }

    @Throws(IOException::class, SQLException::class)
    private fun saveFileToInternalStorage(
        inputStream: InputStream, dstFile: File
    ): String {
        val os: OutputStream = FileOutputStream(dstFile)
        val buff = ByteArray(1024)
        var len: Int
        while (inputStream.read(buff).also { len = it } > 0) {
            os.write(buff, 0, len)
        }

        inputStream.close()
        os.close()
        return dstFile.name
    }

    private fun createDestinationFile(media: Media): File? {
        val originalFileOnExternalStorage = File(media.fullPath)
        if (!originalFileOnExternalStorage.canRead()) {
            return null
        }

        val nextGeneratedPath = getGalleryPath(media)
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, nextGeneratedPath) // i.e. /20/
        if (!storeInternal.exists()) {
            storeInternal.mkdirs()
        } else {
            return null
        }
        return File(
            storeInternal,
            originalFileOnExternalStorage.name.lowercase(Locale.getDefault())
        ) // i.e. 1.png
    }


    /**
     * Takes a MEDIA object, generates a FILE and saves it to the internal storage to the proper directory of the contact.
     *
     * @param Media
     */
    fun createFileAndSaveToInternalStorage(media: Media) {
        val file = createDestinationFile(media)
    }

    /**
     * Returns a gallery path of a contact
     *
     * @param media
     * @return /20/
     */
    private fun getGalleryPath(media: Media): String {
        return "/" + media.contactId + "/"
    }

    /**
     * Returns the full path (with uses file name) of a Media file
     */
    fun getFullPath(media: Media): String {
        return absolutePath + getGalleryPath(media) + media.usedFileName
    }

    /**
     * Creates a file from MediaEntity with its original file name for sending to external apps.
     *
     * @param media
     * @return
     */
    fun createTemporaryFileForSharing(media: Media): File? {
        val nextGeneratedPath = "/" + Const.TEMPORARY_PATH_FOR_SHARING + "/"
        val file = File(media.fullPath)
        if (!file.canRead()) {
            return null
        }

        // DESTINATION FILE (INTERNAL STORAGE)
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, nextGeneratedPath) // i.e. /temp_sharing/
        if (!storeInternal.exists()) {
            storeInternal.mkdirs()
        }
        val tempFileForSharing = File(storeInternal, media.originalFileName) // i.e. xyz.png
        return try {
            val inputStream: InputStream = FileInputStream(file)
            val os: OutputStream = FileOutputStream(tempFileForSharing)
            val buff = ByteArray(1024)
            var len: Int
            while (inputStream.read(buff).also { len = it } > 0) {
                os.write(buff, 0, len)
            }
            inputStream.close()
            os.close()
            tempFileForSharing
        } catch (e: IOException) {
            // IOException, could not write file to private storage.
            Log.e(TAG, "Error writing to private storage: $e.localizedMessage")
            null
        }
    }

    fun deleteFileFromPrivateStorage(media: Media): Boolean {
        val internalStoragePath = absolutePath
        val generatedPath = getGalleryPath(media)
        val storeInternalPath = File(internalStoragePath, generatedPath) // i.e. /20/
        val internalFile = File(storeInternalPath, media.usedFileName) // i.e. image: /20/1 or /13/4
        val isDeleted = internalFile.delete()
        val isThere = internalFile.exists()
        val internalStorageCacheDir =
            GazeApplication.instance.applicationContext.cacheDir.absolutePath
        val storeInternalCachedPath =
            File(internalStorageCacheDir, generatedPath) // i.e. /20/
        val internalCachedFile = File(
            storeInternalCachedPath, media.usedFileName
        )
        // i.e. image: /20/1 or /13/4
        val isCachedDeleted = internalCachedFile.delete()
        val isCachedThere = internalCachedFile.exists()

        return isDeleted
    }

    fun deleteFilesFromPrivateStorage(mediaList: List<Media>): Int {
        var deleteCount = 0
        for (media in mediaList) {
            val localFile = File(media.fullPath)
            val isDeleted = localFile.delete()
            val isThere = localFile.exists()
            Log.d(TAG, "IS DELETED (int): $isDeleted")
            Log.d(TAG, "IS THERE (int): $isThere")
            val generatedPath = getGalleryPath(media)
            val internalStorageCacheDir =
                GazeApplication.instance.applicationContext.cacheDir.absolutePath
            val storeInternalCachedPath = File(internalStorageCacheDir, generatedPath) // i.e. /20/
            val internalCachedFile = File(
                storeInternalCachedPath,
                media.usedFileName
            ) // i.e. image: /public/20/1 or /private/13/4
            val isCachedDeleted = internalCachedFile.delete()
            val isCachedThere = internalCachedFile.exists()
            if (!isThere) {
                deleteCount++
            }
        }
        return deleteCount
    }

    // Probably not used anymore since caching can be
    // deactivated right in GLIDE itself (diskCacheStrategy())
    fun cleanCacheDir() {
        val cacheDir = GazeApplication.instance.applicationContext.cacheDir
        val files = cacheDir.listFiles()
        files?.forEach {
            Log.d(TAG, "bytesDeleted: " + it.length())
            it.delete()
        }
    }

    fun cleanMyMediaCacheDir() {
        val cacheDir = GazeApplication.instance.applicationContext.cacheDir
        val myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" // i.e. /myMedia/
        val cacheDirContact = File(cacheDir, myMediaPath)
        val files = cacheDirContact.listFiles()
        files?.forEach {
            Log.d(TAG, "cleanMyMediaCacheDir deleted: " + it.length())
            it.delete()
        }
    }

    // Deprecated
    fun getFileFromInternalStorage(contact: Contact, inPrivateGallery: Boolean): File? {
//        Log.d(TAG, "XXXXX getFileFromInternalStorage()")
//        val internalStoragePath =
//            activity.filesDir.absolutePath + "/" + (if (inPrivateGallery) Const.GALLERY_PATH_PRIVATE else Const.GALLERY_PATH_PUBLIC) + "/" + contact.id
//        val fileToLoad: File
//        fileToLoad = if (contact.mainPicFileName == null) {
//            File(internalStoragePath, contact.mainPicFileName + ".png")
//        } else {
//            File(internalStoragePath, contact.mainPicFileName)
//        }
//        return if (fileToLoad.exists()) {
//            fileToLoad
//        } else {
//            null
//        }
        return null
    }

    fun countMediaFromLocalStorage(contact: Contact): Int {
        var mediaCount = 0
        val imagePath = absolutePath + "/" + contact.id
        val pathToFiles = File(imagePath)
        val allFiles = pathToFiles.listFiles()
        if (allFiles != null) {
            mediaCount = allFiles.size
        }
        return mediaCount
    }

    fun convertFileToBitmap(imgFile: File): Bitmap {
        return BitmapFactory.decodeFile(imgFile.absolutePath)
    }

//    /**
//     * Get all media files of a contact in the Public directory
//     *
//     * @param contactId
//     * @return
//     */
//    @Throws(SQLException::class)
//    fun getMediaListFromDatabase(contactId: Int): List<Media> {
//        return getMediaListFromDatabase(contactId, false)
//    }
//
//    @Throws(SQLException::class)
//    fun getMediaListFromDatabase(contactId: Int, isInPrivateGallery: Boolean): List<Media> {
//        val mediaList = mediaDao.getMedia(contactId, isInPrivateGallery)
//        return mediaList
//    }
//
//    /**
//     * Gets all the media from the database that match the filenames in the parameter
//     *
//     * @param contactId
//     * @param fileList           List of the filenames to search the database with
//     * @param isInPrivateGallery
//     * @return
//     */
//    fun getMediaListFromDatabaseByFileNames(
//        contactId: Int,
//        fileList: ArrayList<String>,
//        isInPrivateGallery: Boolean
//    ): List<Media>? {
//        // false = public gallery only
//        val mediaList = mediaDao.getMedia(contactId, fileList, isInPrivateGallery)
//        return if (mediaList != null && mediaList.size > 0) {
//            mediaList
//        } else null
//    }

    //    public ArrayList<MediaEntity> getMyMediaListFromDatabase(ContactEntity contact, int galleryNumber, MediaListLoadCallback callback) {
    //        ArrayList<MediaEntity> mediaList = null;
    //
    //        try {
    //            mediaList = contactService.getMyMedia(contact, galleryNumber);
    //            if ((mediaList != null) && (mediaList.size() > 0)) {
    //                callback.success(mediaList);
    //            } else {
    //                callback.empty();
    //            }
    //        } catch (SQLException e) {
    //            callback.fail(e);
    //        }
    //
    //        return null;
    //    }

    // DEPRECATED: we use ROOM now
//    fun getMediaListFromDatabase(
//        contactId: Int,
//        isInPrivateGallery: Boolean,
//        callback: MediaListLoadCallback
//    ) {
//        // isInPrivateGallery = false = public gallery only
//        Needle.onBackgroundThread().execute {
//            val mediaList = mediaDao.getMedia(contactId, isInPrivateGallery)
//            if (mediaList != null && mediaList.size > 0) {
//                callback.success(mediaList)
//            } else {
//                callback.empty()
//            }
//        }
//    }

    /**
     * Goes through the media list and finds out the next available ID for the file name
     *
     * @param contact
     * @return
     */
    @Deprecated("")
    @Throws(SQLException::class)
    fun getNextAvailableFileName(contact: Contact?): Int {
        // TODO currently only "public" gallery is considered
        // later also consider "private" gallery

//        List<Media> media = getMediaListFromDatabase(contact.getId(), Const.GALLERY_PUBLIC);
//        int nextFileName = 1;
//        if ((media != null) && (media.size() > 0)) {
//            int[] fileNames = new int[media.size()];
//
//            for (int i = 0; i < media.size(); i++) {
//                Media m = media.get(i);
//                fileNames[i] = m.getGeneratedFileName();
//            }
//
//            Arrays.sort(fileNames);
//            nextFileName = fileNames[fileNames.length - 1] + 1;
//        }
//
//        return nextFileName;
        return 0
    }

    /**
     * Copies an image to the "My MediaEntity" folder. If a file with the same file name already exists the new file will be renamed.
     *
     * @param image
     * @param galleryNumber
     * @return
     */
    fun copyMyMediaFileToPrivateStorage(image: GazeImage): Boolean {
        val originalFileOnExternalStorage = File(image.path)
        if (!originalFileOnExternalStorage.canRead()) {
            return false
        }
        val myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" // i.e. /myMedia/

        // DESTINATION FILE (INTERNAL STORAGE)
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, myMediaPath) // i.e. /myMedia/
        if (!storeInternal.exists()) {
            storeInternal.mkdirs()
        }
        val myMediaList = getMyMediaFromInternalStorage()
        var mustRenameFile = false
        if (myMediaList != null) {
            for ((_, name) in myMediaList) {
                Log.d(TAG, "Already in Gallery: $name")
                if (originalFileOnExternalStorage.name.equals(name, ignoreCase = true)) {
                    mustRenameFile = true
                }
            }
        }
        var dstFile = File(
            storeInternal,
            originalFileOnExternalStorage.name.lowercase(Locale.getDefault())
        )
        if (mustRenameFile) {
            // Rename the file. Add timestamp to the end of the file name.
            val newFileName = createUniqueFileName(originalFileOnExternalStorage)
            dstFile = File(storeInternal, newFileName)
        }
        return copyFileToMyMedia(originalFileOnExternalStorage, dstFile)
    }

    /**
     * Takes the original file name and adds a timestamp to the file name to make it unique.
     *
     * @param originalFileOnExternalStorage
     * @return unique file name
     */
    private fun createUniqueFileName(originalFileOnExternalStorage: File): String {
        val fileNameExtension = getFileExtension(originalFileOnExternalStorage)
        val fileNameWithoutExtensionSplits =
            originalFileOnExternalStorage.name.split(".$fileNameExtension".toRegex()).toTypedArray()
        val fileNameWithoutExtension = fileNameWithoutExtensionSplits[0]
        return fileNameWithoutExtension + "_" + System.currentTimeMillis() + "." + fileNameExtension.lowercase(
            Locale.getDefault()
        )
    }

//    // Currently not used. Copying file by file seems to be the better solution than doing the whole list at once.
//    fun copyMyMediaFilesToPrivateStorage(
//        originalFilesOnExternalStorage: List<File>,
//        galleryNumber: Int,
//        activity: Activity,
//        view: View?
//    ) {
//        val myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" // i.e. /myMedia/
//        val internalStoragePath = activity.filesDir.absolutePath
//        val storeInternal = File(internalStoragePath, myMediaPath) // i.e. /myMedia/1/
//        if (!storeInternal.exists()) {
//            storeInternal.mkdirs()
//        }
//
//        // TODO war com.darsh.multipleimageselect.models.Image
//        getMyMediaFromInternalStorage(galleryNumber, object : MyMediaListLoadCallback {
//            override fun success(mediaList: ArrayList<GazeImage>) {
//                var filesCopied = 0
//                for (originalFileOnExternalStorage in originalFilesOnExternalStorage) {
//                    var mustRenameFile = false
//                    if (originalFileOnExternalStorage.canRead()) {
//                        for ((_, name) in mediaList) {
//                            if (originalFileOnExternalStorage.name.equals(
//                                    name,
//                                    ignoreCase = true
//                                )
//                            ) {
//                                mustRenameFile = true
//                            }
//                        }
//                        var dstFile = File(storeInternal, originalFileOnExternalStorage.name)
//                        if (mustRenameFile) {
//                            // Rename the file. Add timestamp to the end of the file name.
//                            dstFile = File(
//                                storeInternal,
//                                originalFileOnExternalStorage.name + "_" + System.currentTimeMillis()
//                            )
//                        }
//                        if (copyFileToMyMedia(originalFileOnExternalStorage, dstFile)) {
//                            filesCopied++
//                        }
//                    }
//                }
//                if (filesCopied > 0) {
//                    val snackBarSuccessMessage = activity.resources.getString(
//                        R.string.success_copied_files_to_my_media_gallery,
//                        filesCopied,
//                        if (galleryNumber == 1) activity.resources.getString(R.string.one) else activity.resources.getString(
//                            R.string.two
//                        )
//                    )
//                    GazeTools.showMaterialSnackBar(
//                        activity,
//                        view,
//                        snackBarSuccessMessage,
//                        SnackBarType.SUCCESS
//                    )
//                }
//            }
//
//            override fun fail(e: Exception) {
//                var filesCopied = 0
//                for (originalFileOnExternalStorage in originalFilesOnExternalStorage) {
//                    if (originalFileOnExternalStorage.canRead()) {
//                        // TODO: make sure the extension is lower case
//                        val dstFile = File(storeInternal, originalFileOnExternalStorage.name)
//                        if (copyFileToMyMedia(originalFileOnExternalStorage, dstFile)) {
//                            filesCopied++
//                        }
//                    }
//                }
//                if (filesCopied > 0) {
//                    val snackBarSuccessMessage = activity.resources.getString(
//                        R.string.success_copied_files_to_my_media_gallery,
//                        filesCopied,
//                        if (galleryNumber == 1) activity.resources.getString(R.string.one) else activity.resources.getString(
//                            R.string.two
//                        )
//                    )
//                    GazeTools.showMaterialSnackBar(
//                        activity,
//                        view,
//                        snackBarSuccessMessage,
//                        SnackBarType.SUCCESS
//                    )
//                }
//            }
//
//            override fun empty() {
//                var filesCopied = 0
//                for (originalFileOnExternalStorage in originalFilesOnExternalStorage) {
//                    if (originalFileOnExternalStorage.canRead()) {
//                        // TODO: make sure the extension is lower case
//                        val dstFile = File(storeInternal, originalFileOnExternalStorage.name)
//                        if (copyFileToMyMedia(originalFileOnExternalStorage, dstFile)) {
//                            filesCopied++
//                        }
//                    }
//                }
//                if (filesCopied > 0) {
//                    val snackBarSuccessMessage = activity.resources.getString(
//                        R.string.success_copied_files_to_my_media_gallery,
//                        filesCopied,
//                        if (galleryNumber == 1) activity.resources.getString(R.string.one) else activity.resources.getString(
//                            R.string.two
//                        )
//                    )
//                    GazeTools.showMaterialSnackBar(
//                        activity,
//                        view,
//                        snackBarSuccessMessage,
//                        SnackBarType.SUCCESS
//                    )
//                }
//            }
//        })
//    }

    /**
     * Copies the file to the MyMedia path on the internal storage. No Checks are being made, simply copy.
     *
     * @param originalFileOnExternalStorage
     * @param dstFile                       File & file name where to save the file
     * @return
     */
    private fun copyFileToMyMedia(originalFileOnExternalStorage: File, dstFile: File): Boolean {
        return try {
            val inputStream: InputStream = FileInputStream(originalFileOnExternalStorage)
            val os: OutputStream = FileOutputStream(dstFile)
            val buff = ByteArray(1024)
            var len: Int
            while (inputStream.read(buff).also { len = it } > 0) {
                os.write(buff, 0, len)
            }
            inputStream.close()
            os.close()
            true
        } catch (e: IOException) {
            // IOException, could not write file to private storage.
            // SQLException, could not insert entry to database
            Log.e(TAG, "Error copying file to private Storage: " + e.localizedMessage)
            false
        }
    }

    // DEPRECATED: get extension name from ROOM
    fun getFileExtension(originalFileOnExternalStorage: File): String {
//        return MimeTypeMap.getFileExtensionFromUrl(originalFileOnExternalStorage.absolutePath)
        return "DEPRECATED"
    }


    // DEPRECATED: get file name from ROOM
    fun getFileName(activity: Activity, uri: Uri): String? {
//        var result: String? = null
//        if (uri.scheme == "content") {
//            val cursor = activity.contentResolver.query(uri, null, null, null, null)
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                }
//            } finally {
//                cursor!!.close()
//            }
//        }
//        if (result == null) {
//            result = uri.path
//            val cut = result!!.lastIndexOf('/')
//            if (cut != -1) {
//                result = result.substring(cut + 1)
//            }
//        }
//        return result
        return "DEPRECATED"
    }

//    fun getContactMainPicPath(contact: Contact): String? {
//        return if (contact.mainPicId == 0) {
//            null
//        } else {
//            GazeApplication.getApp GazeApplication.instance.applicationContext().filesDir.absolutePath + "/" + Const.GALLERY_PATH_PUBLIC + "/" + contact.id + "/" + contact.mainPicFileName
//        }
//    }

    fun getMyMediaFromInternalStorage(callback: MyMediaListLoadCallback) {
        val myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" // i.e. /myMedia/

        // DESTINATION FILE (INTERNAL STORAGE)
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, myMediaPath) // i.e. /myMedia/
        if (!storeInternal.exists()) {
            callback.empty()
        } else {
            val files = storeInternal.listFiles()
            if (files != null && files.size > 0) {
                val imageArrayList = ArrayList<GazeImage>()
                for (f in files) {
                    val i = GazeImage(0, f.name, f.absolutePath, false)
                    imageArrayList.add(i)
                }
                callback.success(imageArrayList)
            } else {
                callback.empty()
            }
        }
    }

    fun getMyMediaFromInternalStorage(): List<GazeImage> {
        val myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" // i.e. /myMedia/
        return getMediaFromInternalStoragePath(myMediaPath)
    }

    fun getMediaFromInternalStoragePath(mediaPath: String?): List<GazeImage> {
        val imageArrayList: MutableList<GazeImage> = ArrayList()

        // DESTINATION FILE (INTERNAL STORAGE)
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, mediaPath) // i.e. /public/20/
        return if (!storeInternal.exists()) {
            imageArrayList
        } else {
            val files = storeInternal.listFiles()
            if (files != null && files.size > 0) {
                for (f in files) {
                    val i = GazeImage(0, f.name, f.absolutePath, false)
                    imageArrayList.add(i)
                }
            }
            imageArrayList
        }
    }

    /**
     * Counts the amount of saved images from the "My MediaEntity" gallery.
     *
     * @param galleryNumber
     * @return
     */
    fun countMyMediaFromInternalStorage(galleryNumber: Int): Int {
        // DEPRECATED count the files from the room database and not on the file system...
//        val myMediaPath =
//            "/" + Const.GALLERY_MY_MEDIA_PATH + "/" + galleryNumber + "/" // i.e. /myMedia/1/
//        val internalStoragePath = activity.filesDir.absolutePath
//        val storeInternal = File(internalStoragePath, myMediaPath) // i.e. /public/20/
//        return if (!storeInternal.exists()) {
//            0
//        } else {
//            val files = storeInternal.listFiles()
//            if (files != null && files.size > 0) {
//                files.size
//            } else {
//                0
//            }
//        }

        return 0
    }

    // NEW METHODS FROM HERE (2021)
    // NEW METHODS FROM HERE (2021)
    // NEW METHODS FROM HERE (2021)
    // NEW METHODS FROM HERE (2021)

    /**
     * Method to save a resource image to internal storage gallery of a contact.
     */
    fun saveImageFromResourceToInternalStorage(media: Media, drawableId: Int): Uri? {
        // Get the image from drawable resource as drawable object
        val drawable = GazeApplication.instance.applicationContext.getDrawable(drawableId)

        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap

        val internalStoragePath = absolutePath
        val storeInternal = File(
            internalStoragePath,
            getGalleryPath(media)
        ) // i.e. /data/user/0/net.gazeapp/files/1/
        if (!storeInternal.exists()) {
            storeInternal.mkdirs()
        }

        val dstFile = File(storeInternal, media.usedFileName)

        return try {
            val os: OutputStream = FileOutputStream(dstFile)

            // Compress bitmap (needed)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)

            // Flush the stream
            os.flush()
            os.close()

            Uri.parse(dstFile.absolutePath)
        } catch (e: IOException) {
            // IOException, could not write file to private storage.
            e.printStackTrace()
            null
        }
    }


    fun copyMyMediaFileToPrivateStorageNew(image: GazeImage): Uri? {
        val originalFileOnExternalStorage = File(image.path)
        if (!originalFileOnExternalStorage.canRead()) {
            return null
        }
        val myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" // i.e. /myMedia/

        // DESTINATION FILE (INTERNAL STORAGE)
        val internalStoragePath = absolutePath
        val storeInternal = File(internalStoragePath, myMediaPath) // i.e. /myMedia/
        if (!storeInternal.exists()) {
            storeInternal.mkdirs()
        }
        val myMediaList = getMyMediaFromInternalStorage()
        var mustRenameFile = false
        for ((_, name) in myMediaList) {
            Log.d(TAG, "Already in Gallery: $name")
            if (originalFileOnExternalStorage.name.equals(name, ignoreCase = true)) {
                mustRenameFile = true
            }
        }
        var dstFile =
            File(storeInternal, originalFileOnExternalStorage.name.lowercase(Locale.getDefault()))
        if (mustRenameFile) {
            // Rename the file. Add timestamp to the end of the file name.
            val newFileName = createUniqueFileName(originalFileOnExternalStorage)
            dstFile = File(storeInternal, newFileName)
        }
        if (copyFileToMyMedia(originalFileOnExternalStorage, dstFile)) {
            return Uri.parse(dstFile.absolutePath)
        }
        return null
    }
}