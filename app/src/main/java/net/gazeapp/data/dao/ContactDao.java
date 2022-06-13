package net.gazeapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

import net.gazeapp.data.model.Address;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Email;
import net.gazeapp.data.model.Nickname;
import net.gazeapp.data.model.Phone;
import net.gazeapp.data.model.Website;
import net.gazeapp.data.model.Work;

import java.util.List;

@Deprecated
@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact WHERE isMe = 0 ORDER BY " + Contact.CONTACT_NAME + " ASC")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE ID = :contactId LIMIT 1")
    Contact getContact(int contactId);

    @Query("SELECT * FROM Contact WHERE isMe = 1 LIMIT 1")
    Contact getMe();

    @Query("SELECT * FROM Contact WHERE isMe = 0 ORDER BY " + Contact.LAST_VIEWED + " LIMIT :limit")
    List<Contact> getRecentContacts(long limit);

    @Insert
    long insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Delete
    void delete(Contact... contact);

    @Query("DELETE FROM Contact WHERE ID = :contactId")
    void deleteContacts(String contactId);

    @Update
    void update(Contact contact);

    @Query("SELECT COUNT(ID) FROM Contact")
    int countContacts();


    /**
     * Do a full text search.
     *
     * @param query Parameter MUST be surrounded by %XXX% in order for the LIKE clause to work.
     * @return
     */
    @Transaction
    @Query("SELECT * FROM Contact AS c " +
            "LEFT JOIN Nickname AS n ON n.contact_id = c.id " +
            "LEFT JOIN Address AS a ON a.contact_id = c.id " +
            "LEFT JOIN Email AS e ON e.contact_id = c.id " +
            "LEFT JOIN Phone AS p ON p.contact_id = c.id " +
            "LEFT JOIN Website AS we ON we.contact_id = c.id " +
            "LEFT JOIN Work AS wo ON wo.contact_id = c.id " +

            "WHERE c." + Contact.CONTACT_NAME + " LIKE :query " +
            "OR c." + Contact.ADDITIONAL_INFO + " LIKE :query " +
            "OR n." + Nickname.NICKNAME + " LIKE :query " +
            "OR a." + Address.ADDRESS + " LIKE :query " +
            "OR e." + Email.EMAIL + " LIKE :query " +
            "OR p." + Phone.PHONE_NUMBER + " LIKE :query " +
            "OR we." + Website.WEBSITE + " LIKE :query " +
            "OR wo." + Work.EMPLOYER + " LIKE :query " +
            "OR wo." + Work.PROFESSION + " LIKE :query " +
            "OR wo." + Work.EMPLOYER_ADDRESS + " LIKE :query " +
            "OR wo." + Work.PHONE + " LIKE :query " +
            "OR wo." + Work.EMAIL + " LIKE :query " +

            "ORDER BY c." + Contact.CONTACT_NAME + ", " +
            "c." + Contact.ADDITIONAL_INFO + ", " +
            "c." + Contact.CONTACT_NAME + " COLLATE NOCASE")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<Contact> searchContacts(String query);
}


// TODO searchContacts
// TODO searchContacts
// TODO searchContacts
//    public List<Contact> searchContdacts(String query) throws ItemNotFoundException, SQLException {
//
//        QueryBuilder<Contact, Integer> queryBuilder = getContactDao().queryBuilder();
//        Where contactWhere = queryBuilder.where();
//        contactWhere.like(Contact.CONTACT_NAME, "%" + query + "%");
//        contactWhere.or();
//        contactWhere.like(Contact.ADDITIONAL_INFO, "%" + query + "%");
//
//        QueryBuilder<Nickname, Integer> nicknameQueryBuilder = mNicknameDao.queryBuilder();
//        nicknameQueryBuilder.where().like(Nickname.NICKNAME, "%" + query + "%");
//
//        QueryBuilder<Address, Integer> addressQueryBuilder = mAddressDao.queryBuilder();
//        addressQueryBuilder.where().like(Address.ADDRESS, "%" + query + "%");
//
//        QueryBuilder<Email, Integer> emailQueryBuilder = mEmailDao.queryBuilder();
//        emailQueryBuilder.where().like(Email.EMAIL, "%" + query + "%");
//
//        QueryBuilder<Phone, Integer> phoneQueryBuilder = mPhoneDao.queryBuilder();
//        phoneQueryBuilder.where().like(Phone.PHONE_NUMBER, "%" + query + "%");
//
//        QueryBuilder<Website, Integer> websiteQueryBuilder = mWebsiteDao.queryBuilder();
//        websiteQueryBuilder.where().like(Website.WEBSITE, "%" + query + "%");
//
//        QueryBuilder<Work, Integer> workQueryBuilder = mWorkDao.queryBuilder();
//        Where workWhere = workQueryBuilder.where();
//        workWhere.like(Work.EMPLOYER, "%" + query + "%");
//        workWhere.or();
//        workWhere.like(Work.PROFESSION, "%" + query + "%");
//        workWhere.or();
//        workWhere.like(Work.EMPLOYER_ADDRESS, "%" + query + "%");
//        workWhere.or();
//        workWhere.like(Work.PHONE, "%" + query + "%");
//        workWhere.or();
//        workWhere.like(Work.EMAIL, "%" + query + "%");
//
//        queryBuilder.leftJoinOr(nicknameQueryBuilder);
//        queryBuilder.leftJoinOr(addressQueryBuilder);
//        queryBuilder.leftJoinOr(emailQueryBuilder);
//        queryBuilder.leftJoinOr(phoneQueryBuilder);
//        queryBuilder.leftJoinOr(websiteQueryBuilder);
//        queryBuilder.leftJoinOr(workQueryBuilder);
//        queryBuilder.orderBy(Contact.CONTACT_NAME, true);
//        queryBuilder.orderBy(Contact.ADDITIONAL_INFO, true);
//        queryBuilder.orderByRaw(Contact.CONTACT_NAME + " COLLATE NOCASE");
//        queryBuilder.groupBy(Contact.CONTACT_NAME);
//
//        PreparedQuery<Contact> preparedQuery = queryBuilder.prepare();
//        List<Contact> contactList = getContactDao().query(preparedQuery);
//        Log.d(TAG, "SEARCH QUERY: " + preparedQuery.getStatement());
//
//        if (contactList == null) {
//            Log.e(TAG, "No contacts found by search query: " + query);
//            throw new ItemNotFoundException();
//        }
//
//        return contactList;
//    }

