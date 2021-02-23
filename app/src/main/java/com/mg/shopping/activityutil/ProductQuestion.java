package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.adapterutil.FaqAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ProductQuestion extends BaseActivity implements View.OnClickListener{
    ImageView imageBack;
    TextView txtMenu;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerViewFaq;
    FaqAdapter dataAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private ListOfDatum product;
    private RoundKornerLinearLayout layoutAskQuestion;


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
        return R.layout.activity_product_question;
    }

    @Override
    protected void getIntentData() {
        product = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.question));

        layoutAskQuestion = findViewById(R.id.layout_ask_question);

        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerViewFaq = findViewById(R.id.recycler_view_faq);
        recyclerViewFaq.setLayoutManager(layoutManager);

        dataAdapter = new FaqAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                int pos = selectionObject.getPosition();

                com.mg.shopping.jsonutil.specificproductquestionsutil.ListOfDatum
                        faq = (com.mg.shopping.jsonutil.specificproductquestionsutil.ListOfDatum) objectArrayList.get(pos);

                DataObject dataObject = new DataObject();
                dataObject.setQuestionId( faq.getId());
                dataObject.setQuestion(faq.getName());
                dataObject.setAnswerCount(String.valueOf( faq.getAnswer().size()));


                Intent intent = new Intent(context,FaqAnswer.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT,dataObject);
                intent.putExtra(Constant.IntentKey.PRODUCT_OBJECT,product);
                startActivity(intent);

            }
        });
        recyclerViewFaq.setAdapter(dataAdapter);
        recyclerViewFaq.addItemDecoration(new LayoutMarginDecoration(1, Utility.dpToPx(5)));


        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_questions")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS)
                        .setId(product.getId())))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS));

        layoutAskQuestion.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS) {

                jsonObject.accumulate("product_id", jsObject.getId());

            }
            else if (jsObject.getConnection() == Constant.CONNECTION.ASK_QUESTION_OF_SPECIFIC_PRODUCT) {

                jsonObject.accumulate("product_id", jsObject.getId());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("question", jsObject.getQuestion());


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
        if (v == layoutAskQuestion){

            if (!management.getPreferences(null).isLogin()){

                Utility.Toaster(context,
                        Utility.getStringFromRes(context,R.string.need_login)
                , Toast.LENGTH_SHORT);

                return;
            }

            showQskQuestionBottomSheet(context,product);
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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_QUESTIONS
          || requestObject.getConnection() == Constant.CONNECTION.ASK_QUESTION_OF_SPECIFIC_PRODUCT) {

            objectArrayList.clear();
            objectArrayList.addAll(dtObject.getObjectList());
            dataAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }


    /**
     * <p>It is used to open Bottomsheet for Asking Questions</p>
     * @param context
     * @param datum
     */
    private void showQskQuestionBottomSheet(final Context context, final ListOfDatum datum) {

        final View view = LayoutInflater.from(context).inflate(R.layout.ask_question_bottom_sheet_item_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        final EditText editQuestion = (EditText) view.findViewById(R.id.edit_question);
        RoundKornerLinearLayout layoutAddToCart = (RoundKornerLinearLayout) view.findViewById(R.id.layout_add_to_cart);

        layoutAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequestToServer(new RequestObject()
                        .setJson(getJson(new JsonObject()
                                .setFunctionality("add_question_into_product")
                                .setId(datum.getId())
                                .setUserId(BottomNavigationSample.getUserId())
                                .setQuestion(editQuestion.getText().toString())
                                .setConnection(Constant.CONNECTION.ASK_QUESTION_OF_SPECIFIC_PRODUCT)))
                        .setLoadingText(Utility.getStringFromRes(context,R.string.submiting))
                        .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.ASK_QUESTION_OF_SPECIFIC_PRODUCT));

                if (bottomSheetDialog.isShowing()){
                    bottomSheetDialog.dismiss();
                }


            }
        });


    }


}




