package com.mg.shopping.activityutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.mg.shopping.adapterutil.ProductQuickDetailAdapter;
import com.mg.shopping.adapterutil.TextualPagerAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.customutil.LockableViewPager;
import com.mg.shopping.databaseutil.DatabaseObject;
import com.mg.shopping.databaseutil.DbConstraint;
import com.mg.shopping.databaseutil.TypeConstraint;
import com.mg.shopping.fontutil.Font;
import com.mg.shopping.fragmentutil.AccountProfile;
import com.mg.shopping.fragmentutil.CartFragment;
import com.mg.shopping.fragmentutil.CategoryFragment;
import com.mg.shopping.fragmentutil.HomeFragment;
import com.mg.shopping.fragmentutil.SaleFragment;
import com.mg.shopping.interfaceutil.ConnectionCallback;
import com.mg.shopping.interfaceutil.CustomLocationCallback;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.featuredcategoriesutil.ListOfDatum;
import com.mg.shopping.jsonutil.specificproductutil.Attribute;
import com.mg.shopping.jsonutil.specificproductutil.AvailableShipping;
import com.mg.shopping.jsonutil.specificproductutil.Item;
import com.mg.shopping.locationutil.CustomLocationEngine;
import com.mg.shopping.managementutil.Management;
import com.mg.shopping.MyApplication;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.GeocodeObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.PagerTabObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.R;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class BottomNavigationSample extends BaseActivity implements
        CustomLocationCallback, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    CustomLocationEngine locationEngine;
    ArrayList<PagerTabObject> pagerList = new ArrayList<>();
    TabLayout tabMain;
    LockableViewPager viewPagerMain;
    TextualPagerAdapter textualPagerAdapter;
    ArrayList<ListOfDatum> featuredCategoryList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();  //Retrieve Intent Data
        initUI(); //Initialize UI

    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Override
    protected String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected Context getContextInstance() {
        return this;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_bottom_navigation;
    }

    @Override
    protected void getIntentData() {
        featuredCategoryList =  getIntent().getParcelableArrayListExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        Utility.Logger(tag, String.valueOf(featuredCategoryList.size()));

        locationEngine = new CustomLocationEngine(context, this);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);


        pagerList.clear();
        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context, R.string.home))
                .setIcon(R.drawable.ic_home_menu)
                .setFragment(HomeFragment.getFragmentInstance(featuredCategoryList)));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context, R.string.category))
                .setIcon(R.drawable.ic_category_menu)
                .setFragment(new CategoryFragment()));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context, R.string.sale))
                .setIcon(R.drawable.ic_sale_menu)
                .setFragment(new SaleFragment()));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context, R.string.shopping_cart))
                .setIcon(R.drawable.ic_cart_menu)
                .setFragment(new CartFragment()));

        pagerList.add(new PagerTabObject()
                .setTitle(Utility.getStringFromRes(context, R.string.profile))
                .setIcon(R.drawable.ic_profile)
                .setFragment(new AccountProfile()));

        //Initialize TabLayout & Setup with Viewpager

        tabMain = findViewById(R.id.tab_main);
        viewPagerMain = findViewById(R.id.view_pager_main);
        viewPagerMain.setSwipeLocked(true);

        textualPagerAdapter = new TextualPagerAdapter(getSupportFragmentManager(), pagerList);
        viewPagerMain.setAdapter(textualPagerAdapter);
        tabMain.setupWithViewPager(viewPagerMain);

        for (int i = 0; i < tabMain.getTabCount(); i++) {

            View customTabView = LayoutInflater.from(context).inflate(R.layout.navigation_item_layout, null);

            TextView tv = (TextView) customTabView.findViewById(R.id.txt_menu);
            tv.setTextColor(Utility.getAttrColor(context, R.attr.colorBackgroundSeptenary));
            tv.setText(Utility.capitalize(pagerList.get(i).getTitle()));

            ImageView imageMenu = customTabView.findViewById(R.id.image_menu);
            imageMenu.setImageResource(pagerList.get(i).getIcon());


            if (i <= 0) {

                tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextPrimary));
                tv.setTypeface(Font.ubuntuMediumFont(context));
                imageMenu.setColorFilter(Utility.getAttrColor(this, R.attr.colorTextPrimary));
            }
            else {

                tv.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
                tv.setTypeface(Font.ubuntuLightFont(context));
                imageMenu.setColorFilter(Utility.getAttrColor(this, R.attr.colorTextQuinary));
            }

            tabMain.getTabAt(i).setCustomView(customTabView);

        }


        viewPagerMain.addOnPageChangeListener(this);

    }

    @Override
    protected String getJson(JsonObject jsObject) {
        return null;
    }


    public static String getProductBottomSheetJson(JsonObject jsObject) {
        String json = "";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            if (jsObject.getConnection() == Constant.CONNECTION.ADD_PRODUCT_INTO_CART) {

                jsonObject.accumulate("functionality", jsObject.getFunctionality());
                jsonObject.accumulate("brand_id", jsObject.getBrandId());
                jsonObject.accumulate("product_id", jsObject.getId());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("price", jsObject.getPrice());
                jsonObject.accumulate("quantity", jsObject.getQuantity());
                jsonObject.accumulate("attributes", jsObject.getAttributeId());

                if (!Utility.isEmptyString(jsObject.getCourierId()))
                    jsonObject.accumulate("courier_id", jsObject.getCourierId());


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 2. convert JSONObject to JSON to String
        json = jsonObject.toString();
        Utility.Logger(MyApplication.getInstance().getClass().getSimpleName() + " Json", json);

        return json;
    }

    @Override
    protected void sendRequestToServer(RequestObject requestObject) {
        //Do nothing , needed in case of sending request to server
    }

    @Override
    public void onLocationUpdates(Location location) {

        GeocodeObject geocodeObject = Utility.getGeoCodeObject(context, location.getLatitude(), location.getLongitude());
        Utility.Logger(tag, geocodeObject.toString());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        locationEngine.stopLocationEngine();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //do nothing , we didn't have any used of it
    }

    @Override
    public void onPageSelected(int position) {

        Utility.Logger(tag, "Page Selected , Position = " + position);

        for (int i = 0; i < tabMain.getTabCount(); i++) {

            Utility.Logger(tag, "Position Tab = " + i);

            View customItemView = tabMain.getTabAt(i).getCustomView();
            TextView txtTab = customItemView.findViewById(R.id.txt_menu);
            ImageView imageMenu = customItemView.findViewById(R.id.image_menu);

            if (i == position) {

                txtTab.setTextColor(Utility.getAttrColor(context, R.attr.colorTextPrimary));
                txtTab.setTypeface(Font.ubuntuMediumFont(context));
                imageMenu.setColorFilter(Utility.getAttrColor(this, R.attr.colorTextPrimary));


            } else  {

                txtTab.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
                txtTab.setTypeface(Font.ubuntuLightFont(context));
                imageMenu.setColorFilter(Utility.getAttrColor(this, R.attr.colorTextQuinary));


            }

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing , we didn't have any used of it
    }

    @Override
    public void onBackPressed() {
       //We override it because we have to disable back button feature of Smartphone
       //On that specific screen
    }

    /**
     * <p>It is used to open Fragment</p>
     *
     * @param fragment
     */
    public void openFragment(Fragment fragment) {

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_container, fragment);
            fragmentTransaction.commit();

        }
    }

    public LockableViewPager getViewPager(){
        return viewPagerMain;
    }

    public static void showProductCartBottomSheet(final Context context, final DataObject dataObject) {

        final String tag = BottomNavigationSample.class.getSimpleName();
        final Management management = MyApplication.getManagementInstance();
        final View view = LayoutInflater.from(context).inflate(R.layout.product_bottom_sheet_item_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        TextView txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
        final ImageView imageProduct = (ImageView) view.findViewById(R.id.image_product);
        TextView txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
        final TextView txtPrice = (TextView) view.findViewById(R.id.txt_price);
        TextView txtDiscount = (TextView) view.findViewById(R.id.txt_discount);

        LinearLayout layoutMinus = (LinearLayout) view.findViewById(R.id.layout_minus);
        final TextView editQuantity = (TextView) view.findViewById(R.id.txt_quantity);
        LinearLayout layoutAdd = (LinearLayout) view.findViewById(R.id.layout_add);

        RoundKornerLinearLayout layoutAddToCart = (RoundKornerLinearLayout) view.findViewById(R.id.layout_add_to_cart);

        LinearLayout layoutScroller = view.findViewById(R.id.layout_scroller);
        final com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum =
                (com.mg.shopping.jsonutil.specificproductutil.ListOfDatum) dataObject.getObjectList().get(0);

        txtProductName.setText(datum.getName());
        txtPrice.setText(getProductPrice(datum,txtDiscount));

        txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
        GlideApp.with(context).load(BottomNavigationSample.getProductPicture(datum.getImage().get(0).getImage())).into(imageProduct);

        final ArrayList<ArrayList<Object>> attrList = new ArrayList<>();
        attrList.addAll(getPopulatedAttributeData(context,datum,imageProduct,layoutScroller));

        final ArrayList<Object> shippingArrayList = new ArrayList<>();
        if (!datum.getShipping().isEmpty()){

            for (int i = 0; i < datum.getShipping().size(); i++) {

                AvailableShipping availableShipping = datum.getShipping().get(i);
                availableShipping.setDialog(true);

                shippingArrayList.add(availableShipping);

            }

            View shippingCustomView = LayoutInflater.from(context).inflate(R.layout.attribute_recycler_item_layout, null, false);

            TextView txtAttribute = shippingCustomView.findViewById(R.id.txt_attribute);
            txtAttribute.setText(Utility.getStringFromRes(context,R.string.select_shipping));

            RecyclerView recyclerViewShipping = shippingCustomView.findViewById(R.id.recycler_view_attribute);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
            recyclerViewShipping.setLayoutManager(linearLayoutManager);

            LayoutMarginDecoration decorator = new LayoutMarginDecoration(Utility.dpToPx(5));
            recyclerViewShipping.addItemDecoration(decorator);

            final ProductQuickDetailAdapter dataAdapter = new ProductQuickDetailAdapter(context, shippingArrayList);
            recyclerViewShipping.setAdapter(dataAdapter);
            dataAdapter.setSelectionInterface(new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {

                    int pos = selectionObject.getPosition();
                    Utility.Logger(tag, selectionObject.getAction());

                    for (int j = 0; j < shippingArrayList.size(); j++) {
                        ((AvailableShipping) shippingArrayList.get(j)).setSelection((j == pos));

                    }
                    dataAdapter.notifyDataSetChanged();

                }
            });


            layoutScroller.addView(shippingCustomView);

        }

        layoutAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!management.getPreferences(null).isLogin()){
                    Utility.Logger(tag,"Login required");

                    context.startActivity(new Intent(context,Login.class));

                    return;
                }

                StringBuilder attributeBuilder = getSelectedProductAttribute(attrList);
                if (Utility.isEmptyString(attributeBuilder.toString())
                        && !attrList.isEmpty()){

                    Utility.Toaster(MyApplication.getInstance(),
                            Utility.getStringFromRes(MyApplication.getInstance(),R.string.select_required_options),
                            Toast.LENGTH_SHORT);
                    return ;
                }

                String courierId = getSelectedShippingId(shippingArrayList);
                if (Utility.isEmptyString(courierId)){
                    Utility.Toaster(context,
                            Utility.getStringFromRes(context,R.string.select_courier_service),
                            Toast.LENGTH_SHORT);
                    return;
                }

                management.sendRequestToServer(new RequestObject()
                        .setJson(getProductBottomSheetJson(new JsonObject()
                                .setFunctionality("add_product_into_cart")
                                .setQuantity(editQuantity.getText().toString())
                                .setAttributeId(attributeBuilder.toString())
                                .setId(datum.getId())
                                .setUserId(BottomNavigationSample.getUserId())
                                .setCourierId(courierId)
                                .setBrandId(datum.getBrand().get(0).getId())
                                .setPrice(txtPrice.getText().toString())
                                .setConnection(Constant.CONNECTION.ADD_PRODUCT_INTO_CART)))
                        .setConnectionType(Constant.CONNECTION_TYPE.UI)
                        .setConnection(Constant.CONNECTION.ADD_PRODUCT_INTO_CART)
                        .setConnectionCallback(new ConnectionCallback() {
                            @Override
                            public void onSuccess(Object data, RequestObject requestObject) {
                                bottomSheetDialog.dismiss();
                            }

                            @Override
                            public void onError(String data, RequestObject requestObject) {
                                Utility.Toaster(context, data, Toast.LENGTH_SHORT);
                            }
                        }));

            }
        });

        layoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = Integer.parseInt(editQuantity.getText().toString());
                ++quantity;
                editQuantity.setText(String.valueOf(quantity));

            }
        });

        layoutMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = Integer.parseInt(editQuantity.getText().toString());
                if (quantity>1){
                    --quantity;
                    editQuantity.setText(String.valueOf(quantity));
                }


            }
        });


    }


    /**
     * <p>It is used to get Discounted Amount</p>
     * @param datum
     * @return
     */
    private static double getDiscountedAmount(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum ){

        String[] discount = datum.getDiscount().split(":");
        double discountedPercentageValue;

        if (discount[1].equalsIgnoreCase("per_discount")){
            discountedPercentageValue = Double.parseDouble(datum.getPrice())
                    * (Double.parseDouble(discount[0])/100);
        }
        else {
            discountedPercentageValue = Double.parseDouble(datum.getPrice()) - Double.parseDouble(discount[0]);
        }

        return discountedPercentageValue;

    }

    private static String getProductPrice(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum, TextView txtDiscount){

        String productPrice = null;

        if (!Utility.isEmptyString(datum.getDiscount())
                && datum.getDiscount().contains(":")){

            String[] discount = datum.getDiscount().split(":");
            double discountedPrice = Double.parseDouble(datum.getPrice()) - getDiscountedAmount(datum);

            productPrice=String.valueOf(Utility.round(discountedPrice,2));

            if (discount[1].equalsIgnoreCase("per_discount")){
                txtDiscount.setText(discount[0]+" % off");
            }
            else {
                txtDiscount.setText(discount[0]+" "+BottomNavigationSample.getCurrencyInformation()+" off");
            }

            txtDiscount.setVisibility(View.VISIBLE);

        }
        else {

            productPrice = datum.getPrice();

        }

        return productPrice;

    }

    private static StringBuilder getSelectedProductAttribute(ArrayList<ArrayList<Object>> attrList){
        final StringBuilder attributeBuilder = new StringBuilder();
        for (int i = 0; i < attrList.size(); i++) {

            for (int j = 0; j < attrList.get(i).size(); j++) {

                if (((Item) attrList.get(i).get(j)).isSelection()) {

                    attributeBuilder.append(((Item) attrList.get(i).get(j)).getId());
                    if (j < (attrList.get(i).size() - 1)) {
                        attributeBuilder.append(",");
                    }
                }

            }

        }

        return attributeBuilder;
    }

    private static String getSelectedShippingId(ArrayList<Object> shippingArrayList){

        String courierId = null;
        for (int i = 0; i < shippingArrayList.size(); i++) {

            if ( ((AvailableShipping)shippingArrayList.get(i)).isSelection() ){
                courierId = ((AvailableShipping)shippingArrayList.get(i)).getId();
            }
        }

        return courierId;

    }

    private static ArrayList<ArrayList<Object>> getPopulatedAttributeData(Context context, final com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum , final ImageView imageProduct , LinearLayout layoutScroller){

        final ArrayList<ArrayList<Object>> attrList = new ArrayList<>();
        for (int i = 0; i < datum.getAttribute().size(); i++) {

            if (datum.getAttribute().get(i).getItem().isEmpty()) {
                continue;
            }

            final ArrayList<Object> objectArrayList = new ArrayList<>();
            Attribute attribute = datum.getAttribute().get(i);
            View attributeView = LayoutInflater.from(context).inflate(R.layout.attribute_recycler_item_layout, null, false);

            TextView txtAttribute = attributeView.findViewById(R.id.txt_attribute);
            txtAttribute.setText(attribute.getName());

            for (int j = 0; j < attribute.getItem().size(); j++) {

                Item item = attribute.getItem().get(j);
                item.setChildId(String.valueOf(i));
                objectArrayList.add(item);

            }
            attrList.add(objectArrayList);

            int spanCount = getAttributeLayoutManagerSpanCount(attribute);
            RecyclerView recyclerViewAttribute = attributeView.findViewById(R.id.recycler_view_attribute);
            StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.HORIZONTAL);
            recyclerViewAttribute.setLayoutManager(linearLayoutManager);

            LayoutMarginDecoration decorator = attribute.getItem().size() > 3 ? new LayoutMarginDecoration(2, Utility.dpToPx(5))
                    : new LayoutMarginDecoration(1, Utility.dpToPx(5));

            recyclerViewAttribute.addItemDecoration(decorator);

            final ProductQuickDetailAdapter dataAdapter = new ProductQuickDetailAdapter(context, objectArrayList);
            recyclerViewAttribute.setAdapter(dataAdapter);
            dataAdapter.setSelectionInterface(new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {

                    int pos = selectionObject.getPosition();
                    int childPos = selectionObject.getChildPosition();

                    Utility.Logger(MyApplication.getInstance().getClass().getSimpleName(), selectionObject.getAction());

                    for (int j = 0; j < attrList.get(childPos).size(); j++) {

                        ((Item) attrList.get(childPos).get(j)).setSelection( j == pos );

                    }
                    if (!Utility.isEmptyString(((Item) objectArrayList.get(pos)).getImage())) {
                        GlideApp.with(MyApplication.getInstance())
                                .load(BottomNavigationSample.getProductPicture(((Item) objectArrayList.get(pos)).getImage()))
                                .into(imageProduct);
                    }
                    else {
                        GlideApp.with(MyApplication.getInstance())
                                .load(BottomNavigationSample.getProductPicture(datum.getImage().get(0).getImage()))
                                .into(imageProduct);
                    }
                    dataAdapter.notifyDataSetChanged();

                }
            });


            layoutScroller.addView(attributeView);

        }

        return attrList;
    }

    private static int getAttributeLayoutManagerSpanCount(Attribute attribute){
       return attribute.getItem().size() > 3 ? 2 : 1;

    }

    public static String getCategoryPicture(String picture) {
        return Constant.ServerInformation.CATEGORY_PICTURE_URL + picture;
    }

    public static String getProductPicture(String picture) {
        return Constant.ServerInformation.PRODUCT_PICTURE_URL + picture;
    }

    public static String getUserPicture(String picture) {
        return Constant.ServerInformation.USER_PICTURE_URL + picture;
    }

    public static String getReviewPicture(String picture) {
        return Constant.ServerInformation.REVIEW_PICTURE_URL + picture;
    }

    public static String getFeaturedBanner(String picture) {
        return Constant.ServerInformation.FEATURED_BANNER_PICTURE_URL + picture;
    }

    public static String getShippingPicture(String picture) {
        return Constant.ServerInformation.SHIPPING_PICTURE_URL + picture;
    }

    public static String getRefundPicture(String picture) {
        return Constant.ServerInformation.REFUND_PICTURE_URL + picture;
    }

    public static String getBrandPicture(String picture) {
        return Constant.ServerInformation.BRAND_PICTURE_URL + picture;
    }


    public static String getUserId(){

        final Management management = MyApplication.getManagementInstance();
        return management.getPreferences(null).getUserId();

    }

    public static String getCurrencyInformation(){

        Management management = MyApplication.getManagementInstance();

      return  management.getPreferences(null).getCurrencySymbol();
    }

    public static Map<String, DataObject> getUserFavouritesProduct(){

        ArrayList<Object> objectArrayList = new ArrayList<>();
        HashMap<String,DataObject> objectHashMap = new HashMap<>();

        final Management management = MyApplication.getManagementInstance();
        PrefObject prefObject =  management.getPreferences(new PrefObject()
        .setRetrieveUserId(true));

        Utility.Logger(MyApplication.getInstance().getClass().getSimpleName(),prefObject.getUserId());

        objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(TypeConstraint.FAVOURITES)
                .setDbOperation(DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_PRODUCT)
                .setDataObject(new DataObject()
                        .setUserId(prefObject.getUserId()))));

        for (int i = 0; i < objectArrayList.size(); i++) {

            DataObject dataObject = (DataObject) objectArrayList.get(i);
            objectHashMap.put( dataObject.getProductId(),dataObject);

        }

        return objectHashMap;

    }

    public static Map<String, DataObject> getUserFollowingStore(){

        ArrayList<Object> objectArrayList = new ArrayList<>();
        HashMap<String,DataObject> objectHashMap = new HashMap<>();

        final Management management = MyApplication.getManagementInstance();
        PrefObject prefObject =  management.getPreferences(new PrefObject()
                .setRetrieveUserId(true));

        objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(TypeConstraint.BRAND)
                .setDbOperation(DbConstraint.RETRIEVE_SPECIFIC_BRAND_FOLLOWING)
                .setDataObject(new DataObject()
.setUserId(prefObject.getUserId()))));

        for (int i = 0; i < objectArrayList.size(); i++) {

            DataObject dataObject = (DataObject) objectArrayList.get(i);

            Utility.Logger(MyApplication.getInstance().getClass().getSimpleName(),dataObject.getProductId());

            objectHashMap.put( dataObject.getProductId(),dataObject);

        }

        return objectHashMap;

    }

    public static Map<String, DataObject> getUserFavouriteStore(){

        ArrayList<Object> objectArrayList = new ArrayList<>();
        HashMap<String,DataObject> objectHashMap = new HashMap<>();

        final Management management = MyApplication.getManagementInstance();
        PrefObject prefObject =  management.getPreferences(new PrefObject()
                .setRetrieveUserId(true));

        objectArrayList.addAll(management.getDataFromDatabase(new DatabaseObject()
                .setTypeOperation(TypeConstraint.FAVOURITES)
                .setDbOperation(DbConstraint.RETRIEVE_SPECIFIC_USER_FAVOURITES_STORE)
                .setDataObject(new DataObject()
.setUserId(prefObject.getUserId()))));

        for (int i = 0; i < objectArrayList.size(); i++) {

            DataObject dataObject = (DataObject) objectArrayList.get(i);

            Utility.Logger(MyApplication.getInstance().getClass().getSimpleName(),dataObject.getProductId());

            objectHashMap.put( dataObject.getProductId(),dataObject);

        }

        return objectHashMap;

    }


}
