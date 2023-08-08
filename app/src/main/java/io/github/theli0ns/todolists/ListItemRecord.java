package io.github.theli0ns.todolists;

import java.util.ArrayList;

public class ListItemRecord {
    private long ID;
    private long list_id;
    private String text;
    private int state;

    enum States{
        NONE(""),
        TODO("ToDo"),
        IN_PROGRESS("In progress"),
        COMPLETED("Done");

        private final String name;

        States(String name) {
            this.name = name;
        }

        public static String[] getNamesArray(){
            ArrayList<String> namesArr = new ArrayList<>();
            for (States value : values()) {
                namesArr.add(value.name);
            }
            return namesArr.toArray(new String[values().length]);
        }
    }

    public ListItemRecord(long id, long list_id, String text, int state) {
        this.ID = id;
        this.list_id = list_id;
        this.text = text;
        this.state = state;
    }

    public ListItemRecord(long list_id, String text, int state) {
        this.list_id = list_id;
        this.text = text;
        this.state = state;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public void setList_id(long list_id) {
        this.list_id = list_id;
    }

    public long getList_id() {
        return list_id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public String getStateString(){
        return States.values()[state].name;
    }

    public void scrollState(){
        state++;
        if(state >= States.values().length) state = 0;
    }
}
