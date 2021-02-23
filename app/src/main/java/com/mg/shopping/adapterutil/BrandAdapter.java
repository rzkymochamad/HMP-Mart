package com.mg.shopping.adapterutil;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.listofbrandutil.ListOfDatum;
import com.mg.shopping.jsonutil.specificbrandproductutil.Datum;
import com.mg.shopping.jsonutil.specificbrandproductutil.LatestDatum;
import com.mg.shopping.jsonutil.specificbrandproductutil.SaleDatum;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

public class BrandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int ALL_BRANDS_VIEW = 3;
    private static final int BRAND_PRODUCT_LATEST_VIEW = 4;
    private static final int BRAND_PRODUCT_SALE_VIEW = 5;
    private static final int BRAND_ALL_PRODUCT_SALE_VIEW = 6;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    private String actionBrand = "brand";
    private String actionProduct = "product";

    public BrandAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public BrandAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatum) {
            return ALL_BRANDS_VIEW;
         }
         else if (dataArray.get(position) instanceof SaleDatum) {
             return BRAND_PRODUCT_SALE_VIEW;
         }
         else if (dataArray.get(position) instanceof LatestDatum) {
             return BRAND_PRODUCT_LATEST_VIEW;
         }
         else if (dataArray.get(position) instanceof Datum) {
             return BRAND_ALL_PRODUCT_SALE_VIEW;
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

            case ALL_BRANDS_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.all_brand_item_layout, parent, false);
                return new AllBrandHolder(view);

            case BRAND_PRODUCT_LATEST_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.brand_latest_product_item_layout, parent, false);
                return new SpecificBrandLatestProductHolder(view);

            case BRAND_PRODUCT_SALE_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.brand_sale_product_item_layout, parent, false);
                return new SpecificBrandSaleProductHolder(view);

            case BRAND_ALL_PRODUCT_SALE_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.brand_product_item_layout, parent, false);
                return new SpecificBrandProductHolder(view);

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

    public BrandAdapter setSelectionInterface(SelectionInterface selectionInterface) {
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

    protected class AllBrandHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private ImageView imageCover;
        private RoundedImageView imageLogo;
        private TextView txtName;
        private TextView txtCtg;
        private AppCompatRatingBar ratingStore;
        private TextView txtCount;
        private RoundKornerLinearLayout layoutBrand;

        public AllBrandHolder(View view) {
            super(view);

            imageCover = (ImageView) view.findViewById(R.id.image_cover);
            imageLogo = (RoundedImageView) view.findViewById(R.id.image_logo);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtCtg = (TextView) view.findViewById(R.id.txt_ctg);
            ratingStore = (AppCompatRatingBar) view.findViewById(R.id.rating_store);
            txtCount = (TextView) view.findViewById(R.id.txt_count);
            layoutBrand = view.findViewById(R.id.layout_brand);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final AllBrandHolder allBrandHolder = (AllBrandHolder) holder;
            final com.mg.shopping.jsonutil.listofbrandutil.ListOfDatum listOfDatum = (com.mg.shopping.jsonutil.listofbrandutil.ListOfDatum) dataArray.get(position);

            allBrandHolder.txtName.setText(listOfDatum.getName());
            allBrandHolder.txtCtg.setText(listOfDatum.getCategoryName());
            int count = 0;
            double rating = 0;

            if (!Utility.isEmptyString(listOfDatum.getCount())) {
                count = Integer.parseInt(listOfDatum.getCount());
            }

            if (!Utility.isEmptyString(listOfDatum.getRating())) {
                rating = Double.parseDouble(listOfDatum.getRating());
            }

            allBrandHolder.txtCount.setText("( " + count + " )");
            allBrandHolder.ratingStore.setRating((float) rating);

            GlideApp.with(context).load(BottomNavigationSample.getBrandPicture(listOfDatum.getImage())).into(allBrandHolder.imageLogo);
            GlideApp.with(context).load(BottomNavigationSample.getBrandPicture(listOfDatum.getCoverPhoto())).into(allBrandHolder.imageCover);

            allBrandHolder.layoutBrand.setTag(position);
            allBrandHolder.layoutBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) allBrandHolder.layoutBrand.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionBrand));
                    }
                }
            });

        }
        
        @Override
        public int getViewTypeIdentifier() {
            return ALL_BRANDS_VIEW;
        }
    }

    protected class SpecificBrandLatestProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private RoundedImageView imageCategory;
        private TextView txtProduct;
        private TextView txtPrice;
        private TextView txtCurrencySymbol;


        public SpecificBrandLatestProductHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageCategory = (RoundedImageView) view.findViewById(R.id.image_category);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final SpecificBrandLatestProductHolder productHolder = (SpecificBrandLatestProductHolder) holder;
            final LatestDatum latestDatum = (LatestDatum) dataArray.get(position);

            productHolder.txtProduct.setText(latestDatum.getName());
            productHolder.txtPrice.setText(latestDatum.getPrice());
            productHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            String pictureUrl = latestDatum.getCoverImage();
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(productHolder.imageCategory);


            productHolder.layoutProduct.setTag(position);
            productHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("latest_product"));
                    }
                }
            });

        }


        @Override
        public int getViewTypeIdentifier() {
            return BRAND_PRODUCT_LATEST_VIEW;
        }
    }

    protected class SpecificBrandSaleProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private AppCompatImageView imageCategory;
        private TextView txtProduct;

        private TextView txtSaleCurrencySymbol;
        private TextView txtSalePrice;

        private TextView txtPriceCurrencySymbol;
        private TextView txtPrice;

        private TextView txtDayLeft;

        public SpecificBrandSaleProductHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageCategory = (AppCompatImageView) view.findViewById(R.id.image_category);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);

            txtSaleCurrencySymbol = view.findViewById(R.id.txt_sale_currency_symbol);
            txtSalePrice = (TextView) view.findViewById(R.id.txt_sale_price);

            txtPriceCurrencySymbol = view.findViewById(R.id.txt_price_currency_symbol);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            txtDayLeft = view.findViewById(R.id.txt_days_left);


        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final SpecificBrandSaleProductHolder productHolder = (SpecificBrandSaleProductHolder) holder;
            final SaleDatum datum = (SaleDatum) dataArray.get(position);

            productHolder.txtProduct.setText(datum.getName());
            productHolder.txtDayLeft.setText(datum.getDaysLeft() + " " + Utility.getStringFromRes(context, R.string.days_left));

            double discountPercentage = Double.parseDouble(datum.getDiscount());
            double discountPrice = (Double.parseDouble(datum.getPrice()) * discountPercentage) / 100;
            double actualPrice = Math.round(Double.parseDouble(datum.getPrice()) - discountPrice);

            productHolder.txtSalePrice.setText(String.valueOf(actualPrice));
            productHolder.txtSaleCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            productHolder.txtPrice.setText(datum.getPrice());
            productHolder.txtPriceCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            String pictureUrl = datum.getCoverImage();
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(productHolder.imageCategory);


            productHolder.layoutProduct.setTag(position);
            productHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionProduct));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return BRAND_PRODUCT_SALE_VIEW;
        }
    }

    protected class SpecificBrandProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private AppCompatImageView imageCategory;
        private TextView txtProduct;
        private TextView txtPrice;
        private TextView txtCurrencySymbol;


        public SpecificBrandProductHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageCategory = (AppCompatImageView) view.findViewById(R.id.image_category);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);


        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final SpecificBrandProductHolder productHolder = (SpecificBrandProductHolder) holder;
            final com.mg.shopping.jsonutil.specificbrandproductutil.Datum datum
                    = (com.mg.shopping.jsonutil.specificbrandproductutil.Datum) dataArray.get(position);

            productHolder.txtProduct.setText(datum.getName());
            productHolder.txtPrice.setText(datum.getPrice());
            productHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());


            String pictureUrl = datum.getCoverImage();
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(productHolder.imageCategory);


            productHolder.layoutProduct.setTag(position);
            productHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionProduct));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return BRAND_ALL_PRODUCT_SALE_VIEW;
        }
    }


    

}