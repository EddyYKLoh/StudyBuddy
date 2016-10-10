package com.ykloh.studybuddy;

/**
 * Created by LYK on 10/10/2016.
 */

public class Subject {

    private String name;
    private boolean selected;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}