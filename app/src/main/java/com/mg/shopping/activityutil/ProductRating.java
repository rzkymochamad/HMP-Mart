package com.mg.shopping.activityutil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mg.shopping.adapterutil.ReviewAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConfirmationCallback;
import com.mg.shopping.interfaceutil.ConnectionCallback;
import com.mg.shopping.interfaceutil.SelectionInterface;

import com.mg.shopping.jsonutil.productratingutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductRating extends BaseActivity implements View.OnClickListener{
    ImageView imageBack;
    TextView txtMenu;
    GridLayoutManager layoutManager;
    RecyclerView recyclerViewRating;
    ReviewAdapter dataAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    private DataObject dataObject;


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
        return R.layout.activity_product_rating;
    }

    @Override
    protected void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context,R.string.reviews_rating));

        layoutManager = new GridLayoutManager(context,1,LinearLayoutManager.VERTICAL,false);
        recyclerViewRating = findViewById(R.id.recycler_view_rating);
        recyclerViewRating.setLayoutManager(layoutManager);

        dataAdapter = new ReviewAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag,selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum datum = (ListOfDatum) objectArrayList.get(pos);
                if (selectionObject.getAction().equalsIgnoreCase("other_selected")){
                    showPopup(selectionObject.getView(),datum.getId(),String.valueOf(pos));
                }
            }
        });
        recyclerViewRating.setAdapter(dataAdapter);
        recyclerViewRating.addItemDecoration(new LayoutMarginDecoration(1, Utility.dpToPx(5)));


        if (dataObject.getType().equalsIgnoreCase("product_rating")){

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("retrieve_specific_product_rating")
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_REVIEWS)
                            .setId(dataObject.getProductId())))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_REVIEWS));

        }
        else  if (dataObject.getType().equalsIgnoreCase("user_rating")){

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("retrieve_specific_user_review")
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REVIEWS_RATING)
                            .setUserId(BottomNavigationSample.getUserId())))
                    .setConnectionType(Constant.CONNECTION_TYPE.UI)
                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REVIEWS_RATING));

        }


        imageBack.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_REVIEWS) {

                jsonObject.accumulate("product_id", jsObject.getId());

            }
            else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REVIEWS_RATING) {

                jsonObject.accumulate("user_id", jsObject.getUserId());

            }
            else if (jsObject.getConnection() == Constant.CONNECTION.DELETE_SPECIFIC_USER_REVIEWS) {

                jsonObject.accumulate("review_id", jsObject.getId());

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
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        DataObject dtObject = (DataObject) data;
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT_REVIEWS) {

            objectArrayList.addAll(dtObject.getObjectList());
            dataAdapter.notifyDataSetChanged();

        }
        else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_USER_REVIEWS_RATING) {

            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                ListOfDatum datum = (ListOfDatum) dtObject.getObjectList().get(i);
                datum.setUser(true);
                objectArrayList.add(datum);
            }
            dataAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    /* <p> you should refer to a view to stick your popup wherever u want.
     **/
    public void showPopup(View v, final String review_id, final String position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.order_report_item_layout, null);

        TextView txtMessage = popupView.findViewById(R.id.txt_message);
        txtMessage.setText(Utility.getStringFromRes(context,R.string.delete));

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v, -230, -50);

        LinearLayout layoutRefund = popupView.findViewById(R.id.layout_refund);
        layoutRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.Logger(tag,"Dispute Now");
                popupWindow.dismiss();
                showConfirmationBottomSheet(context,Utility.getStringFromRes(context,R.string.want_to_delete_review), new ConfirmationCallback() {
                    @Override
                    public void onDone() {
                        Utility.Logger(tag,"Confirmation Done");

                        management.sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality("delete_specific_review")
                                        .setConnection(Constant.CONNECTION.DELETE_SPECIFIC_USER_REVIEWS)
                                        .setId(review_id)))
                                .setFirstRequest(true)
                                .setLoadingText(Utility.getStringFromRes(context,R.string.delete))
                                .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.DELETE_SPECIFIC_USER_REVIEWS)
                                .setConnectionCallback(new ConnectionCallback() {
                                    @Override
                                    public void onSuccess(Object data, RequestObject requestObject) {
                                        Utility.Logger(tag,"Delete OnSuccess");
                                        objectArrayList.remove(Integer.parseInt(position));
                                        dataAdapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onError(String data, RequestObject requestObject) {
                                        Utility.Logger(tag,data);
                                    }
                                }));

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




