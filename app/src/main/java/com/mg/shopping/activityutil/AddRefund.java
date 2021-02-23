package com.mg.shopping.activityutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.adapterutil.ReviewAdapter;
import com.mg.shopping.base64util.Base64Builder;
import com.mg.shopping.base64util.Base64Object;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConfirmationCallback;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.specificorderrefundutil.ListOfDatum;
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

public class AddRefund extends BaseActivity implements View.OnClickListener {
    TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageOther;
    private EditText editQuestion;
    private LinearLayout layoutAddPicture;

    private ArrayList<Object> objectArrayList = new ArrayList<>();
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewPicture;
    private ReviewAdapter dataAdapter;

    private RoundKornerLinearLayout layoutAddReview;
    private String id;
    private String brandId;
    private String type;

    private LinearLayout layoutResponse;
    private TextView editResponse;
    private String refundRequestId;

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
        return R.layout.activity_add_refund;
    }

    @Override
    protected void getIntentData() {

        id = getIntent().getStringExtra(Constant.IntentKey.ID);
        refundRequestId = getIntent().getStringExtra(Constant.IntentKey.REFUND_REQUEST_ID);
        type = getIntent().getStringExtra(Constant.IntentKey.TYPE);
        brandId = getIntent().getStringExtra(Constant.IntentKey.BRAND_ID);

    }

    @Override
    protected void initUI() {

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.add_refund_request));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        imageOther = findViewById(R.id.image_more);
        imageOther.setVisibility(View.VISIBLE);
        imageOther.setRotation(90);

        editQuestion = findViewById(R.id.edit_question);
        editQuestion.setHint(Utility.getStringFromRes(context,R.string.reason_of_refund));

        layoutResponse = findViewById(R.id.layout_response);
        editResponse = findViewById(R.id.edit_response);
        editResponse.setHint(Utility.getStringFromRes(context,R.string.response_of_refund));

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
                            .setFunctionality("retrieve_specific_order_refund_request")
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_ORDER_REFUND)
                            .setId(id)))
                    .setFirstRequest(true)
                    .setLoadingText(Utility.getStringFromRes(context,R.string.loading))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_ORDER_REFUND));


        }


        layoutAddReview.setOnClickListener(this);
        layoutAddPicture.setOnClickListener(this);
        layoutResponse.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        imageOther.setOnClickListener(this);

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
            jsonObject.accumulate("order_id", jsObject.getId());

            if (jsObject.getConnection() == Constant.CONNECTION.ADD_REFUND_REQUEST) {


                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("brand_id", jsObject.getBrandId());
                jsonObject.accumulate("review", jsObject.getReview());
                jsonObject.accumulate("picture", convertPictureIntoArray());

            }
            else  if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_ORDER_REFUND) {

                jsonObject.accumulate("order_id", jsObject.getId());

            }
            else  if (jsObject.getConnection() == Constant.CONNECTION.ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST) {

                jsonObject.accumulate("user_id", jsObject.getUserId());

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

        if (requestObject.getConnection() == Constant.CONNECTION.ADD_REFUND_REQUEST) {

            finish();

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_ORDER_REFUND){

            DataObject dataObject = (DataObject) data;
            ListOfDatum datum = (ListOfDatum) dataObject.getObjectList().get(0);

            editQuestion.setText(datum.getReason());
            editResponse.setText(datum.getResponse());
            layoutResponse.setVisibility(View.VISIBLE);

            if (!Utility.isEmptyString(datum.getRefundImage())) {
                String[] reviewPicture = datum.getRefundImage().split(",");
                for (int i = 0; i < reviewPicture.length; i++) {

                    objectArrayList.add(new PictureObject().setPictureUrl(reviewPicture[i]).setRefundPicture(true));

                }
                dataAdapter.notifyDataSetChanged();
            }

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST){

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
                            .setFunctionality("adding_refund_specific_order")
                            .setConnection(Constant.CONNECTION.ADD_REFUND_REQUEST)
                            .setId(id)
                            .setBrandId(brandId)
                            .setUserId(BottomNavigationSample.getUserId())
                            .setReview(editQuestion.getText().toString())))
                    .setFirstRequest(true)
                    .setLoadingText(Utility.getStringFromRes(context,R.string.adding))
                    .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.ADD_REFUND_REQUEST));

        }
        if (v == imageOther){
            showPopup(imageOther);
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


    /* <p> you should refer to a view to stick your popup wherever u want.
     *
     **/
    public void showPopup(View v) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.order_report_item_layout, null);

        TextView txtMessage = popupView.findViewById(R.id.txt_message);
        txtMessage.setText(Utility.getStringFromRes(context,R.string.dispute));



        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        popupWindow.setOutsideTouchable(true);


        popupWindow.showAsDropDown(v, -230, -20);

        LinearLayout layoutRefund = popupView.findViewById(R.id.layout_refund);
        layoutRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.Logger(tag,"Dispute Now");
                showConfirmationBottomSheet(context,Utility.getStringFromRes(context,R.string.want_to_create_dispute), new ConfirmationCallback() {
                    @Override
                    public void onDone() {
                        Utility.Logger(tag,"Confirmation Done");

                        sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality("add_dispute_specific_refund_request")
                                        .setConnection(Constant.CONNECTION.ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST)
                                        .setId(refundRequestId)
                                        .setUserId(BottomNavigationSample.getUserId())))
                                .setFirstRequest(true)
                                .setLoadingText(Utility.getStringFromRes(context,R.string.adding))
                                .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.ADDING_DISPUTE_SPECIFIC_REFUND_REQUEST));

                    }

                    @Override
                    public void onCancel() {
                        Utility.Logger(tag,"Confirmation Cancel");
                    }
                });
                popupWindow.dismiss();

            }
        });

    }



}
