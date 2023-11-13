package com.catail.bimax_defect2.f_defect2.adapter;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefect2ListResultBean;
import com.catail.lib_commons.utils.DateFormatUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class Defect2ListAdapter extends BaseQuickAdapter<QueryDefect2ListResultBean.ResultBean, BaseViewHolder> {

    public Defect2ListAdapter(int layoutResId, @Nullable List<QueryDefect2ListResultBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
    @Override
    protected void convert(final BaseViewHolder helper, final QueryDefect2ListResultBean.ResultBean item) {
        String updateTime = DateFormatUtils.CNStr2ENStr(item.getUpdate_time());
        String type = item.getDiscipline() + "-" + item.getCategory() + "-" + item.getTrade();
        helper.setText(R.id.tv_title, type)
                .setText(R.id.tv_inspection_no, item.getDefect_no())
                .setText(R.id.tv_block, item.getBlock() + "-" + item.getSecondary_region() + "-" + item.getLocation())
                .setText(R.id.tv_time, updateTime)
                .setText(R.id.tv_name, item.getShort_name())
                .addOnClickListener(R.id.ll_right_content)
                .addOnClickListener(R.id.main);

        //设置图标和inspection_no 颜色.
        ImageView iv_inspection_no_status = helper.getView(R.id.iv_inspection_no_status);
        TextView tv_inspection_no = helper.getView(R.id.tv_inspection_no);

        if (item.getStatus().equals("0")) {
            iv_inspection_no_status.setImageResource(R.mipmap.ins_list_orange_icon);
            tv_inspection_no.setTextColor(mContext.getResources().getColor(R.color.orange_textcolor_FA9A18));
        } else if (item.getStatus().equals("6")) {
            iv_inspection_no_status.setImageResource(R.mipmap.ins_list_green_icon);
            tv_inspection_no.setTextColor(mContext.getResources().getColor(R.color.green_status_66CC76));
        } else if (item.getStatus().equals("10")) {
            iv_inspection_no_status.setImageResource(R.mipmap.ins_list_red_icon);
            tv_inspection_no.setTextColor(mContext.getResources().getColor(R.color.red_background_FF6666));
        } else if (item.getStatus().equals("9")) {
            iv_inspection_no_status.setImageResource(R.mipmap.ins_list_blue_icon);
            tv_inspection_no.setTextColor(mContext.getResources().getColor(R.color.blue_textcolor_34B9FC));
        }
        if (item.getStatus().equals("-1")) {
            iv_inspection_no_status.setImageResource(R.mipmap.ins_list_blue_icon);
            tv_inspection_no.setTextColor(mContext.getResources().getColor(R.color.blue_textcolor_34B9FC));
        }


    }
}
