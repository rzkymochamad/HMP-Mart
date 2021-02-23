package com.mg.shopping.preferenceutil;

import android.content.SharedPreferences;

public abstract class PreferenceObserver {

    PrefObject prefObject = null;
    SharedPreferences sharedPreferences;

    public abstract void onUpdate();
    public abstract PrefObject getPreference();

}
