package com.catail.lib_commons.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.catail.lib_commons.R;

public class PhotoSelectionAdapter extends BaseAdapter {
    private final int[] strs;

    public PhotoSelectionAdapter(int[] str) {
        this.strs = str;
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int position) {
        return strs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.photo_selection_item, null);
            viewHolder.name = convertView.findViewById(R.id.tv_photo_selection);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String str = parent.getContext().getResources().getString(strs[position]);
        viewHolder.name.setText(str);
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
