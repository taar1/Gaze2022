package net.gazeapp.event;

import net.gazeapp.data.model.Encounter;

/**
 * Created by taar1 on 12.03.2017.
 * updated on 06.08.2019.
 */
public class EncounterAddedEditedEvent {

    public enum Action {
        DELETE, UPDATE, ADD, CANCEL
    }

    private Encounter encounter;
    private Action action;

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public EncounterAddedEditedEvent(Encounter encounter, Action action) {
        this.encounter = encounter;
        this.action = action;
    }
}
