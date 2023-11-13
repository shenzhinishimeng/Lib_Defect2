package com.catail.lib_commons.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.catail.lib_commons.utils.Common;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.Logger;

import java.io.File;
import java.io.IOException;

/**
 * 照片生成的目录在 sd卡的/a/image/camera/.. .jpg
 *
 * @author baozi
 */
public class UseCameraActivity extends Activity {
    private String mImageFilePath;
    public static final String IMAGEFILEPATH = "ImageFilePath";
    public final static String IMAGE_PATH = "image_path";
    static Activity mContext;
    public final static int GET_IMAGE_REQ = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断 activity被销毁后 有没有数据被保存下来
        if (savedInstanceState != null) {

            mImageFilePath = savedInstanceState.getString(IMAGEFILEPATH);

            Log.e("123--savedInstanceState", mImageFilePath);

            File mFile = new File(IMAGEFILEPATH);
            if (mFile.exists()) {
                Intent rsl = new Intent();
                rsl.putExtra(IMAGE_PATH, mImageFilePath);
                setResult(Activity.RESULT_OK, rsl);
                Log.e("123--savedInstanceState", "图片拍摄成功");
                finish();
            } else {
                Log.e("123--savedInstanceState", "图片拍摄失败");
            }
        }

        mContext = this;
        if (savedInstanceState == null) {
            initialUI();
        }

    }

    public void initialUI() {
        //根据时间生成 后缀为  .jpg 的图片
        long ts = System.currentTimeMillis();
        mImageFilePath = getCameraPath() + ts + ".jpg";
        File out = new File(mImageFilePath);
        if (!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showCamera(out);

    }

    private void showCamera(File out) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = null;
        if (Build.VERSION.SDK_INT > 24) {
            uri = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider",
                    out);
        } else {
            uri = Uri.fromFile(out);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // set
        startActivityForResult(intent, GET_IMAGE_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Logger.e("------------------------------");
        Logger.e("requestCode====" + requestCode);
        Logger.e("GET_IMAGE_REQ====" + GET_IMAGE_REQ);
        Logger.e("resultCode====" + resultCode);
        Logger.e("Activity.RESULT_OK====" + Activity.RESULT_OK);
        Logger.e("------------------------------");

        if (GET_IMAGE_REQ == requestCode && resultCode == Activity.RESULT_OK) {

            Intent rsl = new Intent();
            rsl.putExtra(IMAGE_PATH, mImageFilePath);
            mContext.setResult(ConstantValue.takePhotoCode, rsl);
            mContext.finish();

        } else {
            mContext.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ImageFilePath", mImageFilePath + "");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    public static String getCameraPath() {
        String filePath = getImageRootPath();
        File file = new File(filePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    public static String getImageRootPath() {
        String filePath = getAppRootPath();
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    public static String getAppRootPath() {
        String filePath = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            filePath = Common.PHOTO_SRC;
        } else {
            filePath = Common.PHOTO_SRC;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        File nomedia = new File(filePath);
        if (!nomedia.exists())
            try {
                nomedia.createNewFile();
            } catch (IOException e) {
            }
        return filePath;
    }

}
//┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//┃　　　┃   神兽保佑　　　　　　　　
//┃　　　┃   代码无BUG！
//┃　　　┗━━━┓
//┃　　　　　　　┣┓
//┃　　　　　　　┏┛
//┗┓┓┏━┳┓┏┛
//  ┃┫┫　┃┫┫
//  ┗┻┛　┗┻┛
