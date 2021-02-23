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
import com.mg.shopping.constantutil.Constant;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcategoryutil.Brand;
import com.mg.shopping.jsonutil.allcategoryutil.Datum;
import com.mg.shopping.jsonutil.allcategoryutil.DatumDetail;
import com.mg.shopping.jsonutil.allcategoryutil.ListOfDatum;
import com.mg.shopping.objectutil.DataObject;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;

import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int CATEGORY_VIEW = 3;
    private static final int SUB_CATEGORY_VIEW = 4;
    private static final int CHILD_CATEGORY_VIEW = 5;
    private static final int CATEGORY_LIST_VIEW = 6;
    private static final int BRAND_LABEL_ITEM_VIEW = 7;
    private static final int BRAND_ITEM_VIEW = 8;

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    private String actionSubCategory = "sub_category";
    private String actionBrand = "brand";

    public CategoryAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray = (ArrayList<Object>) dataArray;


    }

    public CategoryAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray = (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatum) {
            return CATEGORY_VIEW;
        }
         else if (dataArray.get(position) instanceof Datum) {
            return SUB_CATEGORY_VIEW;
        }
         else if (dataArray.get(position) instanceof DatumDetail) {
            return CHILD_CATEGORY_VIEW;
        }
         else if (dataArray.get(position) instanceof DataObject &&
                 ((DataObject)dataArray.get(position)).getDataType() == Constant.CONNECTION.LIST_OF_BRAND) {
             return BRAND_LABEL_ITEM_VIEW;
         }
         else if (dataArray.get(position) instanceof DataObject &&
                 ((DataObject)dataArray.get(position)).getDataType() == Constant.CONNECTION.LIST_OF_CATEGORIES) {
             return CATEGORY_LIST_VIEW;
         }
         else if (dataArray.get(position) instanceof Brand) {
             return BRAND_ITEM_VIEW;
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


            case CATEGORY_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_layout, parent, false);
                return new CategoryHolder(view);

            case SUB_CATEGORY_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item_layout, parent, false);
                return new SubCategoryHolder(view);

            case CHILD_CATEGORY_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_category_item_layout, parent, false);
                return new ChildCategoryHolder(view);

            case CATEGORY_LIST_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item_layout, parent, false);
                return new CategoryBottomSheetHolder(view);

            case BRAND_LABEL_ITEM_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item_layout, parent, false);
                return new BrandLabelHolder(view);

            case BRAND_ITEM_VIEW:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item_layout, parent, false);
                return new BrandHolder(view);

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

    protected class CategoryHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutCategory;
        private LinearLayout layoutSelection;
        private ImageView imageCategory;
        private TextView txtCategoryName;

        public CategoryHolder(View view) {
            super(view);

            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
            layoutCategory = (LinearLayout) view.findViewById(R.id.layout_category);
            imageCategory = (ImageView) view.findViewById(R.id.image_category);
            txtCategoryName = (TextView) view.findViewById(R.id.txt_category_name);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final CategoryHolder categoryHolder = (CategoryHolder) holder;
            final ListOfDatum datum = (ListOfDatum) dataArray.get(position);

            categoryHolder.txtCategoryName.setText(datum.getName());
            GlideApp.with(context).load(BottomNavigationSample.getCategoryPicture(datum.getImage())).into(categoryHolder.imageCategory);

            if (datum.isSelected()) {
                categoryHolder.layoutSelection.setVisibility(View.VISIBLE);
                categoryHolder.layoutCategory.setBackgroundColor(Utility.getAttrColor(context, R.attr.colorBackgroundSecondary));
            } else {
                categoryHolder.layoutSelection.setVisibility(View.INVISIBLE);
                categoryHolder.layoutCategory.setBackgroundColor(Utility.getAttrColor(context, R.attr.colorBackgroundTertiary));

            }

            categoryHolder.layoutCategory.setTag(position);
            categoryHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutCategory.getTag();
                    ((ListOfDatum) dataArray.get(pos)).setSelected(((ListOfDatum) dataArray.get(pos)).isSelected());
                    notifyItemChanged(pos);


                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("category"));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return CATEGORY_VIEW;
        }
    }

    protected class SubCategoryHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutSubCategory;
        private TextView txtSubCategoryName;
        private TextView txtAll;
        private GridLayoutManager gridLayoutManager;
        private RecyclerView recyclerViewSubCategory;
        private CategoryAdapter dataAdapter;

        public SubCategoryHolder(View view) {
            super(view);

            layoutSubCategory = (LinearLayout) view.findViewById(R.id.layout_sub_category);
            txtSubCategoryName = (TextView) view.findViewById(R.id.txt_sub_category_name);
            txtAll = (TextView) view.findViewById(R.id.txt_all);

            gridLayoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
            recyclerViewSubCategory = (RecyclerView) view.findViewById(R.id.recycler_view_sub_category);
            recyclerViewSubCategory.setLayoutManager(gridLayoutManager);


        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final SubCategoryHolder categoryHolder = (SubCategoryHolder) holder;
            final Datum datum = (Datum) dataArray.get(position);

            categoryHolder.txtSubCategoryName.setText(datum.getName());
            categoryHolder.dataAdapter = new CategoryAdapter(context, new ArrayList<Object>(datum.getData()), new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {

                    int childPost = selectionObject.getChildPosition();
                    int pos = (int) categoryHolder.layoutSubCategory.getTag();

                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setChildPosition(childPost)
                                .setAction("child_sub_category_id"));
                    }

                }
            });
            categoryHolder.recyclerViewSubCategory.setAdapter(categoryHolder.dataAdapter);

            if (datum.getData().isEmpty()) {
                categoryHolder.recyclerViewSubCategory.setVisibility(View.GONE);
                categoryHolder.txtAll.setVisibility(View.GONE);
            } else {
                categoryHolder.recyclerViewSubCategory.setVisibility(View.VISIBLE);
                categoryHolder.txtAll.setVisibility(View.VISIBLE);
            }

            categoryHolder.layoutSubCategory.setTag(position);
            categoryHolder.layoutSubCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutSubCategory.getTag();

                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionSubCategory));
                    }
                }
            });

        }


        @Override
        public int getViewTypeIdentifier() {
            return SUB_CATEGORY_VIEW;
        }
    }

    protected class ChildCategoryHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutCategory;
        private AppCompatImageView imageCategory;
        private TextView txtCategoryName;

        public ChildCategoryHolder(View view) {
            super(view);

            layoutCategory = (LinearLayout) view.findViewById(R.id.layout_category);
            imageCategory = (AppCompatImageView) view.findViewById(R.id.image_category);
            txtCategoryName = (TextView) view.findViewById(R.id.txt_category_name);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final ChildCategoryHolder categoryHolder = (ChildCategoryHolder) holder;
            final DatumDetail datum = (DatumDetail) dataArray.get(position);

            categoryHolder.txtCategoryName.setText(datum.getName());
            GlideApp.with(context).load(BottomNavigationSample.getCategoryPicture(datum.getImage())).into(categoryHolder.imageCategory);

            categoryHolder.layoutCategory.setTag(position);
            categoryHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutCategory.getTag();

                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setChildPosition(pos)
                                .setAction("child_sub_category"));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return CHILD_CATEGORY_VIEW;
        }
    }

    protected class CategoryBottomSheetHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutCategory;
        private TextView txtCategoryName;
        private ImageView imageSelected;
        private TextView divider;

        public CategoryBottomSheetHolder(View view) {
            super(view);
            layoutCategory = (LinearLayout) view.findViewById(R.id.layout_category);
            txtCategoryName = (TextView) view.findViewById(R.id.txt_category_name);
            imageSelected = (ImageView) view.findViewById(R.id.image_selected);
            divider = (TextView) view.findViewById(R.id.divider);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final CategoryBottomSheetHolder categoryHolder = (CategoryBottomSheetHolder) holder;
            final DataObject dataObject = (DataObject) dataArray.get(position);

            categoryHolder.txtCategoryName.setText(dataObject.getCategoryName());

            if (dataObject.isSelection()) {
                categoryHolder.imageSelected.setVisibility(View.VISIBLE);
                categoryHolder.txtCategoryName.setTextColor(Utility.getAttrColor(context, R.attr.colorTextPrimary));
            } else {
                categoryHolder.imageSelected.setVisibility(View.INVISIBLE);
                categoryHolder.txtCategoryName.setTextColor(Utility.getAttrColor(context, R.attr.colorTextQuinary));
            }

            if (dataObject.isLastItem()) {
                categoryHolder.divider.setVisibility(View.GONE);
            }

            categoryHolder.layoutCategory.setTag(position);
            categoryHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutCategory.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionSubCategory));
                    }
                }
            });

        }


        @Override
        public int getViewTypeIdentifier() {
            return CATEGORY_LIST_VIEW;
        }
    }

    protected class BrandLabelHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutSubCategory;
        private TextView txtSubCategoryName;
        TextView txtAll;
        private GridLayoutManager gridLayoutManager;
        private RecyclerView recyclerViewSubCategory;
        private CategoryAdapter dataAdapter;

        public BrandLabelHolder(View view) {
            super(view);

            layoutSubCategory = (LinearLayout) view.findViewById(R.id.layout_sub_category);
            txtSubCategoryName = (TextView) view.findViewById(R.id.txt_sub_category_name);
            txtAll = (TextView) view.findViewById(R.id.txt_all);

            gridLayoutManager = new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
            recyclerViewSubCategory = (RecyclerView) view.findViewById(R.id.recycler_view_sub_category);
            recyclerViewSubCategory.setLayoutManager(gridLayoutManager);


        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final BrandLabelHolder categoryHolder = (BrandLabelHolder) holder;
            final DataObject datum = (DataObject) dataArray.get(position);

            categoryHolder.txtSubCategoryName.setText(datum.getName());
            Utility.Logger("Brand Size", String.valueOf(datum.getBrandArrayList().size()));
            categoryHolder.dataAdapter = new CategoryAdapter(context, new ArrayList<Object>(datum.getBrandArrayList()), new SelectionInterface() {
                @Override
                public void onSelection(SelectionObject selectionObject) {

                    int childPost = selectionObject.getChildPosition();
                    int pos = (int) categoryHolder.layoutSubCategory.getTag();

                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setChildPosition(childPost)
                                .setAction(actionBrand));
                    }

                }
            });
            categoryHolder.recyclerViewSubCategory.setAdapter(categoryHolder.dataAdapter);

            if (datum.getBrandArrayList().isEmpty()) {
                categoryHolder.recyclerViewSubCategory.setVisibility(View.GONE);
            } else {
                categoryHolder.recyclerViewSubCategory.setVisibility(View.VISIBLE);
            }

            categoryHolder.layoutSubCategory.setTag(position);
            categoryHolder.layoutSubCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) categoryHolder.layoutSubCategory.getTag();

                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionSubCategory));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return BRAND_LABEL_ITEM_VIEW;
        }
    }

    protected class BrandHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutBrand;
        private RoundedImageView imageBrand;

        public BrandHolder(View view) {
            super(view);

            layoutBrand = view.findViewById(R.id.layout_brand);
            imageBrand = (RoundedImageView) view.findViewById(R.id.image_brand);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final BrandHolder brandHolder = (BrandHolder) holder;
            final Brand datum = (Brand) dataArray.get(position);

            GlideApp.with(context).load(BottomNavigationSample.getBrandPicture(datum.getImage())).into(brandHolder.imageBrand);

            brandHolder.layoutBrand.setTag(position);
            brandHolder.layoutBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) brandHolder.layoutBrand.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setChildPosition(pos)
                                .setAction(actionBrand));
                    }
                }
            });


        }

        @Override
        public int getViewTypeIdentifier() {
            return BRAND_ITEM_VIEW;
        }
    }

    

}