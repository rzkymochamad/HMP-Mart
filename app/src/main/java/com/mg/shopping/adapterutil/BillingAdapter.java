package com.mg.shopping.adapterutil;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mg.shopping.R;
import com.mg.shopping.activityutil.Billing;
import com.mg.shopping.activityutil.BottomNavigationSample;
import com.mg.shopping.customutil.GlideApp;
import com.mg.shopping.interfaceutil.SelectionInterface;
import com.mg.shopping.jsonutil.allcardutil.ListOfDatum;
import com.mg.shopping.jsonutil.billingproductutil.PaymentMethod;
import com.mg.shopping.jsonutil.multibillingproductutil.MultiBillingProductObject;
import com.mg.shopping.objectutil.EmptyObject;
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

public class BillingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NO_DATA_VIEW = 1;
    private static final int PROGRESS_VIEW = 2;
    private static final int PAYMENT_METHOD_VIEW = 3;
    private static final int AVAILABLE_CARD_VIEW = 4;
    private static final int BILLING_PRODUCT_ITEM_VIEW = 5;
    private static final int MULTI_BILLING_PRODUCT_VIEW = 6;
    private static final int MULTI_PRODUCT_BILLING_PRODUCT_VIEW = 7;

    private static final String COUPON_TYPE_SHIPPING = "Shipping";
    private static final String COUPON_TYPE_PERCENTAGE = "Percentage";
    private static final String COUPON_TYPE_FIXED = "Fixed";
    private String actionShipping = "shipping";
    
    private Context context;
    private ArrayList<Object> dataArray = new ArrayList<>();
    private SelectionInterface selectionInterface;

    public BillingAdapter(Context context, List<Object> dataArray) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;


    }

    public BillingAdapter(Context context, List<Object> dataArray, SelectionInterface selectionInterface) {
        this.context = context;
        this.dataArray= (ArrayList<Object>) dataArray;
        this.selectionInterface = selectionInterface;


    }

    @Override
    public int getItemViewType(int position) {

         if (dataArray.get(position) instanceof PaymentMethod) {
            return PAYMENT_METHOD_VIEW;
         }
         else if (dataArray.get(position) instanceof ListOfDatum) {
             return AVAILABLE_CARD_VIEW;
         }
         else if (dataArray.get(position) instanceof com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) {

             if (((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(position)).isBilingSection()) {
                 return BILLING_PRODUCT_ITEM_VIEW;
             } else {
                 return MULTI_BILLING_PRODUCT_VIEW;
             }

         }
         else if (dataArray.get(position) instanceof MultiBillingProductObject) {
             return MULTI_PRODUCT_BILLING_PRODUCT_VIEW;
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

            case PAYMENT_METHOD_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.payment_method_item_layout, parent, false);
                return new PaymentMethodHolder(view);

            case AVAILABLE_CARD_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.all_card_item_layout, parent, false);
                return new AllCardHolder(view);

            case BILLING_PRODUCT_ITEM_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.billing_product_item_layout, parent, false);
                return new BillingHolder(view);

            case MULTI_BILLING_PRODUCT_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.order_history_product_item_layout, parent, false);
                return new MultiBillingProductHolder(view);

            case MULTI_PRODUCT_BILLING_PRODUCT_VIEW:

                view = LayoutInflater.from(context).inflate(R.layout.multi_billing_product_item_layout, parent, false);
                return new MultiProductBillingHolder(view);

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

    public BillingAdapter setSelectionInterface(SelectionInterface selectionInterface) {
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

    protected class PaymentMethodHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutPaymentMethod;
        private RoundedImageView imagePaymentMethod;
        private TextView txtPaymentMethod;
        private LinearLayout layoutSelection;

        public PaymentMethodHolder(View view) {
            super(view);

            layoutPaymentMethod = (LinearLayout) view.findViewById(R.id.layout_payment_method);
            imagePaymentMethod = (RoundedImageView) view.findViewById(R.id.image_payment_method);
            txtPaymentMethod = (TextView) view.findViewById(R.id.txt_payment_method);
            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final PaymentMethodHolder paymentMethodHolder = (PaymentMethodHolder) holder;
            final PaymentMethod paymentMethod = (PaymentMethod) dataArray.get(position);

            paymentMethodHolder.txtPaymentMethod.setText(paymentMethod.getName());
            GlideApp.with(context).load(BottomNavigationSample.getCategoryPicture(paymentMethod.getImage())).into(paymentMethodHolder.imagePaymentMethod);

            if (paymentMethod.isSelection()) {
                paymentMethodHolder.layoutSelection.setBackgroundResource(R.drawable.bg_circle_filled);
            } else {
                paymentMethodHolder.layoutSelection.setBackgroundResource(R.drawable.bg_circle_stroked);
            }

            paymentMethodHolder.layoutPaymentMethod.setTag(position);
            paymentMethodHolder.layoutPaymentMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) paymentMethodHolder.layoutPaymentMethod.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("payment_method"));
                    }


                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return PAYMENT_METHOD_VIEW;
        }
    }

    protected class AllCardHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private LinearLayout layoutPaymentMethod;
        private RoundedImageView imagePaymentMethod;
        private TextView txtPaymentMethod;
        LinearLayout layoutSelection;

        public AllCardHolder(View view) {
            super(view);

            layoutPaymentMethod = (LinearLayout) view.findViewById(R.id.layout_payment_method);
            imagePaymentMethod = (RoundedImageView) view.findViewById(R.id.image_payment_method);
            txtPaymentMethod = (TextView) view.findViewById(R.id.txt_payment_method);
            layoutSelection = (LinearLayout) view.findViewById(R.id.layout_selection);
        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final AllCardHolder allCardHolder = (AllCardHolder) holder;
            final com.mg.shopping.jsonutil.allcardutil.ListOfDatum paymentMethod = (com.mg.shopping.jsonutil.allcardutil.ListOfDatum) dataArray.get(position);

            allCardHolder.txtPaymentMethod.setText(Utility.maskSomeCharacter(paymentMethod.getCardNo()));
            GlideApp.with(context).load(BottomNavigationSample.getCategoryPicture(paymentMethod.getImage())).into(allCardHolder.imagePaymentMethod);

            allCardHolder.layoutPaymentMethod.setTag(position);
            allCardHolder.layoutPaymentMethod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) allCardHolder.layoutPaymentMethod.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setPosition(pos)
                                .setAction("select_card"));
                    }


                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return AVAILABLE_CARD_VIEW;
        }
    }

    protected class BillingHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageProduct;
        ImageView imageNext;

        private TextView txtProductName;
        private TextView txtPrice;
        private TextView txtQuantity;
        private TextView txtShippingCompany;
        private TextView txtShippingTagline;
        private TextView txtShippingInsurance;
        private TextView txtTrafficInsurance;
        private TextView txtShippingFee;
        private TextView txtTotalBill;
        private TextView txtDiscountBill;
        private TextView txtDiscountTagline;

        private TextView txtCurrencySymbol;
        private TextView txtTotalCurrencySymbol;

        private Switch switchShipping;
        private Switch switchTraffic;

        private LinearLayoutManager layoutManager;
        private RecyclerView recyclerViewAttribute;
        private StringAttributeAdapter dataAdapter;

        private LinearLayout layoutShipping;
        private LinearLayout layoutShippingFee;
        private LinearLayout layoutDiscount;

        private String pictureUrl = null;


        public BillingHolder(View view) {
            super(view);

            imageProduct = (RoundedImageView) view.findViewById(R.id.image_product);
            txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtQuantity = (TextView) view.findViewById(R.id.txt_quantity);

            txtShippingCompany = (TextView) view.findViewById(R.id.txt_shipping_company);
            txtShippingTagline = (TextView) view.findViewById(R.id.txt_shipping_tagline);

            txtShippingInsurance = (TextView) view.findViewById(R.id.txt_shipping_insurance);
            switchShipping = (Switch) view.findViewById(R.id.switch_shipping);
            txtTrafficInsurance = (TextView) view.findViewById(R.id.txt_traffic_insurance);
            switchTraffic = (Switch) view.findViewById(R.id.switch_traffic);
            txtShippingFee = (TextView) view.findViewById(R.id.txt_shipping_fee);
            txtTotalBill = (TextView) view.findViewById(R.id.txt_total_bill);
            txtDiscountBill = view.findViewById(R.id.txt_discount_bill);
            txtDiscountTagline = view.findViewById(R.id.txt_discount_tagline);

            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
            txtTotalCurrencySymbol = view.findViewById(R.id.txt_total_currency_symbol);

            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewAttribute = (RecyclerView) view.findViewById(R.id.recycler_view_attribute);
            recyclerViewAttribute.setLayoutManager(layoutManager);

            layoutShipping = view.findViewById(R.id.layout_shipping);
            layoutShippingFee = view.findViewById(R.id.layout_shipping_fee);
            layoutDiscount = view.findViewById(R.id.layout_discount);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            //<editor-fold desc="Functionality of holder">

            final BillingHolder billingHolder = (BillingHolder) holder;
            final com.mg.shopping.jsonutil.billingproductutil.ListOfDatum datum
                    = (com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(position);

            pictureUrl = getProductPicture(datum);
            if (datum.getShippingObject() != null) {

                billingHolder.txtShippingCompany.setText(datum.getShippingObject().getCarrier() + " " + datum.getShippingObject().getService());

                String shippingCharge = datum.getShippingObject().getCurrency() + " " + datum.getShippingObject().getRate();
                String deliveryDays;

                if (datum.getShippingObject().getDeliveryDays() == null) {
                    deliveryDays = Utility.getStringFromRes(context, R.string.same_day);
                } else {
                    deliveryDays = datum.getShippingObject().getDeliveryDays()
                            + " " + Utility.getStringFromRes(context, R.string.business_days);
                }

                billingHolder.txtShippingTagline.setText(shippingCharge + " - " + deliveryDays);
                billingHolder.txtShippingTagline.setVisibility(View.VISIBLE);

            } else {
                billingHolder.txtShippingTagline.setVisibility(View.GONE);
            }

            billingHolder.dataAdapter = new StringAttributeAdapter(context, getAttributeData(datum));
            billingHolder.recyclerViewAttribute.setAdapter(billingHolder.dataAdapter);
            billingHolder.recyclerViewAttribute.addItemDecoration(new LayoutMarginDecoration(2));

            billingHolder.txtProductName.setText(datum.getName());
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(billingHolder.imageProduct);

            billingHolder.txtPrice.setText(datum.getPrice());
            billingHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
            billingHolder.txtTotalCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
            billingHolder.txtQuantity.setText("x " + datum.getQuantity());

            double totalAmount = Double.parseDouble(datum.getPrice()) * Double.parseDouble(datum.getQuantity());
            billingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));

            totalAmount = checkCalculateShippingCharges(billingHolder,datum,totalAmount);  // check and calculate shipping charges
            totalAmount = checkCalculateInsuranceCharges(billingHolder,datum,totalAmount);  // check and calculate insurance charges

            billingHolder.txtShippingInsurance.setText(datum.getShippingInsurance());
            billingHolder.txtTrafficInsurance.setText(datum.getTariffInsurance());

            double finalBillingAmount = 0.0;

            if (datum.getCouponResponseObject() != null) {
                double discountedAmount;
                double totalDiscountedAmount;

                switch (datum.getCouponResponseObject().getType()){
                    case COUPON_TYPE_PERCENTAGE:

                        discountedAmount = (totalAmount * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                        totalDiscountedAmount = totalAmount - discountedAmount;
                        finalBillingAmount = totalDiscountedAmount;

                        billingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                        billingHolder.txtTotalBill.setPaintFlags(billingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        billingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                        billingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                        billingHolder.txtDiscountTagline.setText("( " + datum.getCouponResponseObject().getOffer() + "% )" + Utility.getStringFromRes(context, R.string.discount_applied));


                        break;

                    case COUPON_TYPE_SHIPPING:

                        if (datum.isShippingSelected()) {

                            discountedAmount = (Double.parseDouble(datum.getShippingObject().getRate())
                                    * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                            totalDiscountedAmount = totalAmount - discountedAmount;
                            finalBillingAmount = totalDiscountedAmount;

                            billingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                            billingHolder.txtTotalBill.setPaintFlags(billingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            billingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                            billingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                            billingHolder.txtDiscountTagline.setText("( " + datum.getCouponResponseObject().getOffer() + "% ) " + Utility.getStringFromRes(context, R.string.discount_applied)
                                    + " on " + Utility.getStringFromRes(context, R.string.shipping_cost));


                        }

                        break;

                    case COUPON_TYPE_FIXED:

                        discountedAmount = Double.parseDouble(datum.getCouponResponseObject().getOffer());
                        totalDiscountedAmount = totalAmount - discountedAmount;
                        finalBillingAmount = totalDiscountedAmount;

                        billingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                        billingHolder.txtTotalBill.setPaintFlags(billingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        billingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                        billingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                        billingHolder.txtDiscountTagline.setText("( " + datum.getCouponResponseObject().getOffer() + "% )" + Utility.getStringFromRes(context, R.string.discount_applied));


                        break;

                    default:

                        discountedAmount = (totalAmount * Double.parseDouble(datum.getCouponResponseObject().getOffer())) / 100;
                        totalDiscountedAmount = totalAmount - discountedAmount;
                        finalBillingAmount = totalDiscountedAmount;

                        billingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                        billingHolder.txtTotalBill.setPaintFlags(billingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        billingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                        billingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                        billingHolder.txtDiscountTagline.setText("( " + datum.getCouponResponseObject().getOffer() + "% ) " + Utility.getStringFromRes(context, R.string.discount_applied));


                        break;
                }

            }
            if (datum.getCouponResponseObject() != null && datum.isDiscountCalculationNeeded()) {
                ((Billing) context).calculateTotalBilling();
                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(position)).setDiscountCalculationNeeded(false);

            }


            ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(position)).setDiscountedAmount(String.valueOf(finalBillingAmount));

            billingHolder.switchShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) billingHolder.layoutShipping.getTag();
                    enableShippignOptionInItem(dataArray,pos);
                    notifyItemChanged(pos);
                    onAction(new SelectionObject()
                            .setAction("shipping_insurance_added")
                            .setPosition(pos));

                }
            });

            billingHolder.switchTraffic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) billingHolder.layoutShipping.getTag();
                    enableInsuranceOptionInItem(dataArray,pos);
                    notifyItemChanged(pos);
                    onAction(new SelectionObject()
                            .setAction("traffic_insurance_added")
                            .setPosition(pos));


                }
            });


            billingHolder.layoutShipping.setTag(position);
            billingHolder.layoutShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) billingHolder.layoutShipping.getTag();
                    onAction(new SelectionObject()
                            .setAction(actionShipping)
                            .setPosition(pos));
                }
            });

            //</editor-fold>

        }

        private String getProductPicture(com.mg.shopping.jsonutil.billingproductutil.ListOfDatum datum) {

            if (Utility.isEmptyString(pictureUrl))
               return  !datum.getImage().isEmpty() ? datum.getImage().get(0).getImage() : "null";

            return "null";
        }

        private List<Object> getAttributeData(com.mg.shopping.jsonutil.billingproductutil.ListOfDatum datum) {

            ArrayList<Object> attrList = new ArrayList<>();
            for (int i = 0; i < datum.getAttribute().size(); i++) {

                StringBuilder attrBuilder = new StringBuilder();
                com.mg.shopping.jsonutil.billingproductutil.Attribute attribute = datum.getAttribute().get(i);
                attrBuilder.append(attribute.getName());
                attrBuilder.append(" : ");

                for (int j = 0; j < attribute.getItem().size(); j++) {

                    com.mg.shopping.jsonutil.billingproductutil.Item item = attribute.getItem().get(j);
                    attrBuilder.append(item.getName());
                    pictureUrl = item.getImage();

                }

                attrList.add(attrBuilder.toString());

            }

            return attrList;
        }

        private void enableInsuranceOptionInItem(ArrayList<Object> dataArray, int pos) {

            if (((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).isTrafficInsuranceAdded()) {

                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setTrafficInsuranceAdded(false);
                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setTrafficInsuranceFee(0.0);
            }
            else {

                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setTrafficInsuranceAdded(true);
                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setTrafficInsuranceFee(

                        Double.parseDouble(((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).getTariffInsurance())

                );
            }

        }

        private void enableShippignOptionInItem(ArrayList<Object> dataArray, int pos) {

            if (((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).isShippingInsuranceAdded()) {

                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setShippingInsuranceAdded(false);
                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setShippingInsuranceFee(0.0);

            }
            else {

                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setShippingInsuranceAdded(true);
                ((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).setShippingInsuranceFee(

                        Double.parseDouble(((com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(pos)).getShippingInsurance())
                );

            }

        }

        private double checkCalculateInsuranceCharges(BillingHolder billingHolder, com.mg.shopping.jsonutil.billingproductutil.ListOfDatum datum, double totalAmount) {

            if (datum.isShippingInsuranceAdded()) {
                totalAmount += datum.getShippingInsuranceFee();
                billingHolder.switchShipping.setChecked(true);
                billingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            } else {
                billingHolder.switchShipping.setChecked(false);
                totalAmount -= datum.getShippingInsuranceFee();
                billingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            }
            if (datum.isTrafficInsuranceAdded()) {
                billingHolder.switchTraffic.setChecked(true);
                totalAmount += datum.getTrafficInsuranceFee();
                billingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            } else {
                billingHolder.switchTraffic.setChecked(false);
                totalAmount -= datum.getTrafficInsuranceFee();
                billingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            }

            return totalAmount;
        }

        private double checkCalculateShippingCharges(BillingHolder billingHolder, com.mg.shopping.jsonutil.billingproductutil.ListOfDatum datum , double totalAmount) {

            if (datum.isShippingSelected()) {
                billingHolder.layoutShippingFee.setVisibility(View.VISIBLE);
                billingHolder.txtShippingFee.setText(datum.getShippingObject().getCurrency() + " " + datum.getShippingObject().getRate());
                totalAmount += Double.parseDouble(datum.getShippingObject().getRate());
            } else {
                billingHolder.layoutShippingFee.setVisibility(View.GONE);
            }

            return totalAmount;

        }

        @Override
        public int getViewTypeIdentifier() {
            return BILLING_PRODUCT_ITEM_VIEW;
        }

        private void onAction(SelectionObject selectionObject){
            if (selectionInterface != null) {
                selectionInterface.onSelection(selectionObject);
            }

        }
    }

    protected class MultiBillingProductHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private RoundedImageView imageProduct;

        private TextView txtProductName;
        private TextView txtPrice;
        private TextView txtQuantity;
        private LinearLayoutManager layoutManager;
        private RecyclerView recyclerViewAttribute;
        private StringAttributeAdapter dataAdapter;

        private RoundKornerLinearLayout layoutProduct;
        private AppCompatRatingBar ratingBar;

        public MultiBillingProductHolder(View view) {
            super(view);

            imageProduct = (RoundedImageView) view.findViewById(R.id.image_product);
            txtProductName = (TextView) view.findViewById(R.id.txt_product_name);

            txtPrice = (TextView) view.findViewById(R.id.txt_price);
            txtQuantity = (TextView) view.findViewById(R.id.txt_quantity);

            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewAttribute = (RecyclerView) view.findViewById(R.id.recycler_view_attribute);
            recyclerViewAttribute.setLayoutManager(layoutManager);

            layoutProduct = view.findViewById(R.id.layout_product);
            ratingBar = view.findViewById(R.id.rating_bar);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            final MultiBillingProductHolder billingHolder = (MultiBillingProductHolder) holder;
            final com.mg.shopping.jsonutil.billingproductutil.ListOfDatum datum = (com.mg.shopping.jsonutil.billingproductutil.ListOfDatum) dataArray.get(position);

            String pictureUrl = null;

            ArrayList<Object> attrList = new ArrayList<>();
            for (int i = 0; i < datum.getAttribute().size(); i++) {

                StringBuilder attrBuilder = new StringBuilder();
                com.mg.shopping.jsonutil.billingproductutil.Attribute attribute = datum.getAttribute().get(i);
                attrBuilder.append(attribute.getName());

                if (!attribute.getItem().isEmpty())
                    attrBuilder.append(" : ");

                for (int j = 0; j < attribute.getItem().size(); j++) {

                    com.mg.shopping.jsonutil.billingproductutil.Item item = attribute.getItem().get(j);
                    attrBuilder.append(item.getName());
                    pictureUrl = item.getImage();

                }

                attrList.add(attrBuilder.toString());

            }

            if (Utility.isEmptyString(pictureUrl)) {
                pictureUrl = datum.getImage().get(0).getImage();
            }

            billingHolder.dataAdapter = new StringAttributeAdapter(context, attrList);
            billingHolder.recyclerViewAttribute.setAdapter(billingHolder.dataAdapter);
            billingHolder.recyclerViewAttribute.addItemDecoration(new LayoutMarginDecoration(2));

            billingHolder.txtProductName.setText(datum.getName());
            GlideApp.with(context).load(BottomNavigationSample.getProductPicture(pictureUrl)).into(billingHolder.imageProduct);

            billingHolder.txtPrice.setText(datum.getPrice());
            billingHolder.txtQuantity.setText("x " + datum.getQuantity());
            billingHolder.ratingBar.setVisibility(View.GONE);

            billingHolder.layoutProduct.setTag(position);
            billingHolder.layoutProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) billingHolder.layoutProduct.getTag();
                    if (selectionInterface != null) {
                        selectionInterface.onSelection(new SelectionObject()
                                .setAction("product_selector")
                                .setPosition(pos));
                    }

                }
            });

        }

        @Override
        public int getViewTypeIdentifier() {
            return MULTI_BILLING_PRODUCT_VIEW;
        }
    }

    protected class MultiProductBillingHolder extends RecyclerView.ViewHolder implements StrategyAdapter {
        private TextView txtTotalProducts;
        private ImageView imageNext;

        private TextView txtShippingCompany;
        private TextView txtShippingTagline;
        private TextView txtShippingInsurance;
        private TextView txtTrafficInsurance;
        private TextView txtShippingFee;

        private TextView txtTotalBill;
        private TextView txtCurrencySymbol;
        private TextView txtShippingCurrencySymbol;
        private TextView txtHalfCurrencySymbol;

        private TextView txtDiscountBill;
        private TextView txtDiscountCurrencySymbol;

        private TextView txtDiscountTagline;

        private Switch switchShipping;
        private Switch switchTraffic;

        private LinearLayout layoutShipping;
        private LinearLayout layoutShippingFee;
        private LinearLayout layoutDiscount;
        private LinearLayout layoutAddScroller;

        public MultiProductBillingHolder(View view) {
            super(view);

            txtShippingCompany = (TextView) view.findViewById(R.id.txt_shipping_company);
            txtShippingTagline = (TextView) view.findViewById(R.id.txt_shipping_tagline);

            txtTotalProducts = view.findViewById(R.id.txt_total_products);
            imageNext = (ImageView) view.findViewById(R.id.image_next);

            txtShippingInsurance = (TextView) view.findViewById(R.id.txt_shipping_insurance);
            switchShipping = (Switch) view.findViewById(R.id.switch_shipping);
            txtTrafficInsurance = (TextView) view.findViewById(R.id.txt_traffic_insurance);
            switchTraffic = (Switch) view.findViewById(R.id.switch_traffic);
            txtShippingFee = (TextView) view.findViewById(R.id.txt_shipping_fee);

            txtCurrencySymbol = view.findViewById(R.id.txt_currency_symbol);
            txtShippingCurrencySymbol = view.findViewById(R.id.txt_shipping_currency_symbol);
            txtHalfCurrencySymbol = view.findViewById(R.id.txt_half_currency_symbol);

            txtTotalBill = (TextView) view.findViewById(R.id.txt_total_bill);

            txtDiscountCurrencySymbol = view.findViewById(R.id.txt_discount_currency_symbol);
            txtDiscountBill = view.findViewById(R.id.txt_discount_bill);
            txtDiscountTagline = view.findViewById(R.id.txt_discount_tagline);

            layoutShipping = view.findViewById(R.id.layout_shipping);
            layoutShippingFee = view.findViewById(R.id.layout_shipping_fee);
            layoutDiscount = view.findViewById(R.id.layout_discount);
            layoutAddScroller = view.findViewById(R.id.layout_add_scroller);

        }

        @Override
        public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position) {

            //<editor-fold desc="Functionality of holder">

            final MultiProductBillingHolder multiProductBillingHolder = (MultiProductBillingHolder) holder;
            final MultiBillingProductObject billingObject = (MultiBillingProductObject) dataArray.get(position);

            multiProductBillingHolder.txtTotalProducts.setText(billingObject.getNoOfProduct());
            multiProductBillingHolder.layoutAddScroller.removeAllViews();

            for (int i = 0; i < billingObject.getPictureArrayList().size(); i++) {

                View pictureView = LayoutInflater.from(context).inflate(R.layout.product_picture_item_layout, null);

                ImageView imageProduct = pictureView.findViewById(R.id.image_product);

                String picture = BottomNavigationSample.getProductPicture(billingObject.getPictureArrayList().get(i));
                GlideApp.with(context).load(picture).into(imageProduct);

                multiProductBillingHolder.layoutAddScroller.addView(pictureView);

            }
            double totalAmount = Double.parseDouble(billingObject.getPrice());

            totalAmount= checkCalculateShipping(multiProductBillingHolder,billingObject,totalAmount);  //check and calculate shipping fee
            totalAmount= checkCalculateInsurance(multiProductBillingHolder,billingObject,totalAmount); // check and calculate insurance fee

            multiProductBillingHolder.txtShippingInsurance.setText(String.valueOf(
                    Utility.round(
                            Double.parseDouble(billingObject.getShippingInsurance())
                            , 2))
            );
            multiProductBillingHolder.txtTrafficInsurance.setText(billingObject.getTariffInsurance());
            multiProductBillingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));

            multiProductBillingHolder.txtCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());
            multiProductBillingHolder.txtShippingCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            if (multiProductBillingHolder.txtDiscountCurrencySymbol != null)
                multiProductBillingHolder.txtDiscountCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            if (multiProductBillingHolder.txtHalfCurrencySymbol != null)
                multiProductBillingHolder.txtHalfCurrencySymbol.setText(BottomNavigationSample.getCurrencyInformation());

            double finalBillingAmount = 0.0;

            if (billingObject.getCouponResponseObject() != null) {
                double discountedAmount;
                double totalDiscountedAmount = 0.0;

                switch (billingObject.getCouponResponseObject().getType()){
                    case COUPON_TYPE_SHIPPING:

                        if (billingObject.isShippingSelected()) {

                            discountedAmount = (Double.parseDouble(billingObject.getShippingObject().getRate())
                                    * Double.parseDouble(billingObject.getCouponResponseObject().getOffer())) / 100;
                            totalDiscountedAmount = totalAmount - discountedAmount;


                            multiProductBillingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                            multiProductBillingHolder.txtTotalBill.setPaintFlags(multiProductBillingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            multiProductBillingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                            multiProductBillingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                            multiProductBillingHolder.txtDiscountTagline.setText("( " + billingObject.getCouponResponseObject().getOffer() + "% ) " + Utility.getStringFromRes(context, R.string.discount_applied)
                                    + " on " + Utility.getStringFromRes(context, R.string.shipping_cost));


                        }

                        break;

                    case COUPON_TYPE_PERCENTAGE:

                        discountedAmount = (totalAmount * Double.parseDouble(billingObject.getCouponResponseObject().getOffer())) / 100;
                        totalDiscountedAmount = totalAmount - discountedAmount;

                        multiProductBillingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                        multiProductBillingHolder.txtTotalBill.setPaintFlags(multiProductBillingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        multiProductBillingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                        multiProductBillingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                        multiProductBillingHolder.txtDiscountTagline.setText("( " + billingObject.getCouponResponseObject().getOffer() + "% )" + Utility.getStringFromRes(context, R.string.discount_applied));

                        break;

                        case COUPON_TYPE_FIXED:

                            discountedAmount = Double.parseDouble(billingObject.getCouponResponseObject().getOffer());
                            totalDiscountedAmount = totalAmount - discountedAmount;

                            multiProductBillingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                            multiProductBillingHolder.txtTotalBill.setPaintFlags(multiProductBillingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            multiProductBillingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                            multiProductBillingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                            multiProductBillingHolder.txtDiscountTagline.setText("( " + billingObject.getCouponResponseObject().getOffer() + "% )" + Utility.getStringFromRes(context, R.string.discount_applied));

                            break;

                    default:

                        discountedAmount = (totalAmount * Double.parseDouble(billingObject.getCouponResponseObject().getOffer())) / 100;
                        totalDiscountedAmount = totalAmount - discountedAmount;

                        multiProductBillingHolder.txtDiscountBill.setText(String.valueOf(Utility.round(totalDiscountedAmount, 2)));
                        multiProductBillingHolder.txtTotalBill.setPaintFlags(multiProductBillingHolder.txtTotalBill.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        multiProductBillingHolder.layoutDiscount.setVisibility(View.VISIBLE);
                        multiProductBillingHolder.txtDiscountBill.setVisibility(View.VISIBLE);

                        multiProductBillingHolder.txtDiscountTagline.setText("( " + billingObject.getCouponResponseObject().getOffer() + "% ) " + Utility.getStringFromRes(context, R.string.discount_applied));


                        break;

                }

                finalBillingAmount = totalDiscountedAmount;

            }
            if (billingObject.getCouponResponseObject() != null && billingObject.isDiscountCalculationNeeded()) {
                ((Billing) context).calculateTotalBilling();
                ((MultiBillingProductObject) dataArray.get(position)).setDiscountCalculationNeeded(false);
            }

            ((MultiBillingProductObject) dataArray.get(position)).setDiscountedAmount(String.valueOf(finalBillingAmount));

            multiProductBillingHolder.switchShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) multiProductBillingHolder.layoutShipping.getTag();
                    enableShippingOptionInItem(dataArray,pos);  // enable shipping option in item

                    notifyItemChanged(pos);
                    onAction(new SelectionObject()
                            .setAction("shipping_insurance_added")
                            .setPosition(pos));

                }
            });

            multiProductBillingHolder.switchTraffic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) multiProductBillingHolder.layoutShipping.getTag();
                    enableInsuranceOptionInItem(dataArray,pos);  // enable insurance option in item

                    notifyItemChanged(pos);
                    onAction(new SelectionObject()
                            .setAction("traffic_insurance_added")
                            .setPosition(pos));


                }
            });


            multiProductBillingHolder.layoutShipping.setTag(position);
            multiProductBillingHolder.layoutShipping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) multiProductBillingHolder.layoutShipping.getTag();
                    onAction(new SelectionObject()
                            .setAction(actionShipping)
                            .setPosition(pos));


                }
            });

            multiProductBillingHolder.imageNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) multiProductBillingHolder.layoutShipping.getTag();
                    onAction(new SelectionObject()
                            .setAction("next")
                            .setPosition(pos));

                }
            });
            //</editor-fold>

        }


        @Override
        public int getViewTypeIdentifier() {
            return MULTI_PRODUCT_BILLING_PRODUCT_VIEW;
        }

        /**
         * <p>It is used to enable insurance option in item</p>
         *
         * @param dataArray
         * @param pos
         */
        private void enableInsuranceOptionInItem(ArrayList<Object> dataArray, int pos) {

            if (((MultiBillingProductObject) dataArray.get(pos)).isTrafficInsuranceAdded()) {

                ((MultiBillingProductObject) dataArray.get(pos)).setTrafficInsuranceAdded(false);
                ((MultiBillingProductObject) dataArray.get(pos)).setTrafficInsuranceFee(0.0);
            }
            else {

                ((MultiBillingProductObject) dataArray.get(pos)).setTrafficInsuranceAdded(true);
                ((MultiBillingProductObject) dataArray.get(pos)).setTrafficInsuranceFee(

                        Double.parseDouble(((MultiBillingProductObject) dataArray.get(pos)).getTariffInsurance())

                );
            }

        }

        /**
         * <p>It is used to enable shipping option in item</p>
         *
         * @param dataArray
         * @param pos
         */
        private void enableShippingOptionInItem(ArrayList<Object> dataArray, int pos) {

            if (((MultiBillingProductObject) dataArray.get(pos)).isShippingInsuranceAdded()) {

                ((MultiBillingProductObject) dataArray.get(pos)).setShippingInsuranceAdded(false);
                ((MultiBillingProductObject) dataArray.get(pos)).setShippingInsuranceFee(0.0);

            }
            else {

                ((MultiBillingProductObject) dataArray.get(pos)).setShippingInsuranceAdded(true);
                ((MultiBillingProductObject) dataArray.get(pos)).setShippingInsuranceFee(

                        Double.parseDouble(((MultiBillingProductObject) dataArray.get(pos)).getShippingInsurance())
                );

            }

        }

        /**
         * <p>It is used to calculate insurance</p>
         *
         * @param multiProductBillingHolder
         * @param billingObject
         * @return
         */
        private double checkCalculateInsurance(MultiProductBillingHolder multiProductBillingHolder, MultiBillingProductObject billingObject,double totalAmount) {

            if (billingObject.isShippingInsuranceAdded()) {
                totalAmount += billingObject.getShippingInsuranceFee();
                multiProductBillingHolder.switchShipping.setChecked(true);
                multiProductBillingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            } else {
                multiProductBillingHolder.switchShipping.setChecked(false);
                totalAmount -= billingObject.getShippingInsuranceFee();
                multiProductBillingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            }
            if (billingObject.isTrafficInsuranceAdded()) {
                multiProductBillingHolder.switchTraffic.setChecked(true);
                totalAmount += billingObject.getTrafficInsuranceFee();
                multiProductBillingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            } else {
                multiProductBillingHolder.switchTraffic.setChecked(false);
                totalAmount -= billingObject.getTrafficInsuranceFee();
                multiProductBillingHolder.txtTotalBill.setText(String.valueOf(Utility.round(totalAmount, 2)));
            }

            return totalAmount;
        }

        /**
         * <p>It is used to calculate shipping charges</p>
         *
         * @param multiProductBillingHolder
         * @param billingObject
         * @return
         */
        private double checkCalculateShipping(MultiProductBillingHolder multiProductBillingHolder, MultiBillingProductObject billingObject,double totalAmount) {

            if (billingObject.isShippingSelected()) {

                multiProductBillingHolder.layoutShippingFee.setVisibility(View.VISIBLE);
                multiProductBillingHolder.txtShippingFee.setText(billingObject.getShippingObject().getCurrency() + " " + billingObject.getShippingObject().getRate());
                totalAmount += Double.parseDouble(billingObject.getShippingObject().getRate());
                multiProductBillingHolder.txtShippingCompany.setText(billingObject.getShippingObject().getCarrier() + " " + billingObject.getShippingObject().getService());

                String shippingCharge = billingObject.getShippingObject().getCurrency() + " " + billingObject.getShippingObject().getRate();
                String deliveryDays;

                if (billingObject.getShippingObject().getDeliveryDays() == null) {
                    deliveryDays = Utility.getStringFromRes(context, R.string.same_day);
                } else {
                    deliveryDays = billingObject.getShippingObject().getDeliveryDays()
                            + " " + Utility.getStringFromRes(context, R.string.business_days);
                }

                multiProductBillingHolder.txtShippingTagline.setText(shippingCharge + " - " + deliveryDays);
                multiProductBillingHolder.txtShippingTagline.setVisibility(View.VISIBLE);


            } else {
                multiProductBillingHolder.layoutShippingFee.setVisibility(View.GONE);
                multiProductBillingHolder.txtShippingTagline.setVisibility(View.GONE);
            }
            return totalAmount;

        }

        private void onAction(SelectionObject selectionObject){

            if (selectionInterface != null) {
                selectionInterface.onSelection(selectionObject);
            }

        }

    }

}