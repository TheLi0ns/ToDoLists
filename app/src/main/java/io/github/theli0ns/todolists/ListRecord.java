package io.github.theli0ns.todolists;

public class ListRecord {
    private long ID;
    private String name;
    private ListColors color;

    public ListRecord(long ID, String name, ListColors color) {
        this.ID = ID;
        this.name = name;
        this.color = color;
    }

    public ListRecord(String name, ListColors color) {
        this.name = name;
        this.color = color;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setColor(ListColors color) {
        this.color = color;
    }

    public ListColors getColor(){
        return color;
    }
}
