package net.gazeapp.event;

import net.gazeapp.data.model.Contact;

import java.sql.SQLException;

/**
 * Created by taar1 on 22.02.2016.
 */
public class NewContactObjectPreparedEvent {

    private Exception mException;
    private Contact mContact;

    public NewContactObjectPreparedEvent(Contact contact) {
        mContact = contact;
    }

    public NewContactObjectPreparedEvent(SQLException exception) {
        mException = exception;
    }

    public Exception getException() {
        return mException;
    }

    public Contact getContact() {
        return mContact;
    }
}
