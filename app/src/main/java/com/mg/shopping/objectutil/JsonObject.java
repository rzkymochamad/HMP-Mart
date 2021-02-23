package com.mg.shopping.objectutil;

import com.mg.shopping.constantutil.Constant;

public class JsonObject {

    /* Variable for functionality type */

    private Constant.CONNECTION connection;
    private String functionality;
    private String id;
    private String userId;
    private String type;
    private String latitude;
    private String longitude;
    private String skipIds;
    private String name;
    private String attributeId;
    private String quantity;
    private String shippingId;
    private String rateId;
    private String carrier;
    private String service;
    private String rate;
    private String deliveryDays;
    private String currency;
    private String toPostalCode;
    private String categoryId;


    private String action;

    private String address;
    private String apartment;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
    private String note;

    private String addressId;
    private String cartId;

    private String couponCode;
    private String courierId;

    private String question;
    private String answer;

    private String currencyCode;
    private String currencySymbol;

    private String totalAmount;
    private String shippingAmount;
    
    private String couponId;
    private String discountAmount;

    private String orderDetail;

    private String review;

    private String brandId;
    private String productId;
    private String price;

    private String cardHolderName;
    private String cardNo;
    private String cardMonthExpiry;
    private String cardYearExpiry;
    private String cardCvv;
    private String saveCard;
    private String stripeToken;
    private String cardCompanyName;

    private String subject;
    private String detail;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String userPhone;
    private String password;

    private String featureType;



    public String getFunctionality() {
        return functionality;
    }

    public JsonObject setFunctionality(String functionality) {
        this.functionality = functionality;
        return this;
    }

    public String getId() {
        return id;
    }

    public JsonObject setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public JsonObject setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    

    public String getType() {
        return type;
    }

    public JsonObject setType(String type) {
        this.type = type;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public JsonObject setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public JsonObject setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getSkipIds() {
        return skipIds;
    }

    public JsonObject setSkipIds(String skipIds) {
        this.skipIds = skipIds;
        return this;
    }
    

    public String getName() {
        return name;
    }

    public JsonObject setName(String name) {
        this.name = name;
        return this;
    }
    

    public Constant.CONNECTION getConnection() {
        return connection;
    }

    public JsonObject setConnection(Constant.CONNECTION connection) {
        this.connection = connection;
        return this;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public JsonObject setAttributeId(String attributeId) {
        this.attributeId = attributeId;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public JsonObject setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getShippingId() {
        return shippingId;
    }

    public JsonObject setShippingId(String shippingId) {
        this.shippingId = shippingId;
        return this;
    }

    public String getRateId() {
        return rateId;
    }

    public JsonObject setRateId(String rateId) {
        this.rateId = rateId;
        return this;
    }

    public String getCarrier() {
        return carrier;
    }

    public JsonObject setCarrier(String carrier) {
        this.carrier = carrier;
        return this;
    }

    public String getService() {
        return service;
    }

    public JsonObject setService(String service) {
        this.service = service;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public JsonObject setRate(String rate) {
        this.rate = rate;
        return this;
    }

    public String getDeliveryDays() {
        return deliveryDays;
    }

    public JsonObject setDeliveryDays(String deliveryDays) {
        this.deliveryDays = deliveryDays;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public JsonObject setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getToPostalCode() {
        return toPostalCode;
    }

    public JsonObject setToPostalCode(String toPostalCode) {
        this.toPostalCode = toPostalCode;
        return this;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public JsonObject setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getAction() {
        return action;
    }

    public JsonObject setAction(String action) {
        this.action = action;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public JsonObject setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public JsonObject setApartment(String apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getCity() {
        return city;
    }

    public JsonObject setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public JsonObject setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public JsonObject setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public JsonObject setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getNote() {
        return note;
    }

    public JsonObject setNote(String note) {
        this.note = note;
        return this;
    }

    public String getAddressId() {
        return addressId;
    }

    public JsonObject setAddressId(String addressId) {
        this.addressId = addressId;
        return this;
    }

    public String getCartId() {
        return cartId;
    }

    public JsonObject setCartId(String cartId) {
        this.cartId = cartId;
        return this;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public JsonObject setCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public String getCourierId() {
        return courierId;
    }

    public JsonObject setCourierId(String courierId) {
        this.courierId = courierId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public JsonObject setQuestion(String question) {
        this.question = question;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public JsonObject setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public JsonObject setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public JsonObject setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public JsonObject setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getShippingAmount() {
        return shippingAmount;
    }

    public JsonObject setShippingAmount(String shippingAmount) {
        this.shippingAmount = shippingAmount;
        return this;
    }
    

    public String getCouponId() {
        return couponId;
    }

    public JsonObject setCouponId(String couponId) {
        this.couponId = couponId;
        return this;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public JsonObject setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public JsonObject setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
        return this;
    }

    public String getReview() {
        return review;
    }

    public JsonObject setReview(String review) {
        this.review = review;
        return this;
    }

    public String getBrandId() {
        return brandId;
    }

    public JsonObject setBrandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public JsonObject setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public JsonObject setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public JsonObject setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        return this;
    }

    public String getCardNo() {
        return cardNo;
    }

    public JsonObject setCardNo(String cardNo) {
        this.cardNo = cardNo;
        return this;
    }

    public String getCardMonthExpiry() {
        return cardMonthExpiry;
    }

    public JsonObject setCardMonthExpiry(String cardMonthExpiry) {
        this.cardMonthExpiry = cardMonthExpiry;
        return this;
    }

    public String getCardYearExpiry() {
        return cardYearExpiry;
    }

    public JsonObject setCardYearExpiry(String cardYearExpiry) {
        this.cardYearExpiry = cardYearExpiry;
        return this;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public JsonObject setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
        return this;
    }
    

    public String isSaveCard() {
        return saveCard;
    }

    public JsonObject setSaveCard(String saveCard) {
        this.saveCard = saveCard;
        return this;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public JsonObject setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
        return this;
    }

    public String getSaveCard() {
        return saveCard;
    }

    public String getCardCompanyName() {
        return cardCompanyName;
    }

    public JsonObject setCardCompanyName(String cardCompanyName) {
        this.cardCompanyName = cardCompanyName;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public JsonObject setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public JsonObject setDetail(String detail) {
        this.detail = detail;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public JsonObject setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public JsonObject setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public JsonObject setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public JsonObject setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JsonObject setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFeatureType() {
        return featureType;
    }

    public JsonObject setFeatureType(String featureType) {
        this.featureType = featureType;
        return this;
    }
}
