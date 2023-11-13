package com.catail.bimax_defect2.f_defect2.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2CategoryListRequestBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2CategoryListAdapter extends BaseQuickAdapter<QueryDefect2CategoryListRequestBean.ResultBean, BaseViewHolder> {

    public Defect2CategoryListAdapter(int layoutResId, @Nullable List<QueryDefect2CategoryListRequestBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
    @Override
    protected void convert(final BaseViewHolder helper, final QueryDefect2CategoryListRequestBean.ResultBean item) {

        helper.setText(R.id.tv_type_name, item.getCategory())
                .setText(R.id.tv_total, item.getComplete_cnt() + "/" + item.getTotal_cnt())
                .addOnClickListener(R.id.rl_left_content);


    }
}
