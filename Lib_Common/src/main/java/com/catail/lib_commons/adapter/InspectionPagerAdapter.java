package com.catail.lib_commons.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.catail.lib_commons.base.BaseVisibleInitFragment;
import com.catail.lib_commons.utils.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by D on 2018/4/23.
 */

public class InspectionPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<BaseVisibleInitFragment> contentList;
    private final List<String> titleList;
    public InspectionPagerAdapter(FragmentManager fm, ArrayList<BaseVisibleInitFragment> contentList, List<String> titleList1) {
        super(fm);
        this.contentList = contentList;
        this.titleList = titleList1;
        Logger.e("titleList==",titleList.toString());
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


}
