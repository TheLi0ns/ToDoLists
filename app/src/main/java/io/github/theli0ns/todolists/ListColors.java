package io.github.theli0ns.todolists;

import java.util.ArrayList;
import java.util.Objects;

public enum ListColors {
    RED("Red", "#fc0303"),
    CYAN("Cyan", "#03e3fc"),
    GREEN("Green", "#14fc03"),
    PURPLE("Purple", "#ff6be9"),
    ORANGE("Orange", "#ffa500"),
    YELLOW("Yellow", "#f8ff36");

    final String name;
    final String code;

    ListColors(String name, String code){
        this.name = name;
        this.code = code;
    }

    public static String[] getNamesArray(){
        ArrayList<String> namesArr = new ArrayList<>();
        for (ListColors value : values()) {
            namesArr.add(value.name);
        }
        return namesArr.toArray(new String[values().length]);
    }

    public static ListColors getByName(String name){
        for (ListColors value : values()) {
            if(Objects.equals(value.name, name)) return value;
        }
        return null;
    }

}
