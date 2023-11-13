package com.catail.lib_commons.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.catail.lib_commons.R;
import com.catail.lib_commons.bean.AddPhotoBean;
import com.catail.lib_commons.utils.GlideUtils;
import com.catail.lib_commons.utils.NetApi;
import com.catail.lib_commons.utils.Utils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AddPhotoAdapter extends BaseMultiItemQuickAdapter<AddPhotoBean, BaseViewHolder> {
    private final List<AddPhotoBean> photoList;
    private final int MaxImageCount;

    public AddPhotoAdapter(List<AddPhotoBean> photoList, Activity activity, int MaxImageCount) {
        super(photoList);
        this.photoList = photoList;
        this.MaxImageCount = MaxImageCount;
//        getWidthAndHeight(activity);
        addItemType(AddPhotoBean.Item_Type0, R.layout.add_photo_item);//前面的照片
        addItemType(AddPhotoBean.Item_Type1, R.layout.add_photo_item1);//最后一张照片

    }

    @Override
    protected void convert(final BaseViewHolder helper, AddPhotoBean item) {
        switch (helper.getItemViewType()) {// 类型：
            case AddPhotoBean.Item_Type0:
                ImageView ivPhoto = helper.getView(R.id.iv_photo);
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
//                params.setMargins(6, 6, 6, 6);
//                ivPhoto.setLayoutParams(params);
                ivPhoto.setScaleType(ImageView.ScaleType.FIT_XY);

                if (item.getPic().startsWith("/opt") || item.getPic().startsWith("/filedev")) {
                    String thumbUrl = Utils.OriginalUrlToThumbUrl(item.getPic());
                    GlideUtils.load(mContext, ivPhoto, NetApi.IMG_SHOW_URL + thumbUrl);
                } else {
                    GlideUtils.load(mContext, ivPhoto, item.getPic());
                }

//                EditText etTitle = helper.getView(R.id.et_title);
//                etTitle.setText(item.getPhotoTitle());
//                etTitle.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        photoList.get(helper.getAdapterPosition()).setPhotoTitle(s.toString().trim());
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
                helper.addOnClickListener(R.id.iv_photo)
                        .addOnClickListener(R.id.iv_del_photo);


                break;
            case AddPhotoBean.Item_Type1:
                ImageView ivAddPhoto = helper.getView(R.id.iv_add_photo);
//                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width, width);
//                params1.setMargins(6, 6, 6, 6);
//                ivAddPhoto.setLayoutParams(params1);
                ivAddPhoto.setScaleType(ImageView.ScaleType.FIT_XY);

                if (photoList.size() > MaxImageCount) {
                    ivAddPhoto.setVisibility(View.GONE);
                } else {
                    ivAddPhoto.setVisibility(View.VISIBLE);
                }
                helper.addOnClickListener(R.id.iv_add_photo);
                break;

        }
    }

//    private int width;// 屏幕宽度

    public void getWidthAndHeight(Activity activity) {

//        WindowManager manager = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        manager.getDefaultDisplay().getMetrics(outMetrics);
//        width = (int) (outMetrics.widthPixels - 100) / 3;
    }
}
