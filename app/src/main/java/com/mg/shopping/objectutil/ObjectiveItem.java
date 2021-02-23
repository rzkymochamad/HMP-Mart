package com.mg.shopping.objectutil;

/**
 * Created by hp on 3/9/2018.
 */

public class ObjectiveItem {
    int picture;
    String title;

    public ObjectiveItem(String title, int picture) {
        this.picture = picture;
        this.title = title;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
