package com.catail.bimax_defect2.f_defect2.adapter;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2StatisticsLineChartListsResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2StatisticsContractorAdapter extends BaseQuickAdapter<QueryDefect2StatisticsLineChartListsResult.ResultBean, BaseViewHolder> {
    public Defect2StatisticsContractorAdapter(int layoutResId, @Nullable List<QueryDefect2StatisticsLineChartListsResult.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryDefect2StatisticsLineChartListsResult.ResultBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_count, item.getCnt()+"")
                .setText(R.id.tv_percent, item.getCnt() + " %")
                .addOnClickListener(R.id.ll_content);

        if (helper.getAdapterPosition() < 5) {
            if (helper.getAdapterPosition() % 5 == 0) {
                helper.setBackgroundColor(R.id.iv_color, mContext.getResources().getColor(R.color.color_1));
            } else if (helper.getAdapterPosition() % 5 == 1) {
                helper.setBackgroundColor(R.id.iv_color, mContext.getResources().getColor(R.color.color_2));
            } else if (helper.getAdapterPosition() % 5 == 2) {
                helper.setBackgroundColor(R.id.iv_color, mContext.getResources().getColor(R.color.color_3));
            } else if (helper.getAdapterPosition() % 5 == 3) {
                helper.setBackgroundColor(R.id.iv_color, mContext.getResources().getColor(R.color.color_4));
            } else if (helper.getAdapterPosition() % 5 == 4) {
                helper.setBackgroundColor(R.id.iv_color, mContext.getResources().getColor(R.color.color_5));
            }
        } else {
            helper.setBackgroundColor(R.id.iv_color, mContext.getResources().getColor(R.color.color_6));
        }


    }
}
