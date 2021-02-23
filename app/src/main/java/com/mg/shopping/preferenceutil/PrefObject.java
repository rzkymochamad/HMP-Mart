package com.mg.shopping.preferenceutil;

import java.util.ArrayList;

public class PrefObject {

    private ArrayList<PreferenceObserver> observers = new ArrayList<>();

    private boolean firstLaunch;
    private boolean saveFirstLaunch;
    private boolean retrieveFirstLaunch;

    private boolean isLogin;
    private boolean saveLogin;
    private boolean retrieveLogin;

    private String userId;
    private boolean saveUserId;
    private boolean retrieveUserId;

    private boolean userRemember;
    private boolean saveUserRemember;
    private boolean retrieveUserRemember;


    private String userName;
    private String pictureUrl;
    private String loginType;
    private String userEmail;
    private String userPassword;
    private boolean saveUserCredential;
    private boolean retrieveUserCredential;
    private boolean saveUserPicture;
    private boolean saveUserName;


    private String countryId;
    private String currencyCode;
    private String currencySymbol;
    private boolean saveCountryInformation;
    private boolean retrieveCountryInformation;



    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public PrefObject setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
        return this;
    }

    public boolean isSaveFirstLaunch() {
        return saveFirstLaunch;
    }

    public PrefObject setSaveFirstLaunch(boolean saveFirstLaunch) {
        this.saveFirstLaunch = saveFirstLaunch;
        return this;
    }

    public boolean isRetrieveFirstLaunch() {
        return retrieveFirstLaunch;
    }

    public PrefObject setRetrieveFirstLaunch(boolean retrieveFirstLaunch) {
        this.retrieveFirstLaunch = retrieveFirstLaunch;
        return this;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public PrefObject setLogin(boolean login) {
        isLogin = login;
        return this;
    }

    public boolean isSaveLogin() {
        return saveLogin;
    }

    public PrefObject setSaveLogin(boolean saveLogin) {
        this.saveLogin = saveLogin;
        return this;
    }

    public boolean isRetrieveLogin() {
        return retrieveLogin;
    }

    public PrefObject setRetrieveLogin(boolean retrieveLogin) {
        this.retrieveLogin = retrieveLogin;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PrefObject setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public boolean isSaveUserId() {
        return saveUserId;
    }

    public PrefObject setSaveUserId(boolean saveUserId) {
        this.saveUserId = saveUserId;
        return this;
    }

    public boolean isRetrieveUserId() {
        return retrieveUserId;
    }

    public PrefObject setRetrieveUserId(boolean retrieveUserId) {
        this.retrieveUserId = retrieveUserId;
        return this;
    }

    public boolean isUserRemember() {
        return userRemember;
    }

    public PrefObject setUserRemember(boolean userRemember) {
        this.userRemember = userRemember;
        return this;
    }



    public boolean isSaveUserRemember() {
        return saveUserRemember;
    }

    public PrefObject setSaveUserRemember(boolean saveUserRemember) {
        this.saveUserRemember = saveUserRemember;
        return this;
    }

    public boolean isRetrieveUserRemember() {
        return retrieveUserRemember;
    }

    public PrefObject setRetrieveUserRemember(boolean retrieveUserRemember) {
        this.retrieveUserRemember = retrieveUserRemember;
        return this;
    }


    public String getUserName() {
        return userName;
    }

    public PrefObject setUserName(String userName) {
        this.userName = userName;
        return this;
    }


    public String getLoginType() {
        return loginType;
    }

    public PrefObject setLoginType(String loginType) {
        this.loginType = loginType;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public PrefObject setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public PrefObject setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public PrefObject setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public boolean isSaveUserCredential() {
        return saveUserCredential;
    }

    public PrefObject setSaveUserCredential(boolean saveUserCredential) {
        this.saveUserCredential = saveUserCredential;
        return this;
    }

    public boolean isRetrieveUserCredential() {
        return retrieveUserCredential;
    }

    public PrefObject setRetrieveUserCredential(boolean retrieveUserCredential) {
        this.retrieveUserCredential = retrieveUserCredential;
        return this;
    }

    public boolean isSaveUserPicture() {
        return saveUserPicture;
    }

    public PrefObject setSaveUserPicture(boolean saveUserPicture) {
        this.saveUserPicture = saveUserPicture;
        return this;
    }

    public boolean isSaveUserName() {
        return saveUserName;
    }

    public PrefObject setSaveUserName(boolean saveUserName) {
        this.saveUserName = saveUserName;
        return this;
    }

    public String getCountryId() {
        return countryId;
    }

    public PrefObject setCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public PrefObject setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public PrefObject setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }

    public boolean isSaveCountryInformation() {
        return saveCountryInformation;
    }

    public PrefObject setSaveCountryInformation(boolean saveCountryInformation) {
        this.saveCountryInformation = saveCountryInformation;
        return this;
    }

    public boolean isRetrieveCountryInformation() {
        return retrieveCountryInformation;
    }

    public PrefObject setRetrieveCountryInformation(boolean retrieveCountryInformation) {
        this.retrieveCountryInformation = retrieveCountryInformation;
        return this;
    }

    public void attachObserver(PreferenceObserver preferenceObserver){
        observers.add(preferenceObserver);
    }

    public void notifyAllObserver(){

        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onUpdate();
        }

    }


    @Override
    public String toString() {
        return "PrefObject{" +
                "firstLaunch=" + firstLaunch +
                ", isLogin=" + isLogin +
                ", userId='" + userId + '\'' +
                ", userRemember=" + userRemember +
                ", userName='" + userName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", countryId='" + countryId + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                '}';
    }
}
