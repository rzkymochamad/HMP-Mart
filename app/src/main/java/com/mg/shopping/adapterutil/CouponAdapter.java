package com.mg.shopping.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.dateutil.DateBuilder;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.listofcouponutil.ListOfDatumCoupon;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int COUPON_VIEW = 3;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;


    public CouponAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray = (ArrayList<Object>) dataArray;

    }

    public CouponAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray = (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatumCoupon) {
            return COUPON_VIEW;
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

        Utility.Logger(getClass().getSimpleName(),"ViewType = "+viewType);

        switch (viewType) {
            case NO_DATA_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
                return  new EmptyHolder(view);

            case PROGRESS_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
                return new ProgressHolder(view);

            case COUPON_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.brand_coupon_item_layout, parent, false);
                return new CouponHolder(view);


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

    protected class CouponHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private TextView txtSaleOffer;
        private TextView txtSaleTagline;
        private TextView txtExpireDate;
        private RoundKornerLinearLayout layoutGet;

        public CouponHolder(View view) {
            super(view);

            txtSaleOffer = (TextView) view.findViewById(R.id.txt_sale_offer);
            txtSaleTagline = (TextView) view.findViewById(R.id.txt_sale_tagline);
            txtExpireDate = (TextView) view.findViewById(R.id.txt_expire_date);
            layoutGet = (RoundKornerLinearLayout) view.findViewById(R.id.layout_get);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final CouponHolder couponHolder = (CouponHolder) holder;
            final ListOfDatumCoupon listOfDatum = (ListOfDatumCoupon) dataArray.get(position);

            DateBuilder date = new DateBuilder()
                    .setGivenDateTime(listOfDatum.getExpireDate())
                    .setGivenFormat("yyyy-MM-dd hh:mm:ss")
                    .setOutputFormat("dd MMM yyyy")
                    .buildDate();

            couponHolder.txtSaleOffer.setText(listOfDatum.getName());
            couponHolder.txtSaleTagline.setText(listOfDatum.getName() + " for " + listOfDatum.getCategoryType());
            couponHolder.txtExpireDate.setText(Utility.getStringFromRes(context, R.string.expires) + " : " + date.getResult());

            couponHolder.layoutGet.setTag(position);
            couponHolder.layoutGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) couponHolder.layoutGet.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("coupon"));
                    }
                }
            });

        }



        @Override
        public int getViewTypeIdentifier() {
            return COUPON_VIEW;
        }
    }


}