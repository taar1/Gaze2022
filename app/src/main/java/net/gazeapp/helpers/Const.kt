package net.gazeapp.helpers

object Const {
    const val DB_NAME = "Gaze.db"
    const val DATABASE_VERSION = 1

    const val MIN_AGE = 16
    const val MAX_AGE = 100
    const val FREE_USER_MAXIMUM_CONTACTS = 30
    const val FREE_USER_MAXIMUM_IMAGES_PER_CONTACT = 9
    const val FREE_USER_MAXIMUM_IMAGES_MY_MEDIA_GALLERY = 10

    val TWENTY_CONTACTS_HOOKS =
        intArrayOf(20, 23, 26, 29) // Show dialog after adding these amounts of contacts

    const val RECENT_CONTACTS_LIMIT: Long = 10
    const val ADDITIONAL_IMAGE_SAVE_1 = 10
    const val ADDITIONAL_IMAGE_SAVE_2 = 20
    const val ADDITIONAL_IMAGE_SAVE_3 = 30
    const val ADDITIONAL_CONTACT_SAVE_1 = 40
    const val ADDITIONAL_CONTACT_SAVE_2 = 50
    const val ADDITIONAL_CONTACT_SAVE_3 = 60
    const val ADDITIONAL_CONTACT_SAVE_4 = 70
    const val ADDITIONAL_CONTACT_SAVE_5 = 80
    const val MAX_AMOUNT_EGGPLANTS = 80
    const val EMAIL_FEEDBACK = "feedback@gazeapp.net"
    const val SHARED_PREFERENCES_FILE = "GazeSharedPreferencesPrivate"
    const val MOPUB_ID = "efb539b423544b52ad92e761a9642754"
    const val MOPUB_REWARDED_VIDEO_AD_ID = "81e1f0a6e0ca45d4ac09f3ce042975c6"
    const val MOPUB_TARGET_GROUP = "m_age:24,m_gender:m,m_marital:single"
    const val SPLUNK_MINT_API_KEY = "3244b8e9"
    const val MOBFOX_INVENTORY_HASH = "26a0b6551b77b569220cb3010fec34bf"
    const val MOBFOX_INVENTORY_ID = "194521"
    const val VUNGLE_REPORTING_API_KEY = "ca03d5b14167cddd1352e433872b4fc7"
    const val VUNGLE_APP_ID = "58ebe5cac24004e755000de2"
    const val VUNGLE_REPORTING_ID = "58ebe5cac24004e755000de2"
    const val MOBILECORE_APP_ID = "268639"
    const val CHARTBOOST_USER_ID = "533d3524f8975c5f90b22887"
    const val CHARTBOOST_USER_SIGNATURE =
        "732ac0b1eee4162f0120f16248c2093b4c5260686a43c5d4f91e11e948c0908b"
    const val CHARTBOOST_APP_ID = "58b419f804b01632f812b2c0"
    const val CHARTBOOST_APP_SIGNATURE = "671c5d8910ff2dce7c618f8b40692ea3ce7b43d9"
    const val ADCOLONY_APP_ID = "app5a77b041652e42749b"
    const val ADCOLONY_ZONE_ID = "vz78d66418075d4bdf9e"
    const val TAPJOY_SDK_KEY = "Z1UsHrvwRzWTAWDwX2JU6wEC5Ez4uox6G1eqyFbsf5tkQRIAyU-8BfKvGp__"
    const val UNITY_ADS_PROJECT_ID = "82e8fa9f-f730-4375-b41f-7fa7c3ffbc48"
    const val UNITY_GAME_ID = "1324789"
    const val ADDBUDDIZ_PUBLISHER_KEY = "06fc4156-3aa0-4851-a707-90f34a474bdb"
    const val GALLERY_PATH_PUBLIC = "public"
    const val GALLERY_PATH_PRIVATE = "private"
    const val GALLERY_MY_MEDIA_PATH = "myMedia"
    const val TEMPORARY_PATH_FOR_SHARING = "temp_sharing"
    const val GALLERY_PRIVATE = true
    const val GALLERY_PUBLIC = false
    const val GRID_GALLERY_ITEM_HEIGHT = 490
    const val GRID_GALLERY_COLUMNS = 3
    const val SHARED_PREFERENCES_USE_PASSWORD = "usePassword"
    const val SHARED_PREFERENCES_USE_FINGERPRINT = "useFingerprint"
    const val SHARED_PREFERENCES_USE_METRIC_SYSTEM = "useMetricSystem"
    const val SHARED_PREFERENCES_SHOW_PRIVATE_PICS = "showPrivatePics"
    const val SHARED_PREFERENCES_PASSWORD_MD5 = "passwordMD5"

    // IMAGES GRID GALLERY
    const val FETCH_STARTED = 2001
    const val FETCH_COMPLETED = 2002
    const val PERMISSION_GRANTED = 2003
    const val PERMISSION_DENIED = 2004
    const val ERROR = 2005
    const val ACTIVITY_FOR_RESULT_EDIT_CONTACT_CODE = 900
    const val RESULT_UPDATE_FAILED = 666

    // ACTIVITY RESULT CODES
    const val MAIN_PIC_MAY_HAVE_CHANGED = 1000
    const val MAIN_PIC_MIGHT_HAVE_CHANGED_KEY = "mainPicMayHaveChanged"
    const val MEDIA_LIST = "mediaList"
    const val CONTACT = "contact"
    const val REQUEST_CODE = 1
    const val REQUEST_CODE_ADD_IMAGES_TO_GALLERY = 100
    const val REQUEST_CODE_EDIT_IMAGES_GALLERY = 110

    /**
     * Request code for permission has to be < (1 << 8)
     * Otherwise throws java.lang.IllegalArgumentException: Can only use lower 8 bits for requestCode
     */
    const val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 23
    const val INTENT_EXTRA_ALBUM = "album"
    const val INTENT_EXTRA_IMAGES = "images"
    const val INTENT_EXTRA_LIMIT = "limit"
    const val HEIGHT_TAB_BAR_AND_AD_BANNER = 1750f

    // Google Billing
    const val BASE64_ENCODED_PUBLIC_KEY =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApcp/Httd/V+79Z3dauFSId60ULSMCMPbX0Yl9k+3EHlk9r4wRW+iHpfhKCxq/umiO7d5S7pUT5RfoAEK4HhrS1JKhIi9DwGuxZ+SGUfKdO1nagtpXy7nKcM2xjhC3a6Jz2CdB45xI9VZrja6FgULQq3sxyXL+ixcUsejk+/71G0Ig3yRbKMfNWMQp0Z3bHqHLHzK9rOmNgb4S5o7T1dSdylfgUYC/8fswVQN5g81aC/djnTJXhaWs066YbldobZMGAQxNzUw//+mfDLcmty6bRcgp5ACrQFtQRfnFa99CXyRtOWFs6lizh2uYzuNeSAPWjArHffhZScmh7lNxuQurwIDAQAB"

    const val GAZE_PRO_MONTHLY_SKU = "gaze.pro.monthly"
    const val GAZE_PRO_ANNUALLY_SKU = "gaze.pro.annually"
    const val MANHUNT_BASE_URL = "https://www.manhunt.net/userprofile/"
    const val GAYDAR_BASE_URL = "https://www.gaydar.net/profile/"
    const val GAYCOM_BASE_URL = "https://www.gay.com/profiles/"
    const val DUDESNUDE_BASE_URL =
        "http://www.dudesnude.com/members/" // http://www.dudesnude.com/members/1267074/
    const val ADAM4ADAM_BASE_URL = "https://www.adam4adam.com/profile/view/"
    const val YOUTUBE_BASE_URL = "https://www.youtube.com/user/"
    const val XTUBE_BASE_URL = "http://www.xtube.com/profile/"
    const val XHAMSTER_BASE_URL = "https://xhamster.com/user/"
    const val XING_BASE_URL = "https://www.xing.com/profile/"
    const val VIMEO_BASE_URL = "https://vimeo.com/"
    const val FIRST_GAZE_CONTACT_NAME = "Josh from Gaze"
    const val FACEBOOK_AUDIENCE_NETWORK_NATIVE_BANNER_ID = "254596548531525_254596931864820"
    const val GOOGLE_ANALYTICS_ID = "UA-6697147-7"
    const val FIREBASE_PROJECT_ID = "gaze-67375"
}