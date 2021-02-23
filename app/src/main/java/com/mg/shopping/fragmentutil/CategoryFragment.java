package com.mg.shopping.fragmentutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mg.shopping.activityutil.BrandDetail;
import com.mg.shopping.activityutil.CategorizedProducts;
import com.mg.shopping.adapterutil.CategoryAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcategoryutil.Datum;
import com.mg.shopping.jsonutil.allcategoryutil.DatumDetail;
import com.mg.shopping.jsonutil.allcategoryutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;

import com.mg.shopping.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryFragment extends BaseFragment {
    TextView txtMenu;
    LinearLayoutManager categoryLayoutManager;
    LinearLayoutManager subCategoryLayoutManager;
    RecyclerView recyclerViewCategory;
    RecyclerView recyclerViewSubCategory;
    CategoryAdapter categoryDataAdapter;
    CategoryAdapter subCategoryDataAdapter;
    ArrayList<Object> categoryArrayList = new ArrayList<>();
    ArrayList<Object> subCategoryArrayList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentData(); //Retrieve Intent Data
        initUI(view);  //Initialize View

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
        return R.layout.activity_home;
    }

    @Override
    protected void getIntentData() {
        //do nothing
    }

    @Override
    protected int initAdmobBannerAds() {
        return R.id.layout_ad;
    }

    @Override
    protected void initUI(View view) {

        txtMenu = view.findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(context, R.string.category));

        categoryLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recyclerViewCategory = view.findViewById(R.id.recycler_view_category);
        recyclerViewCategory.setLayoutManager(categoryLayoutManager);


        subCategoryLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerViewSubCategory = view.findViewById(R.id.recycler_view_sub_category);
        recyclerViewSubCategory.setLayoutManager(subCategoryLayoutManager);


        sendRequestToServer(new RequestObject()
                .setContext(context)
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_categories")))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_CATEGORIES));

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());


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
        categoryArrayList.clear();

        for (int i = 0; i < dtObject.getObjectList().size(); i++) {

            ListOfDatum dataObject = (ListOfDatum) dtObject.getObjectList().get(i);
            categoryArrayList.add(dataObject);

        }

        categoryDataAdapter = new CategoryAdapter(context, categoryArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {
                int pos = selectionObject.getPosition();

                if ( ((ListOfDatum)categoryArrayList.get(pos)).isSelected() ){
                    recyclerViewSubCategory.setBackgroundColor(Utility.getAttrColor(context,R.attr.colorBackgroundSecondary));
                    refreshLayout(pos);
                }
                else {
                    recyclerViewSubCategory.setBackgroundColor(Utility.getAttrColor(context,R.attr.colorBackgroundTertiary));
                }

                subCategoryArrayList.clear();
                subCategoryArrayList.addAll( ((ListOfDatum)categoryArrayList.get(pos)).getData() );

                if ( !((ListOfDatum)categoryArrayList.get(pos)).getBrand().isEmpty() ){

                    DataObject dataObject = new DataObject();
                    dataObject.setName(Utility.getStringFromRes(context,R.string.brand));
                    dataObject.setDataType(Constant.CONNECTION.LIST_OF_BRAND);
                    dataObject.setBrandArrayList( new ArrayList<>( ((ListOfDatum)categoryArrayList.get(pos)).getBrand()) );

                    subCategoryArrayList.add(dataObject);

                }

                subCategoryDataAdapter.notifyDataSetChanged();

            }
        });
        recyclerViewCategory.setAdapter(categoryDataAdapter);

        Utility.Logger(tag,"Size = "+categoryArrayList.size());

        int pos = 0;
        recyclerViewSubCategory.setBackgroundColor(Utility.getAttrColor(context,R.attr.colorBackgroundSecondary));
        ((ListOfDatum)categoryArrayList.get(pos)).setSelected(true);
        categoryDataAdapter.notifyItemChanged(pos);

        subCategoryArrayList.clear();
        subCategoryArrayList.addAll( ((ListOfDatum)categoryArrayList.get(pos)).getData() );
        if ( !((ListOfDatum)categoryArrayList.get(pos)).getBrand().isEmpty()){

            DataObject dataObject = new DataObject();
            dataObject.setName(Utility.getStringFromRes(context,R.string.brand));
            dataObject.setDataType(Constant.CONNECTION.LIST_OF_BRAND);
            dataObject.setBrandArrayList( new ArrayList<>( ((ListOfDatum)categoryArrayList.get(pos)).getBrand()) );

            subCategoryArrayList.add(dataObject);

        }

        subCategoryDataAdapter = new CategoryAdapter(context, subCategoryArrayList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                Utility.Logger(tag,selectionObject.getAction());
                int pos = selectionObject.getPosition();

                DataObject dataObject = new DataObject();
                if (selectionObject.getAction().equalsIgnoreCase("sub_category")){

                    dataObject.setCategoryId(((Datum)subCategoryArrayList.get(pos)).getId());
                    dataObject.setCategoryName(((Datum)subCategoryArrayList.get(pos)).getName());

                    ArrayList<DatumDetail> subCtgList = new ArrayList<>();
                    DatumDetail dataSubCategory = new DatumDetail();
                    dataSubCategory.setId(((Datum)subCategoryArrayList.get(pos)).getId());
                    dataSubCategory.setName(((Datum)subCategoryArrayList.get(pos)).getName());

                    subCtgList.add(dataSubCategory);
                    subCtgList.addAll(((Datum)subCategoryArrayList.get(pos)).getData());

                    dataObject.setSubCategoryList(
                            new ArrayList<>(
                                    subCtgList
                            )
                    );
                    dataObject.setCategoryType(selectionObject.getAction());
                    Intent intent = new Intent(context, CategorizedProducts.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT,dataObject);
                    startActivity(intent);

                }
                else if (selectionObject.getAction().equalsIgnoreCase("child_sub_category_id")){

                    int childPos = selectionObject.getChildPosition();

                    ArrayList<DatumDetail> subCtgList = new ArrayList<>();
                    DatumDetail dataChildSubCategory = new DatumDetail();
                    dataChildSubCategory.setId(((Datum)subCategoryArrayList.get(pos)).getId());
                    dataChildSubCategory.setName(Utility.getStringFromRes(context,R.string.all));

                    subCtgList.add(dataChildSubCategory);
                    subCtgList.addAll(((Datum)subCategoryArrayList.get(pos)).getData());

                    dataObject.setCategoryId(
                            ((Datum)subCategoryArrayList.get(pos))
                                    .getData().get(childPos).getId()
                    );
                    dataObject.setCategoryName(
                            ((Datum)subCategoryArrayList.get(pos))
                                    .getData().get(childPos).getName()

                    );
                    dataObject.setCategoryImage(
                            ((Datum)subCategoryArrayList.get(pos))
                                    .getData().get(childPos).getImage());

                    dataObject.setSubCategoryList(
                            new ArrayList<>(
                                    subCtgList
                            )
                    );

                    dataObject.setCategoryType(selectionObject.getAction());

                    Intent intent = new Intent(context, CategorizedProducts.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT,dataObject);
                    startActivity(intent);


                }
                else if (selectionObject.getAction().equalsIgnoreCase("brand")){

                    int childPos = selectionObject.getChildPosition();
                    Utility.Logger(tag,((DataObject)subCategoryArrayList.get(pos)).getBrandArrayList().get(childPos).getName());

                    Intent intent = new Intent(context, BrandDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT,((DataObject)subCategoryArrayList.get(pos)).getBrandArrayList().get(childPos));
                    startActivity(intent);


                }


            }
        });
        recyclerViewSubCategory.setAdapter(subCategoryDataAdapter);



    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);

        Utility.Logger(tag, data);

    }
    


    private void refreshLayout(int pos){

        for (int i = 0; i < categoryArrayList.size(); i++) {

            if (pos!=i){

                ((ListOfDatum)categoryArrayList.get(i)).setSelected(false);

            }

        }
        categoryDataAdapter.notifyDataSetChanged();

    }


}
