package com.catail.bimax_defect2.f_defect2.adapter;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectLevelListsResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2FilterLevelAdapter extends BaseQuickAdapter<QueryDefectLevelListsResultBean.ResultBean.LevelBean, BaseViewHolder> {
    public Defect2FilterLevelAdapter(int layoutResId, @Nullable List<QueryDefectLevelListsResultBean.ResultBean.LevelBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryDefectLevelListsResultBean.ResultBean.LevelBean item) {
        helper.setText(R.id.tv_name, item.getSecondary_region())
                .addOnClickListener(R.id.rl_content);
    }
}
