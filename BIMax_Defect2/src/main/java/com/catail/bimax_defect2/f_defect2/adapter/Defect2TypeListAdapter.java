package com.catail.bimax_defect2.f_defect2.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2TypeListResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2TypeListAdapter extends BaseQuickAdapter<QueryDefect2TypeListResultBean.ResultBean, BaseViewHolder> {

    public Defect2TypeListAdapter(int layoutResId, @Nullable List<QueryDefect2TypeListResultBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
    @Override
    protected void convert(final BaseViewHolder helper, final QueryDefect2TypeListResultBean.ResultBean item) {
        helper.setText(R.id.tv_type_name, item.getTrade())
                .setText(R.id.tv_sub_name, item.getContractor_name())
                .setText(R.id.tv_total, item.getComplete_cnt() + "/" + item.getTotal_cnt())
                .addOnClickListener(R.id.rl_left_content);


    }
}
