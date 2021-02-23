package com.mg.shopping.activityutil;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mg.shopping.R;
import com.mg.shopping.adapterutil.CategorizedProductAdapter;
import com.mg.shopping.adapterutil.ProductQuickDetailAdapter;
import com.mg.shopping.adapterutil.ReviewAdapter;
import com.mg.shopping.adapterutil.SimpleDividerItemDecoration;
import com.mg.shopping.adapterutil.SimplePagerAdapter;
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.databaseutil.DatabaseObject;
import com.mg.shopping.databaseutil.DbConstraint;
import com.mg.shopping.databaseutil.TypeConstraint;
import com.mg.shopping.fragmentutil.ProductPictureFragment;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
import com.mg.shopping.jsonutil.specificproductutil.Attribute;
import com.mg.shopping.jsonutil.specificproductutil.AvailableShipping;
import com.mg.shopping.jsonutil.specificproductutil.Faq;
import com.mg.shopping.jsonutil.specificproductutil.Image;
import com.mg.shopping.jsonutil.specificproductutil.Rating;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.JsonObject;
import com.mg.shopping.objectutil.RequestObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.preferenceutil.PrefObject;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;


public class ProductDetail extends BaseActivity implements  ViewPager.OnPageChangeListener {
    ImageView imageBack;
    LinearLayout layoutFilter;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView recyclerViewProduct;
    ArrayList<Object> objectArrayList = new ArrayList<>();
    DataObject dataObject;
    LayoutMarginDecoration itemDecoration;

    ArrayList<Fragment> pictureArrayList = new ArrayList<>();
    ArrayList<Object> shippingList = new ArrayList<>();

    SimplePagerAdapter pagerAdapter;
    ViewPager viewPagerPicture;
    LinearLayout layoutCounter;
    TextView txtCounter;

    LinearLayout layoutScroller;
    LinearLayout layoutReviewScroller;
    LinearLayout layoutQuestionScroller;

    LinearLayout layoutSale;
    LinearLayout layoutShare;

    TextView txtPrice;
    TextView txtCurrencySymbol;
    TextView txtOriginalPrice;
    TextView txtSale;

    AppCompatRatingBar ratingProduct;

    LinearLayout layoutNextDetail;
    LinearLayout layoutNextRating;
    LinearLayout layoutNextQa;
    LinearLayout layoutShipping;

    TextView txtQuestionAnswerLabel;
    TextView txtReviewLabel;

    RelativeLayout frameContainer;
    RelativeLayout mainFrame;
    ListOfDatum listOfProduct;

    LinearLayout layoutAttribute;
    TextView txtAttribute;
    TextView txtName;
    TextView txtOrder;
    TextView txtReview;
    TextView txtRating;

    TextView txtQuantity;
    TextView txtShipping;
    TextView txtShippingLabel;
    TextView txtDeliveryService;
    TextView txtDeliveryDays;

    RoundKornerLinearLayout layoutBuyNow;
    RoundKornerLinearLayout layoutAddToCart;
    LinearLayout layoutCart;
    int shippingPosition = -1;

    ImageView imageOther;
    CardView layoutFavourites;
    ImageView imageFavourite;

    HashMap<String, DataObject> productHashmap = new HashMap<>();
    PrefObject prefObject;

    boolean isDeepLinkScreen;
    ArrayList<Parcelable> featuredCtgList = new ArrayList<>();

    TextView txtProcessingTime;
    TextView txtDescription;
    String description;
    static final String DB_FAVOURITE_TYPE = "favourite_product";


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
        return R.layout.activity_product_detail;
    }

    @Override
    protected void getIntentData() {
        featuredCtgList = getIntent().getParcelableArrayListExtra(Constant.IntentKey.LIST);
        isDeepLinkScreen = getIntent().getBooleanExtra(Constant.IntentKey.TYPE, false);
        listOfProduct = getIntent().getParcelableExtra(Constant.IntentKey.DATA_OBJECT);
    }

    @Override
    protected void initUI() {

        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDeepLinkScreen) {

                    Intent intent = new Intent(context, BottomNavigationSample.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, featuredCtgList);
                    startActivity(intent);

                }
                finish();
            }
        });


        layoutFavourites = findViewById(R.id.layout_favourites);
        layoutFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!management.getPreferences(null).isLogin()) {
                    Utility.Toaster(context,
                            Utility.getStringFromRes(context, R.string.need_login),
                            Toast.LENGTH_SHORT);
                    return;
                }

                proceedFavAndUnFavFunctionality(); // favourite or either unfavourites

            }
        });
        imageFavourite = findViewById(R.id.image_favourite);

        frameContainer = findViewById(R.id.frame_container);
        mainFrame = findViewById(R.id.main_frame);

        pagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), pictureArrayList);
        viewPagerPicture = (ViewPager) findViewById(R.id.view_pager_picture);
        viewPagerPicture.setAdapter(pagerAdapter);

        layoutCounter = (LinearLayout) findViewById(R.id.layout_counter);
        txtCounter = (TextView) findViewById(R.id.txt_counter);

        layoutAttribute = (LinearLayout) findViewById(R.id.layout_attribute);
        txtAttribute = (TextView) findViewById(R.id.txt_attribute);

        layoutScroller = (LinearLayout) findViewById(R.id.layout_scroller);
        layoutReviewScroller = (LinearLayout) findViewById(R.id.layout_review_scroller);
        layoutQuestionScroller = (LinearLayout) findViewById(R.id.layout_question_scroller);

        layoutSale = (LinearLayout) findViewById(R.id.layout_sale);

        layoutShare = (LinearLayout) findViewById(R.id.layout_share);
        layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequestToServer(new RequestObject()
                        .setId(listOfProduct.getId())
                        .setName(listOfProduct.getName())
                        .setPrice(listOfProduct.getPrice())
                        .setRating(listOfProduct.getRating())
                        .setLoadingText(Utility.getStringFromRes(context, R.string.creating))
                        .setLoadingType(Constant.LOADING_TYPE.DIALOG)
                        .setConnectionType(Constant.CONNECTION_TYPE.BUILD_DEEPLINK)
                        .setConnection(Constant.CONNECTION.PRODUCT_DEEPLINK));

            }
        });

        txtPrice = (TextView) findViewById(R.id.txt_price);
        txtCurrencySymbol = findViewById(R.id.txt_currency_symbol);
        txtOriginalPrice = (TextView) findViewById(R.id.txt_original_price);
        txtSale = (TextView) findViewById(R.id.txt_sale);

        txtDeliveryService = findViewById(R.id.txt_delivery_service);
        txtDeliveryDays = findViewById(R.id.txt_delivery_days);

        ratingProduct = (AppCompatRatingBar) findViewById(R.id.rating_product);

        layoutNextDetail = (LinearLayout) findViewById(R.id.layout_next_detail);
        layoutNextDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isEmptyString(description)) {
                    Utility.Logger(tag, "Empty Description");
                    return;
                }

                Intent intent = new Intent(context, PrivacyPolicy.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT, description);
                startActivity(intent);

            }
        });

        layoutNextRating = (LinearLayout) findViewById(R.id.layout_next_rating);
        layoutNextRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductRating.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT, new DataObject()
                        .setProductId(listOfProduct.getId())
                        .setType("product_rating"));
                startActivity(intent);

            }
        });

        layoutNextQa = (LinearLayout) findViewById(R.id.layout_next_qa);
        layoutNextQa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductQuestion.class);
                intent.putExtra(Constant.IntentKey.DATA_OBJECT, listOfProduct);
                startActivity(intent);

            }
        });
        layoutShipping = findViewById(R.id.layout_shipping);

        layoutBuyNow = (RoundKornerLinearLayout) findViewById(R.id.layout_buy_now);
        layoutBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationSample.showProductCartBottomSheet(context, dataObject);
            }
        });

        layoutCart = findViewById(R.id.layout_cart);
        layoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!management.getPreferences(null).isLogin()) {

                    Utility.Toaster(context,
                            Utility.getStringFromRes(context, R.string.need_login)
                            , Toast.LENGTH_SHORT);

                    return;

                }

                Intent intent = new Intent(context, Cart.class);
                startActivity(intent);

            }
        });

        layoutAddToCart = (RoundKornerLinearLayout) findViewById(R.id.layout_add_to_cart);
        layoutAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomNavigationSample.showProductCartBottomSheet(context, dataObject);

            }
        });

        txtQuestionAnswerLabel = (TextView) findViewById(R.id.txt_question_answer_label);
        txtReviewLabel = (TextView) findViewById(R.id.txt_question_answer_label);

        txtQuantity = findViewById(R.id.txt_quantity);
        txtQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataObject != null) {
                    BottomNavigationSample.showProductCartBottomSheet(context, dataObject);
                }

            }
        });

        txtShipping = findViewById(R.id.txt_shipping);
        txtShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!shippingList.isEmpty()) {
                    showShippingBottomSheet(context);
                }

            }
        });
        txtShippingLabel = findViewById(R.id.txt_shipping_label);

        txtName = findViewById(R.id.txt_name);
        txtDescription = findViewById(R.id.txt_description);
        txtProcessingTime = findViewById(R.id.txt_processing_time);
        txtOrder = findViewById(R.id.txt_order);
        txtReview = findViewById(R.id.txt_review);
        txtRating = findViewById(R.id.txt_rating);


        txtName.setText(listOfProduct.getName());
        txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
        txtPrice.setText(listOfProduct.getPrice());

        if (listOfProduct.getRating() != null)
            txtRating.setText(String.valueOf(
                    Utility.round(
                            Double.parseDouble(listOfProduct.getRating()), 2)
                    )
            );

        if (!Utility.isEmptyString(listOfProduct.getRating()))
            ratingProduct.setRating(Float.parseFloat(listOfProduct.getRating()));
        else
            ratingProduct.setRating(0);

        imageOther = findViewById(R.id.image_other);
        imageOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup(imageOther);

            }
        });

        sendRequestToServer(new RequestObject()
                .setJson(getJson(new JsonObject()
                        .setFunctionality("retrieve_specific_product")
                        .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT)
                        .setId(listOfProduct.getId())))
                .setConnectionType(Constant.CONNECTION_TYPE.UI)
                .setConnection(Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT));

        viewPagerPicture.addOnPageChangeListener(this);


    }

    @Override
    protected String getJson(JsonObject jsObject) {
        String json = "";
        String jsonProductId = "product_id";

        // 1. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate("functionality", jsObject.getFunctionality());

            if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {


                jsonObject.accumulate(jsonProductId, jsObject.getId());


            } else if (jsObject.getConnection() == Constant.CONNECTION.ADD_PRODUCT_INTO_CART) {

                jsonObject.accumulate(jsonProductId, jsObject.getId());
                jsonObject.accumulate("user_id", jsObject.getUserId());
                jsonObject.accumulate("quantity", jsObject.getQuantity());
                jsonObject.accumulate("attributes", jsObject.getAttributeId());


            } else if (jsObject.getConnection() == Constant.CONNECTION.RETRIEVE_SHIPPING_RATES) {

                jsonObject.accumulate(jsonProductId, jsObject.getId());
                jsonObject.accumulate("quantity", jsObject.getQuantity());
                jsonObject.accumulate("to_postal_code", jsObject.getToPostalCode());


            } else if (jsObject.getConnection() == Constant.CONNECTION.CHANGE_FAVOURITE_STATUS) {

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
    public void onSuccess(Object data, RequestObject requestObject) {
        super.onSuccess(data, requestObject);

        DataObject dtObject = (DataObject) data;
        Utility.Logger(tag, "onSuccess = " + dtObject.getObjectArrayList().size());

        if (requestObject.isFirstRequest()) {
            objectArrayList.clear();
        }

        if (requestObject.getConnection() == Constant.CONNECTION.RETRIEVE_SPECIFIC_PRODUCT) {

            dataObject = dtObject;

            for (int i = 0; i < dtObject.getObjectList().size(); i++) {

                final com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum =
                        (com.mg.shopping.jsonutil.specificproductutil.ListOfDatum) dtObject.getObjectList().get(i);

                pictureArrayList.addAll(getProductImages(datum));

                shippingList.clear();
                initShipping(datum);  //initialize shipping

                pagerAdapter.notifyDataSetChanged();

                txtName.setText(datum.getName());
                txtProcessingTime.setText(datum.getProcessingTime());
                txtPrice.setText(datum.getPrice());
                txtOrder.setText(datum.getSold());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtDescription.setText(Html.fromHtml(datum.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                }
                else {
                    txtDescription.setText(Html.fromHtml(datum.getDescription()));
                }

                txtDescription.setTextColor(Utility.getColourFromRes(context, R.color.colorTextSecondaryDay));

                txtReview.setText(String.valueOf(datum.getRating().size()));

                Utility.Logger(tag, "Avg Rating = " + datum.getAvgRating());
                description = datum.getDescription();

                ratingProduct.setRating(Float.parseFloat(getProductRating(datum)));
                txtRating.setText(String.valueOf(Utility.round(
                        Double.parseDouble(getProductRating(datum)), 2)));

                checkingDiscountPrice(datum);  //Checking discount price

                txtCounter.setText("1/" + pictureArrayList.size());

                initCustomerRatingView(datum);  //initialize customer rating regading product
                initFaqQuestionAnswerView(datum);  // initialize Faq question /answer view

                txtAttribute.setText(getProductAttributes(datum));  //get product attributes

                View relatedView = LayoutInflater.from(context).inflate(R.layout.related_product_item_layout, null);

                StaggeredGridLayoutManager layoutManagerRelatedProduct = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                RecyclerView recyclerView = relatedView.findViewById(R.id.recycler_view_related_product);
                recyclerView.setLayoutManager(layoutManagerRelatedProduct);

                final ArrayList<Object> relatedArrayList = new ArrayList<>();
                for (int j = 0; j < datum.getRelatedProduct().size(); j++) {

                    relatedArrayList.add(datum.getRelatedProduct().get(j).setCartIconAvailable(false));

                }

                CategorizedProductAdapter dataAdapterRelatedProduct = new CategorizedProductAdapter(context, relatedArrayList);
                dataAdapterRelatedProduct.setSelectionInterface(new SelectionInterface() {
                    @Override
                    public void onSelection(SelectionObject selectionObject) {

                        int pos = selectionObject.getPosition();
                        ListOfDatum productDatum = (ListOfDatum) relatedArrayList.get(pos);

                        Intent intent = new Intent(context, ProductDetail.class);
                        intent.putExtra(Constant.IntentKey.DATA_OBJECT, productDatum);
                        startActivity(intent);


                    }
                });
                recyclerView.setAdapter(dataAdapterRelatedProduct);
                recyclerView.addItemDecoration(new LayoutMarginDecoration(2, Utility.dpToPx(5)));
                layoutScroller.addView(relatedView);




            }


        }
        else if (requestObject.getConnection() == Constant.CONNECTION.PRODUCT_DEEPLINK) {

            Utility.copyData(context, dtObject.getDeepLinkUrl());
            Utility.Toaster(context,
                    Utility.getStringFromRes(context, R.string.copied)
                    , Toast.LENGTH_SHORT);

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

        productHashmap = new HashMap<>(BottomNavigationSample.getUserFavouritesProduct());
        prefObject = management.getPreferences(new PrefObject()
                .setRetrieveUserId(true));
        if (productHashmap.containsKey(listOfProduct.getId())) {
            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorButtonTertiary));
        }

    }


    /**
     * <p>It is used to proceed Favourites / Un Favourites functionality</p>
     */
    private void proceedFavAndUnFavFunctionality() {

        productHashmap = new HashMap<>(BottomNavigationSample.getUserFavouritesProduct());
        if (productHashmap.containsKey(listOfProduct.getId())) {

            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorButtonPrimary));
            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(TypeConstraint.FAVOURITES)
                    .setDbOperation(DbConstraint.DELETE_SPECIFIC_USER_FAVOURITES)
                    .setDataObject(new DataObject()
                            .setId(listOfProduct.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(DB_FAVOURITE_TYPE)));

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("change_favourite_status")
                            .setConnection(Constant.CONNECTION.CHANGE_FAVOURITE_STATUS)
                            .setId(listOfProduct.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(DB_FAVOURITE_TYPE)
                            .setAction("delete")))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.CHANGE_FAVOURITE_STATUS));

        } else {

            imageFavourite.setColorFilter(Utility.getAttrColor(this, R.attr.colorButtonTertiary));
            management.getDataFromDatabase(new DatabaseObject()
                    .setTypeOperation(TypeConstraint.FAVOURITES)
                    .setDbOperation(DbConstraint.INSERT_NEW_FAVOURITES_PRODUCT)
                    .setDataObject(new DataObject()
                            .setId(listOfProduct.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(DB_FAVOURITE_TYPE)));

            sendRequestToServer(new RequestObject()
                    .setJson(getJson(new JsonObject()
                            .setFunctionality("change_favourite_status")
                            .setConnection(Constant.CONNECTION.CHANGE_FAVOURITE_STATUS)
                            .setId(listOfProduct.getId())
                            .setUserId(prefObject.getUserId())
                            .setType(DB_FAVOURITE_TYPE)
                            .setAction("insert")))
                    .setConnectionType(Constant.CONNECTION_TYPE.BACKGROUND)
                    .setConnection(Constant.CONNECTION.CHANGE_FAVOURITE_STATUS));

        }

    }

    /**
     * <p>It is used to initialize customer rating view</p>
     * @param datum
     */
    private void initCustomerRatingView(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        for (int j = 0; j < datum.getRating().size(); j++) {

            if (j > 0) {
                break;
            }


            Rating rating = datum.getRating().get(j);
            View ratingView = LayoutInflater.from(context).inflate(R.layout.product_review_item_layout, null);

            RoundedImageView imageUser = (RoundedImageView) ratingView.findViewById(R.id.image_user);
            TextView txtUserName = (TextView) ratingView.findViewById(R.id.txt_user_name);
            AppCompatRatingBar ratingCustomer = (AppCompatRatingBar) ratingView.findViewById(R.id.rating_customer);
            TextView txtDateTime = (TextView) ratingView.findViewById(R.id.txt_date_time);
            TextView txtUserReview = (TextView) ratingView.findViewById(R.id.txt_description);

            txtUserName.setText(rating.getName());
            txtUserReview.setText(StringUtils.capitalize(rating.getReview()));
            txtDateTime.setText(rating.getDateCreated());

            ratingCustomer.setRating(getCustomerRating(rating));

            GlideApp.with(context).load(BottomNavigationSample.getUserPicture(rating.getUserImage())).into(imageUser);

            LinearLayoutManager layoutManagerReview = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerViewPicture = (RecyclerView) ratingView.findViewById(R.id.recycler_view_picture);
            recyclerViewPicture.setLayoutManager(layoutManagerReview);

            ReviewAdapter dataAdapterReview = new ReviewAdapter(context, new ArrayList<Object>(rating.getImage()));
            recyclerViewPicture.setAdapter(dataAdapterReview);

            layoutReviewScroller.addView(ratingView);

        }

    }

    /**
     * <p>It is used to initialize faq view</p>
     * @param datum
     */
    private void initFaqQuestionAnswerView(final com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        for (int j = 0; j < datum.getFaq().size(); j++) {

            if (j > 0) {
                break;
            }


            final Faq faq = datum.getFaq().get(j);
            View faqView = LayoutInflater.from(context).inflate(R.layout.question_answer_item_layout, null);

            TextView txtQuestion = (TextView) faqView.findViewById(R.id.txt_question);
            TextView txtAnswer = (TextView) faqView.findViewById(R.id.txt_answer);
            final TextView txtAllAnswer = (TextView) faqView.findViewById(R.id.txt_all_answer);
            LinearLayout layoutAnswer = (LinearLayout) faqView.findViewById(R.id.layout_answer);

            txtQuestion.setText(faq.getName());
            if (!faq.getAnswer().isEmpty()) {
                txtAnswer.setText(faq.getAnswer().get(0).getAnswer());
                layoutAnswer.setVisibility(View.VISIBLE);
            } else {
                layoutAnswer.setVisibility(View.GONE);
            }

            txtAllAnswer.setText(Utility.getStringFromRes(context, R.string.total)
                    + " " + faq.getAnswer().size()
                    + " " + Utility.getStringFromRes(context, R.string.answers));

            txtAllAnswer.setTag(j);
            txtAllAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) txtAllAnswer.getTag();

                    DataObject dataObjectQuestion = new DataObject();
                    dataObjectQuestion.setQuestionId(datum.getFaq().get(pos).getId());
                    dataObjectQuestion.setQuestion(datum.getFaq().get(pos).getName());
                    dataObjectQuestion.setAnswerCount(String.valueOf(datum.getFaq().get(pos).getAnswer().size()));

                    Intent intent = new Intent(context, FaqAnswer.class);
                    intent.putExtra(Constant.IntentKey.DATA_OBJECT, dataObjectQuestion);
                    intent.putExtra(Constant.IntentKey.PRODUCT_OBJECT, listOfProduct);
                    startActivity(intent);

                }
            });

            layoutQuestionScroller.addView(faqView);

        }

    }

    /**
     * <p>It is used to get Product Attributes</p>
     * @param datum
     * @return
     */
    private String getProductAttributes(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        StringBuilder attributeBuilder = new StringBuilder();
        for (int j = 0; j < datum.getAttribute().size(); j++) {

            if (datum.getAttribute().get(j).getItem().isEmpty()) {
                continue;
            }

            Attribute attribute = datum.getAttribute().get(j);
            attributeBuilder.append(attribute.getItem().size() + " " + attribute.getName() + " " + Utility.getStringFromRes(context, R.string.available));

            if (j < (datum.getAttribute().size() - 1)) {

                if ((j + 1) < datum.getAttribute().size()
                        && (datum.getAttribute().get((j + 1)).getItem().isEmpty())) {
                    continue;
                }

                attributeBuilder.append(" , ");
            }

        }

        if (!datum.getAttribute().isEmpty()) {
            layoutAttribute.setVisibility(View.VISIBLE);
        } else {
            layoutAttribute.setVisibility(View.GONE);
        }


        return attributeBuilder.toString();
    }

    /**
     * <p>It is used to get Product Rating </p>
     * @param datum
     * @return
     */
    private String getProductRating(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        if (!Utility.isEmptyString(datum.getAvgRating())) {
            return datum.getAvgRating();
        }

        return "0";
    }

    /**
     * <p>It is used to initialize shipping</p>
     * @param datum
     */
    private void initShipping(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        for (int j = 0; j < datum.getShipping().size(); j++) {
            shippingList.add(datum.getShipping().get(j).setDialog(false));
        }

    }

    /**
     * <p>It is used to get Product Picture</p>
     * @param datum
     * @return
     */
    private Collection<? extends Fragment> getProductImages(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        ArrayList<Fragment> pictureList = new ArrayList<>();
        for (int j = 0; j < datum.getImage().size(); j++) {

            Image image = datum.getImage().get(j);
            pictureArrayList.add(ProductPictureFragment.getFragmentInstance(image.getImage()));

        }

        return pictureList;

    }

    /**
     * <p>It is used to get Customer Rating</p>
     * @param rating
     * @return
     */
    private float getCustomerRating(Rating rating) {
        if (!Utility.isEmptyString(rating.getRating()))
            return Float.parseFloat(rating.getRating());
        else
            return 0f;

    }

    /**
     * <p>It is used to check/calculate discount price </p>
     *
     * @param datum
     */
    private void checkingDiscountPrice(com.mg.shopping.jsonutil.specificproductutil.ListOfDatum datum) {

        if (!Utility.isEmptyString(datum.getDiscount())
                && datum.getDiscount().contains(":")) {

            String[] discount = datum.getDiscount().split(":");
            double discountedPercentageValue;

            if (discount[1].equalsIgnoreCase("per_discount")) {
                discountedPercentageValue = Double.parseDouble(datum.getPrice())
                        * (Double.parseDouble(discount[0]) / 100);
            } else {
                discountedPercentageValue = Double.parseDouble(datum.getPrice()) - Double.parseDouble(discount[0]);
            }

            double discountedPrice = Double.parseDouble(datum.getPrice()) - discountedPercentageValue;

            txtPrice.setText(String.valueOf(discountedPrice));
            txtOriginalPrice.setText(BottomNavigationSample.getCurrencyInformation()
                    + datum.getPrice());

            if (discount[1].equalsIgnoreCase("per_discount")) {
                txtSale.setText(discount[0] + " % off");
            } else {
                txtSale.setText(discount[0] + " " + BottomNavigationSample.getCurrencyInformation() + " off");
            }

            txtOriginalPrice.setVisibility(View.VISIBLE);
            layoutSale.setVisibility(View.VISIBLE);

            Utility.getCrossedText(txtOriginalPrice);

        }

    }


    private void showShippingBottomSheet(final Context context) {

        final View view = LayoutInflater.from(context).inflate(R.layout.shipping_bottom_sheet_layout, null);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();


        TextView txtLabel = view.findViewById(R.id.txt_label);
        txtLabel.setText(Utility.getStringFromRes(context, R.string.available_shipping));


        RecyclerView recyclerViewList = view.findViewById(R.id.recycler_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < shippingList.size(); i++) {

            ((AvailableShipping) shippingList.get(i)).setDialog(false);

        }

        final ProductQuickDetailAdapter dataAdapterShipping = new ProductQuickDetailAdapter(context, shippingList, new SelectionInterface() {
            @Override
            public void onSelection(SelectionObject selectionObject) {

                int pos = selectionObject.getPosition();
                AvailableShipping shipping = (AvailableShipping) shippingList.get(pos);
                Utility.Logger(tag, shipping.toString());

                txtDeliveryService.setText(shipping.getName());
                txtDeliveryDays.setText(shipping.getEstimatedDeliveryDays() == null ? Utility.getStringFromRes(context, R.string.same_day) : shipping.getEstimatedDeliveryDays() + " " + Utility.getStringFromRes(context, R.string.business_days));
                layoutShipping.setVisibility(View.VISIBLE);

                for (int i = 0; i < shippingList.size(); i++) {
                    ((AvailableShipping) shippingList.get(i)).setSelection(pos != i);
                }

                if (bottomSheetDialog.isShowing())
                    bottomSheetDialog.dismiss();

            }
        });
        recyclerViewList.setAdapter(dataAdapterShipping);
        recyclerViewList.addItemDecoration(new SimpleDividerItemDecoration(context, ContextCompat.getDrawable(context, R.drawable.line_divider)));


    }

    /* <p> you should refer to a view to stick your popup wherever u want.</p>
     **/
    public void showPopup(View v) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_add_category_layout, null);

        LinearLayout layoutWishlist = popupView.findViewById(R.id.layout_wishlist);
        TextView txtWishlist = popupView.findViewById(R.id.txt_wishlist);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        popupWindow.setOutsideTouchable(true);

        productHashmap = new HashMap<>(BottomNavigationSample.getUserFavouritesProduct());
        if (productHashmap.containsKey(listOfProduct.getId())) {
            txtWishlist.setText(Utility.getStringFromRes(context, R.string.wishlisted));
        } else {
            txtWishlist.setText(Utility.getStringFromRes(context, R.string.wishlist));
        }

        layoutWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFavourites.callOnClick();
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(v, -230, -20);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // do nothing
    }

    @Override
    public void onPageSelected(int position) {
        if (!isFullScreen())
            txtCounter.setText((position + 1) + "/" + (pictureArrayList.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //do nothing
    }

    @Override
    public void onBackPressed() {
        //It override the back button functionality
    }

    public void onSelectPicture() {
        ///do nothing , needed in case of increase size of picture
    }


}




