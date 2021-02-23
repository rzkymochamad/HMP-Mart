package com.mg.shopping.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.cartproductutil.Attribute;
import com.mg.shopping.jsonutil.cartproductutil.ListOfDatum;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import com.mg.shopping.textviewutil.TaglineTextview;
import com.mg.shopping.utility.Utility;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int CART_PRODUCT_VIEW = 3;
    private static final int PRODUCT_ATTRIBUTE_VIEW = 4;


    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    public CartAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public CartAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatum) {
            return CART_PRODUCT_VIEW;
         }
         else  if (dataArray.get(position) instanceof ProgressObject) {
             return PROGRESS_VIEW;
         }
         else  if (dataArray.get(position) instanceof String) {
             return PRODUCT_ATTRIBUTE_VIEW;
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

            case CART_PRODUCT_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
                return new CartProductHolder(view);


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

    protected class CartProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutSelector;
        private LinearLayout layoutSelection;
        private LinearLayout layoutItem;
        private RoundedImageView imageProduct;
        private TextView txtProductName;
        private TextView txtPrice;
        private TextView txtCurrencySymbol;
        private ImageView imageMinus;
        private EditText editQuantity;
        ProgressBar progressQuantity;
        private ImageView imageAdd;
        TextView txtDiscount;

        private LinearLayoutManager layoutManager;
        private RecyclerView recyclerViewAttribute;
        private StringAttributeAdapter dataAdapter;
        String pictureUrl = null;

        public CartProductHolder(View view) {
            super(view);

            layoutSelector = (LinearLayout) view.findViewById(R.id.layout_selector);
            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
            layoutItem = view.findViewById(R.id.layout_item);

            imageProduct = (RoundedImageView) view.findViewById(R.id.image_product);
            txtProductName = (TaglineTextview) view.findViewById(R.id.txt_product_name);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
            imageMinus = (ImageView) view.findViewById(R.id.image_minus);
            editQuantity = (EditText) view.findViewById(R.id.edit_quantity);
            progressQuantity = (ProgressBar) view.findViewById(R.id.progress_quantity);
            imageAdd = (ImageView) view.findViewById(R.id.image_add);
            txtDiscount = (TaglineTextview) view.findViewById(R.id.txt_discount);

            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewAttribute = (RecyclerView) view.findViewById(R.id.recycler_view_attribute);
            recyclerViewAttribute.setLayoutManager(layoutManager);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            //<editor-fold desc="Functionality of holder">
            final CartProductHolder cartProductHolder = (CartProductHolder) holder;
            final com.mg.shopping.jsonutil.cartproductutil.ListOfDatum listOfDatum
                    = (com.mg.shopping.jsonutil.cartproductutil.ListOfDatum) dataArray.get(position);

            cartProductHolder.txtProductName.setText(listOfDatum.getName());
            cartProductHolder.editQuantity.setText(listOfDatum.getQuantity());
            cartProductHolder.txtPrice.setText(listOfDatum.getPrice());
            cartProductHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());



            ArrayList<Object> attrList = new ArrayList<>();
            attrList.addAll(getProductAttributes(listOfDatum));  // get attributes list

            cartProductHolder.dataAdapter = new StringAttributeAdapter(context, attrList);

            cartProductHolder.recyclerViewAttribute.setAdapter(cartProductHolder.dataAdapter);
            cartProductHolder.recyclerViewAttribute.addItemDecoration(new LayoutMarginDecoration(2));

            if (Utility.isEmptyString(pictureUrl)) {
                pictureUrl = !listOfDatum.getImage().isEmpty() ? listOfDatum.getImage().get(0).getImage() : "null";
            }

            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(cartProductHolder.imageProduct);

            cartProductHolder.layoutSelection.setBackgroundResource(getSelectionBg(listOfDatum));

            cartProductHolder.layoutSelector.setTag(position);
            cartProductHolder.layoutSelector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) cartProductHolder.layoutSelector.getTag();

                    if (((com.mg.shopping.jsonutil.cartproductutil.ListOfDatum) dataArray.get(pos)).isSelection()) {
                        ((com.mg.shopping.jsonutil.cartproductutil.ListOfDatum) dataArray.get(pos)).setSelection(false);

                        if (selectionInterface != null) {
                            selectionInterface.onSelection(new SelectionObject()
                                    .setPosition(pos)
                                    .setAction("unselected"));
                        }

                    } else {
                        ((com.mg.shopping.jsonutil.cartproductutil.ListOfDatum) dataArray.get(pos)).setSelection(true);

                        if (selectionInterface != null) {
                            selectionInterface.onSelection(new SelectionObject()
                                    .setPosition(pos)
                                    .setAction("selected"));
                        }

                    }

                    notifyItemChanged(pos);
                }
            });

            cartProductHolder.imageMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) cartProductHolder.layoutSelector.getTag();
                    int quantity = Integer.parseInt(cartProductHolder.editQuantity.getText().toString());
                    if (quantity <= 1) {
                        return;
                    }

                    int totalQuantity = --quantity;
                    cartProductHolder.editQuantity.setText(String.valueOf(totalQuantity));

                    applyQuantityAction(new SelectionObject()
                            .setPosition(pos)
                            .setQuantity(totalQuantity)
                            .setAction("minus"));


                }
            });

            cartProductHolder.imageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) cartProductHolder.layoutSelector.getTag();
                    int quantity = Integer.parseInt(cartProductHolder.editQuantity.getText().toString());
                    int totalQuantity = ++quantity;
                    cartProductHolder.editQuantity.setText(String.valueOf(totalQuantity));

                    applyQuantityAction(new SelectionObject()
                            .setPosition(pos)
                            .setQuantity(totalQuantity)
                            .setAction("add"));

                }
            });


            cartProductHolder.layoutItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = (int) cartProductHolder.layoutSelector.getTag();
                    Utility.Logger(getClass().getSimpleName(), "LongClick Happened");

                    applyQuantityAction(new SelectionObject()
                            .setPosition(pos)
                            .setAction("delete_items"));


                    return false;
                }
            });

            //</editor-fold>

        }

        @Override
        public int getViewTypeIdentifier() {
            return CART_PRODUCT_VIEW;
        }

        private Collection<?> getProductAttributes(ListOfDatum listOfDatum) {

            ArrayList<Object> attrList = new ArrayList<>();

            for (int i = 0; i < listOfDatum.getAttribute().size(); i++) {

                StringBuilder attrBuilder = new StringBuilder();
                Attribute attribute = listOfDatum.getAttribute().get(i);
                attrBuilder.append(attribute.getName());
                attrBuilder.append(" : ");

                for (int j = 0; j < attribute.getItem().size(); j++) {

                    com.mg.shopping.jsonutil.cartproductutil.Item item = attribute.getItem().get(j);
                    attrBuilder.append(item.getName());
                    pictureUrl = item.getImage();

                }

                attrList.add(attrBuilder.toString());

            }

            return attrList;

        }

        private int getSelectionBg(ListOfDatum listOfDatum) {

            if (listOfDatum.isSelection()) {
                return R.drawable.bg_circle_filled;
            } else {
                return R.drawable.bg_circle_stroked;
            }

        }

        private void applyQuantityAction(SelectionObject selectionObject) {

            if (selectionInterface != null) {
                selectionInterface.onSelection(selectionObject);
            }

        }


    }

    public CartAdapter setSelectionInterface(SelectionInterface selectionInterface) {
        this.selectionInterface = selectionInterface;
        return this;
    }

}