package com.catail.bimax_defect2.f_defect2.adapter;


import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDisciplineListsResultBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2DisciplineListsAdapter extends BaseQuickAdapter<QueryDisciplineListsResultBean.ResultBean, BaseViewHolder> {
    public Defect2DisciplineListsAdapter(int layoutResId, @Nullable List<QueryDisciplineListsResultBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryDisciplineListsResultBean.ResultBean checklistCreateByBean) {
        helper.setText(R.id.tv_name, checklistCreateByBean.getDiscipline())
                .addOnClickListener(R.id.rl_content);

    }
}
