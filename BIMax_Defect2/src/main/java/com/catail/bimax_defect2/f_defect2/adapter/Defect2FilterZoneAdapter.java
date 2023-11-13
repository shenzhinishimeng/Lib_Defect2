package com.catail.bimax_defect2.f_defect2.adapter;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectZoneListsResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2FilterZoneAdapter extends BaseQuickAdapter<QueryDefectZoneListsResultBean.ResultBean.UnitListBean, BaseViewHolder> {
    public Defect2FilterZoneAdapter(int layoutResId, @Nullable List<QueryDefectZoneListsResultBean.ResultBean.UnitListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryDefectZoneListsResultBean.ResultBean.UnitListBean item) {
        helper.setText(R.id.tv_name, item.getUnit())
                .addOnClickListener(R.id.rl_content);
    }
}
