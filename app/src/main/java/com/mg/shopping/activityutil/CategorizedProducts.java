package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.adapterutil.CategoryAdapter;
import com.mg.shopping.adapterutil.CategorizedProductAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcategoryutil.DatumDetail;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class CategorizedProducts extends BaseActivity implements View.OnClickListener {
    ImageView imageBack;
    private ImageView imageStyle;
    private LinearLayout layoutCategory;
    private LinearLayout layoutPopular;
    LinearLayout layoutFilter;
    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView recyclerViewProduct;
    private CategorizedProductAdapter dataAdapter;
    private ArrayList<Object> objectArrayList = new ArrayList<>();
    DataObject dataObject;
    private TextView txtSearch;
    private TextView txtType;
    private LayoutMarginDecoration itemDecoration;
    private String categoryType;

    int visibleThreshold = 1;
    private boolean loading;
    private String skipIds;
    private StringBuilder stringBuilder = new StringBuilder();
    private String ctgId;

    String funcRetrieveCategorizedProduct = "retrieve_categorized_product";

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
        return R.layout.activity_categorized_product;
    }

    @Override
    protected void getIntentData() {
        dataObject = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageStyle = (ImageView) findViewById(R.id.image_style);
        imageStyle.setTag(0);

        txtSearch = (TextView) findViewById(R.id.edit_search);
        txtSearch.setText(dataObject.getCategoryName());

        txtType = findViewById(R.id.txt_type);

        layoutCategory = (LinearLayout) findViewById(R.id.layout_category);
        layoutPopular = (LinearLayout) findViewById(R.id.layout_popular);
        layoutFilter = (LinearLayout) findViewById(R.id.layout_filter);

        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewProduct = (RecyclerView) findViewById(R.id.recycler_view_product);
        recyclerViewProduct.setLayoutManager(layoutManager);

        dataAdapter = new CategorizedProductAdapter(context, objectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                Utility.Logger(tag, selectionObject.getAction());
                int pos = selectionObject.getPosition();
                ListOfDatum productDatum = (ListOfDatum) objectArrayList.get(pos);

                if (selectionObject.getAction().equalsIgnoreCase("product_cart")) {

                    sendRequestToServer(new RequestObject()
                            .setJson(getJson(new JsonObject()
                                    .setFunctionality("retrieve_specific_product")
                                    .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT)
                                    .setId(productDatum.getId())))
                            .setLoadingText(Utility.getStringFromRes(context, R.string.loading))
                            .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                            .setConnectionType(Constant.CONNECTION_TYPE.UI)
                            .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT));

                } else {

                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, productDatum);
                    startActivity(intent);


                }
            }
        });
        recyclerViewProduct.setAdapter(dataAdapter);

        int tag = (int) imageStyle.getTag();
        itemDecoration = tag == 0 ? new LayoutMarginDecoration(2, Utility.dpToPx(5))
                : new LayoutMarginDecoration(1, Utility.dpToPx(5));
        recyclerViewProduct.addItemDecoration(itemDecoration);

        recyclerViewProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);
                    int lastVisibleItem = getLastVisibleItem(lastVisibleItemPositions);
                    int totalItemCount = layoutManager.getItemCount();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loading = true;


                        skipIds = stringBuilder.toString();
                        sendRequestToServer(new RequestObject()
                                .setJson(getJson(new JsonObject()
                                        .setFunctionality(funcRetrieveCategorizedProduct)
                                        .setType(categoryType)
                                        .setFeatureType(txtType.getText().toString())
                                        .setSkipIds(skipIds)
                                        .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS)
                                        .setId(dataObject.getCategoryId())))
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS));

                    }

                }
            }
        });


        categoryType = dataObject.getCategoryType();
        ctgId = dataObject.getCategoryId();
        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality(funcRetrieveCategorizedProduct)
                        .setType(categoryType)
                        .setFeatureType(/*txtType.getText().toString()*/"null")
                        .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS)
                        .setId(ctgId)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS));

        layoutCategory.setOnClickListener(this);
        layoutPopular.setOnClickListener(this);
        imageStyle.setOnClickListener(this);


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS) {


                jsonObject.accumulate("category_id", jsObject.getId());
                jsonObject.accumulate("sub_ctgId", jsObject.getCategoryId());
                jsonObject.accumulate("category_type", jsObject.getType());
                jsonObject.accumulate("feature_type", jsObject.getFeatureType());

                if (!Utility.isEmptyString(jsObject.getSkipIds()))
                    jsonObject.accumulate("skip_ids", jsObject.getSkipIds());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

                jsonObject.accumulate("product_id", jsObject.getId());


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
        if (v == layoutCategory) {
            showCategoriesBottomSheet(context, new ArrayList<Object>(dataObject.getSubCategoryList()));
        }
        if (v == layoutPopular) {
            showTypeBottomSheet(context);
        }
        if (v == imageStyle) {

            int tag = (int) imageStyle.getTag();
            if (tag == 0) {
                imageStyle.setTag(1);
                imageStyle.setImageResource(R.drawable.ic_list_item);
                for (int i = 0; i < objectArrayList.size(); i++) {
                    ((ListOfDatum) objectArrayList.get(i)).setGrid(false);
                }


            } else if (tag == 1) {
                imageStyle.setTag(0);
                imageStyle.setImageResource(R.drawable.ic_grid_item);
                for (int i = 0; i < objectArrayList.size(); i++) {
                    ((ListOfDatum) objectArrayList.get(i)).setGrid(true);
                }
            }

            tag = (int) imageStyle.getTag();
            recyclerViewProduct.removeItemDecoration(itemDecoration);
            itemDecoration = tag == 0 ? new LayoutMarginDecoration(2, Utility.dpToPx(5))
                    : new LayoutMarginDecoration(1, Utility.dpToPx(5));
            recyclerViewProduct.addItemDecoration(itemDecoration);

            layoutManager = new StaggeredGridLayoutManager(getStaggeredManagerSpanCount(tag), LinearLayoutManager.VERTICAL);
            recyclerViewProduct.setLayoutManager(layoutManager);

            dataAdapter.notifyDataSetChanged();
            recyclerViewProduct.invalidateItemDecorations();
            recyclerViewProduct.invalidate();

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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS) {

            loading = false;
            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                objectArrayList.add(dtObject.getObjectList().get(i));

                stringBuilder.append("'");
                stringBuilder.append(((ListOfDatum) dtObject.getObjectList().get(i)).getId());
                stringBuilder.append("'");

                if (i < (dtObject.getObjectList().size() - 1))
                    stringBuilder.append(",");


            }
            dataAdapter.notifyDataSetChanged();


        } else if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

            BottomNavigationSample.showProductCartBottomSheet(context, dtObject);

        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    private int getStaggeredManagerSpanCount(int tag){
        return tag == 0 ? 2 : 1;
    }
    
    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            maxSize = lastVisibleItemPositions[i];
        }
        return maxSize;
    }

    private void showCategoriesBottomSheet(Context context, ArrayList<Object> objectList) {

        final ArrayList<Object> categoriesObjectArrayList = new ArrayList<>();
        final View view = LayoutInflater.from(context).inflate(R.layout.categorized_item_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        TextView txtLabel = view.findViewById(R.id.txt_label);
        txtLabel.setText(Utility.getStringFromRes(context, R.string.categories));

        for (int i = 0; i < objectList.size(); i++) {

            DatumDetail data = (DatumDetail) objectList.get(i);

            categoriesObjectArrayList.add(new DataObject()
                    .setCategoryId(data.getId())
                    .setCategoryName(data.getName())
                    .setCategoryImage(data.getImage())
                    .setSelection(txtSearch.getText().toString().equalsIgnoreCase(data.getName()))
                    .setLastItem((i == (objectList.size() - 1)))
                    .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        }

        RecyclerView recyclerViewList = view.findViewById(R.id.recycler_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        CategoryAdapter dataAdapterCategories = new CategoryAdapter(context, categoriesObjectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                int pos = selectionObject.getPosition();
                DataObject dataObjectCategory = (DataObject) categoriesObjectArrayList.get(pos);
                txtSearch.setText(dataObjectCategory.getCategoryName());
                ctgId = dataObjectCategory.getCategoryId();

                categoryType = pos <= 0 ? "sub_category" : "child_sub_category_id";
                sendRequestToServer(new RequestObject()
                        .setJson(getJson(new JsonObject()
                                .setFunctionality(funcRetrieveCategorizedProduct)
                                .setType(categoryType)
                                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS)
                                .setId(dataObjectCategory.getCategoryId())))
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setFirstRequest(true)
                        .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS));

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });
        recyclerViewList.setAdapter(dataAdapterCategories);


    }

    private void showTypeBottomSheet(final Context context) {

        final ArrayList<Object> typeObjectArrayList = new ArrayList<>();
        final View view = LayoutInflater.from(context).inflate(R.layout.categorized_item_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        final TextView txtLabel = view.findViewById(R.id.txt_label);
        txtLabel.setText(Utility.getStringFromRes(context, R.string.sorting));

        typeObjectArrayList.add(new DataObject()
                .setCategoryName(Utility.getStringFromRes(context, R.string.popular))
                .setSelection(txtType.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.popular)))
                .setLastItem(false)
                .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        typeObjectArrayList.add(new DataObject()
                .setCategoryName(Utility.getStringFromRes(context, R.string.most_reviews))
                .setSelection(txtType.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.most_reviews)))
                .setLastItem(false)
                .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        typeObjectArrayList.add(new DataObject()
                .setCategoryName(Utility.getStringFromRes(context, R.string.newest))
                .setSelection(txtType.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.newest)))
                .setLastItem(false)
                .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        typeObjectArrayList.add(new DataObject()
                .setCategoryName(Utility.getStringFromRes(context, R.string.low_price))
                .setSelection(txtType.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.low_price)))
                .setLastItem(false)
                .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        typeObjectArrayList.add(new DataObject()
                .setCategoryName(Utility.getStringFromRes(context, R.string.high_price))
                .setSelection(txtType.getText().toString().equalsIgnoreCase(Utility.getStringFromRes(context, R.string.high_price)))
                .setLastItem(true)
                .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        RecyclerView recyclerViewList = view.findViewById(R.id.recycler_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        CategoryAdapter dataAdapterType = new CategoryAdapter(context, typeObjectArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                int pos = selectionObject.getPosition();
                DataObject dataObjects = (DataObject) typeObjectArrayList.get(pos);
                txtType.setText(dataObjects.getCategoryName());

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();
                
                sendRequestToServer(new RequestObject()
                        .setJson(getJson(new JsonObject()
                                .setFunctionality(funcRetrieveCategorizedProduct)
                                .setType(categoryType)
                                .setFeatureType(txtType.getText().toString())
                                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS)
                                .setId(ctgId)
                                .setCategoryId(dataObject.getCategoryId())))
                        .setFirstRequest(true)
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIZED_PRODUCTS));


            }
        });
        recyclerViewList.setAdapter(dataAdapterType);


    }
    


}




