package com.mg.shopping.connectionutil;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.mg.shopping.BuildConfig;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.fontutil.Font;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.GlobalDataObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.serviceutil.MyIntentService;
import com.mg.shopping.serviceutil.OreoIntentService;
import com.mg.shopping.utility.Utility;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import needle.Needle;
import needle.UiRelatedProgressTask;


public class ConnectionBuilder {
    private String tag = ConnectionBuilder.class.getName();
    ACProgressFlower acProgressFlower = null;
    BottomSheetDialog bottomSheetDialog;

    public ConnectionBuilder(final RequestObject requestObject) {

        //Checking Connection

        if (!Utility.checkConnection(requestObject.getContext())) {

            if (requestObject.getInternetCallback() != null) {
                requestObject.getInternetCallback().onConnectivityFailed();
                return;
            }

            Utility.Toaster(requestObject.getContext(), Utility.getStringFromRes(requestObject.getContext(), R.string.internet_not_available), Toast.LENGTH_SHORT);
            return;
        }

        //Showing Progress BottomSheet Dialog / Simple Progress Dialog

        if (!Utility.isEmptyString(requestObject.getLoadingText())) {

            if (requestObject.getLoadingType() == Constant.LOADING_TYPE.DIALOG) {

                acProgressFlower = getACProgressFlower(requestObject.getContext(),
                        requestObject.getLoadingText());
                acProgressFlower.show();

            } else if (requestObject.getLoadingType() == Constant.LOADING_TYPE.BOTTOM_SHEET) {

                bottomSheetDialog = getBottomSheetDialog(requestObject.getContext(),
                        requestObject.getLoadingText());
                bottomSheetDialog.show();

            }

        }

        //debug the main json response
        Utility.extraData(tag, "Json = " + requestObject.toString());

        if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.UI) {

            sendUIRequest(requestObject);  //send UI Request

        }
        else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            sendBackgroundRequest(requestObject);  //send Background Request

        }
        else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BUILD_DEEPLINK) {

            generateDeepLink(requestObject);  //generate deep link

        }
        else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.DOWNLOAD) {

            sendDownloadingRequest(requestObject); // send downloading request
        }

    }

    /**
     * <p>It is used to send UI Request</p>
     *
     * @param requestObject
     */
    private void sendUIRequest(final RequestObject requestObject) {

        Needle.onBackgroundThread().execute(new UiRelatedProgressTask<String, Integer>() {

            @Override
            protected void onProgressUpdate(Integer integer) {
                //do nothing
            }

            @Override
            protected String doWork() {
                return getNetworkRequestType(requestObject);
            }

            @Override
            protected void thenDoUiRelatedWork(String data) {

                Utility.extraData(tag, "Response = " + data);

                if (Utility.isEmptyString(data)) {
                    return;
                }

                if (data.equals(Constant.ImportantMessages.CONNECTION_ERROR)) {
                    return;
                }


                sendResponseToCallback(requestObject,DataObject.getDataObject(requestObject, data));


            }


        });

    }


    /**
     * <p>It is used to send request in background</p>
     *
     * @param requestObject
     */
    private void sendBackgroundRequest(final RequestObject requestObject) {

        GlobalDataObject.setRequestObject(requestObject);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Intent intent = new Intent(requestObject.getContext(), OreoIntentService.class);
            OreoIntentService.enqueueWork(requestObject.getContext(), intent);

        } else {

            Intent intent = new Intent(requestObject.getContext(), MyIntentService.class);
            requestObject.getContext().startService(intent);
        }

    }


    /**
     * <p>It is used to generate deep link</p>
     * @param requestObject
     */
    private void generateDeepLink(final RequestObject requestObject){

        String uriPrefix = Constant.ServerInformation.DEEP_LINK_URL;
        final Uri[] uri = {null};

        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setDomainUriPrefix(uriPrefix)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(BuildConfig.VERSION_CODE)
                        .build())
                .setLink(Uri.parse(requestObject.getServerUrl()))
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {

                        if (task.isSuccessful()) {
                            // Short link created
                            uri[0] = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            Utility.Logger(tag, "ShortLink = " + uri[0].toString() + " flowChartLink = " + flowchartLink.toString());
                            if (requestObject.getConnectionCallback() != null) {
                                requestObject.getConnectionCallback().onSuccess(new DataObject()
                                        .setDeepLinkUrl(uri[0].toString()), requestObject);
                            }


                        } else {
                            Utility.Logger(tag, "Error = " + task.getException().getMessage());
                            if (requestObject.getConnectionCallback() != null) {
                                requestObject.getConnectionCallback().onError(task.getException().getMessage(), requestObject);
                            }
                        }

                        if (acProgressFlower != null && acProgressFlower.isShowing())
                            acProgressFlower.dismiss();

                        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
                            bottomSheetDialog.dismiss();

                    }
                });

    }


    /**
     * <p>It is used to send Downloading request</p>
     * @param requestObject
     */
    private void sendDownloadingRequest(final RequestObject requestObject){

        final File folder = new File(Environment.getExternalStorageDirectory(), Utility.getStringFromRes(requestObject.getContext(), R.string.app_name));

        if (!folder.exists())
            folder.mkdirs();

        int downloadId = 0;
        NotificationManager notificationManager = null;
        final String folderPath = folder.getAbsolutePath();

        String serverUrl = null;

        serverUrl = requestObject.getServerUrl();
        notificationManager = createNotification(requestObject.getContext(), String.valueOf(downloadId), requestObject.getLoadingText());
        final NotificationManager finalNotificationManager = notificationManager;

        final String fileName = requestObject.getName();

        Utility.Logger(tag, "FileName = " + fileName);

        PRDownloader.download(serverUrl, folder.getAbsolutePath(), fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        //do nothing
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        //do nothing
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        // do nothing
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        //do nothing
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        if (finalNotificationManager != null) {


                            String link = folderPath + "/" + fileName;
                            Uri uri;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                if (requestObject.isShare())
                                    uri = FileProvider.getUriForFile(requestObject.getContext(), BuildConfig.APPLICATION_ID + ".provider", new File(link));
                                else
                                    uri = Uri.fromFile(new File(link));

                            } else
                                uri = Uri.fromFile(new File(link));

                            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            scanIntent.setData(uri);
                            requestObject.getContext().sendBroadcast(scanIntent);


                        }

                    }

                    @Override
                    public void onError(Error error) {

                        Utility.Logger(tag, "Error : Connection = "
                                + error.isConnectionError() + " : Server Error = " + error.isServerError());

                        if (requestObject.getConnectionCallback() != null) {
                            requestObject.getConnectionCallback().onError(Utility.getStringFromRes(requestObject.getContext(), R.string.download_error)
                                    , requestObject);
                        }

                    }
                });

    }


    /**
     * <p>It is used to get required network request type</p>
     * @param requestObject
     * @return
     */
    private String getNetworkRequestType(RequestObject requestObject){

        if (Constant.REQUEST.valueOf(requestObject.getRequestType()) == Constant.REQUEST.GET) {
            return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getRequestType());
        } else if (Constant.REQUEST.valueOf(requestObject.getRequestType()) == Constant.REQUEST.POST) {
            return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getJson(), requestObject.getRequestType());
        } else
            return Connection.makeRequest(requestObject.getServerUrl(), requestObject.getRequestType());

    }


    /**
     * <p>It is used to send Response to callback</p>
     *
     * @param requestObject
     * @param dataObject
     */
    private void sendResponseToCallback(RequestObject requestObject,DataObject dataObject){


        if (acProgressFlower != null && acProgressFlower.isShowing())
            acProgressFlower.dismiss();

        if (bottomSheetDialog != null && bottomSheetDialog.isShowing())
            bottomSheetDialog.dismiss();

        if ( dataObject==null) {
            Utility.Logger(tag, "Empty object ");
            return;
        }

        if (Utility.isEmptyString(dataObject.getCode())) {
            Utility.Logger(tag, "No Response Code");
            return;
        }


        String responseCode = dataObject.getCode();
        String responseMessage = dataObject.getMessage();

        if (requestObject.getConnectionCallback() != null) {

            if (responseCode.equals(Constant.ErrorCodes.SUCCESS_CODE)) {
                requestObject.getConnectionCallback().onSuccess(dataObject, requestObject);
            } else if (responseCode.equals(Constant.ErrorCodes.ERROR_CODE)) {
                requestObject.getConnectionCallback().onError(responseMessage, requestObject);

            } else {
                requestObject.getConnectionCallback().onError(responseMessage, requestObject);
            }

        }


    }


    public static class CreateConnection {
        private RequestObject requestObject;

        public CreateConnection setRequestObject(RequestObject requestObject) {
            this.requestObject = requestObject;
            return this;
        }

        public ConnectionBuilder buildConnection() {
            return new ConnectionBuilder(requestObject);
        }

    }

    /**
     * <p>It is used to Create Notification
     * with Look Up action button</p>
     *
     * @param
     * @param aMessage
     * @param context
     */
    public NotificationManager createNotification(Context context, String id, String aMessage) {
        //String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.app_name); // Default Channel
        NotificationCompat.Builder builder;

        Utility.Logger(tag, "Working");

        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);

            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }

            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(context.getString(R.string.app_name) + " " + context.getString(R.string.downloading))                            // required
                    .setSmallIcon(R.drawable.app_icon)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        } else {

            builder = new NotificationCompat.Builder(context, id);
            builder.setContentTitle(context.getString(R.string.app_name) + " " + context.getString(R.string.downloading))                            // required
                    .setSmallIcon(R.drawable.app_icon)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(aMessage))
                    .setTicker(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        }

        return notifManager;
    }

    /**
     * <p>It is used to retrieve Progress Dialog object</p>
     *
     * @param context
     * @param progress
     * @return
     */
    private ACProgressFlower getACProgressFlower(Context context, String progress) {

        ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(progress)
                .textTypeface(Font.ubuntuMediumFont(context))
                .fadeColor(Color.DKGRAY).build();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;


    }


    /**
     * <p>It is used to retrieve BottomSheet Dialog </p>
     * @param context
     * @param progress
     * @return
     */
    private BottomSheetDialog getBottomSheetDialog(Context context, String progress) {

        final View view = LayoutInflater.from(context).inflate(R.layout.process_order_sheet_layout, null);

        final BottomSheetDialog universalBottomSheetDialog = new BottomSheetDialog(context);
        universalBottomSheetDialog.setContentView(view);
        universalBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        universalBottomSheetDialog.setCancelable(true);

        Utility.Logger(tag, progress);

        return universalBottomSheetDialog;

    }


}
