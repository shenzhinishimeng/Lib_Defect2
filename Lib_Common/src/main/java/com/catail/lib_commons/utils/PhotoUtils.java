package com.catail.lib_commons.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.catail.lib_commons.R;
import com.catail.lib_commons.activity.UseCameraActivity;
import com.catail.lib_commons.adapter.AddPhotoAdapter;
import com.catail.lib_commons.adapter.PhotoSelectionAdapter;
import com.catail.lib_commons.bean.AddPhotoBean;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hzw.doodle.DoodleActivity;
import cn.hzw.doodle.DoodleParams;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PhotoUtils {
    /**
     * 拍照
     */
    public static void takePhoto(Activity activity) {
        try {

            Intent intent = new Intent(activity,
                    UseCameraActivity.class);
            activity.startActivityForResult(intent, ConstantValue.takePhotoCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void DealTakePhoto(Bitmap bitmap, String path) {
        try {
            int SystemCurrentVersion = Utils.GetSystemCurrentVersion();
            Date date = new Date(System.currentTimeMillis());
            // 获取当前时间转中/英文
            String time;
            if (SystemCurrentVersion == 0) {
                time = DateFormatUtils.Date2CNStr(date);
            } else {
                time = DateFormatUtils.CNDateStrTOENDate(date);
            }

            Bitmap img = Utils.createBitmap(bitmap, null, time);

            Utils.saveBitmap(path, img);

            if (bitmap != null) {
                bitmap.recycle();
            }
            if (img != null) {
                img.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从相册选择图片
     **/
    public static void SelectSingleFromAlbum(Activity activity) {
        //单选
        ImageSelector.builder()
                .useCamera(false) // 设置是否使用拍照
                .setSingle(true)  //设置是否单选
                .setViewImage(true) //是否点击放大图片查看,，默认为true
                .start(activity, ConstantValue.CopyWeChatImageSelector); // 打开相册
    }

    /**
     * 从相册选择图片
     **/
    public static void SelectMultipleFromAlbum(Activity activity, int maxselectCount) {
        //多选
        ImageSelector.builder()
                .useCamera(false) // 设置是否使用拍照
                .setSingle(false)  //设置是否单选
                .setViewImage(true) //是否点击放大图片查看,，默认为true
                .setMaxSelectCount(maxselectCount)
                .start(activity, ConstantValue.CopyWeChatImageSelector); // 打开相册
    }

    /**
     * 鲁班压缩图片工具压缩.
     */
    public static void ImageLubanCompression(Activity activity, ArrayList<String> images,
                                             final List<AddPhotoBean> mPhotosList,
                                             final AddPhotoAdapter mAddPhotoAdapter) {
        try {
            Luban.with(activity)
                    .load(images)
                    .ignoreBy(10)
                    .setTargetDir(new File(Common.PHOTO_SRC).getAbsolutePath())
                    .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            //  压缩成功后调用，返回压缩后的图片文件
                            String filePath = file.getAbsolutePath();
                            Logger.e("onSuccess", filePath);
                            Logger.e("图片路径=", filePath);
                            ImageShow(filePath, mPhotosList, mAddPhotoAdapter, -1);
                        }

                        @Override
                        public void onError(Throwable e) {
                            //  当压缩过程出现问题时调用
                            e.printStackTrace();
                        }
                    }).launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片列表显示
     * position 如果是-1 说明是添加,否则就是替换.
     */
    public static void ImageShow(String imagePath, final List<AddPhotoBean> mPhotosList,
                                 final AddPhotoAdapter mAddPhotoAdapter, int position) {
        AddPhotoBean albumBean = new AddPhotoBean();
        albumBean.setItemType(0);
        albumBean.setPhotoTitle("");
        albumBean.setPic(imagePath);
        if (position == -1) {
            if (mPhotosList.size() > 0) {
                mPhotosList.add(mPhotosList.size() - 1, albumBean);
            } else {
                mPhotosList.add(mPhotosList.size(), albumBean);
            }
        } else {
            mPhotosList.set(position, albumBean);
        }
        mAddPhotoAdapter.notifyDataSetChanged();
    }

    public static int[] strs = {R.string.use_camera, R.string.choose_image};
    public static Uri captureFileUri = null;

    /**
     * 列表 拍照 从本地相册选择照片去做检查
     */
    public static void showPhotoSelectionDialog(final AppCompatActivity activity, int maxselectCount) {
        final AlertDialog PhotoSelectionDialog = new AlertDialog.Builder(activity).create();
        View photoSelectionView = activity.getLayoutInflater().inflate(R.layout.photo_selection_listview, null);
        ListView PhotoSelectionListView = photoSelectionView.findViewById(R.id.lv_photo_selection);
        PhotoSelectionAdapter photoSelectionAdapter = new PhotoSelectionAdapter(strs);
        PhotoSelectionListView.setAdapter(photoSelectionAdapter);
        PhotoSelectionDialog.setView(photoSelectionView);
        PhotoSelectionDialog.show();

        Utils.setAlertDialogConner(PhotoSelectionDialog);
        Utils.setAlertDialogSize(activity, PhotoSelectionDialog, 0);

        PhotoSelectionListView.setOnItemClickListener((parent, view, position, id) -> {
            if (position != -1) {
                if (position == 0) {
                    // 选的第一个为拍照.
                    takePhoto(activity);
                    PhotoSelectionDialog.dismiss();
                } else if (position == 1) {
                    PhotoUtils.SelectMultipleFromAlbum(activity, maxselectCount);
                    PhotoSelectionDialog.dismiss();
                }
            }
        });
    }

    public static void ImageEditor(Activity activity, String imageEditorPic, int position) {
        DoodleParams params = new DoodleParams(); // 涂鸦参数
        params.mImagePath = imageEditorPic; // the file path of image
        params.mPosition = position;//索引
        params.mType = 1;//0是拍照相册图片 ,1是附件图片
        DoodleActivity.startActivityForResult(activity, params, ConstantValue.ImageEditorCode);
    }

}
