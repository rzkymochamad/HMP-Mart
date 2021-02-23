package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.MyApplication;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;

public class AddBillingCard extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    private ImageView imageBack;
    private RoundKornerLinearLayout layoutSave;
    private EditText editCardHolder;
    private EditText editCardNumber;
    private EditText editExpiryMonth;
    private EditText editExpiryYear;
    private EditText editCvv;
    private AppCompatCheckBox checkboxSave;
    private String cardType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI();  //Initialize View


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
        return R.layout.activity_add_billing_card;
    }

    @Override
    protected void getIntentData() {
        cardType = getIntent().getStringExtra(Constant.IntentKey.TYPE);
    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.add_billing_detail));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editCardHolder = (EditText) findViewById(R.id.edit_card_holder);
        editCardNumber = (EditText) findViewById(R.id.edit_card_number);
        editExpiryMonth = (EditText) findViewById(R.id.edit_expiry_month);
        editExpiryYear = (EditText) findViewById(R.id.edit_expiry_year);
        editCvv = (EditText) findViewById(R.id.edit_cvv);
        checkboxSave = (AppCompatCheckBox) findViewById(R.id.checkbox_save);

        layoutSave = (RoundKornerLinearLayout) findViewById(R.id.layout_save);

        layoutSave.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    protected int initAdmobBannerAds() {
        return R.id.layout_ad;
    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());
            jsonObject.accumulate("user_id", jsObject.getUserId());
            jsonObject.accumulate("card_no", jsObject.getCardNo());
            jsonObject.accumulate("stripe_token", jsObject.getStripeToken());
            jsonObject.accumulate("card_company_name", jsObject.getCardCompanyName());
            jsonObject.accumulate("save_card", jsObject.isSaveCard());


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
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        if (requestObject.getConnection() == Constant.CONNECTION.SAVE_ADDRESS) {

            setResult(RESULT_OK);
            finish();

        } else if (requestObject.getConnection() == Constant.CONNECTION.SAVE_BILLING_CARD) {

            DataObject dataObject = (DataObject) data;
            String customerId = (String) dataObject.getObjectList().get(0);

            Utility.Logger(tag, "Stripe Token = " + customerId);

            Intent intent = new Intent();
            intent.putExtra(Constant.IntentKey.ID, customerId);
            setResult(RESULT_OK, intent);
            finish();


        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);

    }

    @Override
    public void onClick(View v) {

        if (v == imageBack) {
            finish();
        } else if (v == layoutSave) {

            if (Utility.isEmptyString(editCardHolder.getText().toString())
            || Utility.isEmptyString(editCardNumber.getText().toString())
            || Utility.isEmptyString(editExpiryMonth.getText().toString())
            || Utility.isEmptyString(editExpiryYear.getText().toString())
            || Utility.isEmptyString(editCvv.getText().toString())) {
                return;
            }

            Stripe stripe = MyApplication.getStripe();
            Card.Builder builder = new Card.Builder(editCardNumber.getText().toString().trim()
                    , Integer.parseInt(editExpiryMonth.getText().toString())
                    , Integer.parseInt(editExpiryYear.getText().toString())
                    , editCvv.getText().toString());
            builder.name(editCardHolder.getText().toString());
            final Card card = builder.build();

            //Card Validation

            if (!checkCardValidation(card))
                return;

            Utility.Logger(tag, "Card Brand = " + card.getBrand() + " Type = " + cardType);


            stripe.createToken(card, new TokenCallback() {
                @Override
                public void onError(@NonNull Exception error) {
                    Utility.Toaster(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
                }

                @Override
                public void onSuccess(@NonNull final Token token) {
                    Utility.Logger(tag, "Token = " + token.getId());


                    String json = getJson(new JsonObject()
                            .setFunctionality("save_billing_card")
                            .setUserId(BottomNavigationSample.getUserId())
                            .setCardNo(editCardNumber.getText().toString())
                            .setStripeToken(token.getId())
                            .setCardCompanyName(cardType)
                            .setSaveCard(checkboxSave.isChecked() ? "0" : "1"));

                    sendRequestToServer(new RequestObject()
                            .setJson(json)
                            .setLoadingText(Utility.getStringFromRes(context, R.string.adding))
                            .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnection(Constant.CONNECTION.SAVE_BILLING_CARD));


                }
            });

        }

    }


    /**
     * <p>It is used to check Card Validation</p>
     * @param card
     */
    private boolean checkCardValidation(Card card){

        if (!card.validateCard()) {
            Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_card), Toast.LENGTH_SHORT);
            return false;
        }

        if (!card.validateCVC()) {
            Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_cvc), Toast.LENGTH_SHORT);
            return false;
        }

        if (!card.validateExpiryDate()) {
            Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_expiry_date), Toast.LENGTH_SHORT);
            return false;
        }

        if (!card.validateExpMonth()) {
            Utility.Toaster(this, Utility.getStringFromRes(this, R.string.validate_expiry_month), Toast.LENGTH_SHORT);
            return false;
        }

        return true;

    }


}
