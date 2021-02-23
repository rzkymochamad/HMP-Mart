package com.mg.shopping.objectutil;


import androidx.fragment.app.Fragment;

public class PagerTabObject {
    private String id;
    private String title;
    private int icon;
    private Fragment fragment;

    public String getId() {
        return id;
    }

    public PagerTabObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PagerTabObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public PagerTabObject setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public PagerTabObject setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }
}
