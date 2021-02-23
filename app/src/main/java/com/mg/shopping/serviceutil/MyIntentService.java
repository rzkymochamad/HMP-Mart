package com.mg.shopping.serviceutil;

import android.app.IntentService;
import android.content.Intent;

import com.mg.shopping.connectionutil.Connection;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.GlobalDataObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.utility.Utility;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class MyIntentService extends IntentService {
    private String tag = MyIntentService.class.getName();

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Utility.Logger(tag, "Setting : Working");

        if (intent != null) {


            //It load specific tags wallpaper at background
            //After loading it would add them into Wallpaper db

            RequestObject requestObject = GlobalDataObject.getRequestObject() != null ? GlobalDataObject.getRequestObject()
                    : (RequestObject) intent.getParcelableExtra(Constant.IntentKey.REQUEST_OBJECT);

            Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());
            Utility.Logger(tag, "JSON = " + requestObject.getJson());
            GlobalDataObject.setRequestObject(null);

        }
    }


}
