package com.catail.bimax_defect2.common.adapter;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bm.library.PhotoView;
import com.catail.bimax_defect2.R;
import com.catail.lib_commons.utils.GlideUtils;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.NetApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class InsCommentPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public InsCommentPhotoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView iv_pic = helper.getView(R.id.iv_photo);
        ImageView iv_del_photo = helper.getView(R.id.iv_del_photo);
        iv_del_photo.setVisibility(View.INVISIBLE);


        Logger.e("item==" + item);
        if (!TextUtils.isEmpty(item)) {
//            String thumbUrl = Utils.OriginalUrlToThumbUrl(item);
//            GlideUtils.load(mContext, iv_pic, NetApi.IMG_SHOW_URL + thumbUrl);

            if (!item.startsWith("/opt")) {
                GlideUtils.load(mContext, iv_pic, item);
            } else {
                GlideUtils.load(mContext, iv_pic, NetApi.IMG_SHOW_URL + item);
            }


        }

        helper.setOnClickListener(R.id.iv_photo, v -> {
                    showImg(item);
                }
        );
    }


    /**
     * 放大显示图片
     */
    private void showImg(String ImgUrl) {
        // ToastUtils.toastStr(TBMDetailActivity.this,"图片的地址="+faceImg );
        Logger.e("图片地址", ImgUrl);
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        View view = LayoutInflater.from(mContext).inflate(R.layout.alert_img, null);
        final PhotoView userImg = view.findViewById(R.id.img);
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        window.setContentView(view);

        if (!ImgUrl.startsWith("/opt")) {
            GlideUtils.load(mContext, userImg, ImgUrl);
        } else {
            GlideUtils.load(mContext, userImg, NetApi.IMG_SHOW_URL + ImgUrl);
        }
        userImg.enable();
    }

}
