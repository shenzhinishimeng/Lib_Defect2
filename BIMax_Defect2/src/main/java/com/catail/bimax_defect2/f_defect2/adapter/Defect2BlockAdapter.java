package com.catail.bimax_defect2.f_defect2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.catail.bimax_defect2.R;
import com.catail.bimax_defect2.f_defect2.bean.QueryDefectBlockListsResultBean;

import java.util.List;

public class Defect2BlockAdapter extends BaseAdapter {
    ViewHoler viewHoler;
    private final List<QueryDefectBlockListsResultBean.ResultBean.BlockListBean> progromBlockBeanList;

    public Defect2BlockAdapter(List<QueryDefectBlockListsResultBean.ResultBean.BlockListBean> progromBlockBeanList) {
        super();
        this.progromBlockBeanList = progromBlockBeanList;
    }

    @Override
    public int getCount() {
        return progromBlockBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.text_item, parent, false);
            viewHoler = new ViewHoler();
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        viewHoler.textView = convertView.findViewById(R.id.unitText);
        viewHoler.textView.setText(progromBlockBeanList.get(position).getBlock());
        viewHoler.imageView = convertView
                .findViewById(R.id.triangle_img);
        viewHoler.imageView.setVisibility(View.GONE);
        return convertView;
    }

    class ViewHoler {
        TextView textView;
        ImageView imageView;
    }

}
