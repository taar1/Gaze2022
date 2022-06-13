package net.gazeapp.utilities

import android.content.*
import android.graphics.Color
import android.net.Uri
import android.text.format.DateFormat
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ActivityContext
import net.gazeapp.R
import net.gazeapp.helpers.Const
import net.gazeapp.helpers.Preferences
import net.gazeapp.helpers.SnackBarType
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

class GazeTools @Inject constructor(@param:ActivityContext private val context: Context) {

    val preferences: SharedPreferences =
        context.getSharedPreferences(Const.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()


    companion object {
        private const val TAG = "GazeTools"
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun removeValue(key: String) {
        editor.remove(key)
        editor.apply()
    }

    val innerCardPaddingInPixels: Int
        get() = context.resources.getDimensionPixelOffset(R.dimen.contact_view_card_inner_padding)

    fun isProUser(): Boolean {
        return preferences.getBoolean(
            Preferences.IS_PRO_USER,
            Preferences.IS_PRO_USER_DEFAULT_VALUE
        )
    }

    fun isFreeUser(): Boolean {
        return !isProUser()
    }

    fun setProUser(isProUser: Boolean): Boolean {
        editor.putBoolean(Preferences.IS_PRO_USER, isProUser)
        return editor.commit()
    }

    fun removeProUser() {
        editor.remove(Preferences.IS_PRO_USER)
        editor.apply()
    }

    val proUserSku: String?
        get() {
            return preferences.getString(Preferences.PRO_USER_SKU, "")
        }

    fun setProUserSku(proUserSku: String): Boolean {
        editor.putString(Preferences.PRO_USER_SKU, proUserSku)
        return editor.commit()
    }

    fun removeProUserSku() {
        removeValue(Preferences.PRO_USER_SKU)
    }

    val proUserSince: Long
        get() {
            return preferences.getLong(
                Preferences.PRO_USER_SINCE,
                Preferences.PRO_USER_SINCE_DEFAULT_VALUE
            )
        }

    fun setProUserSince(sinceTimestamp: Long): Boolean {
        editor.putLong(Preferences.PRO_USER_SINCE, sinceTimestamp)
        return editor.commit()
    }

    fun removeProUserSince() {
        removeValue(Preferences.PRO_USER_SINCE)
    }

    val proUserJson: String?
        get() {
            return preferences.getString(Preferences.PRO_USER_JSON, "")
        }

    fun setProUserJson(proUserJson: String): Boolean {
        editor.putString(Preferences.PRO_USER_JSON, proUserJson)
        return editor.commit()
    }

    fun removeProUserJson() {
        removeValue(Preferences.PRO_USER_JSON)
    }

    val isAdsEnabled: Boolean
        get() {
            return preferences.getBoolean(
                Preferences.IS_ADS_ENABLED,
                Preferences.IS_ADS_ENABLED_DEFAULT_VALUE
            )
        }

    fun setAdsEnabled(isAdsEnabled: Boolean): Boolean {
        editor.putBoolean(Preferences.IS_ADS_ENABLED, isAdsEnabled)
        return editor.commit()
    }

    fun removeAdsEnabled() {
        removeValue(Preferences.IS_ADS_ENABLED)
    }

    val isFingerprintAuthenticationEnabled: Boolean
        get() {
            return preferences.getBoolean(
                Preferences.FINGERPRINT_AUTHENTICATION,
                Preferences.FINGERPRINT_AUTHENTICATION_DEFAULT_VALUE
            )
        }

    fun setFingerprintAuthenticationEnabled(isFingerprintAuthenticationEnabled: Boolean): Boolean {
        editor.putBoolean(
            Preferences.FINGERPRINT_AUTHENTICATION,
            isFingerprintAuthenticationEnabled
        )
        return editor.commit()
    }


    fun useMetricSystem(): Boolean {
        return preferences.getInt(
            Preferences.UNIT_SYSTEM,
            Preferences.UNIT_SYSTEM_DEFAULT_VALUE
        ) == 0
    }

    fun setUseMetricSystem(useMetricSystem: Boolean): Boolean {
        editor.putBoolean(Preferences.UNIT_SYSTEM, useMetricSystem)
        return editor.commit()
    }

    fun hasSeenMeTabExplanationDialog(): Boolean {
        return preferences.getBoolean(
            Preferences.HAS_SEEN_ME_TAB_EXPLANATION_DIALOG,
            Preferences.HAS_SEEN_ME_TAB_EXPLANATION_DIALOG_DEFAULT_VALUE
        )
    }

    fun setHasSeenMeTabExplanationDialog(hasSeenMeTabExplanationDialog: Boolean): Boolean {
        editor.putBoolean(
            Preferences.HAS_SEEN_ME_TAB_EXPLANATION_DIALOG,
            hasSeenMeTabExplanationDialog
        )
        return editor.commit()
    }

    /**
     * @param activity
     * @param jsonFileName i.e. bodytypes.json
     * @return
     */
    fun loadJSONFromAsset(jsonFileName: String): String? {
        var json: String? = null
        try {
            val `is` = context.assets.open(jsonFileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun loadLocalizedFileFromRawFolder(
        rawFileNameWithoutExtension: String
    ): String? {
        val nameResourceID = context.resources.getIdentifier(
            rawFileNameWithoutExtension,
            "raw",
            context.applicationInfo.packageName
        )
        var json: String? = null
        try {
            val `is` = context.resources.openRawResource(nameResourceID)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    //    /**
    //     * Gets a file from the RAW directory with the correct locale.
    //     *
    //     * @param name
    //     * @param context
    //     * @return
    //     */
    //    public static String getLocalizedRawFile(String name, Context context) {
    //        int nameResourceID = context.getResources().getIdentifier(name, "raw", context.getApplicationInfo().packageName);
    //        if (nameResourceID == 0) {
    //            throw new IllegalArgumentException("No resource string found with name " + name);
    //        } else {
    //            return context.getString(nameResourceID);
    //        }
    //    }

    // http://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi
//    fun readJsonFile(
//        jsonFileName: String,
//        sortAlphabetically: Boolean
//    ): HashMap<Int?, String?>? {
//        var mList: HashMap<Int?, String?>? = null
//        try {
//            val mJsonArray = JSONArray(loadJSONFromAsset(jsonFileName))
//            val formList = ArrayList<HashMap<String, String>>()
//            mList = HashMap()
//            for (i in 0 until mJsonArray.length()) {
//                val jsonObj = mJsonArray.getJSONObject(i)
//                //Log.d("JSON ITEM: ", String.valueOf(jsonObj.getInt("id")) + ": " + jsonObj.getString("value"));
//                val jsonId = jsonObj.getInt("id")
//                val jsonValue = jsonObj.getString("value")
//                val jsonMillimeters = jsonObj.optInt("mm")
//                val jsonInch = jsonObj.optString("inch")
//                mList[jsonId] = jsonValue
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        if (sortAlphabetically) {
//            val mapKeys: List<*> = ArrayList<Any?>(mList!!.keys)
//            val mapValues: List<*> = ArrayList<Any?>(mList.values)
//            Collections.sort<Comparable<*>>(mapValues)
//            Collections.sort<Comparable<*>>(mapKeys)
//            val sortedMap: LinkedHashMap<*, *> = LinkedHashMap<Any?, Any?>()
//            for (`val` in mapValues) {
//                for (key in mapKeys) {
//                    val comp1 = mList[key]
//                    val comp2 = `val`.toString()
//                    if (comp1 == comp2) {
//                        mList.remove(key)
//                        mapKeys.remove(key)
//                        sortedMap[key] = `val`
//                        break
//                    }
//                }
//            }
//            return sortedMap
//        }
//        return mList
//    }

    fun readJsonFile(jsonFileName: String): JSONArray? {
        var mJsonArray: JSONArray? = null
        try {
            mJsonArray = JSONArray(loadJSONFromAsset(jsonFileName))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return mJsonArray
    }

    fun readJsonFileFromRawFolder(
        jsonFileNameWithoutExtension: String
    ): JSONArray? {
        var mJsonArray: JSONArray? = null
        try {
            mJsonArray = JSONArray(
                loadLocalizedFileFromRawFolder(jsonFileNameWithoutExtension)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return mJsonArray
    }

    fun readJsonFileFromRawFolder(
        jsonFileNameWithoutExtension: String,
        sortAlphabetically: Boolean
    ): Map<Int, String?> {
        val mList: HashMap<Int, String?>?
        val mJsonArray = readJsonFileFromRawFolder(jsonFileNameWithoutExtension)
        val formList = ArrayList<HashMap<String, String>>()
        mList = HashMap()

        for (i in 0 until mJsonArray!!.length()) {
            val jsonObj = mJsonArray.getJSONObject(i)
            //Log.d("JSON ITEM: ", String.valueOf(jsonObj.getInt("id")) + ": " + jsonObj.getString("value"));

            val jsonId = jsonObj.getInt("id")
            val jsonValue = jsonObj.getString("value")
            mList[jsonId] = jsonValue
        }

        if (sortAlphabetically) {
            return mList.toList().sortedBy { (_, value) -> value }.toMap()
        }
        return mList
    }

    /**
     * Formats a date string to the locale set in the user's phone
     *
     * @param ddMMyyyy "26/02/1974"
     * @return
     */
    fun formatDateToPhoneLocale(ddMMyyyy: String): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date: Date? = try {
            sdf.parse(ddMMyyyy)
        } catch (e: ParseException) {
            // handle exception here !
            Date(ddMMyyyy)
        }
        val dateFormat =
            DateFormat.getDateFormat(context)
        return dateFormat.format(date)
    }

    fun formatDateToPhoneLocale(date: Date?): String? {
        val dateFormat = DateFormat.getDateFormat(context)
        date.let {
            return dateFormat.format(date)
        }
    }

    fun formatDateToPhoneLocaleMonthAndYearOnly(date: Date, shortFormat: Boolean): String {
        var sdf = SimpleDateFormat("MMMM yyyy")
        if (shortFormat) {
            sdf = SimpleDateFormat("MM-yyyy")
        }
        return sdf.format(date)
    }

    fun formatDateToPhoneLocaleYearOnly(date: Date): String {
        val sdf = SimpleDateFormat("yyyy")
        return sdf.format(date)
    }

    fun formatDateToPhoneLocale(
        date: Date?,
        useLongFormat: Boolean
    ): String {
        var dateFormat =
            DateFormat.getDateFormat(context)
        if (useLongFormat) {
            dateFormat = DateFormat.getLongDateFormat(context)
        }
        return dateFormat.format(date)
    }

    //    public String getBodytypeById(int bodytypeId, Activity activity) {
    //        // NEW: READ FROM RAW FOLDER AND NOT FROM ASSET FOLDER
    //        HashMap<Integer, String> bodyTypes = GazeTools.readJsonFile(activity, "bodytypes.json", false);
    //        return bodyTypes.get(bodytypeId);
    //    }

    fun getAge(birthdate: Date?): Int {
        if (birthdate != null) {
            val calx = Calendar.getInstance()
            calx.time = birthdate
            val year = calx[Calendar.YEAR]
            val month = calx[Calendar.MONTH]
            val day = calx[Calendar.DAY_OF_MONTH]
            val cal = GregorianCalendar()
            var a: Int
            val y: Int = cal[Calendar.YEAR]
            val m: Int = cal[Calendar.MONTH]
            val d: Int = cal[Calendar.DAY_OF_MONTH]
            cal[year, month] = day
            a = y - cal[Calendar.YEAR]
            if (m < cal[Calendar.MONTH]
                || m == cal[Calendar.MONTH] && d < cal[Calendar.DAY_OF_MONTH]
            ) {
                --a
            }
            if (a < 0) {
                a = 0
            }
            return a
        } else {
            return 0
        }
    }

    //    /**
    //     * Returns 0 if item hasn't been found.
    //     *
    //     * @param bodytype
    //     * @param activity
    //     * @return
    //     */
    //    public int getBodytypeIdByType(String bodytype, Activity activity) {
    //        // NEW: READ FROM RAW FOLDER AND NOT FROM ASSET FOLDER
    //        HashMap<Integer, String> bodyTypes = GazeTools.readJsonFile(activity, "bodytypes.json", false);
    //        for (Map.Entry<Integer, String> entry : bodyTypes.entrySet()) {
    //            if (entry.getValue().equalsIgnoreCase(bodytype)) {
    //                return entry.getKey();
    //            }
    //        }
    //        return 0;
    //    }

    fun convertKiloToPounds(kilo: Int): Int {
        // 1KG = 2.20462 Pounds
        val poundsCalc = 2.20462
        val valueInPounds = kilo * poundsCalc
        val scale = 10.0.pow(1.0).toInt()
        val resultDouble =
            Math.round(valueInPounds * scale).toDouble() / scale
        return resultDouble.roundToInt()
    }

    /**
     * Converts the body height from centimeters to either metric or imperial system.
     *
     * @param cm
     * @param useMetricSystem
     * @return
     */
    fun convertHeightFromCentimetersToReadableFormat(
        cm: Int, useMetricSystem: Boolean
    ): String {
        val heightReadable: String
        if (useMetricSystem) {
            heightReadable = cm.toString() + context.resources.getString(R.string.cm)
        } else {
            val valueInFeet = cm / 30.48
            val afterDecimal = valueInFeet - Math.floor(valueInFeet)
            val remainingInches = afterDecimal * 12
            var remainingInchesRounded = Math.round(remainingInches).toInt()
            if (remainingInchesRounded > 11) {
                remainingInchesRounded = 11
            }
            val valueInFeetRounded = Math.floor(valueInFeet).toInt()
            heightReadable = context.resources.getString(
                R.string.feet_inches_numbered,
                valueInFeetRounded,
                remainingInchesRounded
            )
        }
        return heightReadable
    }

    /**
     * Converts the body weight from grams to either metric or imperial system.
     *
     * @param grams
     * @param useMetricSystem
     * @param context
     * @return
     */
    fun convertWeightFromGramsToReadableFormat(
        grams: Int,
        useMetricSystem: Boolean
    ): String {
        val weightReadable: String = if (useMetricSystem) {
            (grams / 1000).toString() + context.resources.getString(R.string.kg)
        } else {
            context.resources.getString(
                R.string.pounds_numbered_short,
                Math.round(grams / 1000 * 2.2)
            )
        }
        return weightReadable
    }

    fun convertFromMillimetersToReadableFormat(
        millimeters: Int,
        useMetricSystem: Boolean
    ): String {
        var lengthReadable = ""
        if (useMetricSystem) {
            lengthReadable =
                (millimeters / 10).toString() + context.resources.getString(R.string.cm)
        } else {
            val df = DecimalFormat("0.00")
            df.maximumFractionDigits = 1
            lengthReadable =
                df.format(millimeters * 0.0393701) + context.resources.getString(R.string.inches_short)
        }
        return lengthReadable
    }

    fun convertPxToDp(px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun convertDpToPx(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun getContactDetailViewTextPaddingBottomPixels(): Int {
        return (16 * context.resources.displayMetrics.density).toInt()
    }

    fun removeHttpPrefix(url: String?): String? {
        url?.let {
            return try {
                val uri =
                    Uri.parse(url) // i.e. https://www.facebook.com/firstname.lastname
                val path = uri.path // split whatever you need
                val host = uri.host
                val newHost = host!!.replaceFirst("^www.".toRegex(), "")
                newHost + path
            } catch (e: Exception) {
                url
            }
        }
        return null
    }

    //    public static void showSnackBar(Activity activity, String text, SnackBarType type, int dismissTime) {
    //        SnackBar sb = new SnackBar(activity, text);
    //        int color;
    //        switch (type) {
    //            case INFO:
    //                color = Color.BLACK;
    //                break;
    //            case SUCCESS:
    //                color = Color.argb(200, 0, 100, 0);
    //                break;
    //            case WARNING:
    //                color = Color.argb(200, 255, 140, 0);
    //                break;
    //            case ERROR:
    //                color = Color.RED;
    //                break;
    //            default:
    //                color = Color.BLACK;
    //        }
    //        sb.setBackgroundSnackBar(color);
    //        sb.setDismissTimer(dismissTime);
    //        sb.show();
    //    }

    @JvmOverloads
    fun showMaterialSnackBar(
        view: View?,
        text: String?,
        type: SnackBarType?,
        dismissTime: Int = 4000
    ) {
        val color: Int = when (type) {
            SnackBarType.INFO -> Color.BLACK
            SnackBarType.SUCCESS -> Color.argb(200, 0, 100, 0)
            SnackBarType.WARNING -> Color.argb(200, 255, 140, 0)
            SnackBarType.ERROR -> Color.RED
            else -> Color.BLACK
        }
        val snb = Snackbar.make(view!!, text!!, dismissTime)
        snb.view.setBackgroundColor(ContextCompat.getColor(context, color))
        snb.show()
    }

    fun copyToClipboard(valueToCopy: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copiedText", valueToCopy)

        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip)
    }

    fun replaceNewlinesWithBreaks(source: String): String {
        return source.replace("(?:\n|\r\n)".toRegex(), "<br>")
    }

    fun sendEmail(email: String, subject: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    @Throws(IOException::class)
    fun convertStreamToString(`is`: InputStream?): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String? = null
        while (reader.readLine().also { line = it } != null) {
            sb.append(line).append("\n")
        }
        reader.close()
        return sb.toString()
    }

    val maxContactSavesFreeVersion: Int
        get() {
            return Const.FREE_USER_MAXIMUM_CONTACTS + preferences.getInt(
                "additionalContactSaves",
                0
            )
        }

    val maxImageSavesFreeVersion: Int
        get() {
            return Const.FREE_USER_MAXIMUM_IMAGES_PER_CONTACT + preferences.getInt(
                "additionalImageSaves",
                0
            )
        }
}