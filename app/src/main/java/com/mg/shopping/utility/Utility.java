package com.mg.shopping.utility;


import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mg.shopping.objectutil.GeocodeObject;
import com.mg.shopping.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.ColorInt;

public class Utility {

    private Utility(){

    }


    /**
     * <p>Show the Toast in Activity</p>
     *
     * @param context context of activity or either Fragment
     * @param message your message you want to show in Toast
     * @param length  length of Toast
     */
    public static void Toaster(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }


    /**
     * <p>Show the Message in Logcat</p>
     *
     * @param tag     tag you want to use in your logger
     * @param message message you want to show in logcat
     */
    public static void Logger(String tag, String message) {
        ///do nothing , used in case of debugging app
        Log.e(tag,message);

    }




    public static void extraData(String tag, String message) {
        int maxLogSize = 2000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;


        }
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out " + getStringFromRes(context, R.string.app_name) + " app at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>Share your app  with friend & Colleagues</p>
     *
     * @param context reference from the acitivty or fragment
     */
    public static void shareViaCopy(Context context, String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    /**
     * <p>Check the Connection status either it is available or not</p>
     *
     * @param context reference from activity or either fragment
     * @return true if internet connection available
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null ) { // connected to the internet
            // connected to the mobile provider's data plan
            return activeNetworkInfo.isConnected();
        }
        return false;
    }


    /**
     * <p>Check any specific text either it's null or not</p>
     *
     * @param text text about which we want to know about
     * @return true if text is Empty
     */
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }


    /**
     * <p>It is used to open playstore app link for rating</p>
     *
     * @param context
     */
    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


    /**
     * <p>It is used to open web url</p>
     *
     * @param context
     * @param url
     */
    public static void openWebUrl(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }


    /**
     * <p>It is used to copy textual data </p>
     *
     * @param context
     * @param text
     */
    public static void copyData(Context context, String text) {

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Place Address", text);
        clipboard.setPrimaryClip(clip);

    }


    /**
     * <p>It is used to open phone dialer with number</p>
     *
     * @param context
     * @param phone
     */
    public static void openDialer(Context context, String phone) {
        String mobileNo = phone;

        Intent intent = new Intent(Intent.ACTION_DIAL).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:" + mobileNo));
        context.startActivity(intent);
    }


    /**
     * <p>It is used to Save Image into App Private Memorys</p>
     *
     * @param bitmapImage
     * @param name
     * @return
     */
    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String name) throws FileNotFoundException {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name);

        Utility.Logger("Image Path", mypath.toString());

        FileOutputStream fos = new FileOutputStream(mypath);
        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);



        return mypath.toString();
    }


    /**
     * <p>It is used to Capitalize the Word first letter</p>
     *
     * @param capString
     * @return
     */
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }


    /**
     * <p>It is used to get Colour from Resource file</p>
     *
     * @param context
     * @param colour
     * @return
     */
    public static int getColourFromRes(Context context, int colour) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colour,null);
        }else {
            return context.getResources().getColor(colour);
        }
    }


    /**
     * <p>It is used to get String values from Resource file</p>
     *
     * @param context
     * @param label
     * @return
     */
    public static String getStringFromRes(Context context, int label) {
        if (context == null || label == 0)
            return null;
        return context.getResources().getString(label);
    }

    public static void getCrossedText(TextView view) {

        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }





    /**
     * <p>It is used to get String values from Resource file</p>
     *
     * @param context
     * @param
     * @return
     */
    public static String getDrawableFromId(Context context, int icon) {
        if (context == null || icon == 0)
            return null;
        return context.getResources().getResourceEntryName(icon);
    }





    /**
     * <p>It is used to round off value to n decimal points</p>
     *
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    /**
     * <p>It is used to dismiss Keyboard</p>
     *
     * @param context
     * @param editText
     */
    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /**
     * <p>It is used to share wallpaper with other app</p>
     *
     * @param context
     * @param uri
     */
    public static void shareViaUri(Context context, Uri uri) {

        Utility.Logger("Sharing Uri", uri.toString());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share with"));

    }


    /**
     * <p>It is used to get calculated values of counters></p>
     *
     * @param counter
     * @return
     */
    public static String getCalculatedValue(String counter) {
        int count = Integer.parseInt(counter);
        double calculatedAmount;
        String k;

        if (count > 999) {
            calculatedAmount = (double)count / 1000;
            k = calculatedAmount + "k";
        } else {
            calculatedAmount = count;
            k = String.valueOf((int) calculatedAmount);
        }

        return k;
    }


    /**
     * <p>It is used to check Numeric</p>
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("[+-]?\\d*(\\.\\d+)?");
    }


    /**
     * <p>It is used to convert dp into px</p>
     *
     * @param i
     * @return
     */
    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }



    public static String encodeUrl(String url) {
        String encodedText = null;

        try {
            encodedText = URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedText;
    }

    public static boolean isImage(String file) {
        return file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".jpeg") || file.endsWith(".gif");

    }

    public static boolean isVideo(String file) {

        return file.endsWith(".mp4") || file.endsWith(".avi") || file.endsWith(".mkv");

    }


    /**
     * <p>It is used to get Color from Attribute</p>
     *
     * @param context
     * @param attr
     * @return
     */
    public static int getAttrColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        @ColorInt int color = typedValue.data;
        return color;
    }


    /**
     * <p>It is used to Playstore Url</p>
     *
     * @param context
     * @return
     */
    public static String getAppPlaystoreUrl(Context context) {
        final String appPackageName = context.getPackageName();
        return "https://play.google.com/store/apps/details?id=" + appPackageName;
    }


    /**
     * <p>It is used to get numeric data from Stirng</p>
     *
     * @param str
     * @return
     */
    public static String extractNumericDataFromString(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }


    /**
     * <p>It is used to mask some few characters of Card No</p>
     *
     * @param cardNo
     * @return
     */
    public static String maskSomeCharacter(String cardNo) {

        StringBuilder cardNoBuilder = new StringBuilder();

        for (int j = 0; j < cardNo.length(); j++) {
            if (j < (cardNo.length() - 3)) {
                cardNoBuilder.append("\u002A");
            } else {
                cardNoBuilder.append(cardNo.charAt(j));
            }
        }

        return cardNoBuilder.toString();
    }


    /**
     * <p>It is used to get Geo Code Object</p>
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static GeocodeObject getGeoCodeObject(Context context, double latitude, double longitude) {
        GeocodeObject geocodeObject = null;
        List<Address> addresses = null;
        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses == null || addresses.isEmpty()) {
                return geocodeObject;
        }


        geocodeObject = new GeocodeObject()
                .setAddress(addresses.get(0).getAddressLine(0))  // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                .setCity(addresses.get(0).getLocality() == null ? addresses.get(0).getAdminArea() : addresses.get(0).getLocality())
                .setState(addresses.get(0).getAdminArea())
                .setCountry(addresses.get(0).getCountryName())
                .setPostalCode(addresses.get(0).getPostalCode())
                .setCountryCode(addresses.get(0).getCountryCode())
                .setKnownName(addresses.get(0).getFeatureName());   // Only if available else return NULL

        return geocodeObject;

    }





    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }


}
