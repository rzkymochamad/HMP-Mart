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
import com.mg.shopping.jsonutil.shippingrateutil.Shipping;
import com.mg.shopping.jsonutil.specificproductutil.AvailableShipping;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ShippingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int SHIPPING_VIEW = 3;
    private static final int AVAILABLE_SHIPPING_VIEW = 4;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    public ShippingAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray = (ArrayList<Object>) dataArray;


    }

    public ShippingAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray = (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

        if (dataArray.get(position) instanceof Shipping) {
            return SHIPPING_VIEW;
        }
        else if (dataArray.get(position) instanceof AvailableShipping) {
            return AVAILABLE_SHIPPING_VIEW;
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
                return new EmptyHolder(view);

            case PROGRESS_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
                return new ProgressHolder(view);

            case SHIPPING_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.shipping_service_item_layout, parent, false);
                return new ShippingHolder(view);

            case AVAILABLE_SHIPPING_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.shipping_service_item_layout, parent, false);
                return new AvailableShippingHolder(view);

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

    public ShippingAdapter setSelectionInterface(SelectionInterface selectionInterface) {
        this.selectionInterface = selectionInterface;
        return this;
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

    protected class ShippingHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageShipping;
        private TextView txtShippingName;
        private TextView txtDeliveryTime;
        private TextView txtPrice;
        private LinearLayout layoutSelection;
        private LinearLayout layoutShipping;

        public ShippingHolder(View view) {
            super(view);
            imageShipping = (RoundedImageView) view.findViewById(R.id.image_shipping);
            txtShippingName = (TextView) view.findViewById(R.id.txt_shipping_name);
            txtDeliveryTime = (TextView) view.findViewById(R.id.txt_delivery_time);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
            layoutShipping = view.findViewById(R.id.layout_shipping);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final ShippingHolder shippingHolder = (ShippingHolder) holder;
            final Shipping shipping = (Shipping) dataArray.get(position);

            shippingHolder.txtShippingName.setText(shipping.getCarrier() + " " + shipping.getService());
            shippingHolder.txtDeliveryTime.setText(shipping.getDeliveryDays() == null ? Utility.getStringFromRes(context, R.string.same_day) : shipping.getDeliveryDays() + " " + Utility.getStringFromRes(context, R.string.business_days));
            shippingHolder.txtPrice.setText(shipping.getCurrency() + " " + shipping.getRate());

            if (shipping.getCarrier().equalsIgnoreCase("USPS")
                    && Utility.isEmptyString(shipping.getLogo())) {
                GlideApp.with(context).load(R.drawable.ic_usps).into(shippingHolder.imageShipping);
            } else {
                GlideApp.with(context).load(BottomNavigationSample.getShippingPicture(shipping.getLogo())).into(shippingHolder.imageShipping);
            }


            if (shipping.isSelection()) {
                shippingHolder.layoutSelection.setVisibility(View.VISIBLE);
            } else {
                shippingHolder.layoutSelection.setVisibility(View.INVISIBLE);
            }

            shippingHolder.layoutShipping.setTag(position);
            shippingHolder.layoutShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) shippingHolder.layoutShipping.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("shipping"));
                    }

                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return SHIPPING_VIEW;
        }
    }

    protected class AvailableShippingHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageShipping;
        private TextView txtShippingName;
        private TextView txtDeliveryTime;
        private TextView txtPrice;
        private LinearLayout layoutSelection;
        private LinearLayout layoutShipping;

        public AvailableShippingHolder(View view) {
            super(view);

            imageShipping = (RoundedImageView) view.findViewById(R.id.image_shipping);
            txtShippingName = (TextView) view.findViewById(R.id.txt_shipping_name);
            txtDeliveryTime = (TextView) view.findViewById(R.id.txt_delivery_time);

            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtPrice.setVisibility(View.GONE);

            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
            layoutShipping = view.findViewById(R.id.layout_shipping);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final AvailableShippingHolder shippingHolder = (AvailableShippingHolder) holder;
            final AvailableShipping shipping = (AvailableShipping) dataArray.get(position);

            shippingHolder.txtShippingName.setText(shipping.getName());
            shippingHolder.txtDeliveryTime.setText(Utility.getStringFromRes(context, R.string.estimated_time) + " " + shipping.getEstimatedDeliveryDays());

            GlideApp.with(context).load(BottomNavigationSample.getShippingPicture(shipping.getLogo())).into(shippingHolder.imageShipping);

            if (shipping.isSelection()) {
                shippingHolder.layoutSelection.setBackgroundResource(R.drawable.bg_circle_filled);
                shippingHolder.layoutSelection.setVisibility(View.VISIBLE);
            } else {
                shippingHolder.layoutSelection.setVisibility(View.INVISIBLE);
            }

            shippingHolder.layoutShipping.setTag(position);
            shippingHolder.layoutShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) shippingHolder.layoutShipping.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("shipping"));
                    }

                }
            });

        }


        @Override
        public int getViewTypeIdentifier() {
            return AVAILABLE_SHIPPING_VIEW;
        }
    }


}