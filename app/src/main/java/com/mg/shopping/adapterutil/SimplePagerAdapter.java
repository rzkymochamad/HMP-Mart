package com.mg.shopping.adapterutil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public SimplePagerAdapter(FragmentManager fm, List<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList= (ArrayList<Fragment>) fragmentArrayList;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }


}

