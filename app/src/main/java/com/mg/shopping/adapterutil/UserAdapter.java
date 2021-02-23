package com.mg.shopping.adapterutil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.billingproductutil.BillingAddress;
import com.mg.shopping.jsonutil.specificuseraddressbookutil.ListOfDatum;
import com.mg.shopping.objectutil.EmptyObject;
import com.mg.shopping.objectutil.ProgressObject;
import com.mg.shopping.objectutil.SelectionObject;
import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int USER_ADDRESS_VIEW = 3;
    private static final int BILLING_ADDRESS_ITEM_VIEW = 4;

    private String actionAddressSelector = "address_selector";

    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    public UserAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public UserAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof ListOfDatum) {
            return USER_ADDRESS_VIEW;
         }
         else if (dataArray.get(position) instanceof BillingAddress) {
             return BILLING_ADDRESS_ITEM_VIEW;
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

            case USER_ADDRESS_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.specific_user_address_book_item_layout, parent, false);
                return new UserAddressBookHolder(view);

            case BILLING_ADDRESS_ITEM_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.billing_address_item_layout, parent, false);
                return new BillingAddressHolder(view);


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

    public UserAdapter setSelectionInterface(SelectionInterface selectionInterface) {
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

    protected class UserAddressBookHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private TextView txtApartment;
        private TextView txtAddress;
        private TextView txtTagline;
        private LinearLayout layoutSelection;
        LinearLayout layoutDetail;
        private LinearLayout layoutDelete;

        public UserAddressBookHolder(View view) {
            super(view);

            txtApartment = (TextView) view.findViewById(R.id.txt_apartment);
            txtAddress = (TextView) view.findViewById(R.id.txt_address);
            txtTagline = (TextView) view.findViewById(R.id.txt_tagline);
            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
            layoutDetail = view.findViewById(R.id.layout_detail);
            layoutDelete = view.findViewById(R.id.layout_delete);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final UserAddressBookHolder userAddressBookHolder = (UserAddressBookHolder) holder;
            final ListOfDatum listOfDatum = (ListOfDatum) dataArray.get(position);

            userAddressBookHolder.txtApartment.setText(listOfDatum.getApartment() + " - ");
            userAddressBookHolder.txtAddress.setText(listOfDatum.getAddress());
            userAddressBookHolder.txtTagline.setText(listOfDatum.getPhone() + " (" + listOfDatum.getCity() + ")");


            userAddressBookHolder.layoutSelection.setTag(position);
            userAddressBookHolder.layoutSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) userAddressBookHolder.layoutSelection.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionAddressSelector));
                    }
                }
            });

            userAddressBookHolder.layoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) userAddressBookHolder.layoutSelection.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("delete_address_book_selector"));
                    }
                }
            });

        }


        @Override
        public int getViewTypeIdentifier() {
            return USER_ADDRESS_VIEW;
        }
    }

    protected class BillingAddressHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private TextView txtApartment;
        private TextView txtAddress;
        private TextView txtTagline;
        private LinearLayout layoutSelection;
        private LinearLayout layoutDetail;

        public BillingAddressHolder(View view) {
            super(view);

            txtApartment = (TextView) view.findViewById(R.id.txt_apartment);
            txtAddress = (TextView) view.findViewById(R.id.txt_address);
            txtTagline = (TextView) view.findViewById(R.id.txt_tagline);
            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
            layoutDetail = view.findViewById(R.id.layout_detail);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final BillingAddressHolder billingAddressHolder = (BillingAddressHolder) holder;
            final BillingAddress listOfDatum = (BillingAddress) dataArray.get(position);

            billingAddressHolder.txtApartment.setText(listOfDatum.getApartment() + " - ");
            billingAddressHolder.txtAddress.setText(listOfDatum.getAddress());
            billingAddressHolder.txtTagline.setText(listOfDatum.getPhone() + " (" + listOfDatum.getCity() + ")");

            if (listOfDatum.isSelection()) {
                billingAddressHolder.layoutSelection.setBackgroundResource(R.drawable.bg_circle_filled);
            } else {
                billingAddressHolder.layoutSelection.setBackgroundResource(R.drawable.bg_circle_stroked);
            }

            billingAddressHolder.layoutSelection.setTag(position);

            billingAddressHolder.layoutSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) billingAddressHolder.layoutSelection.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionAddressSelector));
                    }
                }
            });

            billingAddressHolder.layoutDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) billingAddressHolder.layoutSelection.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction(actionAddressSelector));
                    }
                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return BILLING_ADDRESS_ITEM_VIEW;
        }
    }

}