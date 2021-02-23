package com.mg.shopping.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.orderhistoryutil.Attribute;
import com.mg.shopping.jsonutil.orderhistoryutil.ListOfProduct;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int ORDER_PRODUCT_VIEW = 3;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    private String actionProductSelector = "product_selector";

    public OrderHistoryAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public OrderHistoryAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfProduct) {
            return ORDER_PRODUCT_VIEW;
         }
         else if (dataArray.get(position) instanceof ProgressObject) {
             return PROGRESS_VIEW;
         }
         else {
             return NO_DATA_VIEW;
         }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;

        switch (viewType) {
            case NO_DATA_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
                return  new EmptyHolder(view);

            case PROGRESS_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
                return new ProgressHolder(view);

            case ORDER_PRODUCT_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.order_history_product_item_layout, parent, false);
                return new OrderHistoryProductHolder(view);

            default:
                return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((StrategyAdapter) holder).executeHolderFunctionality(holder, position);

    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    protected class EmptyHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyState = (EmptyObject) dataArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyState.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyState.getTitle());
            emptyHolder.txtDescription.setText(emptyState.getDescription());

        }


        @Override
        public int getViewTypeIdentifier() {
            return NO_DATA_VIEW;
        }
    }

    protected class ProgressHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {
            //do nothing
        }


        @Override
        public int getViewTypeIdentifier() {
            return PROGRESS_VIEW;
        }
    }

    protected class OrderHistoryProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageProduct;

        private TextView txtProductName;
        private TextView txtPrice;
        private TextView txtCurrencySymbol;
        private TextView txtQuantity;
        private LinearLayoutManager layoutManager;
        private RecyclerView recyclerViewAttribute;
        private StringAttributeAdapter dataAdapter;

        private RoundKornerLinearLayout layoutProduct;
        private LinearLayout layoutProductDesc;
        private AppCompatRatingBar ratingBar;

        public OrderHistoryProductHolder(View view) {
            super(view);

            imageProduct = (RoundedImageView) view.findViewById(R.id.image_product);
            txtProductName = (TextView) view.findViewById(R.id.txt_product_name);

            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
            txtQuantity = (TextView) view.findViewById(R.id.txt_quantity);

            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewAttribute = (RecyclerView) view.findViewById(R.id.recycler_view_attribute);
            recyclerViewAttribute.setLayoutManager(layoutManager);

            layoutProduct = view.findViewById(R.id.layout_product);
            layoutProductDesc = view.findViewById(R.id.layout_product_desc);
            ratingBar = view.findViewById(R.id.rating_bar);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final OrderHistoryProductHolder billingHolder = (OrderHistoryProductHolder) holder;
            final ListOfProduct datum = (ListOfProduct) dataArray.get(position);

            String pictureUrl = datum.getProductImage();
            ArrayList<Object> attrList = new ArrayList<>();
            for (int i = 0; i < datum.getAttribute().size(); i++) {

                StringBuilder attrBuilder = new StringBuilder();
                Attribute attribute = datum.getAttribute().get(i);
                attrBuilder.append(attribute.getName());
                pictureUrl = attribute.getImage();
                attrList.add(attrBuilder.toString());

            }

            if (Utility.isEmptyString(pictureUrl)) {
                pictureUrl = datum.getProductImage();
            }

            billingHolder.dataAdapter = new StringAttributeAdapter(context, attrList);
            billingHolder.recyclerViewAttribute.setAdapter(billingHolder.dataAdapter);
            billingHolder.recyclerViewAttribute.addItemDecoration(new LayoutMarginDecoration(2));

            billingHolder.txtProductName.setText(datum.getProductName());
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(billingHolder.imageProduct);

            billingHolder.txtPrice.setText(datum.getPrice());
            billingHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
            billingHolder.txtQuantity.setText("x " + datum.getQuantity());

            if (Double.parseDouble(datum.getRating()) <= 0.0) {
                billingHolder.ratingBar.setVisibility(View.GONE);
            } else {
                billingHolder.ratingBar.setRating(Float.parseFloat(datum.getRating()));
                billingHolder.ratingBar.setVisibility(View.VISIBLE);
            }

            billingHolder.layoutProduct.setTag(position);
            billingHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) billingHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setAction(actionProductSelector)
                                .setPosition(pos));
                    }

                }
            });
            billingHolder.layoutProductDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) billingHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setAction(actionProductSelector)
                                .setPosition(pos));
                    }

                }
            });

        }


        @Override
        public int getViewTypeIdentifier() {
            return ORDER_PRODUCT_VIEW;
        }
    }


}