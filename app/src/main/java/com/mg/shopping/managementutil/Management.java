package com.mg.shopping.managementutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import com.mg.shopping.connectionutil.ConnectionBuilder;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.databaseutil.DatabaseHandler;
import com.mg.shopping.databaseutil.DatabaseObject;
import com.mg.shopping.databaseutil.DbConstraint;
import com.mg.shopping.databaseutil.QueryFactory;
import com.mg.shopping.databaseutil.QueryRunner;
import com.mg.shopping.objectutil.CursorObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.preferenceutil.UserSharedPreferenceObserver;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import java.util.ArrayList;
import java.util.List;

public class Management extends ManagementBase {
    private String tag = Management.class.getName();
    private Context context;
    QueryRunner queryRunner;
    QueryFactory queryFactory;
    SQLiteOpenHelper sqLiteOpenHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UserSharedPreferenceObserver userSharedPref;
    PrefObject prefObject;


    public Management(Context context) {

        Utility.Logger(tag, "Setting : Working");

        this.context = context;
        prefObject = new PrefObject();
        queryFactory = new QueryFactory();
        queryRunner = new QueryRunner(context);
        sharedPreferences = context.getSharedPreferences(Utility.getStringFromRes(context, R.string.app_name), Context.MODE_PRIVATE);
        userSharedPref = new UserSharedPreferenceObserver(prefObject, sharedPreferences);
        prefObject.notifyAllObserver();


    }


    /**
     * <p>It is used to send Request to Server</p>
     *
     * @param requestObject
     */
    @Override
    public void sendRequestToServer(RequestObject requestObject) {

        String serverUrl = null;
        String requestType = Constant.REQUEST.POST.toString();

        if (requestObject.getContext() != null) {
            context = requestObject.getContext();
        }

        Utility.Logger(tag, "RequestObject : " + requestObject.toString());

        //region All functionality of giving Server Url

        if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.UI) {

            if (requestObject.getConnection() == Constant.CONNECTION.LIST_OF_CITIES
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_CATEGORIES
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SUB_CATEGORIES
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_QUESTION_ANSWER
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_REVIEWS
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_PRODUCT_INTO_CART
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_PRODUCT_OF_CART
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_PRODUCT_IN_BILLING
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SHIPPING_RATES
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_DETAIL
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_FEATURED_CATEGORIES_PRODUCT
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_HOT_DATA
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SALE_DATA
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_COUPON_DATA
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_ALL_BRANDS
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_FEATURED_PRODUCT_DETAIL
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_FEATURED_PRODUCTS
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_HOT_SEARCHES
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_FAVOURITES_BRAND
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_FAVOURITES_PRODUCT
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SEARCH_PRODUCT
                    || requestObject.getConnection() == Constant.CONNECTION.SAVE_ADDRESS
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS
                    || requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_AVAILABLE_SHIPPING_SERVICES
                    || requestObject.getConnection() == Constant.CONNECTION.ASK_QUESTION_OF_SPECIFIC_PRODUCT
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_ANSWER_INTO_QUESTION
                    || requestObject.getConnection() == Constant.CONNECTION.PROCEED_CHECKOUT
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_ORDER_HISTORY
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_REVIEWS
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_REVIEW_RATING
                    || requestObject.getConnection() == Constant.CONNECTION.SAVE_BILLING_CARD
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_BILLING_CARD
                    || requestObject.getConnection() == Constant.CONNECTION.ADD_REFUND_REQUEST
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_ORDER_REFUND
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REPORT
                    || requestObject.getConnection() == Constant.CONNECTION.ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK
                    || requestObject.getConnection() == Constant.CONNECTION.DELETE_SPECIFIC_ADDRESS_BOOK
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REVIEWS_RATING
                    || requestObject.getConnection() == Constant.CONNECTION.DELETE_SPECIFIC_USER_REVIEWS
                    || requestObject.getConnection() == Constant.CONNECTION.CONTACT_US
                    || requestObject.getConnection() == Constant.CONNECTION.SIGN_UP
                    || requestObject.getConnection() == Constant.CONNECTION.LOGIN
                    || requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_PROFILE
                    || requestObject.getConnection() == Constant.CONNECTION.UPDATE_PROFILE
                    || requestObject.getConnection() == Constant.CONNECTION.PRIVACY_POLICY
                    || requestObject.getConnection() == Constant.CONNECTION.ABOUT_US) {

                serverUrl = Constant.ServerInformation.REST_API_URL;

            }


        } else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BACKGROUND) {

            if (requestObject.getConnection() == Constant.CONNECTION.CHANGE_FAVOURITE_STATUS
                    || requestObject.getConnection() == Constant.CONNECTION.CHANGE_FOLLOW_STATUS
                    || requestObject.getConnection() == Constant.CONNECTION.CHANGE_PRODUCT_QUANTITY_DELETE
                    || requestObject.getConnection() == Constant.CONNECTION.DELETE_ITEM_FROM_CART) {

                serverUrl = Constant.ServerInformation.REST_API_URL;

            }


        } else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.DOWNLOAD) {

            serverUrl = "";

        } else if (requestObject.getConnectionType() == Constant.CONNECTION_TYPE.BUILD_DEEPLINK &&
                requestObject.getConnection() == Constant.CONNECTION.PRODUCT_DEEPLINK) {

                serverUrl = String.format(Constant.ServerInformation.CREATE_DEEP_LINK_URL,
                        requestObject.getId()
                        , requestObject.getName()
                        , requestObject.getPrice()
                        , requestObject.getRating());

        }

        //endregion

        //It initialize the Connection to Server

        new ConnectionBuilder.CreateConnection()
                .setRequestObject(requestObject
                        .setContext(context)
                        .setServerUrl(serverUrl)
                        .setRequestType(requestType))
                .buildConnection();


    }


    /**
     * <p>It is used to retrieve data from Database</p>
     *
     * @param databaseObject
     */
    @Override
    public List<Object> getDataFromDatabase(DatabaseObject databaseObject) {
        List<Object> objectArrayList = new ArrayList<>();
        CursorObject cursorObject = null;

        //Get required Query for Specific Db Operation

        String formattedQuery = queryFactory.getRequiredFormattedQuery(databaseObject);
        Utility.extraData(tag, "Setting : Working : Query = " + formattedQuery);

        //If Query Empty then return Empty Arraylist

        if (Utility.isEmptyString(formattedQuery))
            return objectArrayList;

        sqLiteOpenHelper = new DatabaseHandler(context);  //Initialize Database Handler


        //Perform Db Operation on formatted Sql Queries

        if (databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_STORE
                || databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_PRODUCT
                || databaseObject.getDbOperation() == DbConstraint.RETRIEVE_SPECIFIC_BRAND_FOLLOWING) {

            objectArrayList.addAll(queryRunner.getAll(formattedQuery, sqLiteOpenHelper, databaseObject));

        } else if (databaseObject.getDbOperation() == DbConstraint.INSERT_NEW_FAVOURITES_STORE
                || databaseObject.getDbOperation() == DbConstraint.INSERT_NEW_FAVOURITES_PRODUCT
                || databaseObject.getDbOperation() == DbConstraint.INSERT_NEW_BRAND_FOLLOWING
                || databaseObject.getDbOperation() == DbConstraint.DELETE_SPECIFIC_USER_FAVOURITES
                || databaseObject.getDbOperation() == DbConstraint.DELETE_SPECIFIC_USER_FOLLOWING) {

            cursorObject = queryRunner.getStatus(formattedQuery, sqLiteOpenHelper);
            if (cursorObject != null && cursorObject.getDatabase() != null) {
                    cursorObject.getDatabase().close();

            }

        }


        return objectArrayList;
    }


    /**
     * <p>It is used to get Preference Data</p>
     *
     * @param prefObject
     * @return
     */
    @Override
    public PrefObject getPreferences(PrefObject prefObject) {
        PrefObject pref = userSharedPref.getPreference();

        Utility.Logger(tag, "getPreference = " + pref.toString());

        return pref;
    }


    /**
     * <p>It is used to save Preferences data</p>
     *
     * @param prefObject
     */
    @Override
    public void savePreferences(PrefObject prefObject) {
        editor = sharedPreferences.edit();

        Utility.Logger(tag, "Preference = " + prefObject.toString());

        if (prefObject.isSaveFirstLaunch()) {

            editor.putBoolean(Constant.SharedPref.FIRST_LAUNCH, prefObject.isFirstLaunch());

        } else if (prefObject.isSaveLogin()) {

            editor.putBoolean(Constant.SharedPref.LOGIN, prefObject.isLogin());

        } else if (prefObject.isSaveUserId()) {

            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());

        } else if (prefObject.isSaveUserRemember()) {

            editor.putBoolean(Constant.SharedPref.USER_REMEMBER, prefObject.isUserRemember());

        } else if (prefObject.isSaveUserCredential()) {

            editor.putString(Constant.SharedPref.USER_ID, prefObject.getUserId());
            editor.putString(Constant.SharedPref.USER_PICTURE, prefObject.getPictureUrl());
            editor.putString(Constant.SharedPref.USER_PROFILE_NAME, prefObject.getUserName());
            editor.putString(Constant.SharedPref.LOGIN_TYPE, prefObject.getLoginType());
            editor.putString(Constant.SharedPref.USER_EMAIL, prefObject.getUserEmail());
            editor.putString(Constant.SharedPref.USER_PASSWORD, prefObject.getUserPassword());

        } else if (prefObject.isSaveCountryInformation()) {

            editor.putString(Constant.SharedPref.COUNTRY_ID, prefObject.getCountryId());
            editor.putString(Constant.SharedPref.CURRENCY_CODE, prefObject.getCurrencyCode());
            editor.putString(Constant.SharedPref.CURRENCY_SYMBOL, prefObject.getCurrencySymbol());

        } else if (prefObject.isSaveUserPicture()) {

            editor.putString(Constant.SharedPref.USER_PICTURE, prefObject.getPictureUrl());

        } else if (prefObject.isSaveUserName()) {

            editor.putString(Constant.SharedPref.USER_PROFILE_NAME, prefObject.getUserName());


        }

        editor.commit();
        this.prefObject.notifyAllObserver();


    }



}
