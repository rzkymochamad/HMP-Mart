package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.activityutil.AddRefund;
import com.mg.shopping.activityutil.AddReview;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.adapterutil.OrderHistoryAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.orderhistoryutil.ListOfDatum;
import com.mg.shopping.jsonutil.orderhistoryutil.ListOfProduct;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OrderFragment extends BaseFragment{
    ArrayList<Object> objectArrayList = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder();
    int visibleThreshold = 1;
    boolean loading;
    String skipIds;
    PrefObject prefObject;
    LinearLayout layoutScroller;
    String type;


    /**
     * <p>It is used to get Fragment Instance for using in Pager</p>
     *
     * @param
     * @return
     */
    public static Fragment getFragmentInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.IntentKey.DATA_OBJECT, type);
        Fragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData();
        initUI(view); //Initialize UI

    }

    @Override
    protected String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    protected Context getContextInstance() {
        return getActivity();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_history_fragment;
    }

    @Override
    protected void getIntentData() {
        type = getArguments().getString(Constant.IntentKey.DATA_OBJECT,"0");
    }

    @Override
    protected void initUI(View view) {

        layoutScroller = view.findViewById(R.id.layout_scroller);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_history")
                        .setUserId(BottomNavigationSample.getUserId())
                        .setType(type)
                        .setConnection(Constant.CONNECTION.RETRIEVE_ORDER_HISTORY)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_ORDER_HISTORY));



    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());
            jsonObject.accumulate("user_id", jsObject.getUserId());
            jsonObject.accumulate("type", jsObject.getType());

            if (!Utility.isEmptyString(jsObject.getSkipIds()))
                jsonObject.accumulate("skip_ids", jsObject.getSkipIds());


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

        final DataObject dtObject = (DataObject) data;
        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }
        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_ORDER_HISTORY) {

            loading = false;
            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                objectArrayList.add(dtObject.getObjectList().get(i));

                stringBuilder.append("'");
                stringBuilder.append(((ListOfDatum) dtObject.getObjectList().get(i)).getId());
                stringBuilder.append("'");

                if (i < (dtObject.getObjectList().size() - 1))
                    stringBuilder.append(",");


            }
            for (int i = 0; i < objectArrayList.size(); i++) {

                final ListOfDatum datum = (ListOfDatum) objectArrayList.get(i);
                View orderHistoryCustomView = LayoutInflater.from(context).inflate(R.layout.order_history_item_layout,null);

                RoundedImageView imageCourier = (RoundedImageView) orderHistoryCustomView.findViewById(R.id.image_courier);
                final ImageView imageOther = orderHistoryCustomView.findViewById(R.id.image_other);
                final ScaleRatingBar ratingBar = orderHistoryCustomView.findViewById(R.id.simpleRatingBar5);
                TextView txtOrderNo = (TextView) orderHistoryCustomView.findViewById(R.id.txt_order_no);
                TextView txtCourierCompany = (TextView) orderHistoryCustomView.findViewById(R.id.txt_courier_company);
                TextView txtDeliveryDays = (TextView) orderHistoryCustomView.findViewById(R.id.txt_delivery_days);
                TextView txtTotalOrderFee = (TextView) orderHistoryCustomView.findViewById(R.id.txt_total_order_fee);
                TextView txtShippingFee = (TextView) orderHistoryCustomView.findViewById(R.id.txt_shipping_fee);
                TextView txtTotalBill = (TextView) orderHistoryCustomView.findViewById(R.id.txt_total_bill);

                final LinearLayout layoutAddReview = orderHistoryCustomView.findViewById(R.id.layout_add_review);
                GridLayoutManager layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false);
                RecyclerView recyclerViewProduct = (RecyclerView) orderHistoryCustomView.findViewById(R.id.recycler_view_product);
                recyclerViewProduct.setLayoutManager(layoutManager);

                OrderHistoryAdapter dataAdapter = new OrderHistoryAdapter(context, new ArrayList<Object>(datum.getListOfProduct()), new SelectionInterface() {
                    @Override
                    public void onSelection(SelectionObject selectionObject) {

                        Utility.Logger(tag, selectionObject.getAction());

                    }
                });
                recyclerViewProduct.setAdapter(dataAdapter);

                LayoutMarginDecoration itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(2));
                recyclerViewProduct.addItemDecoration(itemDecoration);

                txtOrderNo.setText("#"+datum.getOrderNo());

                txtCourierCompany.setText(datum.getCourierName()+" - "+datum.getCourierService());
                txtDeliveryDays.setText(datum.getCourierDeliveryDay()+" "+Utility.getStringFromRes(context,R.string.business_days));
                GlideApp.with(context).load(BottomNavigationSample.getShippingPicture(datum.getCourierLogo())).into(imageCourier);

                txtTotalOrderFee.setText(datum.getTotalBill());
                txtShippingFee.setText(datum.getShippingFee());

                double totalBill = Double.parseDouble(datum.getTotalBill()) + Double.parseDouble(datum.getShippingFee());
                txtTotalBill.setText(String.valueOf(totalBill));

                imageOther.setTag(i);
                imageOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopup(v,new ArrayList<Object>(datum.getListOfProduct()));
                    }
                });

                layoutAddReview.setTag(i);
                layoutAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAvailableProductBottomSheet(context,String.valueOf(ratingBar.getRating()),new ArrayList<Object>(datum.getListOfProduct()),"review");

                    }
                });



                layoutScroller.addView(orderHistoryCustomView);


            }

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        Utility.Logger(tag,"Size = "+objectArrayList.size());


    }

    /* <p> you should refer to a view to stick your popup wherever u want.
     **/
    public void showPopup(View v, final List<Object> objectArrayList) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.order_report_item_layout, null);

        LinearLayout layoutRefund = popupView.findViewById(R.id.layout_refund);
        layoutRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.Logger(tag,"Refund Now");
                showAvailableProductBottomSheet(context,"0",objectArrayList,"refund");

            }
        });

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOutsideTouchable(true);

        popupWindow.showAsDropDown(v, -230, -20);
    }

    private void showAvailableProductBottomSheet(final Context context, final String rating, final List<Object> objectArrayList, final String type) {

        final View view = LayoutInflater.from(context).inflate(R.layout.product_in_order_item_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        GridLayoutManager layoutManager = new GridLayoutManager(context,1,RecyclerView.VERTICAL,false);
        RecyclerView recyclerViewProduct = view.findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        OrderHistoryAdapter dataAdapter = new OrderHistoryAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag,selectionObject.getAction()+" Dialog View");
                int pos = selectionObject.getPosition();

                ListOfProduct product = (ListOfProduct) objectArrayList.get(pos);
                Utility.Logger(tag,"ID = "+product.getId());

                if (type.equalsIgnoreCase("refund")){

                    Intent intent = new Intent(context, AddRefund.class);
                    intent.putExtra(Constant.IntentKey.TYPE,"1");
                    intent.putExtra(Constant.IntentKey.ID,product.getId());
                    intent.putExtra(Constant.IntentKey.BRAND_ID,product.getBrandId());
                    startActivity(intent);

                    return;
                }

                Intent intent = new Intent(context, AddReview.class);

                if (Double.parseDouble(product.getRating())>0){
                    intent.putExtra(Constant.IntentKey.TYPE,"0");
                }
                else {
                    intent.putExtra(Constant.IntentKey.TYPE,"1");
                }

                intent.putExtra(Constant.IntentKey.RATING,rating);
                intent.putExtra(Constant.IntentKey.ID,product.getId());
                intent.putExtra(Constant.IntentKey.BRAND_ID,product.getBrandId());
                intent.putExtra(Constant.IntentKey.PRODUCT_ID,product.getProductID());
                startActivity(intent);

            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        LayoutMarginDecoration itemDecoration = new LayoutMarginDecoration(1, Utility.dpToPx(2));
        recyclerViewProduct.addItemDecoration(itemDecoration);


    }



}




