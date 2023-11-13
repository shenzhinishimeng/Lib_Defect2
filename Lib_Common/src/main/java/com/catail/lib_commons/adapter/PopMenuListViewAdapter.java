package com.catail.lib_commons.adapter;


import androidx.annotation.Nullable;

import com.catail.lib_commons.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PopMenuListViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PopMenuListViewAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String item) {
        baseViewHolder.setText(R.id.tv_name, item)
                .addOnClickListener(R.id.rl_content);

    }
}
