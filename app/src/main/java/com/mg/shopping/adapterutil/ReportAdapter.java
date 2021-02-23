package com.mg.shopping.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.specificuserrefundutil.ListOfDatum;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class ReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int REPORT_ORDER_VIEW = 3;


    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    public ReportAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public ReportAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatum) {
            return REPORT_ORDER_VIEW;
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

            case REPORT_ORDER_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.specific_user_report_item_layout, parent, false);
                return new ReportHolder(view);


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

    protected class ReportHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private RoundedImageView imageProduct;
        private TextView txtProductName;
        RoundKornerLinearLayout layoutStatus;
        private TextView txtStatus;
        private TextView txtPrice;
        private TextView txtQuantity;

        public ReportHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageProduct = (RoundedImageView) view.findViewById(R.id.image_product);
            txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
            layoutStatus = (RoundKornerLinearLayout) view.findViewById(R.id.layout_status);
            txtStatus = (TextView) view.findViewById(R.id.txt_status);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtQuantity = (TextView) view.findViewById(R.id.txt_quantity);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final ReportHolder reportHolder = (ReportHolder) holder;
            final com.mg.shopping.jsonutil.specificuserrefundutil.ListOfDatum
                    listOfDatum = (com.mg.shopping.jsonutil.specificuserrefundutil.ListOfDatum) dataArray.get(position);

            reportHolder.txtProductName.setText(listOfDatum.getProductName());
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(listOfDatum.getProductImage())).into(reportHolder.imageProduct);

            if (listOfDatum.getType().equalsIgnoreCase("refund")) {

                if (listOfDatum.getStatus().equalsIgnoreCase("0")) {
                    reportHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.pending));
                } else if (listOfDatum.getStatus().equalsIgnoreCase("1")) {
                    reportHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.accepted));
                } else if (listOfDatum.getStatus().equalsIgnoreCase("2")) {
                    reportHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.rejected));
                }

            } else {

                if (listOfDatum.getStatus().equalsIgnoreCase("0")) {
                    reportHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.pending));
                } else if (listOfDatum.getStatus().equalsIgnoreCase("1")) {
                    reportHolder.txtStatus.setText(Utility.getStringFromRes(context, R.string.accepted));
                }

            }

            reportHolder.txtPrice.setText(listOfDatum.getPrice());
            reportHolder.txtQuantity.setText("(x " + listOfDatum.getQuantity() + " )");

            reportHolder.layoutProduct.setTag(position);
            reportHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) reportHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("item_selected"));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return REPORT_ORDER_VIEW;
        }
    }

    

}