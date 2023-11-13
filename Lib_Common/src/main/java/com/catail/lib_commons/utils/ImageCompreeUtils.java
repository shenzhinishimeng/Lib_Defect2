package com.catail.lib_commons.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by D on 2018/4/24.
 */

public class ImageCompreeUtils {
    /**
     * 图片压缩
     */
    public static Bitmap compressBmpFromBmp(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
    /**
     * 进行添加水印图片和文字
     */
    public static Bitmap createBitmap(Bitmap src, Bitmap waterMak, String title) {
        if (src == null) {
            return src;
        }
        // 获取原始图片与水印图片的宽与高
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        mCanvas.drawBitmap(src, 0, 0, null);

        if (null != waterMak) {
            int ww = waterMak.getWidth();
            int wh = waterMak.getHeight();
            // 在src的右下角添加水印
            Paint paint = new Paint();
            // paint.setAlpha(100);
            mCanvas.drawBitmap(waterMak, width - ww - 5, height - wh - 5, paint);
        }
        // 开始加入文字
        if (null != title) {
            Paint textPaint = new Paint();
            // 设置字体颜色
            textPaint.setColor(Color.WHITE);
            // 设置字体大小
            textPaint.setTextSize(40);
            // 阴影设置
            textPaint.setShadowLayer(3f, 1, 1, Color.DKGRAY);
            String familyName = "宋体";
            Typeface typeface = Typeface.create(familyName, Typeface.BOLD_ITALIC);
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Paint.Align.LEFT);
            float textWidth = textPaint.measureText(title);
            // 文字 添加位置
            mCanvas.drawText(title, width - textWidth - 10, height - 26, textPaint);

        }
//        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.save();
        // 保存
        mCanvas.restore();
        return newBitmap;
    }

    /**
     * 删除原图保存水印照片方法
     */
    public static void saveBitmap(Uri captureFileUri, Bitmap bm) {
        File f = new File(captureFileUri.getPath());
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            // 压缩图片 80 是压缩率，表示压缩20%; 如果不压缩是100，
            bm.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
