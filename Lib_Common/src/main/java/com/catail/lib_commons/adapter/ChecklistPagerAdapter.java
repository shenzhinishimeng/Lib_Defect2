package com.catail.lib_commons.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.catail.lib_commons.base.BaseVisibleInitFragment;

import java.util.ArrayList;


/**
 * Created by D on 2018/4/23.
 */

public class ChecklistPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<BaseVisibleInitFragment> contentList;

    public ChecklistPagerAdapter(FragmentManager fm, ArrayList<BaseVisibleInitFragment> contentList) {
        super(fm);
        this.contentList = contentList;
    }

    @Override
    public Fragment getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }


}
