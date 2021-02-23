package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.adapterutil.CategorizedProductAdapter;
import com.mg.shopping.adapterutil.CategoryAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
import com.mg.shopping.jsonutil.specificbrandproductutil.CategoriesDatum;
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


public class SpecificBrandProduct extends BaseActivity implements View.OnClickListener {
    ImageView imageBack;
    ImageView imageStyle;
    LinearLayout layoutCategory;
    LinearLayout layoutPopular;
    LinearLayout layoutFilter;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    CategorizedProductAdapter dataAdapter;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    DataObject dataObject;
    TextView txtSearch;
    TextView txtType;
    LayoutMarginDecoration itemDecoration;
    String categoryType;

    int visibleThreshold = 1;
    boolean loading;
    String skipIds;
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList<Parcelable> categoriesDatum = new ArrayList<>();
    String brandId;
    String selection;
    String ctgId;
    static final String FUNC_RETRIEVE_STORE_PRODUCT = "retrieve_store_product";

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
        return R.layout.activity_specific_brand_product;
    }

    @Override
    protected void getIntentData() {
        brandId = getIntent().getStringExtra(Constant.IntentKey.ID);
        categoriesDatum = getIntent().getParcelableArrayListExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        selection = Utility.getStringFromRes(context, R.string.all);

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        imageStyle = (ImageView) findViewById(R.id.image_more);
        imageStyle.setVisibility(View.VISIBLE);
        imageStyle.setImageResource(R.drawable.ic_grid_item);
        imageStyle.setTag(0);

        txtSearch = (TextView) findViewById(R.id.txt_menu);
        txtSearch.setText(Utility.getStringFromRes(context, R.string.products));

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
                                        .setFunctionality(FUNC_RETRIEVE_STORE_PRODUCT)
                                        .setType(categoryType)
                                        .setSkipIds(skipIds)
                                        .setAction(txtType.getText().toString())
                                        .setCategoryId(Utility.isEmptyString(ctgId) ? null : ctgId)
                                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT)
                                        .setId(brandId)))
                                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT));

                    }

                }
            }
        });


        categoryType = "products";
        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality(FUNC_RETRIEVE_STORE_PRODUCT)
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT)
                        .setType(categoryType)
                        .setId(brandId)))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT));

        layoutCategory.setOnClickListener(this);
        layoutPopular.setOnClickListener(this);
        imageStyle.setOnClickListener(this);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT) {


                jsonObject.accumulate("brand_id", jsObject.getId());
                jsonObject.accumulate("category_type", jsObject.getType());
                jsonObject.accumulate("sorting_type", jsObject.getAction());

                if (!Utility.isEmptyString(jsObject.getType()))
                    jsonObject.accumulate("category_id", jsObject.getCategoryId());


                if (!Utility.isEmptyString(jsObject.getSkipIds()))
                    jsonObject.accumulate("skip_ids", jsObject.getSkipIds());


            }
            else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

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
            showCategoriesBottomSheet(context, new ArrayList<Object>(categoriesDatum));
        }
        if (v == layoutPopular) {
            showTypeBottomSheet(context);
        }
        if (v == imageStyle) {

            int tag = (int) imageStyle.getTag();
            switch (tag) {
                case 0:

                    imageStyle.setTag(1);
                    imageStyle.setImageResource(R.drawable.ic_list_item);
                    for (int i = 0; i < objectArrayList.size(); i++) {
                        ((ListOfDatum) objectArrayList.get(i)).setGrid(false);
                    }


                    break;
                case 1:

                    imageStyle.setTag(0);
                    imageStyle.setImageResource(R.drawable.ic_grid_item);
                    for (int i = 0; i < objectArrayList.size(); i++) {
                        ((ListOfDatum) objectArrayList.get(i)).setGrid(true);
                    }

                    break;

                default:
                    break;
            }


            tag = (int) imageStyle.getTag();
            recyclerViewProduct.removeItemDecoration(itemDecoration);
            itemDecoration = tag == 0 ? new LayoutMarginDecoration(2, Utility.dpToPx(5))
                    : new LayoutMarginDecoration(1, Utility.dpToPx(5));
            recyclerViewProduct.addItemDecoration(itemDecoration);

            layoutManager = new StaggeredGridLayoutManager(tag == 0 ? 2 : 1, LinearLayoutManager.VERTICAL);
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

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT) {

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

        categoriesObjectArrayList.add(new DataObject()
                .setCategoryId(brandId)
                .setCategoryName(Utility.getStringFromRes(context, R.string.all))
                .setSelection(selection.equalsIgnoreCase(Utility.getStringFromRes(context, R.string.all)))
                .setLastItem(false)
                .setDataType(Constant.CONNECTION.LIST_OF_CATEGORIES));

        for (int i = 0; i < objectList.size(); i++) {

            CategoriesDatum datumCategory = (CategoriesDatum) objectList.get(i);

            categoriesObjectArrayList.add(new DataObject()
                    .setCategoryId(datumCategory.getId())
                    .setCategoryName(datumCategory.getName())
                    .setSelection(selection.equalsIgnoreCase(datumCategory.getName()))
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

                ctgId = dataObjectCategory.getCategoryId();
                selection = dataObjectCategory.getCategoryName();
                categoryType = pos <= 0 ? "products" : "sub_category";
                sendRequestToServer(new RequestObject()
                        .setJson(getJson(new JsonObject()
                                .setFunctionality(FUNC_RETRIEVE_STORE_PRODUCT)
                                .setType(categoryType)
                                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT)
                                .setId(brandId)
                                .setCategoryId(pos <= 0 ? "null" : dataObjectCategory.getCategoryId())))
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setFirstRequest(true)
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT));

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
                                .setFunctionality(FUNC_RETRIEVE_STORE_PRODUCT)
                                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT)
                                .setType(categoryType)
                                .setId(brandId)
                                .setCategoryId(Utility.isEmptyString(ctgId) ? null : ctgId)
                                .setAction(dataObjects.getCategoryName())))
                        .setFirstRequest(true)
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_PRODUCT));


            }
        });
        recyclerViewList.setAdapter(dataAdapterType);


    }


}




