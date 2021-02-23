package com.mg.shopping.adapterutil;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haozhang.lib.SlantedTextView;
import com.mg.shopping.R;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.saleproductutil.ListOfDatumData;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class SaleProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int SALE_PRODUCT_VIEW = 3;


    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    private String actionProduct = "product";
    private String actionProductCart = "product_cart";

    public SaleProductAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public SaleProductAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatumData) {
            return SALE_PRODUCT_VIEW;
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

            case SALE_PRODUCT_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.sale_product_item_layout, parent, false);
                return new SaleProductHolder(view);


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

    protected class SaleProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private AppCompatImageView imageCategory;
        private TextView txtProduct;
        private TextView txtPrice;
        private TextView txtCurrencySymbol;
        private TextView txtDiscountedPrice;
        private TextView txtSaleCurrencySymbol;
        private TextView txtSold;
        private ImageView imageCart;
        private SlantedTextView txtSale;

        public SaleProductHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageCategory = (AppCompatImageView) view.findViewById(R.id.image_category);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);

            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            txtSaleCurrencySymbol = view.findViewById(R.id.txt_sale_currency_symbol);
            txtDiscountedPrice = view.findViewById(R.id.txt_discounted_price);
            txtSold = (TextView) view.findViewById(R.id.txt_sold);

            txtSale = view.findViewById(R.id.txt_sale);

            imageCart = (ImageView) view.findViewById(R.id.image_cart);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final SaleProductHolder saleProductHolder = (SaleProductHolder) holder;
            final ListOfDatumData listOfDatum = (ListOfDatumData) dataArray.get(position);

            saleProductHolder.txtProduct.setText(listOfDatum.getName());
            saleProductHolder.txtSold.setText(listOfDatum.getDaysLeft() + " " + Utility.getStringFromRes(context, R.string.days_left));

            double totalPrice = 0;
            double discountedPrice;

            if (listOfDatum.getSaleType().equalsIgnoreCase("1")) {

                saleProductHolder.txtSale.setText(listOfDatum.getDiscount() + " % Off");
                discountedPrice = Double.parseDouble(listOfDatum.getDiscount());
                totalPrice = Double.parseDouble(listOfDatum.getPrice()) -
                        ((Double.parseDouble(listOfDatum.getPrice()) * discountedPrice) / 100);

            } else {
                saleProductHolder.txtSale.setText(listOfDatum.getDiscount() + "  Off");
                discountedPrice = Double.parseDouble(listOfDatum.getDiscount());
                totalPrice = Double.parseDouble(listOfDatum.getPrice()) - discountedPrice;
            }

            saleProductHolder.txtPrice.setText(listOfDatum.getPrice());
            saleProductHolder.txtDiscountedPrice.setText(String.valueOf(Math.round(totalPrice)));

            saleProductHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
            saleProductHolder.txtSaleCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            ///saleProductHolder.txtSale.setText(listOfDatum.get)
            String pictureUrl = listOfDatum.getCoverImage();
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(saleProductHolder.imageCategory);


            saleProductHolder.layoutProduct.setTag(position);
            saleProductHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) saleProductHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionProduct));
                    }
                }
            });

            saleProductHolder.imageCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) saleProductHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionProductCart));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return SALE_PRODUCT_VIEW;
        }

    }


}