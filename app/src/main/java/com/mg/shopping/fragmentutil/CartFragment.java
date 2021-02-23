package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.activityutil.Billing;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.adapterutil.CartAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.ConfirmationCallback;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.cartproductutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.ProgressObject;
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


public class CartFragment extends BaseFragment implements View.OnClickListener {
    ImageView imageBack;
    TextView txtMenu;
    RoundKornerLinearLayout layoutAddToCart;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    CartAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    TextView txtPrice;
    TextView txtCurrencySymbol;


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
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void getIntentData() {
            // do nothing , needed in case of receiving intent data
    }

    @Override
    protected void initUI(View view) {

        imageBack = view.findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.GONE);

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.cart));

        txtPrice = view.findViewById(R.id.txt_price);
        txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);

        layoutAddToCart = view.findViewById(R.id.layout_add_to_cart);

        objectArrayList.clear();

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewProduct = view.findViewById(R.id.recycler_view_cart);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new CartAdapter(context, objectArrayList);
        dataAdapter.setSelectionInterface(new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag, selectionObject.getAction());

                final int pos = selectionObject.getPosition();
                final ListOfDatum datum = (ListOfDatum) objectArrayList.get(pos);
                switch (selectionObject.getAction()){
                    case "delete_items":

                        showConfirmationBottomSheet(context, Utility.getStringFromRes(context, R.string.want_delete_item), new ConfirmationCallback() {
                            @Override
                            public void onDone() {

                                sendRequestToServer(new RequestObject()
                                        .setJson(getJson(new JsonObject()
                                                .setFunctionality("delete_product_from_cart")
                                                .setConnection(Constant.CONNECTION.DELETE_ITEM_FROM_CART)
                                                .setId(datum.getId())))
                                        .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                                        .setConnection(Constant.CONNECTION.DELETE_ITEM_FROM_CART));

                                objectArrayList.remove(pos);
                                dataAdapter.notifyDataSetChanged();
                                calculateTotalPrice();

                            }

                            @Override
                            public void onCancel() {
                                // do nothing
                            }
                        });


                        break;
                    case "selected":
                    case "unselected":

                        String status = selectionObject.getAction().equalsIgnoreCase("selected") ? "0" : "1";

                        sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality("update_product_quantity_delete")
                                        .setConnection(Constant.CONNECTION.CHANGE_PRODUCT_QUANTITY_DELETE)
                                        .setId(datum.getId())
                                        .setQuantity(status)
                                        .setType("update_cart_product")))
                                .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                                .setConnection(Constant.CONNECTION.CHANGE_PRODUCT_QUANTITY_DELETE));

                        calculateTotalPrice();  //calculate total price of added product in cart

                        break;
                    default:

                        sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality("update_product_quantity_delete")
                                        .setConnection(Constant.CONNECTION.CHANGE_PRODUCT_QUANTITY_DELETE)
                                        .setId(datum.getId())
                                        .setQuantity(String.valueOf(selectionObject.getQuantity()))
                                        .setType("update")))
                                .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                                .setConnection(Constant.CONNECTION.CHANGE_PRODUCT_QUANTITY_DELETE));

                        ((ListOfDatum) objectArrayList.get(pos)).setQuantity(String.valueOf(selectionObject.getQuantity()));
                        calculateTotalPrice();  //calculate total price of added product in cart

                        break;
                }

            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);
        recyclerViewProduct.addItemDecoration(new LayoutMarginDecoration(4));




        layoutAddToCart.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_PRODUCT_OF_CART) {

                jsonObject.accumulate("user_id", jsObject.getUserId());

            } else if (jsObject.getConnection() == Constant.CONNECTION.CHANGE_PRODUCT_QUANTITY_DELETE) {

                jsonObject.accumulate("id", jsObject.getId());
                jsonObject.accumulate("quantity", jsObject.getQuantity());
                jsonObject.accumulate("type", jsObject.getType());


            } else if (jsObject.getConnection() == Constant.CONNECTION.DELETE_ITEM_FROM_CART) {

                jsonObject.accumulate("cart_id", jsObject.getId());

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
        if (v == layoutAddToCart) {


            if (objectArrayList.isEmpty()) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.empty_cart)
                        , Toast.LENGTH_SHORT);

                return;
            }

            Intent intent = new Intent(context, Billing.class);
            startActivity(intent);
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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_PRODUCT_OF_CART) {

            objectArrayList.addAll(dtObject.getObjectList());
            dataAdapter.notifyDataSetChanged();
            calculateTotalPrice();

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);

        objectArrayList.clear();
        dataAdapter.notifyDataSetChanged();

        txtPrice.setText(null);
        txtCurrencySymbol.setText(null);

    }

    @Override
    public void onResume() {
        super.onResume();

        objectArrayList.clear();
        objectArrayList.add(new ProgressObject());
        dataAdapter.notifyDataSetChanged();

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_cart_product")
                        .setConnection(Constant.CONNECTION.RETRIEVE_PRODUCT_OF_CART)
                        .setUserId(BottomNavigationSample.getUserId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_PRODUCT_OF_CART));

    }

    /**
     * <p>It is used to calculate total price of items
     * added in cart</p>
     */
    private void calculateTotalPrice() {

        double totalBill = 0.0;

        for (int i = 0; i < objectArrayList.size(); i++) {

            ListOfDatum cartDatum = (ListOfDatum) objectArrayList.get(i);

            if (cartDatum.isSelection())
                totalBill += Double.parseDouble(cartDatum.getPrice()) * Double.parseDouble(cartDatum.getQuantity());

        }

        txtPrice.setText(String.valueOf(Utility.round(totalBill, 2)));
        txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());


    }


}




