package com.mg.shopping.preferenceutil;

import android.content.SharedPreferences;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.utility.Utility;

public class UserSharedPreferenceObserver extends PreferenceObserver{

    public UserSharedPreferenceObserver(PrefObject prefObject, SharedPreferences sharedPreferences) {
        this.prefObject = prefObject;
        this.sharedPreferences = sharedPreferences;
        this.prefObject.attachObserver(this);

    }

    @Override
    public void onUpdate() {

        prefObject.setLogin(sharedPreferences.getBoolean(Constant.SharedPref.LOGIN, false));
        prefObject.setFirstLaunch(sharedPreferences.getBoolean(Constant.SharedPref.FIRST_LAUNCH, false));
        prefObject.setUserRemember(sharedPreferences.getBoolean(Constant.SharedPref.USER_REMEMBER, false));

        prefObject.setUserId(sharedPreferences.getString(Constant.SharedPref.USER_ID, "0"));
        prefObject.setLoginType(sharedPreferences.getString(Constant.SharedPref.LOGIN_TYPE,null));
        prefObject.setUserEmail(sharedPreferences.getString(Constant.SharedPref.USER_EMAIL,null));
        prefObject.setUserPassword(sharedPreferences.getString(Constant.SharedPref.USER_PASSWORD,null));
        prefObject.setUserName(sharedPreferences.getString(Constant.SharedPref.USER_PROFILE_NAME, null));
        prefObject.setPictureUrl(sharedPreferences.getString(Constant.SharedPref.USER_PICTURE, null));

        prefObject.setCountryId(sharedPreferences.getString(Constant.SharedPref.COUNTRY_ID, "0"));
        prefObject.setCurrencyCode(sharedPreferences.getString(Constant.SharedPref.CURRENCY_CODE, null));
        prefObject.setCurrencySymbol(sharedPreferences.getString(Constant.SharedPref.CURRENCY_SYMBOL, null));

        Utility.Logger(getClass().getSimpleName(),prefObject.toString());

    }

    @Override
    public PrefObject getPreference() {
        return this.prefObject;
    }

}
