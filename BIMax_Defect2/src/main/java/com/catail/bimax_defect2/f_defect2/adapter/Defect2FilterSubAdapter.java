package com.catail.bimax_defect2.f_defect2.adapter;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QuerySubConListsResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2FilterSubAdapter extends BaseQuickAdapter<QuerySubConListsResultBean.ResultBean, BaseViewHolder> {
    public Defect2FilterSubAdapter(int layoutResId, @Nullable List<QuerySubConListsResultBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuerySubConListsResultBean.ResultBean item) {
        helper.setText(R.id.tv_name, item.getContractor_name())
                .addOnClickListener(R.id.rl_content);
    }
}
