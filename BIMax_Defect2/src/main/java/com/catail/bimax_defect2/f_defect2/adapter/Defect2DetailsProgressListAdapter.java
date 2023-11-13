package com.catail.bimax_defect2.f_defect2.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.common.adapter.InsCommentPhotoAdapter;
import com.catail.bimax_defect2.f_defect2.bean.Defect2QueryDetailsResultBean;
import com.catail.lib_commons.utils.DateFormatUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Defect2DetailsProgressListAdapter extends BaseQuickAdapter<Defect2QueryDetailsResultBean.ResultBean.DetailBean, BaseViewHolder> {
    private String mStatus = "";

    public Defect2DetailsProgressListAdapter(int layoutResId, @Nullable List<Defect2QueryDetailsResultBean.ResultBean.DetailBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint({"WrongConstant", "ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void convert(final BaseViewHolder baseViewHolder,
                           final Defect2QueryDetailsResultBean.ResultBean.DetailBean item) {

        String recordTime = DateFormatUtils.CNStr2ENStr(item.getRecord_time());
        baseViewHolder.setText(R.id.tv_name, item.getUser_name())
                .setText(R.id.tv_deal_type, item.getDeal_name())
                .setText(R.id.tv_time, recordTime)
                .setText(R.id.tv_content, item.getRemarks());
//                .setText(R.id.tv_deadline, item.getd())
//                .addOnClickListener(R.id.main);

//        String recordTime = DateFormatUtils.CNStr2ENStr(item.getRecord_time());
//        baseViewHolder.setText(R.id.tv_name, item.getUser_name())
//                .setText(R.id.tv_time, recordTime)
//                .setText(R.id.tv_content, item.getRemarks())
////                .addOnClickListener(R.id.iv_pic)
//                .addOnClickListener(R.id.main);
//
        TextView tv_deal_type = baseViewHolder.getView(R.id.tv_deal_type);

        //2023.11.1日PPT 俞自由PPT优化
        //对于所有overdue的defects，最近一次的状态文字背景改为红色，文字为白色。
        if (!mStatus.equals("10")) {

            if (item.getDeal_name().equals("Created")
                    || item.getDeal_name().equals("Onhold")) {
                tv_deal_type.setBackground(mContext.getResources().getDrawable(R.drawable.button_blue_shape, null));
            } else if (item.getDeal_name().equals("Fixed")
                    || item.getDeal_name().equals("Reopened")
                    || item.getDeal_name().equals("Comment")) {
                tv_deal_type.setBackground(mContext.getResources().getDrawable(R.drawable.button_fcb134_shape, null));
            } else if (item.getDeal_name().equals("Solved")) {
                tv_deal_type.setBackground(mContext.getResources().getDrawable(R.drawable.button_66cc76_shape, null));
            } else if (item.getDeal_name().equals("Rejected")) {
                tv_deal_type.setBackground(mContext.getResources().getDrawable(R.drawable.button_ff6666_shape, null));
            } else {
                //其他的状态橙色.
                tv_deal_type.setBackground(mContext.getResources().getDrawable(R.drawable.button_fcb134_shape, null));
            }
        } else {
            if (baseViewHolder.getAdapterPosition() == mData.size() - 1) {
                tv_deal_type.setBackground(mContext.getResources().getDrawable(R.drawable.button_ff6666_shape, null));
            }
        }

        if (item.getPic().length() > 4) {
            //显示图片
            List<String> tempPhotoLists = new ArrayList<>();
            String picListsStr = item.getPic();
            String[] PicArr = picListsStr.split("\\|");
            for (String picPath : PicArr) {
                tempPhotoLists.add(picPath);
            }

            RecyclerView rv_photo_list = baseViewHolder.getView(R.id.rv_photo_list);
            final GridLayoutManager glm1 = new GridLayoutManager(mContext, 3);
            rv_photo_list.setLayoutManager(glm1);
            InsCommentPhotoAdapter mBottomPhotoAdapter
                    = new InsCommentPhotoAdapter(R.layout.add_photo_item, tempPhotoLists);
            rv_photo_list.setAdapter(mBottomPhotoAdapter);
        }

    }

    public void setStatus(String status) {
        this.mStatus = status;
    }
}
