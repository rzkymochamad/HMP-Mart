package com.mg.shopping.objectutil;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.jsonutil.aboutusutil.AboutUsJson;
import com.mg.shopping.jsonutil.addbillingaddressutil.AddBillingAddressJson;
import com.mg.shopping.jsonutil.addbillingcard.AddBillingCardJson;
import com.mg.shopping.jsonutil.addtocartutil.AddToCartJson;
import com.mg.shopping.jsonutil.allcardutil.AllCardJson;
import com.mg.shopping.jsonutil.allcategoryutil.AllCategoryJson;
import com.mg.shopping.jsonutil.allcategoryutil.Brand;
import com.mg.shopping.jsonutil.allcategoryutil.DatumDetail;
import com.mg.shopping.jsonutil.allsubcategoriesutil.AllSubCategoriesJson;
import com.mg.shopping.jsonutil.answerutil.AnswerJson;
import com.mg.shopping.jsonutil.availableshippingserviceutil.AvailableShippingServiceJson;
import com.mg.shopping.jsonutil.billingaddressutil.BillingAddressJson;
import com.mg.shopping.jsonutil.billingproductutil.BillingProductJson;
import com.mg.shopping.jsonutil.cartproductutil.CartProductJson;
import com.mg.shopping.jsonutil.cityjson.CityJson;
import com.mg.shopping.jsonutil.favouritebrandutil.FavouriteBrandJson;
import com.mg.shopping.jsonutil.favouriteproductutil.FavouriteProductJson;
import com.mg.shopping.jsonutil.featuredcategoriesproductutil.FeaturedCategoriesProductJson;
import com.mg.shopping.jsonutil.featuredcategoriesutil.FeaturedCategoriesJson;
import com.mg.shopping.jsonutil.featuredproductutil.FeaturedProductJson;
import com.mg.shopping.jsonutil.generalresponseutil.GeneralResponseJson;
import com.mg.shopping.jsonutil.hotdatautil.HotDataJson;
import com.mg.shopping.jsonutil.hotsearchesutil.HotSearchesJson;
import com.mg.shopping.jsonutil.listofbrandutil.ListOfBrandJson;
import com.mg.shopping.jsonutil.listofcouponutil.ListOfCouponJson;
import com.mg.shopping.jsonutil.listofproductutil.ListOfProductJson;
import com.mg.shopping.jsonutil.loginuserutil.LoginUserJson;
import com.mg.shopping.jsonutil.orderhistoryutil.HistoryOrderJson;
import com.mg.shopping.jsonutil.productratingutil.ProductRatingJson;
import com.mg.shopping.jsonutil.redeemcouponutil.RedeemCouponJson;
import com.mg.shopping.jsonutil.saleproductutil.SaleProductJson;
import com.mg.shopping.jsonutil.shippingrateutil.ShippingRateJson;
import com.mg.shopping.jsonutil.specificbrandproductutil.SpecificBrandProductJson;
import com.mg.shopping.jsonutil.specificorderrefundutil.SpecificOrderRefundJson;
import com.mg.shopping.jsonutil.specificproductquestionsutil.SpecificProductQuestionJson;
import com.mg.shopping.jsonutil.specificproductutil.SpecificProductJson;
import com.mg.shopping.jsonutil.specificreviewutil.SpecificReviewJson;
import com.mg.shopping.jsonutil.specificuseraddressbookutil.SpecificUserAddressBookJson;
import com.mg.shopping.jsonutil.specificuserrefundutil.SpecificUserRefundJson;
import com.mg.shopping.jsonutil.stylejsonutil.Datum;
import com.mg.shopping.jsonutil.userprofileutil.UserProfileJson;
import com.mg.shopping.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class DataObject implements Parcelable {

    /* Variable for Connection Builder */

    static String tag = DataObject.class.getName();
    private boolean selection;
    private boolean dialog;

    /* Variable for Debugging */

    private String code;
    private String message;
    private String deepLinkUrl;
    private String id;
    private String type;
    private boolean lastItem;


    private String productId;
    private String questionId;
    private String question;
    private String answerCount;
    private String name;
    private String price;
    private String rating;
    private boolean isDeeplink;
    private ArrayList<Brand> brandArrayList = new ArrayList<>();

    private String userId;


    /* Variable for Home */

    private ArrayList<Datum> data = new ArrayList<>();


    /* Variable for category */

    private String categoryId;
    private String categoryName;
    private String categoryImage;
    private String categoryType;
    private ArrayList<DatumDetail> subCategoryList = new ArrayList<>();

    /* Variable for Sub Category */

    private String subCategoryId;
    private String subCategoryName;


    /* Variable for Picture Selector */

    private Bitmap picture;
    private String userPicture;


    /* Variable for Data Type */

    private Constant.CONNECTION dataType;
    private ArrayList<DataObject> objectArrayList = new ArrayList<>();
    private ArrayList<Object> objectList = new ArrayList<>();
    private ArrayList<Object> objectList02 = new ArrayList<>();
    private ArrayList<Object> objectList03 = new ArrayList<>();
    private ArrayList<Object> objectList04 = new ArrayList<>();

    /* Variable for Currency */


    public DataObject() {
    }


    public boolean isSelection() {
        return selection;
    }

    public DataObject setSelection(boolean selection) {
        this.selection = selection;
        return this;
    }

    public boolean isDialog() {
        return dialog;
    }

    public DataObject setDialog(boolean dialog) {
        this.dialog = dialog;
        return this;
    }

    /* Getter/Setter for Code/Message */


    public String getCode() {
        return code;
    }

    public DataObject setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DataObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getName() {
        return name;
    }

    public DataObject setName(String name) {
        this.name = name;
        return this;
    }

    public List<Brand> getBrandArrayList() {
        return brandArrayList;
    }

    public DataObject setBrandArrayList(List<Brand> brandArrayList) {
        this.brandArrayList.addAll(brandArrayList);
        return this;
    }

    /* Getter/Setter for category */


    public String getCategoryId() {
        return categoryId;
    }

    public DataObject setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public DataObject setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public DataObject setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
        return this;
    }


    public String getCategoryType() {
        return categoryType;
    }

    public DataObject setCategoryType(String categoryType) {
        this.categoryType = categoryType;
        return this;
    }


    public List<DatumDetail> getSubCategoryList() {
        return subCategoryList;
    }

    public DataObject setSubCategoryList(List<DatumDetail> subCategoryList) {
        this.subCategoryList.addAll(subCategoryList);
        return this;
    }

    /* Getter/Setter for SubCategory */


    public String getSubCategoryId() {
        return subCategoryId;
    }

    public DataObject setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
        return this;
    }


    public DataObject setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
        return this;
    }






    /* Getter/Setter for Picture Selector */


    public Bitmap getPicture() {
        return picture;
    }

    public DataObject setPicture(Bitmap picture) {
        this.picture = picture;
        return this;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public DataObject setUserPicture(String userPicture) {
        this.userPicture = userPicture;
        return this;
    }


    public String getDeepLinkUrl() {
        return deepLinkUrl;
    }

    public DataObject setDeepLinkUrl(String deepLinkUrl) {
        this.deepLinkUrl = deepLinkUrl;
        return this;
    }

    public String getId() {
        return id;
    }

    public DataObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public DataObject setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isLastItem() {
        return lastItem;
    }

    public DataObject setLastItem(boolean lastItem) {
        this.lastItem = lastItem;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public DataObject setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getQuestionId() {
        return questionId;
    }

    public DataObject setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public DataObject setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public DataObject setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public DataObject setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public DataObject setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public boolean isDeeplink() {
        return isDeeplink;
    }

    public DataObject setDeeplink(boolean deeplink) {
        isDeeplink = deeplink;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DataObject setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Constant.CONNECTION getDataType() {
        return dataType;
    }

    public DataObject setDataType(Constant.CONNECTION dataType) {
        this.dataType = dataType;
        return this;
    }


    public List<DataObject> getObjectArrayList() {
        return objectArrayList;
    }

    public DataObject setObjectArrayList(List<DataObject> objectArrayList) {
        this.objectArrayList.addAll(objectArrayList);
        return this;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public DataObject setObjectList(List<Object> objectList) {
        this.objectList.addAll(objectList);
        return this;
    }

    public List<Object> getObjectList02() {
        return objectList02;
    }

    public DataObject setObjectList02(List<Object> objectList02) {
        this.objectList02.addAll(objectList02);
        return this;
    }

    public List<Object> getObjectList03() {
        return objectList03;
    }

    public DataObject setObjectList03(List<Object> objectList03) {
        this.objectList03.addAll(objectList03);
        return this;
    }

    public List<Object> getObjectList04() {
        return objectList04;
    }

    public DataObject setObjectList04(List<Object> objectList04) {
        this.objectList04.addAll(objectList04);
        return this;
    }

    public static DataObject getDataObject(RequestObject requestObject, String data) {

        ArrayList<Object> objectArrayList = new ArrayList<>();
        ArrayList<Object> objectArrayList02 = new ArrayList<>();
        ArrayList<Object> objectArrayList03 = new ArrayList<>();
        ArrayList<Object> objectArrayList04 = new ArrayList<>();
        Gson gson = new Gson();

        switch (requestObject.getConnection()) {
            case LIST_OF_CITIES:

                CityJson cityJson = gson.fromJson(data, CityJson.class);
                objectArrayList.addAll(cityJson.getConfigurations());

                return new DataObject().setCode(cityJson.getCode())
                        .setMessage(cityJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_CATEGORIES:

                AllCategoryJson allCategoriesJson = gson.fromJson(data, AllCategoryJson.class);
                objectArrayList.addAll(allCategoriesJson.getListOfData());

                return new DataObject()
                        .setCode(allCategoriesJson.getCode())
                        .setMessage("Retrieve Successfully")
                        .setObjectList(objectArrayList);

            case RETRIEVE_CATEGORIZED_PRODUCTS:
            case RETRIEVE_SPECIFIC_STORE_PRODUCT:

                ListOfProductJson productJson = gson.fromJson(data, ListOfProductJson.class);
                objectArrayList.addAll(productJson.getListOfData());

                return new DataObject()
                        .setCode(productJson.getCode())
                        .setMessage(productJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_PRODUCT:
            case RETRIEVE_FEATURED_PRODUCT_DETAIL:


                SpecificProductJson specificProductJson = gson.fromJson(data, SpecificProductJson.class);
                objectArrayList.addAll(specificProductJson.getListOfData());
                return new DataObject()
                        .setCode(specificProductJson.getCode())
                        .setMessage(specificProductJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS:
            case ASK_QUESTION_OF_SPECIFIC_PRODUCT:


                SpecificProductQuestionJson questionJson = gson.fromJson(data, SpecificProductQuestionJson.class);
                objectArrayList.addAll(questionJson.getListOfData());

                return new DataObject()
                        .setCode(questionJson.getCode())
                        .setMessage(questionJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_QUESTION_ANSWER:
            case ADD_ANSWER_INTO_QUESTION:

                AnswerJson answerJson = gson.fromJson(data, AnswerJson.class);
                objectArrayList.addAll(answerJson.getAnswerList());

                return new DataObject()
                        .setCode(answerJson.getCode())
                        .setMessage(answerJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_PRODUCT_REVIEWS:
            case RETRIEVE_SPECIFIC_USER_REVIEWS_RATING:

                ProductRatingJson productRatingJson = gson.fromJson(data, ProductRatingJson.class);
                objectArrayList.addAll(productRatingJson.getListOfData());

                return new DataObject()
                        .setCode(productRatingJson.getCode())
                        .setMessage(productRatingJson.getMessage())
                        .setObjectList(objectArrayList);

            case ADD_PRODUCT_INTO_CART:

                AddToCartJson addToCartJson = gson.fromJson(data, AddToCartJson.class);

                return new DataObject()
                        .setCode(addToCartJson.getCode())
                        .setMessage(addToCartJson.getMessage());

            case RETRIEVE_PRODUCT_OF_CART:

                CartProductJson cartProductJson = gson.fromJson(data, CartProductJson.class);
                for (int i = 0; i < cartProductJson.getListOfData().size(); i++) {

                    objectArrayList.add(cartProductJson.getListOfData().get(i)
                            .setSelection(cartProductJson.getListOfData().get(i).getSelector().equalsIgnoreCase("0")));
                }

                return new DataObject()
                        .setCode(cartProductJson.getCode())
                        .setMessage(cartProductJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_PRODUCT_IN_BILLING:

                BillingProductJson billingProductJson = gson.fromJson(data, BillingProductJson.class);
                objectArrayList.addAll(billingProductJson.getPaymentMethods());
                objectArrayList02.addAll(billingProductJson.getListOfData());
                objectArrayList03.addAll(billingProductJson.getBillingAddress());

                return new DataObject()
                        .setCode(billingProductJson.getCode())
                        .setMessage(billingProductJson.getMessage())
                        .setObjectList(objectArrayList)
                        .setObjectList02(objectArrayList02)
                        .setObjectList03(objectArrayList03);

            case RETRIEVE_SHIPPING_RATES:

                ShippingRateJson shippingRateJson = gson.fromJson(data, ShippingRateJson.class);
                objectArrayList.addAll(shippingRateJson.getShippingList());

                return new DataObject()
                        .setCode(shippingRateJson.getCode())
                        .setMessage(shippingRateJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_STORE_DETAIL:

                SpecificBrandProductJson specificBrandProductJson = gson.fromJson(data, SpecificBrandProductJson.class);
                objectArrayList.addAll(specificBrandProductJson.getListOfData());
                objectArrayList02.addAll(specificBrandProductJson.getListOfLatestData());
                objectArrayList03.addAll(specificBrandProductJson.getListOfSaleData());
                objectArrayList04.addAll(specificBrandProductJson.getListOfCategories());

                return new DataObject()
                        .setCode(specificBrandProductJson.getCode())
                        .setMessage(specificBrandProductJson.getMessage())
                        .setObjectList(objectArrayList)
                        .setObjectList02(objectArrayList02)
                        .setObjectList03(objectArrayList03)
                        .setObjectList04(objectArrayList04);

            case RETRIEVE_FEATURED_CATEGORIES:

                FeaturedCategoriesJson featuredCategoriesJson = gson.fromJson(data, FeaturedCategoriesJson.class);
                objectArrayList.addAll(featuredCategoriesJson.getListOfData());

                return new DataObject()
                        .setCode(featuredCategoriesJson.getCode())
                        .setMessage(featuredCategoriesJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_FEATURED_CATEGORIES_PRODUCT:

                ArrayList<Object> categoriesList = new ArrayList<>();
                FeaturedCategoriesProductJson featuredCategoriesProductJson = gson.fromJson(data, FeaturedCategoriesProductJson.class);

                objectArrayList.addAll(featuredCategoriesProductJson.getListOfData());
                categoriesList.addAll(featuredCategoriesProductJson.getListOfCategories());

                return new DataObject()
                        .setCode(featuredCategoriesProductJson.getCode())
                        .setMessage(featuredCategoriesProductJson.getMessage())
                        .setObjectList(objectArrayList)
                        .setObjectList02(categoriesList);

            case RETRIEVE_HOT_DATA:

                ArrayList<Object> featuredBannerList = new ArrayList<>();
                ArrayList<Object> featuredBrandList = new ArrayList<>();
                HotDataJson hotDataJson = gson.fromJson(data, HotDataJson.class);

                objectArrayList.addAll(hotDataJson.getListOfData());
                featuredBannerList.addAll(hotDataJson.getListOfFeatured());
                featuredBrandList.addAll(hotDataJson.getListOfBrand());

                return new DataObject()
                        .setCode(hotDataJson.getCode())
                        .setMessage(hotDataJson.getMessage())
                        .setObjectList(objectArrayList)
                        .setObjectList02(featuredBannerList)
                        .setObjectList03(featuredBrandList);

            case RETRIEVE_SALE_DATA:

                SaleProductJson saleProductJson = gson.fromJson(data, SaleProductJson.class);
                objectArrayList.addAll(saleProductJson.getListOfData());

                return new DataObject()
                        .setCode(saleProductJson.getCode())
                        .setMessage(saleProductJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_COUPON_DATA:

                ListOfCouponJson listOfCouponJson = gson.fromJson(data, ListOfCouponJson.class);
                objectArrayList.addAll(listOfCouponJson.getListOfData());

                return new DataObject()
                        .setCode(listOfCouponJson.getCode())
                        .setMessage(listOfCouponJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_ALL_BRANDS:

                ListOfBrandJson listOfBrandJson = gson.fromJson(data, ListOfBrandJson.class);
                objectArrayList.addAll(listOfBrandJson.getListOfData());

                return new DataObject()
                        .setCode(listOfBrandJson.getCode())
                        .setMessage(listOfBrandJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_FEATURED_PRODUCTS:

                FeaturedProductJson featuredProductJson = gson.fromJson(data, FeaturedProductJson.class);
                objectArrayList.addAll(featuredProductJson.getListOfData());

                return new DataObject()
                        .setCode(featuredProductJson.getCode())
                        .setMessage(featuredProductJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_HOT_SEARCHES:

                HotSearchesJson hotSearchesJson = gson.fromJson(data, HotSearchesJson.class);
                objectArrayList.addAll(hotSearchesJson.getListOfData());



                return new DataObject()
                        .setCode(hotSearchesJson.getCode())
                        .setMessage(hotSearchesJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_FAVOURITES_PRODUCT:
            case RETRIEVE_SEARCH_PRODUCT:

                FavouriteProductJson favouriteProductJson = gson.fromJson(data, FavouriteProductJson.class);
                objectArrayList.addAll(favouriteProductJson.getListOfData());

                Utility.Logger(tag,"Identifier = "+favouriteProductJson.getCode());

                return new DataObject()
                        .setCode(favouriteProductJson.getCode())
                        .setMessage(favouriteProductJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_FAVOURITES_BRAND:

                FavouriteBrandJson favouriteBrandJson = gson.fromJson(data, FavouriteBrandJson.class);
                objectArrayList.addAll(favouriteBrandJson.getListOfData());

                return new DataObject()
                        .setCode(favouriteBrandJson.getCode())
                        .setMessage(favouriteBrandJson.getMessage())
                        .setObjectList(objectArrayList);

            case SAVE_ADDRESS:

                AddBillingAddressJson addBillingAddressJson = gson.fromJson(data, AddBillingAddressJson.class);
                return new DataObject()
                        .setCode(addBillingAddressJson.getCode())
                        .setMessage(addBillingAddressJson.getMessage());

            case RETRIEVE_SPECIFIC_USER_ADDRESS:

                BillingAddressJson billingAddressJson = gson.fromJson(data, BillingAddressJson.class);
                objectArrayList.addAll(billingAddressJson.getListOfData());
                return new DataObject()
                        .setCode(billingAddressJson.getCode())
                        .setMessage(billingAddressJson.getMessage())
                        .setObjectList(objectArrayList);

            case REDEEM_COUPON:

                RedeemCouponJson redeemCouponJson = gson.fromJson(data, RedeemCouponJson.class);
                objectArrayList.addAll(redeemCouponJson.getListOfData());
                return new DataObject()
                        .setCode(redeemCouponJson.getCode())
                        .setMessage(redeemCouponJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_AVAILABLE_SHIPPING_SERVICES:

                AvailableShippingServiceJson availableShippingJson = gson.fromJson(data, AvailableShippingServiceJson.class);
                objectArrayList.addAll(availableShippingJson.getListOfData());
                return new DataObject()
                        .setCode(availableShippingJson.getCode())
                        .setMessage(availableShippingJson.getMessage())
                        .setObjectList(objectArrayList);

            case PROCEED_CHECKOUT:
            case ADD_REVIEWS:
            case ADD_REFUND_REQUEST:
            case ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST:
            case DELETE_SPECIFIC_ADDRESS_BOOK:
            case DELETE_SPECIFIC_USER_REVIEWS:
            case CONTACT_US:
            case SIGN_UP:
            case UPDATE_PROFILE:

                GeneralResponseJson generalResponseJson = gson.fromJson(data, GeneralResponseJson.class);
                return new DataObject()
                        .setCode(generalResponseJson.getCode())
                        .setMessage(generalResponseJson.getMessage())
                        .setUserPicture(!Utility.isEmptyString(generalResponseJson.getUserPicture()) ?
                                generalResponseJson.getUserPicture() : "null");

            case RETRIEVE_ORDER_HISTORY:

                HistoryOrderJson historyOrderJson = gson.fromJson(data, HistoryOrderJson.class);
                objectArrayList.addAll(historyOrderJson.getListOfData());
                return new DataObject()
                        .setCode(historyOrderJson.getCode())
                        .setMessage(historyOrderJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_REVIEW_RATING:

                SpecificReviewJson specificReviewJson= gson.fromJson(data, SpecificReviewJson.class);
                objectArrayList.addAll(specificReviewJson.getListOfData());
                return new DataObject()
                        .setCode(specificReviewJson.getCode())
                        .setMessage(specificReviewJson.getMessage())
                        .setObjectList(objectArrayList);

            case SAVE_BILLING_CARD:

                AddBillingCardJson addBillingCardJson = gson.fromJson(data, AddBillingCardJson.class);
                objectArrayList.add(addBillingCardJson.getCustomerId());
                return new DataObject()
                        .setCode(addBillingCardJson.getCode())
                        .setMessage(addBillingCardJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_USER_BILLING_CARD:

                AllCardJson allCardJson = gson.fromJson(data, AllCardJson.class);
                objectArrayList.addAll(allCardJson.getListOfData());
                return new DataObject()
                        .setCode(allCardJson.getCode())
                        .setMessage(allCardJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_ORDER_REFUND:

                SpecificOrderRefundJson specificOrderRefundJson = gson.fromJson(data, SpecificOrderRefundJson.class);
                objectArrayList.addAll(specificOrderRefundJson.getListOfData());
                return new DataObject()
                        .setCode(specificOrderRefundJson.getCode())
                        .setMessage(specificOrderRefundJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_USER_REPORT:

                SpecificUserRefundJson specificUserRefundJson = gson.fromJson(data, SpecificUserRefundJson.class);
                objectArrayList.addAll(specificUserRefundJson.getListOfData());
                return new DataObject()
                        .setCode(specificUserRefundJson.getCode())
                        .setMessage(specificUserRefundJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SPECIFIC_USER_ADDRESS_BOOK:

                SpecificUserAddressBookJson dataJson = gson.fromJson(data, SpecificUserAddressBookJson.class);
                objectArrayList.addAll(dataJson.getListOfData());
                return new DataObject()
                        .setCode(dataJson.getCode())
                        .setMessage(dataJson.getMessage())
                        .setObjectList(objectArrayList);

            case LOGIN:

                LoginUserJson loginUserJson = gson.fromJson(data, LoginUserJson.class);
                objectArrayList.addAll(loginUserJson.getListOfData());
                return new DataObject()
                        .setCode(loginUserJson.getCode())
                        .setMessage(loginUserJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_PROFILE:

                UserProfileJson userProfileJson = gson.fromJson(data, UserProfileJson.class);
                objectArrayList.addAll(userProfileJson.getListOfData());
                return new DataObject()
                        .setCode(userProfileJson.getCode())
                        .setMessage(userProfileJson.getMessage())
                        .setObjectList(objectArrayList);

            case PRIVACY_POLICY:
            case ABOUT_US:

                AboutUsJson aboutUsJson = gson.fromJson(data, AboutUsJson.class);
                objectArrayList.addAll(aboutUsJson.getListOfData());

                return new DataObject()
                        .setCode(aboutUsJson.getCode())
                        .setMessage(aboutUsJson.getMessage())
                        .setObjectList(objectArrayList);

            case RETRIEVE_SUB_CATEGORIES:

                AllSubCategoriesJson allSubCategoriesJson = gson.fromJson(data, AllSubCategoriesJson.class);
                for (int i = 0; i < allSubCategoriesJson.getListOfData().size(); i++) {

                    com.mg.shopping.jsonutil.allsubcategoriesutil.ListOfDatum
                            listOfDatum = allSubCategoriesJson.getListOfData().get(i);

                    DataObject dtObject = new DataObject()
                            .setSubCategoryId(listOfDatum.getId())
                            .setCategoryName(listOfDatum.getCategoryName())
                            .setSubCategoryName(listOfDatum.getName());

                    objectArrayList.add(dtObject);

                }

                return new DataObject()
                        .setCode(allSubCategoriesJson.getCode())
                        .setMessage(allSubCategoriesJson.getMessage())
                        .setObjectList(objectArrayList);

            default:
               return new DataObject();
        }



    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.selection ? (byte) 1 : (byte) 0);
        dest.writeByte(this.dialog ? (byte) 1 : (byte) 0);
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.deepLinkUrl);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeByte(this.lastItem ? (byte) 1 : (byte) 0);
        dest.writeString(this.productId);
        dest.writeString(this.questionId);
        dest.writeString(this.question);
        dest.writeString(this.answerCount);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.rating);
        dest.writeByte(this.isDeeplink ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.brandArrayList);
        dest.writeString(this.userId);
        dest.writeTypedList(this.data);
        dest.writeString(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeString(this.categoryImage);
        dest.writeString(this.categoryType);
        dest.writeTypedList(this.subCategoryList);
        dest.writeString(this.subCategoryId);
        dest.writeString(this.subCategoryName);
        dest.writeParcelable(this.picture, flags);
        dest.writeString(this.userPicture);
        dest.writeInt(this.dataType == null ? -1 : this.dataType.ordinal());
        dest.writeTypedList(this.objectArrayList);
        dest.writeList(this.objectList);
        dest.writeList(this.objectList02);
        dest.writeList(this.objectList03);
        dest.writeList(this.objectList04);
    }

    protected DataObject(Parcel in) {
        this.selection = in.readByte() != 0;
        this.dialog = in.readByte() != 0;
        this.code = in.readString();
        this.message = in.readString();
        this.deepLinkUrl = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.lastItem = in.readByte() != 0;
        this.productId = in.readString();
        this.questionId = in.readString();
        this.question = in.readString();
        this.answerCount = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.rating = in.readString();
        this.isDeeplink = in.readByte() != 0;
        this.brandArrayList = in.createTypedArrayList(Brand.CREATOR);
        this.userId = in.readString();
        this.data = in.createTypedArrayList(Datum.CREATOR);
        this.categoryId = in.readString();
        this.categoryName = in.readString();
        this.categoryImage = in.readString();
        this.categoryType = in.readString();
        this.subCategoryList = in.createTypedArrayList(DatumDetail.CREATOR);
        this.subCategoryId = in.readString();
        this.subCategoryName = in.readString();
        this.picture = in.readParcelable(Bitmap.class.getClassLoader());
        this.userPicture = in.readString();
        int tmpDataType = in.readInt();
        this.dataType = tmpDataType == -1 ? null : Constant.CONNECTION.values()[tmpDataType];
        this.objectArrayList = in.createTypedArrayList(DataObject.CREATOR);
        this.objectList = new ArrayList<>();
        in.readList(this.objectList, Object.class.getClassLoader());
        this.objectList02 = new ArrayList<>();
        in.readList(this.objectList02, Object.class.getClassLoader());
        this.objectList03 = new ArrayList<>();
        in.readList(this.objectList03, Object.class.getClassLoader());
        this.objectList04 = new ArrayList<>();
        in.readList(this.objectList04, Object.class.getClassLoader());
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel source) {
            return new DataObject(source);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };


}


