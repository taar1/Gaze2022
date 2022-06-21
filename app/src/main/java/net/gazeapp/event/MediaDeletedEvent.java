package net.gazeapp.event;

import net.gazeapp.data.model.Media;

import java.util.List;

/**
 * Event fired when MediaEntity has been deleted (or failed to) from the database.
 * Created by taar1 on 20.09.2016.
 */
public class MediaDeletedEvent {

    private Exception mException;
    private final boolean mSucceeded;
    private Media media;
    private List<Media> mediaList;

    public MediaDeletedEvent(Media media) {
        mSucceeded = true;
        this.media = media;
    }

    public MediaDeletedEvent(List<Media> mediaList) {
        mSucceeded = true;
        this.mediaList = mediaList;
    }

    public MediaDeletedEvent(Exception exception) {
        mSucceeded = false;
        mException = exception;
    }

    public Media getMedia() {
        return media;
    }

    public Exception getException() {
        return mException;
    }

    public boolean isSucceeded() {
        return mSucceeded;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }
}
