package com.mg.shopping.serviceutil;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.mg.shopping.connectionutil.Connection;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.objectutil.GlobalDataObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.utility.Utility;

public class OreoIntentService extends JobIntentService {
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    String tagLog = OreoIntentService.class.getName();

    public OreoIntentService() {
        //do nothing
    }

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {

        enqueueWork(context, OreoIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        Utility.Logger(tagLog, "Setting : Working");

            //It load specific tagLogs wallpaper at background
            //After loading it would add them into Wallpaper db

            RequestObject requestObject = GlobalDataObject.getRequestObject() != null ? GlobalDataObject.getRequestObject()
                    : (RequestObject) intent.getParcelableExtra(Constant.IntentKey.REQUEST_OBJECT);
            Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());
            GlobalDataObject.setRequestObject(null);

            Utility.Logger(tagLog, "JSON = " + requestObject.getJson());





    }


}
