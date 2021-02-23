package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mg.shopping.adapterutil.BrandAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.databaseutil.DatabaseObject;
import com.mg.shopping.databaseutil.DbConstraint;
import com.mg.shopping.databaseutil.TypeConstraint;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcategoryutil.Brand;
import com.mg.shopping.jsonutil.specificbrandproductutil.Datum;
import com.mg.shopping.jsonutil.specificbrandproductutil.LatestDatum;
import com.mg.shopping.jsonutil.specificbrandproductutil.ListOfDatum;
import com.mg.shopping.jsonutil.specificbrandproductutil.SaleDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class BrandDetail extends BaseActivity implements View.OnClickListener {
    ImageView imageBack;
    ImageView imageOther;
    TextView txtMenu;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    Brand brand;
    LinearLayout layoutScroller;
    ImageView imageCover;
    RoundedImageView imageBrand;
    TextView txtBrand;
    TextView txtItem;
    TextView txtReviews;
    AppCompatRatingBar ratingStore;
    LinearLayout layoutFollow;
    ImageView imageTick;
    TextView txtFollow;
    HashMap<String, DataObject> storeHashmap = new HashMap<>();
    HashMap<String, DataObject> storeFavouriteHashmap = new HashMap<>();
    PrefObject prefObject;

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
        return R.layout.activity_brand_detail;
    }

    @Override
    protected void getIntentData() {
        brand = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setImageResource(R.drawable.ic_back);
        imageBack.setVisibility(View.VISIBLE);

        imageOther = findViewById(R.id.image_other);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(brand.getName());

        layoutScroller = (LinearLayout) findViewById(R.id.layout_scroller);

        layoutFollow = findViewById(R.id.layout_follow);
        txtFollow = findViewById(R.id.txt_follow);
        imageTick = findViewById(R.id.image_tick);

        imageCover = (ImageView) findViewById(R.id.image_cover);
        imageBrand = (RoundedImageView) findViewById(R.id.image_brand);

        txtBrand = (TextView) findViewById(R.id.txt_brand);
        txtItem = (TextView) findViewById(R.id.txt_item);
        txtReviews = (TextView) findViewById(R.id.txt_reviews);

        ratingStore = (AppCompatRatingBar) findViewById(R.id.rating_store);

        txtBrand.setText(brand.getName());
        txtItem.setText(Utility.isEmptyString(brand.getItems()) ? "0" : brand.getItems());
        txtReviews.setText(Utility.isEmptyString(brand.getReviews()) ? "0" : brand.getReviews());

        if (!Utility.isEmptyString(brand.getRating()))
            ratingStore.setRating(Float.parseFloat(brand.getRating()));
        else
            ratingStore.setRating(0);

        GlideApp.with(context).load(BottomNavigationSample.getBrandPicture(brand.getImage())).into(imageBrand);
        GlideApp.with(context).load(BottomNavigationSample.getBrandPicture(brand.getCoverPhoto())).into(imageCover);

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_specific_store_detail")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_DETAIL)
                        .setId(brand.getId())))
                .setFirstRequest(true)
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_DETAIL));


        layoutFollow.setOnClickListener(this);
        imageOther.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_DETAIL) {

                jsonObject.accumulate("brand_id", jsObject.getId());

            } else if (jsObject.getConnection() == Constant.CONNECTION.CHANGE_FOLLOW_STATUS) {

                jsonObject.accumulate("id", jsObject.getId());
                jsonObject.accumulate("type", jsObject.getType());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("action", jsObject.getAction());

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
        if (v == imageOther) {
            showPopup(imageOther);
        }
        if (v == layoutFollow) {

            if (!management.getPreferences(null).isLogin()) {

                Utility.Toaster(context,
                        Utility.getStringFromRes(context, R.string.need_login)
                        , Toast.LENGTH_SHORT);

                return;
            }

            followBrand("favourite_store");
        }
    }

    @Override
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        final DataObject dtObject = (DataObject) data;
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }
        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_STORE_DETAIL) {


            View customView = LayoutInflater.from(context).inflate(R.layout.brand_recycler_view_layout, null);
            TextView txtName = customView.findViewById(R.id.txt_name);
            txtName.setText(Utility.getStringFromRes(context, R.string.deals));

            GridLayoutManager layoutManager = new GridLayoutManager(context, 1, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerViewProduct = customView.findViewById(R.id.recycler_view_product);
            recyclerViewProduct.setLayoutManager(layoutManager);

            final ArrayList<Object> latestArraylist = new ArrayList<>(dtObject.getObjectList02());
            final ArrayList<Object> saleArraylist = new ArrayList<>(dtObject.getObjectList03());
            final ArrayList<Object> ctgArraylist = new ArrayList<>(dtObject.getObjectList04());

            BrandAdapter dataAdapter = new BrandAdapter(context, saleArraylist);
            recyclerViewProduct.setAdapter(dataAdapter);
            recyclerViewProduct.addItemDecoration(new LayoutMarginDecoration(1, Utility.dpToPx(5)));
            dataAdapter.setSelectionInterface(new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {

                    int pos = selectionObject.getPosition();
                    SaleDatum saleDatum = (SaleDatum) saleArraylist.get(pos);

                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum()
                            .setId(saleDatum.getId())
                            .setName(saleDatum.getName())
                            .setPrice(saleDatum.getPrice())
                            .setRating(saleDatum.getRating()));
                    startActivity(intent);


                }
            });

            if (!dtObject.getObjectList03().isEmpty())
                layoutScroller.addView(customView);


            View latestView = LayoutInflater.from(context).inflate(R.layout.brand_latest_recycler_view_layout, null);

            TextView txtLatestName = latestView.findViewById(R.id.txt_name);
            txtLatestName.setText(Utility.getStringFromRes(context, R.string.latest_product));

            GridLayoutManager layoutManager01 = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
            RecyclerView recyclerViewProduct01 = latestView.findViewById(R.id.recycler_view_product);
            recyclerViewProduct01.setLayoutManager(layoutManager01);

            BrandAdapter dataAdapter01 = new BrandAdapter(context, latestArraylist);
            recyclerViewProduct01.setAdapter(dataAdapter01);
            recyclerViewProduct01.addItemDecoration(new LayoutMarginDecoration(3, Utility.dpToPx(5)));
            dataAdapter01.setSelectionInterface(new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {

                    int pos = selectionObject.getPosition();
                    LatestDatum latestDatum = (LatestDatum) latestArraylist.get(pos);

                    Intent intent = new Intent(context, ProductDetail.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum()
                            .setId(latestDatum.getId())
                            .setName(latestDatum.getName())
                            .setPrice(latestDatum.getPrice())
                            .setRating(latestDatum.getRating()));
                    startActivity(intent);

                }
            });

            if (!dtObject.getObjectList02().isEmpty())
                layoutScroller.addView(latestView);


            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                ListOfDatum datum = (ListOfDatum) dtObject.getObjectList().get(i);
                View customView02 = LayoutInflater.from(context).inflate(R.layout.brand_recycler_view_layout, null);

                TextView txtName02 = customView02.findViewById(R.id.txt_name);
                txtName02.setText(datum.getCategoryName());

                StaggeredGridLayoutManager layoutManager02 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                RecyclerView recyclerViewProduct02 = customView02.findViewById(R.id.recycler_view_product);
                recyclerViewProduct02.setLayoutManager(layoutManager02);

                final ArrayList<Object> ctgProductArraylist = new ArrayList<Object>(datum.getData());

                BrandAdapter dataAdapter02 = new BrandAdapter(context, ctgProductArraylist);
                recyclerViewProduct02.setAdapter(dataAdapter02);
                recyclerViewProduct02.addItemDecoration(new LayoutMarginDecoration(2, Utility.dpToPx(5)));
                dataAdapter02.setSelectionInterface(new SelectionInterface() {
                    @Override
                    public void onSelection(SelectionObject selectionObject) {

                        int pos = selectionObject.getPosition();
                        Datum datum1 = (Datum) ctgProductArraylist.get(pos);

                        Intent intent = new Intent(context, ProductDetail.class);
                        intent.putExtra(Constant.IntentKey.DATA_OBJECT, new com.mg.shopping.jsonutil.listofproductutil.ListOfDatum()
                                .setId(datum1.getId())
                                .setName(datum1.getName())
                                .setPrice(datum1.getPrice())
                                .setRating(datum1.getRating()));
                        startActivity(intent);

                    }
                });

                if (!ctgProductArraylist.isEmpty())
                    layoutScroller.addView(customView02);

            }


            View viewAllProductView = LayoutInflater.from(context).inflate(R.layout.all_product_item_layout, null);
            LinearLayout layoutAllProduct = viewAllProductView.findViewById(R.id.layout_all_product);
            layoutAllProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Utility.Logger(tag, "Onclick");

                    Intent intent = new Intent(context, SpecificBrandProduct.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, ctgArraylist);
                    intent.putExtra(Constant.IntentKey.ID, brand.getId());
                    startActivity(intent);


                }
            });


            if (Integer.parseInt(txtItem.getText().toString()) > 0) {
                layoutScroller.addView(viewAllProductView);
            }


        }


    }

    @Override
    public void onError(String data, RequestObject requestObject) {
        super.onError(data, requestObject);
        Utility.Logger(tag, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        storeHashmap = new HashMap<>(BottomNavigationSample.getUserFollowingStore());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserId(true));

        Utility.Logger(tag, prefObject.getUserId() + " " + brand.getId());

        if (!management.getPreferences(null).isLogin()){

            return;
        }

        if (storeHashmap.containsKey(brand.getId())) {

            imageTick.setImageResource(R.drawable.ic_tick);
            txtFollow.setText(Utility.getStringFromRes(context, R.string.followed));

        } else {

            imageTick.setImageResource(R.drawable.ic_add);
            txtFollow.setText(Utility.getStringFromRes(context, R.string.follow));

        }


    }

    private void followBrand(String type) {

        storeHashmap = new HashMap<>(BottomNavigationSample.getUserFollowingStore());
        if (storeHashmap.containsKey(brand.getId())) {

            imageTick.setImageResource(R.drawable.ic_add);
            txtFollow.setText(Utility.getStringFromRes(context, R.string.follow));

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(TypeConstraint.BRAND)
                    .setDbOperation(DbConstraint.DELETE_SPECIFIC_USER_FOLLOWING)
                    .setDataObject(new DataObject()
                            .setId(storeHashmap.get(brand.getId()).getId())));

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("change_follow_status")
                            .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS)
                            .setId(brand.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(type)
                            .setAction("delete")))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS));

        } else {

            imageTick.setImageResource(R.drawable.ic_tick);
            txtFollow.setText(Utility.getStringFromRes(context, R.string.followed));

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(TypeConstraint.BRAND)
                    .setDbOperation(DbConstraint.INSERT_NEW_BRAND_FOLLOWING)
                    .setDataObject(new DataObject()
                            .setId(brand.getId())
                            .setUserId(prefObject.getUserId())));

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("change_follow_status")
                            .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS)
                            .setId(brand.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(type)
                            .setAction("insert")))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS));

        }

    }

    private void favouritesBrand(String type) {

        storeFavouriteHashmap = new HashMap<>(BottomNavigationSample.getUserFavouriteStore());
        if (storeFavouriteHashmap.containsKey(brand.getId())) {


            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(TypeConstraint.FAVOURITES)
                    .setDbOperation(DbConstraint.DELETE_SPECIFIC_USER_FAVOURITES)
                    .setDataObject(new DataObject()
                            .setId(storeFavouriteHashmap.get(brand.getId()).getId())));

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("change_favourite_status")
                            .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS)
                            .setId(brand.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(type)
                            .setAction("delete")))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS));

        } else {

            imageTick.setImageResource(R.drawable.ic_tick);
            txtFollow.setText(Utility.getStringFromRes(context, R.string.followed));

            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(TypeConstraint.FAVOURITES)
                    .setDbOperation(DbConstraint.INSERT_NEW_FAVOURITES_STORE)
                    .setDataObject(new DataObject()
                            .setId(brand.getId())
                            .setUserId(prefObject.getUserId())));

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("change_favourite_status")
                            .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS)
                            .setId(brand.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(type)
                            .setAction("insert")))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.CHANGE_FOLLOW_STATUS));

        }

    }


    /* <p> you should refer to a view to stick your popup wherever u want.
     **/
    public void showPopup(View v) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_add_category_layout, null);

        LinearLayout layoutHome = popupView.findViewById(R.id.layout_home);
        layoutHome.setVisibility(View.GONE);

        LinearLayout layoutWishlist = popupView.findViewById(R.id.layout_wishlist);
        TextView txtWishlist = popupView.findViewById(R.id.txt_wishlist);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        popupWindow.setOutsideTouchable(true);





        if (management.getPreferences(null).isLogin()){

            storeFavouriteHashmap = new HashMap<>(BottomNavigationSample.getUserFavouriteStore());
            if (storeFavouriteHashmap.containsKey(brand.getId())) {

                txtWishlist.setText(Utility.getStringFromRes(context,
                        R.string.wishlisted));

            } else {

                txtWishlist.setText(Utility.getStringFromRes(context,
                        R.string.wishlist));

            }

        }
        else {
            txtWishlist.setText(Utility.getStringFromRes(context,
                    R.string.wishlist));
        }

        layoutWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

                if (!management.getPreferences(null).isLogin()) {

                    Utility.Toaster(context,
                            Utility.getStringFromRes(context, R.string.need_login)
                            , Toast.LENGTH_SHORT);

                    return;
                }

                favouritesBrand("favourite_store");
            }
        });

        popupWindow.showAsDropDown(v, -230, -20);
    }

}




