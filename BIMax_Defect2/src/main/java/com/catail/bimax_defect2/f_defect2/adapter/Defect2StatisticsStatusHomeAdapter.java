package com.catail.bimax_defect2.f_defect2.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2StatisticsStatusPieChartListsResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2StatisticsStatusHomeAdapter extends BaseQuickAdapter<QueryDefect2StatisticsStatusPieChartListsResult.ResultBean, BaseViewHolder> {

    public Defect2StatisticsStatusHomeAdapter(int layoutResId, @Nullable List<QueryDefect2StatisticsStatusPieChartListsResult.ResultBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
    @Override
    protected void convert(final BaseViewHolder helper, final QueryDefect2StatisticsStatusPieChartListsResult.ResultBean item) {
        helper.setText(R.id.tv_count, item.getCnt() + "")
                .setText(R.id.tv_name, item.getName())
                .addOnClickListener(R.id.rl_content);

        //设置背景颜色.
//        ImageView iv_status = helper.getView(R.id.iv_status);
//        //获取llview当前背景,返回一个Drawable
//        GradientDrawable myShape = (GradientDrawable) iv_status.getBackground();
////        myShape.setColor(ColorTemplate.INSPECTION_STATUS_COLORS[helper.getAdapterPosition()]);
//        myShape.setColor(Color.parseColor(item.getColor()));

        //设置字体颜色
//        helper.setTextColor(R.id.tv_count,ColorTemplate.INSPECTION_STATUS_COLORS[helper.getAdapterPosition()]);
        helper.setTextColor(R.id.tv_count, Color.parseColor(item.getColor()));

        TextView iv_status = helper.getView(R.id.iv_status);
        iv_status.setBackgroundColor(Color.parseColor(item.getColor()));
    }
}
