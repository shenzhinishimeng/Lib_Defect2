package com.catail.bimax_defect2.f_defect2.adapter;


import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.PinOptionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2PinOptionAdapter extends BaseQuickAdapter<PinOptionBean, BaseViewHolder> {
    public Defect2PinOptionAdapter(int layoutResId, @Nullable List<PinOptionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinOptionBean checklistCreateByBean) {
        helper.setText(R.id.tv_name, checklistCreateByBean.getOption_name())
                .addOnClickListener(R.id.rl_content);

    }
}
