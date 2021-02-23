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
import com.mg.shopping.jsonutil.hotdatautil.ListOfBrand;
import com.mg.shopping.jsonutil.listofproductutil.ListOfDatum;
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

public class HotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int FEATURED_BRANDS_VIEW = 3;
    private static final int TRENDING_PRODUCT_VIEW = 4;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    private String actionProduct = "product";
    private String actionProductCart = "product_cart";
    private String actionBrand = "brand";

    public HotAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public HotAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfBrand) {
            return FEATURED_BRANDS_VIEW;
        }
         else  if (dataArray.get(position) instanceof ListOfDatum) {
            return TRENDING_PRODUCT_VIEW;
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

            case FEATURED_BRANDS_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_brand_item_layout, parent, false);
                return new FeaturedBrandHolder(view);

            case TRENDING_PRODUCT_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorized_product_item_layout, parent, false);
                return new TrendingProductHolder(view);

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

    protected class FeaturedBrandHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutBrand;
        private RoundedImageView imageBrand;

        public FeaturedBrandHolder(View view) {
            super(view);

            layoutBrand = view.findViewById(R.id.layout_brand);
            imageBrand = (RoundedImageView) view.findViewById(R.id.image_brand);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final FeaturedBrandHolder brandHolder = (FeaturedBrandHolder) holder;
            final ListOfBrand datum = (ListOfBrand) dataArray.get(position);

            GlideApp.with(context).load(BottomNavigationSample.getBrandPicture(datum.getImage())).into(brandHolder.imageBrand);

            brandHolder.layoutBrand.setTag(position);
            brandHolder.layoutBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) brandHolder.layoutBrand.getTag();
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
            return FEATURED_BRANDS_VIEW;
        }
    }

    protected class TrendingProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private AppCompatImageView imageCategory;
        private AppCompatRatingBar ratingBook;
        private TextView txtProduct;
        private TextView txtPrice;
        private TextView txtCurrencySymbol;
        private TextView txtSold;
        private ImageView imageCart;

        public TrendingProductHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageCategory = (AppCompatImageView) view.findViewById(R.id.image_category);
            ratingBook = view.findViewById(R.id.rating_book);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
            txtSold = (TextView) view.findViewById(R.id.txt_sold);
            imageCart = (ImageView) view.findViewById(R.id.image_cart);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final TrendingProductHolder productHolder = (TrendingProductHolder) holder;
            final com.mg.shopping.jsonutil.listofproductutil.ListOfDatum listOfDatum
                    = (com.mg.shopping.jsonutil.listofproductutil.ListOfDatum) dataArray.get(position);

            productHolder.txtProduct.setText(listOfDatum.getName());
            productHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
            productHolder.txtPrice.setText(listOfDatum.getPrice());

            if (productHolder.ratingBook != null) {

                if (Utility.isEmptyString(listOfDatum.getRating()))
                    productHolder.ratingBook.setRating(0);
                else
                    productHolder.ratingBook.setRating(Float.parseFloat(listOfDatum.getRating()));

            }


            productHolder.txtSold.setText(listOfDatum.getSold() + " " + Utility.getStringFromRes(context, R.string.sold));

            String pictureUrl = listOfDatum.getCoverImage();
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

            productHolder.imageCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) productHolder.layoutProduct.getTag();
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
            return TRENDING_PRODUCT_VIEW;
        }
    }


    

}