package com.mg.shopping.objectutil;

import android.view.View;

import com.mg.shopping.constantutil.Constant;

public class SelectionObject {
    private int position;
    private int childPosition;
    private String action;
    private int quantity;
    private View view;
    private Constant.CONNECTION connection;


    public int getPosition() {
        return position;
    }

    public SelectionObject setPosition(int position) {
        this.position = position;
        return this;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public SelectionObject setChildPosition(int childPosition) {
        this.childPosition = childPosition;
        return this;
    }

    public String getAction() {
        return action;
    }

    public SelectionObject setAction(String action) {
        this.action = action;
        return this;
    }

    public View getView() {
        return view;
    }

    public SelectionObject setView(View view) {
        this.view = view;
        return this;
    }

    public Constant.CONNECTION getConnection() {
        return connection;
    }

    public SelectionObject setConnection(Constant.CONNECTION connection) {
        this.connection = connection;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public SelectionObject setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
