package com.catail.bimax_defect2.f_defect2.adapter;


import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.Defect2QueryDetailsResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2ActionAdapter extends BaseQuickAdapter<Defect2QueryDetailsResultBean.ResultBean.ActionBean, BaseViewHolder> {
    public Defect2ActionAdapter(int layoutResId, @Nullable List<Defect2QueryDetailsResultBean.ResultBean.ActionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Defect2QueryDetailsResultBean.ResultBean.ActionBean item) {
        helper.setText(R.id.tv_name, item.getName_en())
                .addOnClickListener(R.id.rl_content);

    }
}
