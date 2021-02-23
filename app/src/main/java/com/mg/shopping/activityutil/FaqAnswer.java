package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.adapterutil.FaqAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FaqAnswer extends BaseActivity implements View.OnClickListener {
    ImageView imageBack;
    TextView txtMenu;
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewFaq;
    FaqAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    TextView txtAnswer;
    TextView txtQuestion;
    ListOfDatum product;
    RoundedImageView imageProduct;
    TextView txtName;
    TextView txtPrice;
    TextView txtDiscount;
    DataObject dataObject;
    RoundKornerLinearLayout layoutSubmit;
    EditText editAnswer;

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
        return R.layout.activity_faq_answer;
    }

    @Override
    protected void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
        product = getIntent().getParcelableExtra(Constant.IntentKey.PRODUCT_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.question));

        txtQuestion = findViewById(R.id.txt_question);
        txtAnswer = findViewById(R.id.txt_answer);

        imageProduct = (RoundedImageView) findViewById(R.id.image_product);
        txtName = (TextView) findViewById(R.id.txt_name);
        txtPrice = (TextView) findViewById(R.id.txt_price);
        txtDiscount = (TextView) findViewById(R.id.txt_discount);

        layoutSubmit = findViewById(R.id.layout_submit);
        editAnswer = findViewById(R.id.edit_answer);

        layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);
        recyclerViewFaq = findViewById(R.id.recycler_view_faq);
        recyclerViewFaq.setLayoutManager(layoutManager);

        dataAdapter = new FaqAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());

            }
        });
        recyclerViewFaq.setAdapter(dataAdapter);
        recyclerViewFaq.addItemDecoration(new LayoutMarginDecoration(1, Utility.dpToPx(1)));

        txtQuestion.setText(dataObject.getQuestion());
        txtAnswer.setText(Utility.getStringFromRes(context, R.string.answers) + " (" + dataObject.getAnswerCount() + ")");

        txtName.setText(product.getName());
        txtPrice.setText(BottomNavigationSample.getCurrencyInformation() + product.getPrice());

        if (!product.getImage().isEmpty())
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(product.getImage().get(0).getImage())).into(imageProduct);


        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_answer")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_QUESTION_ANSWER)
                        .setId(dataObject.getQuestionId())))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_QUESTION_ANSWER));


        layoutSubmit.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_QUESTION_ANSWER) {

                jsonObject.accumulate("question_id", jsObject.getId());

            } else if (jsObject.getConnection() == Constant.CONNECTION.ADD_ANSWER_INTO_QUESTION) {


                jsonObject.accumulate("question_id", jsObject.getId());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("answer", jsObject.getAnswer());


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
        if (v == imageBack) {
            finish();
        }
        if (v == layoutSubmit) {

            if (Utility.isEmptyString(editAnswer.getText().toString())) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_answer)
                        , Toast.LENGTH_SHORT);

                return;
            }

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("add_answer_into_question")
                            .setConnection(Constant.CONNECTION.ADD_ANSWER_INTO_QUESTION)
                            .setId(dataObject.getQuestionId())
                            .setAnswer(editAnswer.getText().toString())
                            .setUserId(BottomNavigationSample.getUserId())))
                    .setLoadingText(Utility.getStringFromRes(context, R.string.submiting))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.ADD_ANSWER_INTO_QUESTION));

            editAnswer.setText(null);

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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_QUESTION_ANSWER
                || requestObject.getConnection() == Constant.CONNECTION.ADD_ANSWER_INTO_QUESTION) {

            objectArrayList.clear();
            objectArrayList.addAll(dtObject.getObjectList());
            dataAdapter.notifyDataSetChanged();
            txtAnswer.setText(Utility.getStringFromRes(context, R.string.answers) + " (" + dtObject.getObjectList().size() + ")");

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }


}




