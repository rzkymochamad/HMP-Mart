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
import com.mg.shopping.jsonutil.productratingutil.ListOfDatum;
import com.mg.shopping.jsonutil.specificproductutil.ImageRating;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.PictureObject;
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

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int RATING_IMAGE_VIEW = 3;
    private static final int PRODUCT_RATING_VIEW = 4;
    private static final int PRODUCT_PICTURES_VIEW = 5;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;


    public ReviewAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public ReviewAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ImageRating) {
            return RATING_IMAGE_VIEW;
         }
         else if (dataArray.get(position) instanceof ListOfDatum) {
             return PRODUCT_RATING_VIEW;
         }
         else if (dataArray.get(position) instanceof PictureObject){
             return PRODUCT_PICTURES_VIEW;
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

            case RATING_IMAGE_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.review_picture_item_layout, parent, false);
                return new ReviewPictureHolder(view);

            case PRODUCT_RATING_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.product_review_item_layout, parent, false);
                return new ProductRatingHolder(view);

            case PRODUCT_PICTURES_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.picture_selector_item_view, parent, false);
                return new PictureHolder(view);


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

    public ReviewAdapter setSelectionInterface(SelectionInterface selectionInterface) {
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

    protected class ReviewPictureHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imagePicture;

        public ReviewPictureHolder(View view) {
            super(view);

            imagePicture = (RoundedImageView) view.findViewById(R.id.image_picture);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final ReviewPictureHolder reviewPictureHolder = (ReviewPictureHolder) holder;
            final ImageRating imageReviewObject = (ImageRating) dataArray.get(position);

            GlideApp.with(context).load(BottomNavigationSample.getReviewPicture(imageReviewObject.getImage())).into(reviewPictureHolder.imagePicture);


        }

        @Override
        public int getViewTypeIdentifier() {
            return RATING_IMAGE_VIEW;
        }
    }

    protected class ProductRatingHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageUser;
        private TextView txtUserName;
        private AppCompatRatingBar ratingCustomer;
        private TextView txtDateTime;
        private TextView txtDescription;
        private LinearLayoutManager layoutManager;
        private RecyclerView recyclerViewPicture;
        private ReviewAdapter dataAdapter;
        private LinearLayout layoutOther;

        public ProductRatingHolder(View view) {
            super(view);

            imageUser = (RoundedImageView) view.findViewById(R.id.image_user);
            txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
            ratingCustomer = (AppCompatRatingBar) view.findViewById(R.id.rating_customer);
            txtDateTime = (TextView) view.findViewById(R.id.txt_date_time);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
            layoutOther = view.findViewById(R.id.layout_other);

            layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerViewPicture = (RecyclerView) view.findViewById(R.id.recycler_view_picture);
            recyclerViewPicture.setLayoutManager(layoutManager);

            recyclerViewPicture.addItemDecoration(new LayoutMarginDecoration(Utility.dpToPx(5)));


        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final ProductRatingHolder productRatingHolder = (ProductRatingHolder) holder;
            final com.mg.shopping.jsonutil.productratingutil.ListOfDatum
                    rating = (com.mg.shopping.jsonutil.productratingutil.ListOfDatum) dataArray.get(position);

            productRatingHolder.txtUserName.setText(rating.getName());
            productRatingHolder.txtDateTime.setText(rating.getDateCreated());

            productRatingHolder.txtDescription.setText(rating.getReview());
            productRatingHolder.txtDescription.setMaxLines(20);
            productRatingHolder.ratingCustomer.setRating(Float.parseFloat(rating.getRating()));

            GlideApp.with(context).load(BottomNavigationSample.getUserPicture(rating.getUserImage())).into(productRatingHolder.imageUser);

            productRatingHolder.dataAdapter = new ReviewAdapter(context, new ArrayList<Object>(rating.getImage()));
            productRatingHolder.recyclerViewPicture.setAdapter(productRatingHolder.dataAdapter);

            if (rating.isUser()) {

                productRatingHolder.layoutOther.setVisibility(View.VISIBLE);
                productRatingHolder.layoutOther.setTag(position);
                productRatingHolder.layoutOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) productRatingHolder.layoutOther.getTag();
                        if (selectionInterface != null) {
                            selectionInterface.onSelection(new SelectionObject()
                                    .setPosition(pos)
                                    .setAction("other_selected")
                                    .setView(productRatingHolder.layoutOther));
                        }
                    }
                });

            }

        }

        @Override
        public int getViewTypeIdentifier() {
            return PRODUCT_RATING_VIEW;
        }
    }

    protected class PictureHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundKornerLinearLayout layoutPicture;
        private ImageView imageProductPicture;

        public PictureHolder(View view) {
            super(view);

            layoutPicture = (RoundKornerLinearLayout) view.findViewById(R.id.layout_picture);
            imageProductPicture = (ImageView) view.findViewById(R.id.image_product_picture);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final PictureHolder pictureHolder = (PictureHolder) holder;
            final PictureObject datum = (PictureObject) dataArray.get(position);

            if (Utility.isEmptyString(datum.getPictureUrl())) {
                GlideApp.with(context).load(datum.getPictureBitmap()).into(pictureHolder.imageProductPicture);
            } else {

                if (datum.isRefundPicture()) {
                    GlideApp.with(context).load(BottomNavigationSample.getRefundPicture(datum.getPictureUrl())).into(pictureHolder.imageProductPicture);
                } else {
                    GlideApp.with(context).load(BottomNavigationSample.getReviewPicture(datum.getPictureUrl())).into(pictureHolder.imageProductPicture);
                }

            }


            pictureHolder.layoutPicture.setTag(position);
            pictureHolder.layoutPicture.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = (int) pictureHolder.layoutPicture.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setAction(Utility.isEmptyString(datum.getPictureUrl()) ? "picture_selector_local" : "picture_selector_server")
                                .setPosition(pos));
                    }

                    return false;
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return PRODUCT_PICTURES_VIEW;
        }
    }

}