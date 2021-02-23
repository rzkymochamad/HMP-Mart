package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.adapterutil.BillingAdapter;
import com.mg.shopping.adapterutil.ShippingAdapter;
import com.mg.shopping.adapterutil.SimpleDividerItemDecoration;
import com.mg.shopping.adapterutil.UserAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConnectionCallback;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.billingproductutil.Attribute;
import com.mg.shopping.jsonutil.billingproductutil.BillingAddress;
import com.mg.shopping.jsonutil.billingproductutil.ListOfDatum;
import com.mg.shopping.jsonutil.billingproductutil.PaymentMethod;
import com.mg.shopping.jsonutil.multibillingproductutil.MultiBillingProductObject;
import com.mg.shopping.jsonutil.redeemcouponutil.CouponResponseObject;
import com.mg.shopping.jsonutil.shippingrateutil.Shipping;
import com.mg.shopping.jsonutil.specificproductutil.AvailableShipping;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Billing extends BaseActivity implements View.OnClickListener {
    ImageView imageBack;
    TextView txtMenu;
    LinearLayoutManager layoutManager;
    LinearLayoutManager orderLayoutManager;
    LinearLayoutManager addressLayoutManager;
    RecyclerView recyclerViewPaymentMethod;
    RecyclerView recyclerViewOrder;
    RecyclerView recyclerViewAddress;
    BillingAdapter dataAdapter;
    BillingAdapter orderDataAdapter;
    UserAdapter addressDataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    ArrayList<Object> orderArrayList = new ArrayList<>();
    ArrayList<Object> addressArrayList = new ArrayList<>();
    ArrayList<ArrayList<Object>> shippingDataList = new ArrayList<>();
    LinearLayout layoutAddAddress;
    TextView txtTotalFee;
    TextView txtShippingFee;
    TextView txtInsuranceFee;
    TextView txtBill;
    EditText editCoupon;
    RoundKornerLinearLayout layoutUseCoupon;
    RoundKornerLinearLayout layoutAddTocart;

    TextView txtDiscountTagline;
    TextView txtDiscountedBill;
    LinearLayout layoutDiscount;
    CouponResponseObject couponResponseObject = null;
    double totalBillAmount;
    double totalShippingFee;

    double totalOrderAmount = 0;
    double totalShippingFeeBilling = 0;
    double totalInsuranceFee = 0;

    TextView txtPrice;
    String stripeToken;

    TextView txtCurrencySymbol;
    TextView txtTotalOrderFeeCurrencySymbol;
    TextView txtShippingFeeCurrencySymbol;
    TextView txtInsuranceFeeCurrencySymbol;
    TextView txtTotalCurrencySymbol;

    static final String STORE_COUPON_NATURE = "Store";
    static final String CATEGORY_COUPON_NATURE = "Category";
    static final String PRODUCT_COUPON_NATURE = "Product";
    static final String PERCENTAGE_COUPON_TYPE = "Percentage";
    static final String SHIPPING_COUPON_TYPE = "Shipping";
    static final String FIXED_COUPON_TYPE = "Fixed";
    static final String PRICE_COUPON_TYPE = "Price";

    String keyUserId = "user_id";
    String keyQuantity = "quantity";
    String keyProductId = "product_id";
    String keyCartId = "cart_id";
    String keyDiscountedAmount="discounted_amount";
    String jsonKeyCouponId="coupon_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();
        initUI(); //Initialize UI


    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    protected Context getContextInstance() {
        return this;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_billing;
    }

    @Override
    protected void getIntentData() {
        //do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI() {

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        txtTotalOrderFeeCurrencySymbol = findViewById(R.id.txt_total_order_fee_currency_symbol);
        txtTotalFee = findViewById(R.id.txt_total_order_fee);

        txtShippingFeeCurrencySymbol = findViewById(R.id.txt_shipping_fee_currency_symbol);
        txtShippingFee = findViewById(R.id.txt_shipping_fee);

        txtInsuranceFeeCurrencySymbol = findViewById(R.id.txt_insurance_fee_currency_symbol);
        txtInsuranceFee = findViewById(R.id.txt_insurance_fee);

        txtTotalCurrencySymbol = findViewById(R.id.txt_total_currency_symbol);
        txtBill = findViewById(R.id.txt_total_bill);

        txtPrice = findViewById(R.id.txt_price);
        txtCurrencySymbol = findViewById(R.id.txt_currency_symbol);

        txtTotalOrderFeeCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
        txtShippingFeeCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
        txtInsuranceFeeCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
        txtTotalCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
        txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

        txtDiscountTagline = findViewById(R.id.txt_discount_tagline);
        txtDiscountedBill = findViewById(R.id.txt_discounted_bill);
        layoutDiscount = findViewById(R.id.layout_discount);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.cart));

        layoutAddAddress = findViewById(R.id.layout_add_address);
        layoutUseCoupon = findViewById(R.id.layout_use_coupon);
        layoutAddTocart = findViewById(R.id.layout_add_to_cart);

        editCoupon = findViewById(R.id.edit_coupon);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewPaymentMethod = findViewById(R.id.recycler_view_payment_method);
        recyclerViewPaymentMethod.setLayoutManager(layoutManager);

        dataAdapter = new BillingAdapter(context, objectArrayList);
        dataAdapter.setSelectionInterface(new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag, selectionObject.getAction());
                onPaymentSelectionAction(selectionObject);


            }
        });
        recyclerViewPaymentMethod.setAdapter(dataAdapter);
        recyclerViewPaymentMethod.addItemDecoration(new SimpleDividerItemDecoration(context, ContextCompat.getDrawable(context, R.drawable.line_divider)));


        orderLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewOrder = findViewById(R.id.recycler_view_order);
        recyclerViewOrder.setLayoutManager(orderLayoutManager);

        orderDataAdapter = new BillingAdapter(context, orderArrayList);
        orderDataAdapter.setSelectionInterface(new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());

                int pos = selectionObject.getPosition();
                switch (selectionObject.getAction()){
                    case "shipping":

                        String courierId = null;
                        String cartId = null;
                        String productId = null;
                        String quantity = null;

                        if (Utility.isEmptyString(getSelectedAddressId())) {
                            Utility.Toaster(context, Utility.getStringFromRes(context, R.string.need_address), Toast.LENGTH_SHORT);
                            return;
                        }

                        if (orderArrayList.get(pos) instanceof ListOfDatum) {

                            ListOfDatum datum = (ListOfDatum) orderArrayList.get(pos);
                            courierId = datum.getCourierId();
                            cartId = datum.getCartId();
                            productId = datum.getId();
                            quantity = datum.getQuantity();

                            Utility.Logger(tag,"Courier ID = "+courierId);

                        }
                        else if (orderArrayList.get(pos) instanceof MultiBillingProductObject) {

                            MultiBillingProductObject datum = (MultiBillingProductObject) orderArrayList.get(pos);
                            courierId = datum.getCourierId();
                            cartId = datum.getCartId();
                            productId = datum.getProductId();
                            quantity = datum.getProductQuantity();

                        }

                        if (String.valueOf(courierId).equalsIgnoreCase("0")
                                && Utility.isEmptyString(courierId)) {
                            Utility.Logger(tag, "Need to add Shipping in it");

                            showAvailableShippingCompaniesBottomSheet(context, cartId, productId, quantity, getSelectedAddressId(), pos);

                            return;
                        }

                        showShippingBottomSheet(cartId, productId, quantity, getSelectedAddressId(), String.valueOf(pos), courierId, getShippingCompaniesList(pos));

                        break;

                    case "shipping_insurance_added":
                    case "traffic_insurance_added":

                        calculateTotalBilling();

                        break;

                    case "next":

                        MultiBillingProductObject multiObject = (MultiBillingProductObject) orderArrayList.get(pos);
                        showAvailableProductBottomSheet(context, new ArrayList<Object>(multiObject.getMultiBillingArrayList()));

                        break;

                    default:
                        break;

                }

            }
        });
        recyclerViewOrder.setAdapter(orderDataAdapter);
        recyclerViewOrder.addItemDecoration(new LayoutMarginDecoration(Utility.dpToPx(5)));


        addressLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress = findViewById(R.id.recycler_view_address);
        recyclerViewAddress.setLayoutManager(addressLayoutManager);

        addressDataAdapter = new UserAdapter(context, addressArrayList);
        addressDataAdapter.setSelectionInterface(new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());

                int pos = selectionObject.getPosition();
                for (int i = 0; i < addressArrayList.size(); i++) {
                    ((BillingAddress) addressArrayList.get(i)).setSelection(i == pos);
                }
                addressDataAdapter.notifyDataSetChanged();


            }
        });
        recyclerViewAddress.setAdapter(addressDataAdapter);
        recyclerViewAddress.addItemDecoration(new SimpleDividerItemDecoration(context, ContextCompat.getDrawable(context, R.drawable.line_divider)));


        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_product_in_billing")
                        .setConnection(Constant.CONNECTION.RETRIEVE_PRODUCT_IN_BILLING)
                        .setUserId(BottomNavigationSample.getUserId())))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_PRODUCT_IN_BILLING));

        layoutAddAddress.setOnClickListener(this);
        layoutUseCoupon.setOnClickListener(this);
        layoutAddTocart.setOnClickListener(this);

    }

    private void onPaymentSelectionAction(SelectionObject selectionObject) {

        if (selectionObject.getAction().equalsIgnoreCase("payment_method")){
            int pos = selectionObject.getPosition();
            Utility.Logger(tag, "Payment Method " + ((PaymentMethod) objectArrayList.get(pos)).toString());
            showAvailableCardBottomSheet(context, pos, dataAdapter);
        }

    }

    private ArrayList<Object> getShippingCompaniesList(int pos) {
        return shippingDataList.size() > pos ? shippingDataList.get(pos) : new ArrayList<Object>();
    }
    
    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_PRODUCT_IN_BILLING) {


                jsonObject.accumulate(keyUserId, jsObject.getUserId());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SHIPPING_RATES) {


                jsonObject.accumulate(keyProductId, jsObject.getId());
                jsonObject.accumulate(keyQuantity, jsObject.getQuantity());
                jsonObject.accumulate("address_id", jsObject.getAddressId());
                jsonObject.accumulate(keyCartId, jsObject.getCartId());
                jsonObject.accumulate("courier_id", jsObject.getCourierId());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS) {

                jsonObject.accumulate(keyUserId, jsObject.getUserId());


            } else if (jsObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON) {

                jsonObject.accumulate(keyUserId, jsObject.getUserId());
                jsonObject.accumulate("coupon_code", jsObject.getCouponCode());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_AVAILABLE_SHIPPING_SERVICES) {

                jsonObject.accumulate(keyProductId, jsObject.getId());


            } else if (jsObject.getConnection() == Constant.CONNECTION.PROCEED_CHECKOUT) {


                jsonObject.accumulate(keyUserId, jsObject.getUserId());
                jsonObject.accumulate("address_id", jsObject.getAddressId());

                jsonObject.accumulate("total_amount", jsObject.getTotalAmount());
                jsonObject.accumulate("shipping_fee", jsObject.getShippingAmount());

                if (!Utility.isEmptyString(jsObject.getDiscountAmount())) {

                    jsonObject.accumulate(keyDiscountedAmount, jsObject.getDiscountAmount());

                }

                jsonObject.accumulate("currency_symbol", jsObject.getCurrencySymbol());
                jsonObject.accumulate("currency_code", jsObject.getCurrencyCode());

                jsonObject.accumulate("token", jsObject.getStripeToken());
                jsonObject.accumulate("order_detail", convertProductIntoArray());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_BILLING_CARD) {

                jsonObject.accumulate(keyUserId, jsObject.getUserId());

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(tag + " Json", json);

        return json;
    }

    @Override
    protected void sendRequestToServer(RequestObject requestObject) {

        management.sendRequestToServer(requestObject
                .setConnectionCallback(this));

    }

    @Override
    public void onClick(View v) {
        if (v == layoutAddAddress) {
            Utility.Logger(tag, "Add Address");

            startActivityForResult(new Intent(context, AddBillingAddress.class), Constant.RequestCode.ADDRESS_REQUEST);

        }
        if (v == layoutUseCoupon) {

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("redeem_coupon")
                            .setConnection(Constant.CONNECTION.REDEEM_COUPON)
                            .setCouponCode(editCoupon.getText().toString())
                            .setUserId(BottomNavigationSample.getUserId())))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.REDEEM_COUPON));


        }
        if (v == layoutAddTocart) {

            if (Utility.isEmptyString(getSelectedAddressId())) {
                Utility.Toaster(context, Utility.getStringFromRes(context, R.string.need_address), Toast.LENGTH_SHORT);
                return;
            }

            if (Utility.isEmptyString(stripeToken)) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.select_billing_card)
                        , Toast.LENGTH_SHORT);

                return;
            }


            double shippingFee = Double.parseDouble(txtShippingFee.getText().toString());
            double insuranceFee = Double.parseDouble(txtInsuranceFee.getText().toString());
            double total = shippingFee + insuranceFee;

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("proceed_to_checkout")
                            .setConnection(Constant.CONNECTION.PROCEED_CHECKOUT)
                            .setUserId(BottomNavigationSample.getUserId())
                            .setAddressId(getSelectedAddressId())
                            .setTotalAmount(txtTotalFee.getText().toString())
                            .setShippingAmount(String.valueOf(total))
                            .setStripeToken(stripeToken)
                            .setCurrencySymbol("$")
                            .setCurrencyCode("USD")
                            .setDiscountAmount(txtDiscountedBill.getVisibility() == View.VISIBLE ? txtDiscountedBill.getText().toString()
                                    : null)
                    ))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.PROCEED_CHECKOUT));
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        DataObject dtObject = (DataObject) data;
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_PRODUCT_IN_BILLING) {

            objectArrayList.addAll(dtObject.getObjectList());
            addressArrayList.addAll(dtObject.getObjectList03());

            dataAdapter.notifyDataSetChanged();
            addressDataAdapter.notifyDataSetChanged();

            if (!addressArrayList.isEmpty()) {
                recyclerViewAddress.setVisibility(View.VISIBLE);
            }

            //Creation of billing object for showing in Adapter
            createBillingObject(dtObject);

            //Calculation of Total Billing
            calculateTotalBilling();


        } else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS) {

            addressArrayList.clear();
            addressArrayList.addAll(dtObject.getObjectList());
            addressDataAdapter.notifyDataSetChanged();

            if (!addressArrayList.isEmpty()) {
                recyclerViewAddress.setVisibility(View.VISIBLE);
            }

        } else if (requestObject.getConnection() == Constant.CONNECTION.REDEEM_COUPON) {

            CouponResponseObject couponObject = (CouponResponseObject) dtObject.getObjectList().get(0);

            if (couponObject.getNature().equalsIgnoreCase("All")) {

                couponResponseObject = couponObject;
                calculateCouponTypeAll(couponObject);

            } else {

                processOtherCouponType(orderArrayList,couponObject);
                
            }

        } else if (requestObject.getConnection() == Constant.CONNECTION.PROCEED_CHECKOUT) {

            finish();

        }

    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.RequestCode.ADDRESS_REQUEST && resultCode == RESULT_OK) {

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("retrieve_available_addresses")
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS)
                            .setUserId(management.getPreferences(null).getUserId())))
                    .setLoadingText(Utility.getStringFromRes(context, R.string.loading))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_ADDRESS));

        } else if (requestCode == Constant.RequestCode.REQUEST_CODE_BILLING_CARD && resultCode == RESULT_OK &&
                data != null) {

            stripeToken = data.getStringExtra(Constant.IntentKey.ID);
            Utility.Logger(tag, "Token = " + stripeToken);

        }

    }


    /**
     * <p>It is used to process other coupon type</p>
     * @param orderArrayList
     * @param couponObject
     */
    private void processOtherCouponType(ArrayList<Object> orderArrayList,CouponResponseObject couponObject) {

        for (int i = 0; i < orderArrayList.size(); i++) {

            if (orderArrayList.get(i) instanceof ListOfDatum) {

                processGeneralCouponType(orderArrayList,couponObject,i);

            }
            else {

                processMultiCouponType(orderArrayList,couponObject,i);

            }

        }
        orderDataAdapter.notifyDataSetChanged();

    }

    /**
     * <p>It is used to process Multi Coupon Type</p>
     * @param orderArrayList
     * @param couponObject
     * @param i
     */
    private void processMultiCouponType(ArrayList<Object> orderArrayList, CouponResponseObject couponObject, int i) {

        MultiBillingProductObject datum = (MultiBillingProductObject) orderArrayList.get(i);
        Utility.Logger(tag, "Nature = " + couponObject.getNature() + " Brand ID = " + datum.getBrandId()
                + " Category ID = " + datum.getCategoryId() + " Product ID = " + datum.getProductId() + " Coupon Term ID = " + couponObject.getTermId());


        if (couponObject.getNature().equalsIgnoreCase(STORE_COUPON_NATURE)) {

            if (datum.getBrandId().equalsIgnoreCase(couponObject.getTermId())) {
                ((MultiBillingProductObject) orderArrayList.get(i)).setCouponResponseObject(couponObject);
                ((MultiBillingProductObject) orderArrayList.get(i)).setDiscountCalculationNeeded(true);
            }
        } else if (couponObject.getNature().equalsIgnoreCase(CATEGORY_COUPON_NATURE)) {

            if (datum.getCategoryId().contains(couponObject.getTermId())) {
                ((MultiBillingProductObject) orderArrayList.get(i)).setCouponResponseObject(couponObject);
                ((MultiBillingProductObject) orderArrayList.get(i)).setDiscountCalculationNeeded(true);
            }

        } else if (couponObject.getNature().equalsIgnoreCase(PRODUCT_COUPON_NATURE)) {

            Utility.Logger(tag, "Applied = " + couponObject.getNature());
            if (datum.getProductId().contains(couponObject.getTermId())) {
                ((MultiBillingProductObject) orderArrayList.get(i)).setCouponResponseObject(couponObject);
                ((MultiBillingProductObject) orderArrayList.get(i)).setDiscountCalculationNeeded(true);
            }

        }

    }

    /**
     * <p>It is used to process General Coupon Type</p>
     * @param orderArrayList
     * @param couponObject
     * @param i
     */
    private void processGeneralCouponType(ArrayList<Object> orderArrayList,CouponResponseObject couponObject,int i) {

        ListOfDatum datum = (ListOfDatum) orderArrayList.get(i);

        if (couponObject.getNature().equalsIgnoreCase(STORE_COUPON_NATURE)) {

            if (couponObject.getTermId().equalsIgnoreCase(datum.getBrand().get(0).getId())) {
                ((ListOfDatum) orderArrayList.get(i)).setCouponResponseObject(couponObject);
                ((ListOfDatum) orderArrayList.get(i)).setDiscountCalculationNeeded(true);
            }
        } else if (couponObject.getNature().equalsIgnoreCase(CATEGORY_COUPON_NATURE)) {

            if (couponObject.getTermId().equalsIgnoreCase(datum.getSubCategoryId())) {
                ((ListOfDatum) orderArrayList.get(i)).setCouponResponseObject(couponObject);
                ((ListOfDatum) orderArrayList.get(i)).setDiscountCalculationNeeded(true);
            }

        } else if (couponObject.getNature().equalsIgnoreCase(PRODUCT_COUPON_NATURE) &&
                couponObject.getTermId().equalsIgnoreCase(datum.getId())) {

            ((ListOfDatum) orderArrayList.get(i)).setCouponResponseObject(couponObject);
            ((ListOfDatum) orderArrayList.get(i)).setDiscountCalculationNeeded(true);

        }

    }

    /**
     * <p>It is used to create billing object</p>
     * @param dtObject
     */
    private void createBillingObject(DataObject dtObject){

        MultiBillingProductObject productObject = null;
        for (int i = 0; i < dtObject.getObjectList02().size(); i++) {

            ListOfDatum outerDatum = (ListOfDatum) dtObject.getObjectList02().get(i);

            boolean isMatched = false;
            double price = 0;
            double quantity = 0;
            double shippingInsuranceFee = 0;
            double trafficInsuranceFee = 0;
            ArrayList<String> pictureList = new ArrayList<>();
            ArrayList<ListOfDatum> datumList = new ArrayList<>();
            StringBuilder productId = new StringBuilder();
            StringBuilder cartId = new StringBuilder();
            StringBuilder categoryId = new StringBuilder();
            StringBuilder brandId = new StringBuilder();
            StringBuilder attrId = new StringBuilder();
            StringBuilder productPrice = new StringBuilder();
            StringBuilder productQuantity = new StringBuilder();
            String courierId = null;

            for (int j = i + 1; j < dtObject.getObjectList02().size(); j++) {

                ListOfDatum innerDatum = (ListOfDatum) dtObject.getObjectList02().get(j);

                Utility.Logger(tag, "Courier ID Outer = " + outerDatum.getCourierId()
                        + " Courier ID Inner = " + innerDatum.getCourierId()
                        + " Brand ID Outer = " + outerDatum.getBrand().get(0).getId()
                        + " Brand ID Inner = " + innerDatum.getBrand().get(0).getId());

                if (outerDatum.getCourierId().equalsIgnoreCase(innerDatum.getCourierId())) {

                    productId.append(innerDatum.getId());
                    productId.append(",");

                    cartId.append(innerDatum.getCartId());
                    cartId.append(",");

                    brandId.append(innerDatum.getBrand().get(0).getId());
                    brandId.append(",");

                    categoryId.append(innerDatum.getSubCategoryId());
                    categoryId.append(",");

                    productPrice.append(innerDatum.getPrice());
                    productPrice.append(",");

                    productQuantity.append(innerDatum.getQuantity());
                    productQuantity.append(",");

                    attrId.append(getAttributeId(innerDatum));
                    attrId.append(",");

                    price += Double.parseDouble(innerDatum.getPrice()) * Double.parseDouble(innerDatum.getQuantity());
                    quantity += Double.parseDouble(innerDatum.getQuantity());
                    shippingInsuranceFee += Double.parseDouble(innerDatum.getShippingInsurance());
                    trafficInsuranceFee += Double.parseDouble(innerDatum.getTariffInsurance());
                    pictureList.add(innerDatum.getImage().get(0).getImage());
                    datumList.add(innerDatum);

                    isMatched = true;
                    ((ListOfDatum) dtObject.getObjectList02().get(j)).setAlreadyMatched(isMatched);

                }

            }

            if (isMatched) {

                productId.append(outerDatum.getId());
                cartId.append(outerDatum.getCartId());
                brandId.append(outerDatum.getBrand().get(0).getId());
                categoryId.append(outerDatum.getSubCategoryId());
                productPrice.append(outerDatum.getPrice());
                productQuantity.append(outerDatum.getQuantity());


                attrId.append(getAttributeId(outerDatum));


                courierId = outerDatum.getCourierId();
                price += Double.parseDouble(outerDatum.getPrice()) * Double.parseDouble(outerDatum.getQuantity());
                quantity += Double.parseDouble(outerDatum.getQuantity());
                shippingInsuranceFee += Double.parseDouble(outerDatum.getShippingInsurance());
                trafficInsuranceFee += Double.parseDouble(outerDatum.getTariffInsurance());
                pictureList.add(outerDatum.getImage().get(0).getImage());
                datumList.add(outerDatum);

                productObject = new MultiBillingProductObject();

                productObject.setProductId(productId.toString());
                productObject.setCartId(cartId.toString());

                productObject.setBrandId(brandId.toString());
                productObject.setCategoryId(categoryId.toString());
                productObject.setAttributeId(attrId.toString());
                productObject.setProductPrice(productPrice.toString());
                productObject.setQuantity(productQuantity.toString());

                productObject.setCourierId(courierId);
                productObject.setPrice(String.valueOf(price));
                productObject.setNoOfProduct(String.valueOf(pictureList.size()));
                productObject.setProductQuantity(String.valueOf(quantity));
                productObject.setTariffInsurance(String.valueOf(trafficInsuranceFee));
                productObject.setShippingInsurance(String.valueOf(shippingInsuranceFee));
                productObject.setPictureArrayList(pictureList);
                productObject.setMultiBillingArrayList(datumList);

            } else if (!outerDatum.isAlreadyMatched()) {

                Utility.Logger(tag,"Outer Datum = "+outerDatum.toString());
                orderArrayList.add(outerDatum);
            }

        }
        if (productObject != null) {

            orderArrayList.add(productObject);
            Utility.Logger(tag, "Order Size = " + orderArrayList.size() + " MultiObject = " + productObject.toString());

        }

        orderDataAdapter.notifyDataSetChanged();

    }

    /**
     * <p>It is used to get Attribute ID</p>
     * @param innerDatum
     * @return
     */
    private String getAttributeId(ListOfDatum innerDatum) {

        StringBuilder attr = new StringBuilder();
        for (int k = 0; k < innerDatum.getAttribute().size(); k++) {

            attr.append(innerDatum.getAttribute().get(k).getId());
            if (k < (innerDatum.getAttribute().size() - 1)) {
                attr.append(";");
            }

        }

        return attr.toString();

    }


    /**
     * <p>It is used to convert Cart Product into Json Array</p>
     *
     * @return
     */
    private JSONArray convertProductIntoArray() {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < orderArrayList.size(); i++) {

            if (orderArrayList.get(i) instanceof ListOfDatum) {

               processGeneralProduct(orderArrayList,i,jsonArray);


            }
            else if (orderArrayList.get(i) instanceof MultiBillingProductObject) {

               processMultiProduct(orderArrayList,i,jsonArray);

            }

        }

        return jsonArray;
    }

    /**
     * <p>It is used to process Multi Product to Checkout</p>
     * @param orderArrayList
     * @param i
     * @param jsonArray
     */
    private void processMultiProduct(ArrayList<Object> orderArrayList, int i, JSONArray jsonArray) {

        MultiBillingProductObject productObject = (MultiBillingProductObject) orderArrayList.get(i);
        try {

            String[] cartID = productObject.getCartId().split(",");
            String[] productID = productObject.getProductId().split(",");
            String[] brandID = productObject.getBrandId().split(",");
            String[] attrID = productObject.getAttributeId().split(",");
            String[] quantity = productObject.getQuantity().split(",");
            String[] price = productObject.getProductPrice().split(",");
            String[] category = productObject.getCategoryId().split(",");

            String shippingID = productObject.getShippingObject().getId();
            String packageID = productObject.getShippingObject().getShipmentId();
            double courierRate = Double.parseDouble(productObject.getShippingObject().getRate()) / 2;

            for (int j = 0; j < productID.length; j++) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("cart_id", cartID[j]);
                jsonObject.accumulate(keyProductId, productID[j]);
                jsonObject.accumulate("brand_id", brandID[j]);
                jsonObject.accumulate(keyQuantity, quantity[j]);
                jsonObject.accumulate("price", price[j]);
                jsonObject.accumulate("attribute_id", attrID[j]);

                double totalBill = Double.parseDouble(price[j]) * Double.parseDouble(quantity[j]);

                if (couponResponseObject != null) {

                    jsonObject.accumulate(jsonKeyCouponId, couponResponseObject.getId());
                    jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(
                            courierRate,
                            totalBill, couponResponseObject));

                } else if (productObject.getCouponResponseObject() != null) {

                    
                    switch (productObject.getCouponResponseObject().getNature()){
                        case STORE_COUPON_NATURE:

                            processMultiProductStoreCouponNature(brandID[j],productObject,jsonObject,courierRate,totalBill);

                            break;

                        case CATEGORY_COUPON_NATURE:

                            if (category[j].equalsIgnoreCase(productObject.getCouponResponseObject().getTermId())) {

                                jsonObject.accumulate(jsonKeyCouponId, productObject.getCouponResponseObject().getId());
                                jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(courierRate, totalBill, productObject.getCouponResponseObject()));

                            }

                            
                            break;

                        default:

                            jsonObject.accumulate(jsonKeyCouponId, productObject.getCouponResponseObject().getId());
                            jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(courierRate, totalBill, productObject.getCouponResponseObject()));


                            break;
                    }

                }

                jsonObject.accumulate("shipping_id", shippingID);
                jsonObject.accumulate("package_id", packageID);
                jsonObject.accumulate("courier_rate", courierRate);

                jsonArray.put(jsonObject);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * <p>It is used to process General Product to Checkout</p>
     * @param orderArrayList
     * @param i
     * @param jsonArray
     */
    private void processGeneralProduct(ArrayList<Object> orderArrayList, int i,JSONArray jsonArray) {

        ListOfDatum datum = (ListOfDatum) orderArrayList.get(i);

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate(keyCartId, datum.getCartId());
            jsonObject.accumulate(keyProductId, datum.getId());
            jsonObject.accumulate("brand_id", datum.getBrand().get(0).getId());
            jsonObject.accumulate(keyQuantity, datum.getQuantity());
            jsonObject.accumulate("price", datum.getPrice());
            jsonObject.accumulate("attribute_id", getAttributeIdForGeneralOrders(datum));

            jsonObject.accumulate("shipping_id", datum.getShippingObject().getId());
            jsonObject.accumulate("package_id", datum.getShippingObject().getShipmentId());
            jsonObject.accumulate("courier_rate", datum.getShippingObject().getRate());

            double totalBill = Double.parseDouble(datum.getPrice()) * Double.parseDouble(datum.getQuantity());
            double courierRate = Double.parseDouble(datum.getShippingObject().getRate());

            if (couponResponseObject != null) {

                jsonObject.accumulate(jsonKeyCouponId, couponResponseObject.getId());
                jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(
                        courierRate,
                        totalBill, couponResponseObject));

            } else if (datum.getCouponResponseObject() != null) {

                if (datum.getCouponResponseObject().getNature().equalsIgnoreCase(STORE_COUPON_NATURE)) {

                    if (datum.getBrand().get(0).getId().equalsIgnoreCase(datum.getCouponResponseObject().getTermId())) {

                        jsonObject.accumulate(jsonKeyCouponId, datum.getCouponResponseObject().getId());
                        jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(courierRate, totalBill, datum.getCouponResponseObject()));

                    }

                } else if (datum.getCouponResponseObject().getNature().equalsIgnoreCase(CATEGORY_COUPON_NATURE)) {

                    if (datum.getSubCategoryId().equalsIgnoreCase(datum.getCouponResponseObject().getTermId())) {

                        jsonObject.accumulate(jsonKeyCouponId, datum.getCouponResponseObject().getId());
                        jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(courierRate, totalBill, datum.getCouponResponseObject()));

                    }

                } else if (datum.getCouponResponseObject().getNature().equalsIgnoreCase(PRODUCT_COUPON_NATURE)
                        && datum.getId().equalsIgnoreCase(datum.getCouponResponseObject().getTermId())) {

                    jsonObject.accumulate(jsonKeyCouponId, datum.getCouponResponseObject().getId());
                    jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(courierRate, totalBill, datum.getCouponResponseObject()));


                }

            }

            jsonArray.put(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * <p>It is used to process Multi Product Store Coupon</p>
     * @param s
     * @param productObject
     * @param jsonObject
     * @param courierRate
     * @param totalBill
     * @throws JSONException
     */
    private void processMultiProductStoreCouponNature(String s, MultiBillingProductObject productObject , JSONObject jsonObject , double courierRate , double totalBill) throws JSONException {

        if (s.equalsIgnoreCase(productObject.getCouponResponseObject().getTermId())) {

            jsonObject.accumulate(jsonKeyCouponId, productObject.getCouponResponseObject().getId());
            jsonObject.accumulate(keyDiscountedAmount, getDiscountedAmount(courierRate, totalBill, productObject.getCouponResponseObject()));

        }

    }

    /**
     * <p>It is used to get Attribute Id for General Order for checkout</p>
     * @param datum
     * @return
     */
    private String getAttributeIdForGeneralOrders(ListOfDatum datum) {

        StringBuilder attrBuilder = new StringBuilder();
        for (int j = 0; j < datum.getAttribute().size(); j++) {

            Attribute attribute = datum.getAttribute().get(j);
            attrBuilder.append(attribute.getId());

            if (j < (datum.getAttribute().size() - 1)) {
                attrBuilder.append(",");
            }

        }
        return attrBuilder.toString();
        
    }

    /**
     * <p>It is used to show Shipping Company Selector BottomSheet</p>
     * @param cartId
     * @param productId
     * @param quantity
     * @param addressId
     * @param position
     * @param courierId
     * @param list
     */
    private void showShippingBottomSheet(String cartId, String productId, String quantity, String addressId, final String position, String courierId, ArrayList<Object> list) {
        final ArrayList<Object> shippingList = new ArrayList<>();
        final Context context = this.context;

        shippingList.addAll(getShippingData(list));

        final View view = LayoutInflater.from(context).inflate(R.layout.shipping_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();


        TextView txtLabel = view.findViewById(R.id.txt_label);
        txtLabel.setText(Utility.getStringFromRes(context, R.string.available_shipping));

        RecyclerView recyclerViewList = view.findViewById(R.id.recycler_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        final ShippingAdapter shippingDataAdapter = new ShippingAdapter(context, shippingList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                int pos = selectionObject.getPosition();
                Shipping shippingObject = (Shipping) shippingList.get(pos);
                Utility.Logger(tag, shippingObject.toString());

                for (int i = 0; i < shippingList.size(); i++) {

                    int cartProductPosition = Integer.parseInt(position);
                    if (pos == i) {

                        ((Shipping) shippingList.get(i)).setSelection(true);
                        ((Shipping) shippingDataList.get(0).get(i)).setSelection(true);

                        if (orderArrayList.get(cartProductPosition) instanceof ListOfDatum) {
                            ((ListOfDatum) orderArrayList.get(cartProductPosition)).setShippingSelected(true);
                            ((ListOfDatum) orderArrayList.get(cartProductPosition)).setShippingObject(((Shipping) shippingList.get(i)));
                        } else {
                            ((MultiBillingProductObject) orderArrayList.get(cartProductPosition)).setShippingSelected(true);
                            ((MultiBillingProductObject) orderArrayList.get(cartProductPosition)).setShippingObject(((Shipping) shippingList.get(i)));
                        }


                    }
                    else {

                        ((Shipping) shippingList.get(i)).setSelection(false);
                        ((Shipping) shippingDataList.get(0).get(i)).setSelection(false);

                    }

                }

                if (orderArrayList.get(Integer.parseInt(position)) instanceof ListOfDatum) {
                    ((ListOfDatum) orderArrayList.get(Integer.parseInt(position))).setShippingObject(shippingObject);
                } else {

                    ((MultiBillingProductObject) orderArrayList.get(Integer.parseInt(position))).setShippingObject(shippingObject);
                }

                orderDataAdapter.notifyItemChanged(Integer.parseInt(position));
                calculateTotalBilling();


                bottomSheetDialog.dismiss();

            }
        });
        recyclerViewList.setAdapter(shippingDataAdapter);
        recyclerViewList.addItemDecoration(new SimpleDividerItemDecoration(context, ContextCompat.getDrawable(context, R.drawable.line_divider)));


        if (!list.isEmpty()) {
            return;
        }


        Utility.Logger(tag,"Courier ID = "+courierId);

        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_specific_product_shipping_rates")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SHIPPING_RATES)
                        .setId(productId)
                        .setCartId(cartId)
                        .setCourierId(courierId)
                        .setAddressId(addressId)
                        .setQuantity(quantity)
                ))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SHIPPING_RATES)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        DataObject dataObject = (DataObject) data;
                        shippingList.clear();
                        shippingList.addAll(dataObject.getObjectList());

                        Comparator<Object> compareById = new Comparator<Object>() {
                            @Override
                            public int compare(Object o1, Object o2) {
                                Shipping sh1 = (Shipping) o1;
                                Shipping sh2 = (Shipping) o2;
                                return Double.compare(Double.parseDouble(sh1.getRate()), Double.parseDouble(sh2.getRate()));
                            }

                        };
                        Collections.sort(shippingList, compareById);

                        shippingDataList.add(shippingList);
                        shippingDataAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                    }
                }));


    }

    /**
     * <p>It is used to get Shipping Data from Arraylist</p>
     * @param list
     * @return
     */
    private Collection<?> getShippingData(ArrayList<Object> list) {

        if (!list.isEmpty()) {
            return list;
        } else {

            ArrayList<Object> arrayList = new ArrayList<>();
            arrayList.add(new ProgressObject());

            return arrayList;
        }

    }

    /**
     * <p>It is used to show List of Available Shipping Compannies BottomSheet</p>
     * @param context
     * @param cart_id
     * @param product_id
     * @param quantity
     * @param addressId
     * @param position
     */
    private void showAvailableShippingCompaniesBottomSheet(final Context context, final String cart_id, final String product_id, final String quantity, final String addressId, final int position) {

        final ArrayList<Object> shipppingCompaniesObjectArrayList = new ArrayList<>();

        final View view = LayoutInflater.from(context).inflate(R.layout.shipping_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();


        TextView txtLabel = view.findViewById(R.id.txt_label);
        txtLabel.setText(Utility.getStringFromRes(context, R.string.available_shipping));


        RecyclerView recyclerViewList = view.findViewById(R.id.recycler_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        final ShippingAdapter shippingCompanyDataAdapter = new ShippingAdapter(context, shipppingCompaniesObjectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                int pos = selectionObject.getPosition();
                AvailableShipping shippingCompanyObject = (AvailableShipping) shipppingCompaniesObjectArrayList.get(pos);
                ((ListOfDatum) orderArrayList.get(position)).setCourierId(shippingCompanyObject.getId());
                orderDataAdapter.notifyDataSetChanged();


                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

                showShippingBottomSheet( cart_id, product_id, quantity, addressId, String.valueOf(position), shippingCompanyObject.getId(), shippingDataList.size() > pos ? shippingDataList.get(pos) : new ArrayList<Object>());


            }
        });
        recyclerViewList.setAdapter(shippingCompanyDataAdapter);
        recyclerViewList.addItemDecoration(new SimpleDividerItemDecoration(context, ContextCompat.getDrawable(context, R.drawable.line_divider)));


        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_available_shipping")
                        .setConnection(Constant.CONNECTION.RETRIEVE_AVAILABLE_SHIPPING_SERVICES)
                        .setId(product_id)
                ))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_AVAILABLE_SHIPPING_SERVICES)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        DataObject dataObject = (DataObject) data;
                        shipppingCompaniesObjectArrayList.addAll(dataObject.getObjectList());
                        shippingCompanyDataAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                    }
                }));


    }

    /**
     * <p>It is used to show Available Card BottomSheet</p>
     * @param context
     * @param pos
     * @param adapter
     */
    private void showAvailableCardBottomSheet(final Context context, final int pos, final BillingAdapter adapter) {

        final ArrayList<Object> availableCardList = new ArrayList<>();
        final View view = LayoutInflater.from(context).inflate(R.layout.available_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        ImageView layoutAddCard = view.findViewById(R.id.image_add);
        layoutAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddBillingCard.class);
                intent.putExtra(Constant.IntentKey.TYPE, ((PaymentMethod) objectArrayList.get(pos)).getName());
                startActivityForResult(intent, Constant.RequestCode.REQUEST_CODE_BILLING_CARD);

                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }


            }
        });

        availableCardList.add(new ProgressObject());

        GridLayoutManager layoutManagerCard = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewCard = view.findViewById(R.id.recycler_view_card);
        recyclerViewCard.setLayoutManager(layoutManagerCard);

        final BillingAdapter dataAdapterCard = new BillingAdapter(context, availableCardList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.toString());
                int position = selectionObject.getPosition();
                if (selectionObject.getAction().equalsIgnoreCase("select_card")) {

                    stripeToken = ((com.mg.shopping.jsonutil.allcardutil.ListOfDatum) availableCardList.get(position)).getCardToken();
                    Utility.Logger(tag, "Stripe Token = " + stripeToken);

                    for (int i = 0; i < objectArrayList.size(); i++) {

                        ((PaymentMethod) objectArrayList.get(i)).setSelection(i==pos);

                    }
                    adapter.notifyDataSetChanged();


                    if (bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }
                }


            }
        });
        recyclerViewCard.setAdapter(dataAdapterCard);

        LayoutMarginDecoration decorator = new LayoutMarginDecoration(Utility.dpToPx(2));
        recyclerViewCard.addItemDecoration(decorator);


        management.sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_specific_user_billing_cards")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_BILLING_CARD)
                        .setUserId(BottomNavigationSample.getUserId())))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_BILLING_CARD)
                .setConnectionCallback(new ConnectionCallback() {
                    @Override
                    public void onSuccess(Object data, RequestObject requestObject) {

                        DataObject dataObject = (DataObject) data;

                        Utility.Logger(tag, "OnSuccess Size =" + dataObject.getObjectList().size());

                        availableCardList.clear();
                        availableCardList.addAll(dataObject.getObjectList());
                        dataAdapterCard.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String data, RequestObject requestObject) {
                        Utility.Logger(tag, data);

                        availableCardList.clear();
                        dataAdapterCard.notifyDataSetChanged();
                    }
                }));


    }

    /**
     * <p>It is used to get Selected Address Id</p>
     * @return
     */
    private String getSelectedAddressId() {
        String addressId = null;

        for (int i = 0; i < addressArrayList.size(); i++) {
            if (((BillingAddress) addressArrayList.get(i)).isSelection()) {
                addressId = ((BillingAddress) addressArrayList.get(i)).getId();
                break;
            }
        }

        return addressId;
    }

    /**
     * <p>it is used to calculate total billing in Checkout screen</p>
     */
    public void calculateTotalBilling() {

        totalBillAmount = 0;
        totalOrderAmount=0;
        totalInsuranceFee=0;
        totalShippingFeeBilling=0;

        Utility.Logger(tag, "calculateTotalBillingAfterDiscount");

        for (int i = 0; i < orderArrayList.size(); i++) {

            if (orderArrayList.get(i) instanceof ListOfDatum) {

               processTotalBillingGeneralProduct(orderArrayList,i);
               
            }
            else if (orderArrayList.get(i) instanceof MultiBillingProductObject) {

                processTotalBillingMultiProduct(orderArrayList,i);

            }

        }

        txtTotalFee.setText(String.valueOf(Utility.round(totalOrderAmount, 2)));
        txtInsuranceFee.setText(String.valueOf(Utility.round(totalInsuranceFee, 2)));
        txtShippingFee.setText(String.valueOf(Utility.round(totalShippingFeeBilling, 2)));

        totalBillAmount = totalOrderAmount + totalInsuranceFee + totalShippingFeeBilling;
        txtBill.setText(String.valueOf(Utility.round(totalBillAmount, 2)));

        txtPrice.setText(String.valueOf(Utility.round(totalBillAmount, 2)));
        txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

        if (couponResponseObject != null)
            calculateCouponTypeAll(couponResponseObject);

    }

    /**
     * <p>It is used to process Total Billing Multi Product</p>
     * @param orderArrayList
     * @param i
     */
    private void processTotalBillingMultiProduct(ArrayList<Object> orderArrayList, int i) {

        MultiBillingProductObject datum = (MultiBillingProductObject) orderArrayList.get(i);
        totalOrderAmount += Double.parseDouble(datum.getPrice());

        if (datum.isShippingSelected()) {

            totalShippingFeeBilling += Double.parseDouble(datum.getShippingObject().getRate());

            if (datum.isTrafficInsuranceAdded())
                totalInsuranceFee += Double.parseDouble(datum.getTariffInsurance());

            if (datum.isShippingInsuranceAdded())
                totalInsuranceFee += Double.parseDouble(datum.getShippingInsurance());

        }
        if (datum.getCouponResponseObject() != null) {
            double discountedAmount;

            switch (datum.getCouponResponseObject().getType()){
                case PERCENTAGE_COUPON_TYPE:

                    discountedAmount = (totalOrderAmount * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                    totalOrderAmount = totalOrderAmount - discountedAmount;

                    break;

                case SHIPPING_COUPON_TYPE:

                    if (datum.isShippingSelected()) {

                        discountedAmount = (Double.parseDouble(datum.getShippingObject().getRate())
                                * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                        totalShippingFeeBilling = totalShippingFeeBilling - discountedAmount;

                    }

                    break;

                case FIXED_COUPON_TYPE:

                    discountedAmount = Double.parseDouble(datum.getCouponResponseObject().getOffer());
                    totalOrderAmount = totalOrderAmount - discountedAmount;

                    break;

                case PRICE_COUPON_TYPE:

                    if (totalOrderAmount >= Integer.parseInt(datum.getCouponResponseObject().getPriceDiscountValue())) {

                        discountedAmount = (totalOrderAmount * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                        totalOrderAmount = totalOrderAmount - discountedAmount;


                    }
                    else {

                        Utility.Toaster(context,
                                String.format(Utility.getStringFromRes(context, R.string.price_discount_value), datum.getCouponResponseObject().getPriceDiscountValue())
                                , Toast.LENGTH_SHORT);

                    }

                    break;

                default:
                    break;

            }


        }
        
    }

    /**
     * <p>It is used process Total Biling General Product </p>
     * @param orderArrayList
     * @param i
     */
    private void processTotalBillingGeneralProduct(ArrayList<Object> orderArrayList, int i) {

        ListOfDatum datum = (ListOfDatum) orderArrayList.get(i);
        totalOrderAmount += (Double.parseDouble(datum.getQuantity()) * Double.parseDouble(datum.getPrice()));

        if (datum.isShippingSelected()) {

            totalShippingFeeBilling += Double.parseDouble(datum.getShippingObject().getRate());

            if (datum.isTrafficInsuranceAdded())
                totalInsuranceFee += Double.parseDouble(datum.getTariffInsurance());

            if (datum.isShippingInsuranceAdded())
                totalInsuranceFee += Double.parseDouble(datum.getShippingInsurance());

        }
        if (datum.getCouponResponseObject() != null) {
            double discountedAmount;
            
            switch (datum.getCouponResponseObject().getType()){
                case PERCENTAGE_COUPON_TYPE:

                    discountedAmount = (totalOrderAmount * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                    totalOrderAmount = totalOrderAmount - discountedAmount;
                    
                    break;

                case SHIPPING_COUPON_TYPE:

                    if (datum.isShippingSelected()) {

                        discountedAmount = (Double.parseDouble(datum.getShippingObject().getRate())
                                * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                        totalShippingFeeBilling = totalShippingFeeBilling - discountedAmount;


                    }

                    break;

                case FIXED_COUPON_TYPE:

                    discountedAmount = Double.parseDouble(datum.getCouponResponseObject().getOffer());
                    totalOrderAmount = totalOrderAmount - discountedAmount;

                    break;

                case PRICE_COUPON_TYPE:

                    if (totalOrderAmount >= Integer.parseInt(datum.getCouponResponseObject().getPriceDiscountValue())) {

                        discountedAmount = (totalOrderAmount * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                        totalOrderAmount = totalOrderAmount - discountedAmount;


                    }
                    else {

                        Utility.Toaster(context,
                                String.format(Utility.getStringFromRes(context, R.string.price_discount_value), datum.getCouponResponseObject().getPriceDiscountValue())
                                , Toast.LENGTH_SHORT);

                    }

                    break;

                default:
                    break;
                    
            }


        }

    }

    /**
     * <p>It is used to calculate all coupon type</p>
     * @param couponObject
     */
    private void calculateCouponTypeAll(CouponResponseObject couponObject) {

        Double totalActualBill = totalBillAmount;
        Double discountedAmount = 0.0;
        Double totalDiscountedAmount = 0.0;
        String discountTagline;

        discountTagline = Utility.getStringFromRes(context, R.string.discount_applied);
        Utility.Logger(tag, "Discounted Amount  = " + discountedAmount + " Total Discounted Amount = " + totalDiscountedAmount);

        if (couponObject.getType().equalsIgnoreCase(PERCENTAGE_COUPON_TYPE)) {

            discountedAmount = (totalActualBill * Double.parseDouble(couponObject.getOffer())) / 100;
            totalDiscountedAmount = totalActualBill - discountedAmount;


        } else if (couponObject.getType().equalsIgnoreCase(SHIPPING_COUPON_TYPE)) {

            totalDiscountedAmount = totalDiscountedAmount - totalShippingFee;
            discountedAmount = (totalShippingFee * Double.parseDouble(couponObject.getOffer())) / 100;
            totalShippingFee = totalShippingFee - discountedAmount;
            totalDiscountedAmount = totalDiscountedAmount + totalShippingFee;
            discountTagline = Utility.getStringFromRes(context, R.string.shipping) + " " +
                    Utility.getStringFromRes(context, R.string.discount_applied);


        } else if (couponObject.getType().equalsIgnoreCase(FIXED_COUPON_TYPE)) {

            discountedAmount = Double.parseDouble(couponObject.getOffer());
            totalDiscountedAmount = totalActualBill - discountedAmount;

        } else if (couponObject.getType().equalsIgnoreCase(PRICE_COUPON_TYPE)) {

            if (totalActualBill >= Integer.parseInt(couponObject.getPriceDiscountValue())) {

                discountedAmount = (totalActualBill * Double.parseDouble(couponObject.getOffer())) / 100;
                totalDiscountedAmount = totalActualBill - discountedAmount;


            } else {
                Utility.Toaster(context,
                        String.format(Utility.getStringFromRes(context, R.string.price_discount_value), couponObject.getPriceDiscountValue())
                        , Toast.LENGTH_SHORT);
            }


        }


        layoutDiscount.setVisibility(View.VISIBLE);
        txtDiscountedBill.setVisibility(View.VISIBLE);
        txtBill.setPaintFlags(txtBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtDiscountedBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
        txtDiscountTagline.setText("( " + couponObject.getOffer() + "% Off ) " + discountTagline);
        txtPrice.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));

    }

    /**
     * <p>It is used to get Discounted Amount </p>
     * @param totalShippingFee
     * @param totalBillAmount
     * @param couponObject
     * @return
     */
    private String getDiscountedAmount(double totalShippingFee, double totalBillAmount, CouponResponseObject couponObject) {

        Double totalActualBill = totalBillAmount;
        Double discountedAmount;
        Double totalDiscountedAmount = 0.0;

        if (couponObject.getType().equalsIgnoreCase(SHIPPING_COUPON_TYPE)) {

            totalDiscountedAmount = totalDiscountedAmount - totalShippingFee;
            discountedAmount = (totalShippingFee * Double.parseDouble(couponObject.getOffer())) / 100;
            totalShippingFee = totalShippingFee - discountedAmount;
            totalDiscountedAmount = totalDiscountedAmount + totalShippingFee;


        } else if (couponObject.getType().equalsIgnoreCase(FIXED_COUPON_TYPE)) {

            discountedAmount = Double.parseDouble(couponObject.getOffer());
            totalDiscountedAmount = totalActualBill - discountedAmount;

        } else if ((couponObject.getType().equalsIgnoreCase(PRICE_COUPON_TYPE)
                && totalActualBill >= Integer.parseInt(couponObject.getPriceDiscountValue()))
                || couponObject.getType().equalsIgnoreCase(PERCENTAGE_COUPON_TYPE)) {

            discountedAmount = (totalActualBill * Double.parseDouble(couponObject.getOffer())) / 100;
            totalDiscountedAmount = totalActualBill - discountedAmount;

        }

        return String.valueOf(totalDiscountedAmount);
    }

    /**
     * <p>It is used show available product bottomsheet dialog</p>
     * @param context
     * @param objectArrayList
     */
    private void showAvailableProductBottomSheet(final Context context, final ArrayList<Object> objectArrayList) {

        final View view = LayoutInflater.from(context).inflate(R.layout.product_in_order_item_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        for (int i = 0; i < objectArrayList.size(); i++) {

            ((ListOfDatum) objectArrayList.get(i)).setBilingSection(false);

        }

        GridLayoutManager layoutManagerProduct = new GridLayoutManager(context, 1, RecyclerView.VERTICAL, false);
        RecyclerView recyclerViewProduct = view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManagerProduct);

        BillingAdapter dataAdapterProduct = new BillingAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag, selectionObject.getAction() + " Dialog View");

            }
        });
        recyclerViewProduct.setAdapter(dataAdapterProduct);

        LayoutMarginDecoration itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(2));
        recyclerViewProduct.addItemDecoration(itemDecoration);


    }


}




