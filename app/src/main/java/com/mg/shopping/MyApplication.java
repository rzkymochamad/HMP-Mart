package com.mg.shopping;

import android.app.Application;
import android.content.Context;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.managementutil.Management;
import com.mg.shopping.utility.Utility;
import com.onesignal.OneSignal;
import com.stripe.android.Stripe;
import androidx.multidex.MultiDex;


public class MyApplication extends Application {

    static MyApplication mInstance;
    Context context;
    static Stripe stripe;
    static Management management;

    public MyApplication() {
    }

    public MyApplication(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        Thread.setDefaultUncaughtExceptionHandler(new ThreadHandeling());

        //Initialize One Signal

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                /*.setNotificationOpenedHandler(new CustomOneSignalHandler(this))*/
                .init();



        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Utility.Logger("debug", "UserObject:" + userId);
                Constant.Credentials.setDeviceToken(userId);
                if (registrationId != null)
                    Utility.Logger("debug", "registrationId:" + registrationId);

            }
        });


        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);

        setmInstance(this);
        setManagement(new Management(this));


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static Management getManagementInstance(){
       return management;
    }

    public static void setmInstance(MyApplication mInstance) {
        MyApplication.mInstance = mInstance;
    }

    public static void setManagement(Management management) {
        MyApplication.management = management;
    }

    public static Stripe getStripe() {
        stripe = new Stripe(getInstance(), BuildConfig.STRIPE_KEY);
        return stripe;
    }

}

