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
import com.mg.shopping.jsonutil.favouritebrandutil.ListOfDatum;
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

public class FavouritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int FAVOURITES_BRAND_VIEW = 3;
    private static final int FAVOURITES_PRODUCT_VIEW = 4;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    private String actionBrand = "brand";
    private String actionProduct = "product";
    private String actionProductCart = "product_cart";

    public FavouritesAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public FavouritesAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatum) {
            return FAVOURITES_BRAND_VIEW;
         }
         else if (dataArray.get(position) instanceof com.mg.shopping.jsonutil.favouriteproductutil.ListOfDatum) {
             return FAVOURITES_PRODUCT_VIEW;
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

            case FAVOURITES_BRAND_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.all_brand_item_layout, parent, false);
                return new FavouriteBrandHolder(view);

            case FAVOURITES_PRODUCT_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.categorized_product_item_layout, parent, false);
                return new FavouritesProductHolder(view);

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

    protected class FavouriteBrandHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private ImageView imageCover;
        private RoundedImageView imageLogo;
        private TextView txtName;
        private TextView txtCtg;
        private AppCompatRatingBar ratingStore;
        private TextView txtCount;
        private RoundKornerLinearLayout layoutBrand;

        public FavouriteBrandHolder(View view) {
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

            final FavouriteBrandHolder allBrandHolder = (FavouriteBrandHolder) holder;
            final com.mg.shopping.jsonutil.favouritebrandutil.ListOfDatum listOfDatum = (com.mg.shopping.jsonutil.favouritebrandutil.ListOfDatum) dataArray.get(position);

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
            return FAVOURITES_BRAND_VIEW;
        }
    }

    protected class FavouritesProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutProduct;
        private AppCompatImageView imageCategory;
        private TextView txtProduct;
        TextView txtPrice;
        private TextView txtSold;
        private ImageView imageCart;

        public FavouritesProductHolder(View view) {
            super(view);

            layoutProduct = (RoundKornerLinearLayout) view.findViewById(R.id.layout_product);
            imageCategory = (AppCompatImageView) view.findViewById(R.id.image_category);
            txtProduct = (TextView) view.findViewById(R.id.txt_product);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtSold = (TextView) view.findViewById(R.id.txt_sold);
            imageCart = (ImageView) view.findViewById(R.id.image_cart);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final FavouritesProductHolder productHolder = (FavouritesProductHolder) holder;
            final com.mg.shopping.jsonutil.favouriteproductutil.ListOfDatum listOfDatum
                    = (com.mg.shopping.jsonutil.favouriteproductutil.ListOfDatum) dataArray.get(position);

            productHolder.txtProduct.setText(listOfDatum.getName());
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
            return FAVOURITES_PRODUCT_VIEW;
        }
    }

    

}