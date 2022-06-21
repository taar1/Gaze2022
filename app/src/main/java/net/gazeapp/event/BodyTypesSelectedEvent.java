package net.gazeapp.event;

import java.util.List;

/**
 * Created by taar1 on 22.02.2016.
 */
public class BodyTypesSelectedEvent {

    private final List<String> mBodyTypes;

    public BodyTypesSelectedEvent(List<String> bodyTypes) {
        mBodyTypes = bodyTypes;
    }

    public List<String> getBodytypes() {
        return mBodyTypes;
    }
}
