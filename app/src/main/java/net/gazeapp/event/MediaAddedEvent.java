package net.gazeapp.event;

import net.gazeapp.data.GazeImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Event fired when Media has been added though the image selector.
 * Created by taar1 on 15.01.2017
 */
public class MediaAddedEvent {

    private Exception mException;
    private final boolean mSucceeded;
    private List<GazeImage> mImages;

    public MediaAddedEvent() {
        mSucceeded = true;
    }

    public MediaAddedEvent(Exception exception) {
        mSucceeded = false;
        mException = exception;
    }

    public Exception getException() {
        return mException;
    }

    public boolean isSucceeded() {
        return mSucceeded;
    }

    public List<GazeImage> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<GazeImage> mImages) {
        this.mImages = mImages;
    }
}
