package com.mg.shopping.activityutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.adapterutil.ReviewAdapter;
import com.mg.shopping.base64util.Base64Builder;
import com.mg.shopping.base64util.Base64Object;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.specificreviewutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.PictureObject;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddReview extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    private ImageView imageBack;
    private EditText editQuestion;
    private LinearLayout layoutAddPicture;

    private ArrayList<Object> objectArrayList = new ArrayList<>();
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewPicture;
    private ReviewAdapter dataAdapter;
    private String rating;

    private RoundKornerLinearLayout layoutAddReview;
    private String id;
    private String brandId;
    private String productId;
    private String type;

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
        return R.layout.activity_add_review;
    }

    @Override
    protected void getIntentData() {

        id = getIntent().getStringExtra(Constant.IntentKey.ID);
        type = getIntent().getStringExtra(Constant.IntentKey.TYPE);
        rating = getIntent().getStringExtra(Constant.IntentKey.RATING);
        brandId = getIntent().getStringExtra(Constant.IntentKey.BRAND_ID);
        productId = getIntent().getStringExtra(Constant.IntentKey.PRODUCT_ID);

    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.add_review));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        editQuestion = findViewById(R.id.edit_question);
        layoutAddPicture = findViewById(R.id.layout_add_picture);
        layoutAddReview = findViewById(R.id.layout_add_review);

        layoutManager = new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false);
        recyclerViewPicture = findViewById(R.id.recycler_view_picture);
        recyclerViewPicture.setLayoutManager(layoutManager);

        dataAdapter = new ReviewAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag, selectionObject.getAction());

            }
        });
        recyclerViewPicture.setAdapter(dataAdapter);
        LayoutMarginDecoration itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(2));
        recyclerViewPicture.addItemDecoration(itemDecoration);

        if (type.equalsIgnoreCase("0")){

            layoutAddReview.setVisibility(View.GONE);
            layoutAddPicture.setEnabled(false);

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("retrieve_specific_order_review_rating")
                            .setConnection(Constant.CONNECTION.RETRIEVE_REVIEW_RATING)
                            .setId(id)))
                    .setFirstRequest(true)
                    .setLoadingText(Utility.getStringFromRes(context,R.string.loading))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_REVIEW_RATING));


        }


        layoutAddReview.setOnClickListener(this);
        layoutAddPicture.setOnClickListener(this);
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

            if (jsObject.getConnection() == Constant.CONNECTION.ADD_REVIEWS) {

                jsonObject.accumulate("order_id", jsObject.getId());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("brand_id", jsObject.getBrandId());
                jsonObject.accumulate("product_id", jsObject.getProductId());
                jsonObject.accumulate("rating", jsObject.getRate());
                jsonObject.accumulate("review", jsObject.getReview());
                jsonObject.accumulate("picture", convertPictureIntoArray());

            }
            else  if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_REVIEW_RATING) {

                jsonObject.accumulate("order_id", jsObject.getId());

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
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        if (requestObject.getConnection() == Constant.CONNECTION.ADD_REVIEWS) {

            finish();

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_REVIEW_RATING){

            DataObject dataObject = (DataObject) data;
            ListOfDatum datum = (ListOfDatum) dataObject.getObjectList().get(0);

            editQuestion.setText(datum.getReview());

            if (!Utility.isEmptyString(datum.getImage())) {
                String[] reviewPicture = datum.getImage().split(",");
                for (int i = 0; i < reviewPicture.length; i++) {

                    objectArrayList.add(new PictureObject().setPictureUrl(reviewPicture[i]));

                }
                dataAdapter.notifyDataSetChanged();
            }

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
        }
        if (v == layoutAddPicture) {
            pictureSelector.showImagePickerDialog();
        }
        if (v == layoutAddReview) {

            if (Utility.isEmptyString(editQuestion.getText().toString())) {
                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_review)
                        , Toast.LENGTH_SHORT);
                return;
            }

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("add_review")
                            .setConnection(Constant.CONNECTION.ADD_REVIEWS)
                            .setId(id)
                            .setBrandId(brandId)
                            .setProductId(productId)
                            .setUserId(BottomNavigationSample.getUserId())
                            .setReview(editQuestion.getText().toString())
                            .setRate(rating)))
                    .setFirstRequest(true)
                    .setLoadingText(Utility.getStringFromRes(context,R.string.loading))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.ADD_REVIEWS));

        }

    }

    @Override
    public void onImageHandled(Bitmap image) {
        super.onImageHandled(image);

        objectArrayList.add(new PictureObject().setPictureBitmap(image));
        dataAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImageError() {
        super.onImageError();

        Utility.Toaster(context,Utility.getStringFromRes(context,R.string.failed_to_load_image),Toast.LENGTH_SHORT);


    }

    /**
     * <p>It is used to convert Cart Product into Json Array</p>
     *
     * @return
     */
    private JSONArray convertPictureIntoArray() {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < objectArrayList.size(); i++) {

            Base64Builder base64Builder = new Base64Builder();
            base64Builder.setScaling(true);
            base64Builder.setRequiredHeight(250);
            base64Builder.setBase64Object(new Base64Object().setBitmap(((PictureObject) objectArrayList.get(i)).getPictureBitmap()));
            String base64Picture = base64Builder.encodedBitmapIntoString();

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("picture", base64Picture);

                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return jsonArray;
    }

}
