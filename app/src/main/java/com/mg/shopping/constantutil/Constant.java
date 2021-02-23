package com.mg.shopping.constantutil;

import com.mg.shopping.BuildConfig;


public class Constant {


    public enum CONNECTION {
        LIST_OF_CITIES ,
        RETRIEVE_HOME,
        RETRIEVE_CATEGORIES,
        RETRIEVE_SUB_CATEGORIES,
        LIST_OF_CATEGORIES,
        RETRIEVE_CATEGORIZED_PRODUCTS,
        RETRIEVE_SPECIFIC_PRODUCT,
        RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS,
        RETRIEVE_SPECIFIC_QUESTION_ANSWER,
        RETRIEVE_SPECIFIC_PRODUCT_REVIEWS,
        ADD_PRODUCT_INTO_CART,
        RETRIEVE_PRODUCT_OF_CART,
        RETRIEVE_PRODUCT_IN_BILLING,
        RETRIEVE_SHIPPING_RATES,
        LIST_OF_BRAND,
        RETRIEVE_SPECIFIC_STORE_DETAIL,
        RETRIEVE_SPECIFIC_STORE_PRODUCT,
        RETRIEVE_FEATURED_CATEGORIES,
        RETRIEVE_FEATURED_CATEGORIES_PRODUCT,
        RETRIEVE_HOT_DATA,
        RETRIEVE_SALE_DATA,
        RETRIEVE_COUPON_DATA,
        RETRIEVE_ALL_BRANDS,
        RETRIEVE_FEATURED_PRODUCT_DETAIL,
        RETRIEVE_FEATURED_PRODUCTS,
        RETRIEVE_HOT_SEARCHES,
        RETRIEVE_FAVOURITES_BRAND,
        RETRIEVE_FAVOURITES_PRODUCT,
        RETRIEVE_SEARCH_PRODUCT,
        CHANGE_FAVOURITE_STATUS,
        CHANGE_FOLLOW_STATUS,
        CHANGE_PRODUCT_QUANTITY_DELETE,
        SAVE_ADDRESS,
        RETRIEVE_SPECIFIC_USER_ADDRESS,
        REDEEM_COUPON,
        RETRIEVE_AVAILABLE_SHIPPING_SERVICES,
        ASK_QUESTION_OF_SPECIFIC_PRODUCT,
        ADD_ANSWER_INTO_QUESTION,
        PROCEED_CHECKOUT,
        RETRIEVE_ORDER_HISTORY,
        ADD_REVIEWS,
        RETRIEVE_REVIEW_RATING,
        SAVE_BILLING_CARD,
        RETRIEVE_SPECIFIC_USER_BILLING_CARD,
        ADD_REFUND_REQUEST,
        RETRIEVE_SPECIFIC_ORDER_REFUND,
        RETRIEVE_SPECIFIC_USER_REPORT,
        ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST,
        RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK,
        DELETE_SPECIFIC_ADDRESS_BOOK,
        RETRIEVE_SPECIFIC_USER_REVIEWS_RATING,
        DELETE_SPECIFIC_USER_REVIEWS,
        CONTACT_US,
        SIGN_UP,
        LOGIN,
        UPDATE_PROFILE,
        RETRIEVE_PROFILE,
        DELETE_ITEM_FROM_CART,
        PRIVACY_POLICY,
        ABOUT_US,



        LIST_OF_CATEGORIES_SELECTION,
        LIST_OF_PAYMENT_METHODS,
        LIST_OF_ADDED_PAYMENT_METHODS,
        LIST_OF_ADDED_PAYMENT_METHODS_HOME,
        LIST_OF_TRANSACTION,
        LIST_OF_STATISTICS,
        LIST_OF_GOAL_TRANSACTION ,
        LIST_OF_CATEGORY_BUDGET ,
        LIST_OF_GOAL ,
        LIST_OF_COUNTRIES ,
        LIST_OF_CURRENCIES ,
        PICTURE_SELECTOR,
        REPORT,
        PRODUCT_DEEPLINK ,

    }

    public enum DATA_TYPE {

    }

    public enum CONNECTION_TYPE {BACKGROUND, UI, DOWNLOAD, BUILD_DEEPLINK}

    public enum LOADING_TYPE {DIALOG, BOTTOM_SHEET}

    public enum REQUEST {
        GET, POST
    }


    /**
     * <p>It contain all Server Url</p>
     */
    public static class ServerInformation {

        private ServerInformation(){

        }

        public static final String GOOGLE_DRIVE_LINK = "https://docs.google.com/uc?id=";
        public static final String PRIVACY_URL = "https://docs.google.com/document/d/";
        public static final String DEEP_LINK_URL = BuildConfig.DYNAMIC_URL;

        static final String BASE_URL = BuildConfig.BASE_URL;
        static final String FOLDER = "/alixpress_app/";

        public static final String REST_API_URL = BASE_URL + FOLDER+"alixpress_app.php";
        public static final String PICTURE_URL = BASE_URL + FOLDER+"admin/images/";
        public static final String CATEGORY_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/app_category/";
        public static final String PRODUCT_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/product_image/";
        public static final String USER_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/user_image/";
        public static final String REVIEW_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/review_image/";
        public static final String FEATURED_BANNER_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/featured_banner_image/";
        public static final String SHIPPING_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/shipping_image/";
        public static final String REFUND_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/refund_image/";
        public static final String BRAND_PICTURE_URL = BASE_URL + FOLDER+"admin/uploads/image/brand_image/";

        public static final String CREATE_DEEP_LINK_URL = BASE_URL + FOLDER+"alixpress_app.php?id=%s&name=%s&price=%s&rating=%s";



    }


    /**
     * <p>It contain all of the Credentials </p>
     */
    public static class Credentials {

        private Credentials(){

        }

        public static final boolean FB_HASH_KEY_REQUIRED = true;
        public static final boolean ADMOB_BANNER_REQUIRED = false;
        static String deviceToken = "null";
        public static final String PUBLISHER_ID = BuildConfig.PUBLISHER_ID;
        public static final String ADMOB_APP_ID = BuildConfig.ADMOB_APP_ID;
        public static final String ADMOB_BANNER_ID = BuildConfig.ADMOB_BANNER_ID;
        public static final String ADMOB_INTERSTITIAL_ID = BuildConfig.ADMOB_INTERSTITIAL_ID;
        public static final String ADMOB_TEST_DEVICE_ID = BuildConfig.ADMOB_TEST_DEVICE_ID;
        public static final String ADMOB_PRIVACY_URL = BuildConfig.ADMOB_PRIVACY_URL;


        public static void setDeviceToken(String deviceToken) {
            Credentials.deviceToken = deviceToken;
        }

    }


    /**
     * <p>It contain all of the Important Messages</p>
     */
    public static class ImportantMessages {

        private ImportantMessages(){

        }

        public static final String CONNECTION_ERROR = "Connection Error";

    }


    /**
     * <p>It contain all of the Key of Share Preferences</p>
     */
    public static class SharedPref {

        private SharedPref(){

        }

        public static final String FIRST_LAUNCH = "FIRST_LAUNCH";
        public static final String LOGIN = "LOGIN";
        public static final String USER_ID = "USER_ID";
        public static final String USER_REMEMBER = "USER_REMEMBER";
        public static final String USER_PROFILE_NAME = "USER_PROFILE_NAME";
        public static final String USER_PICTURE = "USER_PICTURE";
        public static final String LOGIN_TYPE = "LOGIN_TYPE";
        public static final String USER_EMAIL = "USER_EMAIL";
        public static final String USER_PASSWORD = "USER_PASSWORD";

        public static final String COUNTRY_ID = "COUNTRY_ID";
        public static final String CURRENCY_CODE = "CURRENCY_CODE";
        public static final String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";


    }


    /**
     * <p>It contain all of the Request Code</p>
     */
    public static class RequestCode {

        private RequestCode(){

        }

        public static final int ADDRESS_REQUEST = 5;
        public static final int REQUEST_CODE_BILLING_CARD = 10;
        public static final int LOGIN_CODE = 11;
        public static final int PROFILE_UPDATE = 12;

    }


    /**
     * <p>It contain all of the Key of Intent Sharing</p>
     */
    public static class IntentKey {

        private IntentKey(){

        }

        public static final String REQUEST_OBJECT = "REQUEST_OBJECT";
        public static final String DATA_OBJECT = "DATA_OBJECT";
        public static final String PRODUCT_OBJECT = "PRODUCT_OBJECT";
        public static final String ID = "ID";
        public static final String BRAND_ID = "BRAND_ID";
        public static final String PRODUCT_ID = "PRODUCT_ID";
        public static final String RATING = "RATING";
        public static final String TYPE = "TYPE";
        public static final String REFUND_REQUEST_ID = "REFUND_REQUEST_ID";
        public static final String LIST="LIST";
    }


    /**
     * <p>It contain al lof the database columns</p>
     */
    public static class DatabaseColumn {

        private DatabaseColumn(){

        }

        public static final String tag = "Database";

        public static final String FAVOURITES_TABLE_NAME = "Favourites";
        public static final String FAVOURITES_COLUMN_ID = "id";
        public static final String FAVOURITES_COLUMN_USER_ID = "user_id";
        public static final String FAVOURITES_COLUMN_OBJECT_ID = "object_id";
        public static final String FAVOURITES_COLUMN_OBJECT_TYPE = "object_type";

        public static final String BRAND_TABLE_NAME = "Following";
        public static final String BRAND_COLUMN_ID = "id";
        public static final String BRAND_COLUMN_USER_ID = "user_id";
        public static final String BRAND_COLUMN_OBJECT_ID = "object_id";


    }




    /**
     * <p>It contain all of error code which may come from Server Request</p>
     */
    public static class ErrorCodes {

        private ErrorCodes(){

        }

        public static final String SUCCESS_CODE = "202";
        public static final String ERROR_CODE = "206";
    }


}
