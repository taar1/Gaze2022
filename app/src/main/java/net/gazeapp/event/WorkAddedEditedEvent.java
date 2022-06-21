package net.gazeapp.event;

import net.gazeapp.data.model.Work;

/**
 * Created by taar1 on 11.03.2017.
 */

public class WorkAddedEditedEvent {

    public enum Action {
        DELETE, UPDATE, ADD, CANCEL
    }

    private Work work;
    private Action action;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public WorkAddedEditedEvent(Work work, Action action) {
        this.work = work;
        this.action = action;
    }
}
