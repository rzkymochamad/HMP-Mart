package com.mg.shopping.adapterutil;

import androidx.recyclerview.widget.RecyclerView;

interface StrategyAdapter {

    public void executeHolderFunctionality(RecyclerView.ViewHolder holder, int position);
    public int getViewTypeIdentifier();

}
