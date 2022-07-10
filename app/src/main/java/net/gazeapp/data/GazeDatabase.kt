package net.gazeapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import net.gazeapp.data.converter.RoomDateConverter
import net.gazeapp.data.converter.RoomUriConverter
import net.gazeapp.data.dao.*
import net.gazeapp.data.model.*
import net.gazeapp.helpers.Const

@Database(
    entities = [
        Address::class,
        Body::class,
        BodyType::class,
        Child::class,
        Club::class,
        Contact::class,
        ContactLabelCrossRef::class,
        Conversation::class,
        Drug::class,
        Email::class,
        Encounter::class,
        Endowment::class,
        Ethnicity::class,
        Fetish::class,
        Food::class,
        Health::class,
        Hobby::class,
        Label::class,
        Media::class,
        MyMedia::class,
        MediaTagCrossRef::class,
        Movie::class,
        MyPreferences::class,
        Nickname::class,
        Note::class,
        Personal::class,
        Phone::class,
        SecurityQuestion::class,
        SocialMedia::class,
        Sport::class,
        Tag::class,
        Website::class,
        Work::class,
        Xxx::class],
    version = Const.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    RoomDateConverter::class, RoomUriConverter::class
)
abstract class GazeDatabase : RoomDatabase() {
    abstract val addressDao: AddressDao
    abstract val bodyDao: BodyDao
    abstract val bodyTypeDao: BodyTypeDao
    abstract val childDao: ChildDao
    abstract val clubDao: ClubDao
    abstract val contactKtDao: ContactKtDao
    abstract val drugDao: DrugDao
    abstract val emailDao: EmailDao
    abstract val encounterDao: EncounterDao
    abstract val endowmentDao: EndowmentDao
    abstract val ethnicityDao: EthnicityDao
    abstract val fetishDao: FetishDao
    abstract val foodDao: FoodDao
    abstract val healthDao: HealthDao
    abstract val hobbyDao: HobbyDao
    abstract val mediaDao: MediaDao
    abstract val myMediaDao: MyMediaDao
    abstract val movieDao: MovieDao
    abstract val nicknameDao: NicknameDao
    abstract val noteDao: NoteDao
    abstract val personalDao: PersonalDao
    abstract val phoneDao: PhoneDao
    abstract val socialMediaDao: SocialMediaDao
    abstract val sportDao: SportDao
    abstract val websiteDao: WebsiteDao
    abstract val workDao: WorkDao
    abstract val xxxDao: XxxDao
    abstract val labelDao: LabelDao
    abstract val contactLabelDao: ContactLabelDao
    abstract val myPreferencesDao: MyPreferencesDao
    abstract val securityQuestionDao: SecurityQuestionDao
    abstract val tagDao: TagDao

    companion object {
        private const val TAG = "GazeDatabase"

        @Volatile
        private var INSTANCE: GazeDatabase? = null

        fun getDatabase(context: Context): GazeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GazeDatabase::class.java,
                    Const.DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(GazeDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        class GazeDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                Log.d(TAG, "onCreate: AddInitialRoomData")
                val addInitialRoomData = AddInitialRoomData(INSTANCE!!)
                addInitialRoomData.addFirstContactToDatabase()
                addInitialRoomData.addLabelsToDatabase()
                addInitialRoomData.addTagsToDatabase()
            }

        }
    }
}