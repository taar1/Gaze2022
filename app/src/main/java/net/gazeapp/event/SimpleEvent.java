package net.gazeapp.event;

/**
 * Just a simple event to fire to trigger stuff.
 * Created by taar1 on 19.01.2017
 */
public class SimpleEvent {

    private Exception mException;
    private final boolean mSucceeded;

    public SimpleEvent() {
        mSucceeded = true;
    }

    public SimpleEvent(Exception exception) {
        mSucceeded = false;
        mException = exception;
    }

    public Exception getException() {
        return mException;
    }

    public boolean isSucceeded() {
        return mSucceeded;
    }

}
