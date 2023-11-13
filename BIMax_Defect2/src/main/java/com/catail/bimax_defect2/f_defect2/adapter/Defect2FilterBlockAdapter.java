package com.catail.bimax_defect2.f_defect2.adapter;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectBlockListsResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2FilterBlockAdapter extends BaseQuickAdapter<QueryDefectBlockListsResultBean.ResultBean.BlockListBean, BaseViewHolder> {
    public Defect2FilterBlockAdapter(int layoutResId, @Nullable List<QueryDefectBlockListsResultBean.ResultBean.BlockListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryDefectBlockListsResultBean.ResultBean.BlockListBean item) {
        helper.setText(R.id.tv_name, item.getBlock())
                .addOnClickListener(R.id.rl_content);
    }
}
