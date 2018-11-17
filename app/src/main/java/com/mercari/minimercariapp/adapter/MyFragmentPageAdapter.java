package com.mercari.minimercariapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.mercari.minimercariapp.model.MasterData;
import com.mercari.minimercariapp.tabs.MasterProductListFragment;

import java.util.List;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {


    private int icons[] = {};
    Context context;
    //List<ProductGroup> productGroupList = null;
    //ProductGroup productGroup = null;
    List<MasterData> masterDataList;
    private int currentItem = 0;
    private String depId;

    public MyFragmentPageAdapter(Context context, FragmentManager fm, List<MasterData> masterDataList, int currentItem) {
        super(fm);
        this.context = context;
        //this.productGroupList = productGroupList;
        this.masterDataList = masterDataList;
        this.currentItem = currentItem;
        this.depId = depId;
    }

    @Override
    public int getCount() {
        return masterDataList.size();
    }

    /*public int getDrawableId(int position) {
        return icons[position];
    }*/

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        MasterData masterData = (MasterData) masterDataList.get(position);

        fragment = MasterProductListFragment.newInstance(position,masterData);
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        MasterData masterData = (MasterData) masterDataList.get(position);

        return masterData.getName();
    }
}