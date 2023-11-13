package com.catail.lib_commons.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.catail.lib_commons.CommonsApplication;
import com.catail.lib_commons.R;
import com.catail.lib_commons.base.BaseActivity;
import com.catail.lib_commons.utils.calendarselection.MonthDateView;
import com.finddreams.languagelib.LanguageType;
import com.finddreams.languagelib.MultiLanguageUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 检查当前网络是否可用
     */
    public static boolean isNetworkAvailable(AppCompatActivity activity) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo.length > 0) {
                for (NetworkInfo info : networkInfo) {
                    // 判断当前网络状态是否为连接状态
                    Logger.e("info.getState()==" + info.getState());
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 杀死当前应用的进程
     */
    public static void killProcess() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }

    /**
     * 获取当前系统的版本       中文/英文
     * return 0   ----中文
     * return 1   ----英文
     */
    public static int GetSystemCurrentVersion() {
        int languageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return 0;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return 1;
        } else if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            //8.0以上的判断
            if (Build.VERSION.SDK_INT >= 26) {
                String language = Locale.getDefault().getLanguage();
                String country = Locale.getDefault().getCountry();
//                Logger.e("language=", language);
//                Logger.e("country=", country);
                String languageAndCountry = language + "-" + country;
                if (languageAndCountry.equals("zh-")) {
                    return 0;
                } else if (languageAndCountry.equals("zh-CN")) {
                    return 0;
                } else if (languageAndCountry.equals("en-")) {
                    return 1;
                } else if (languageAndCountry.equals("en-US")) {
                    return 1;
                } else if (languageAndCountry.equals("en-SG")) {
                    return 1;
                } else {
                    return 1;
                }
            } else {
                return languageType;
//                Resources res = CommonsApplication.getContext().getResources();
//                Configuration conf = res.getConfiguration();
//                Locale locale = conf.locale;
////                Logger.e("local", locale.getLanguage());
////                Logger.e("country", locale.getCountry());
//                String languageAndCountry = locale.getLanguage() + locale.getCountry();
//                if (languageAndCountry.equals("zh-")) {
//                    return 0;
//                } else if (languageAndCountry.equals("zh-CN")) {
//                    return 0;
//                } else if (languageAndCountry.equals("en-")) {
//                    return 1;
//                } else if (languageAndCountry.equals("en-US")) {
//                    return 1;
//                } else if (languageAndCountry.equals("en-SG")) {
//                    return 1;
//                } else {
//                    return 1;
//                }
            }
        } else {
            return 1;
        }
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
        Logger.e("width==" + width);
        Logger.e("height==" + height);
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
        try {
            File f = new File(captureFileUri.getPath());
            Logger.e("captureFileUri.getPath()=" + captureFileUri.getPath());
            if (f.exists()) {
                f.delete();
                Logger.e("f.delete()");
            } else {
                Logger.e("f.delete()==no");
            }

            FileOutputStream out = new FileOutputStream(f);
            // 压缩图片 80 是压缩率，表示压缩20%; 如果不压缩是100，
            bm.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除原图保存水印照片方法
     */
    public static void saveBitmap(String path, Bitmap bm) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            // 压缩图片 80 是压缩率，表示压缩20%; 如果不压缩是100，
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap DealTakePhoto(ContentResolver mContentResolver, String path) {
        Uri uri = Uri.fromFile(new File(path));
        InputStream in;
        try {
            in = mContentResolver.openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取进程号对应的进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断邮箱的格式正确与否
     * return true/false;
     */
    public static boolean isMobileNO(String mobiles) {
        String rex;
        if (mobiles.length() == 8) {
            rex = "^[89]\\d{7}$";
        } else {
            rex = "^1[3578]\\d{9}$";
        }

        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是email不
     */
    public static boolean isEmail(String email) {
//        String regex =  "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        String regex = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();

    }

    /**
     * 得到自定义的progressDialog
     */
    public static Dialog createLoadingDialog(Activity context, String msg) {
        try {
            while (context.getParent() != null) {
                context = context.getParent();
            }
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
            LinearLayout layout = v.findViewById(R.id.dialog_view);// 加载布局
            // main.xml中的ImageView
            ImageView spaceshipImage = v.findViewById(R.id.img);
            TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
            // 加载动画
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                    context, R.anim.load_animation);
            // 使用ImageView显示动画
            spaceshipImage.startAnimation(hyperspaceJumpAnimation);
            tipTextView.setText(msg);// 设置加载信息

            Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

//        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
            loadingDialog.setCanceledOnTouchOutside(false);//点击对话框外不可以取消
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
            return loadingDialog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 在某些没有baseActivity使用progressDialog出现方法
     */
    public static void showProgressDialog(Activity activity, String msg) {
//        loadingDialog=null;
        if (loadingDialog == null) {
            loadingDialog = Utils.createLoadingDialog(activity, msg);
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

    }

    /**
     * 在某些没有baseActivity使用progressDialog出现方法
     */
    public static void dismissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * 判断输入的字符串是不是数字
     */
    public static boolean isNumeric(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * 这个方法是处理显示多个的pic
     */
    public static List<String> DealShowPicList(List<String> PhotoLists, int show_photo_count) {
        List<String> tempPhotoLists = new ArrayList<>();
        if (PhotoLists.size() >= show_photo_count) {
            for (int i = 0; i < show_photo_count; i++) {
                tempPhotoLists.add(PhotoLists.get(i));
            }
        } else {
            tempPhotoLists.addAll(PhotoLists);
        }
        return tempPhotoLists;
    }

    public static String OriginalUrlToThumbUrl(String OriginalUrl) {
        StringBuilder thambulr = new StringBuilder();
        String[] imgStr = OriginalUrl.split("/");
        for (int i = 0; i < imgStr.length; i++) {
            if (i == imgStr.length - 1) {
                thambulr.append(Global.ThambStr).append(imgStr[i]);
            } else {
                thambulr.append(imgStr[i]).append("/");
            }
        }
        Logger.e("thamburl==", thambulr.toString());
        return thambulr.toString();
    }

    public static String ThumbUrlToOriginalUrl(String OriginalUrl) {
        StringBuilder thambulr = new StringBuilder();
        String[] imgStr = OriginalUrl.split("/");
        for (int i = 0; i < imgStr.length; i++) {
            if (i == imgStr.length - 1) {
                String substring = imgStr[i].substring(6);
                thambulr.append(substring);
            } else {
                thambulr.append(imgStr[i]).append("/");
            }
        }
        Logger.e("thamburl==", thambulr.toString());
        return thambulr.toString();
    }

    public static String getStackTraceStr(Exception e) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < e.getStackTrace().length; i++) {
//            Logger.e(i + "==" + (e.getStackTrace())[i].toString());
            s.append((e.getStackTrace())[i].toString()).append("\n");
        }
        return s.toString();
    }

    public static void setAlertDialogSize(AppCompatActivity activity, AlertDialog dialog, double size) {
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
//        Logger.e("(int) (width * size)=="+((int) (width * size))+"");
        if (size != 0) {
            dialog.getWindow().setLayout((int) (width * size), ActionBar.LayoutParams.WRAP_CONTENT);
        } else {
            dialog.getWindow().setLayout((int) (width * 0.88), ActionBar.LayoutParams.WRAP_CONTENT);
        }
    }

    public static void setAlertDialogSize(AppCompatActivity activity, AlertDialog dialog,
                                          double width_size, double height_size) {
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
//        Logger.e("(int) (width * size)=="+((int) (width * size))+"");
        if (width_size != 0) {
            dialog.getWindow().setLayout((int) (width * width_size), ActionBar.LayoutParams.WRAP_CONTENT);
        } else {
            dialog.getWindow().setLayout((int) (width * 0.88), ActionBar.LayoutParams.WRAP_CONTENT);
        }
        if (height_size != 0) {
            dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, (int) (height * height_size));
        } else {
            dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, (int) (height * 0.88));
        }

    }

    public static void setAlertDialogConner(AlertDialog dialog) {
        dialog.getWindow().setBackgroundDrawable(null);
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return DateFormatUtils.DatetoCNDate(date);

    }

    public static String getCurrentCNDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return DateFormatUtils.DatetoCNStrNo(date);
    }

    public static String getCurrentYYDate() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        return DateFormatUtils.DatetoCNStrYY(date);
    }

    public static boolean CheckStartDateAndEndDate(
            Context activity,
            String startDate, String endDate) {
        //1. 起止时间必须得选择, 2. 不能跨年.
        Logger.e("------------------------------");
        Logger.e("开始时间" + !TextUtils.isEmpty(startDate));
        Logger.e("结束时间" + !TextUtils.isEmpty(endDate));
        Logger.e("------------------------------");
        //第一个判断条件
        if (TextUtils.isEmpty(startDate) && TextUtils.isEmpty(endDate)) {
            return true;
        } else {
            if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                if (TextUtils.isEmpty(startDate)) {
                    ToastUtils.toastStrContext(activity,activity.getResources().getString(R.string.please_select_a_start_date));
                }

                if (TextUtils.isEmpty(endDate)) {
                    ToastUtils.toastStrContext(activity,activity.getResources().getString(R.string.please_select_an_end_date));
                }
                return false;
            } else {
                String startDateYY = startDate.substring(0, 4);
                String endDateYY = endDate.substring(0, 4);

                if (!startDateYY.equals(endDateYY)) {
                    ToastUtils.toastStrContext(activity,activity.getResources().getString(R.string.please_select_the_same_year));
                    return false;
                } else {
                    return true;
                }
            }
        }

    }



    public static double Keep1Decimal(double num1) {
        BigDecimal b = new BigDecimal(num1);
        //保留2位小数
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 对象转字符串
     */
    public static String objectToString(Object object) throws IOException {
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 创建字节对象输出流
        ObjectOutputStream out;
        // 然后通过将字对象进行64转码，写入key值为key的sp中
        out = new ObjectOutputStream(baos);
        out.writeObject(object);
        String objectVal = new String(Base64.encode(baos.toByteArray(),
                Base64.DEFAULT));
        out.close();
        out.close();
        baos.close();
        return objectVal;
    }

    /**
     * 字符串转对象
     */
    public static Object stringToObject(String objectVal) throws IOException,
            ClassNotFoundException {
        byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
        // 通过读取字节流，创建字节流输入流，写入对象
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        ObjectInputStream ois;
        ois = new ObjectInputStream(bais);
        Object object = ois.readObject();
        bais.close();
        ois.close();
        return object;
    }


    public static Dialog loadingDialog;




    /**
     * 删除原图保存水印照片方法
     */
    public static void saveStringBitmap(String captureFileUri, Bitmap bm) {
        try {
            File f = new File(captureFileUri);
            Logger.e("captureFileUri.getPath()=" + captureFileUri);
            if (f.exists()) {
                f.delete();
                Logger.e("f.delete()");
            } else {
                Logger.e("f.delete()==no");
            }

            FileOutputStream out = new FileOutputStream(f);
            // 压缩图片 80 是压缩率，表示压缩20%; 如果不压缩是100，
            bm.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下的所有文件
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp;
        for (String s : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + s);
            } else {
                temp = new File(path + File.separator + s);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + s);//先删除文件夹里面的文件
                delFolder(path + "/" + s);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 以指定的缩放比例去缩放TextView
     */
    public static void scaleView(TextView textView, float scale) {
        ViewCompat.animate(textView).scaleX(scale).scaleY(scale);
    }


    public static void scaleView3(TextView textView1, TextView textView2, TextView textView3,
                                  int postion) {
        float scale_12 = (float) 1.2;
        float scale_10 = (float) 1.0;
        if (postion == 0) {
            ViewCompat.animate(textView1).scaleX(scale_12).scaleY(scale_12);
            ViewCompat.animate(textView2).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView3).scaleX(scale_10).scaleY(scale_10);
        } else if (postion == 1) {
            ViewCompat.animate(textView1).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView2).scaleX(scale_12).scaleY(scale_12);
            ViewCompat.animate(textView3).scaleX(scale_10).scaleY(scale_10);
        } else if (postion == 2) {
            ViewCompat.animate(textView1).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView2).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView3).scaleX(scale_12).scaleY(scale_12);
        }
    }

    public static void scaleView4(TextView textView1, TextView textView2, TextView textView3,
                                  TextView textView4, int postion) {
        float scale_12 = (float) 1.2;
        float scale_10 = (float) 1.0;
        if (postion == 0) {
            ViewCompat.animate(textView1).scaleX(scale_12).scaleY(scale_12);
            ViewCompat.animate(textView2).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView3).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView4).scaleX(scale_10).scaleY(scale_10);
        } else if (postion == 1) {
            ViewCompat.animate(textView1).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView2).scaleX(scale_12).scaleY(scale_12);
            ViewCompat.animate(textView3).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView4).scaleX(scale_10).scaleY(scale_10);
        } else if (postion == 2) {
            ViewCompat.animate(textView1).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView2).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView3).scaleX(scale_12).scaleY(scale_12);
            ViewCompat.animate(textView4).scaleX(scale_10).scaleY(scale_10);
        } else if (postion == 3) {
            ViewCompat.animate(textView1).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView2).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView3).scaleX(scale_10).scaleY(scale_10);
            ViewCompat.animate(textView4).scaleX(scale_12).scaleY(scale_12);
        }
    }

    public static int IMAGE_MAX_SIZE = 1024;

    public static void writeSDFile(String fileName, String write_str) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = write_str.getBytes();
        fos.write(bytes);
        fos.close();
    }

    public static String getsTheYYMMSeveralMonthsBefore(int Xmonth) {
        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.add(Calendar.MONTH, Xmonth);
        String cnDate = DateFormatUtils.DatetoCnDateYYMM(calendarInstance.getTime());
        Logger.e("datetoCNStrNo1=" + cnDate);
        return cnDate;
    }


    /**
     * 获取每个月的第一天和最后一天
     */
    public static String getDayDate(String dateYYMM) throws Exception {
        String year = dateYYMM.substring(0, 4);
        String month = dateYYMM.substring(5);
        Logger.e("dateYYMM" + dateYYMM);
        Logger.e("year==" + year);
        Logger.e("month==" + month);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start_str = String.format("%s-%02d-01", year, Integer.parseInt(month));
        String end_index = String.format("%s-%02d-01", year, Integer.parseInt(month) + 1);

        long end_timestamp = sdf.parse(end_index).getTime();
        end_timestamp -= 1000 * 60 * 60 * 24;

        long thistimestamp = new Date().getTime();

        String end_str = null;
        if (end_timestamp > thistimestamp) {
            end_str = sdf.format(new Date(thistimestamp));
        } else {
            end_str = sdf.format(new Date(end_timestamp));
        }

        return start_str + "|" + end_str;
    }

    /**
     * 获取每个月的第一天和最后一天
     * 如果不是当月的话,减一天
     */
    public static String getDayDate(String dateYYMM, boolean isCurrentDay) throws Exception {
        String year = dateYYMM.substring(0, 4);
        String month = dateYYMM.substring(5);
        Logger.e("dateYYMM==" + dateYYMM);
        Logger.e("year==" + year);
        Logger.e("month==" + month);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start_str = String.format("%s-%02d-01", year, Integer.parseInt(month));
        String end_index = String.format("%s-%02d-01", year, Integer.parseInt(month) + 1);

        long end_timestamp = sdf.parse(end_index).getTime();
        end_timestamp -= 1000 * 60 * 60 * 24;

        long thistimestamp = new Date().getTime();

        if (isCurrentDay) {
            thistimestamp -= 1000 * 60 * 60 * 24;
        }

        String end_str = null;
        if (end_timestamp > thistimestamp) {
            end_str = sdf.format(new Date(thistimestamp));
        } else {
            end_str = sdf.format(new Date(end_timestamp));
        }

        return start_str + "|" + end_str;
    }

    /**
     * 判断两个月是不是相同
     */
    public static boolean judgeMonthSame(String startDateStr, String endDateStr) {
        String startMonth = startDateStr.substring(0, 7);
        String endMonth = endDateStr.substring(0, 7);
//        Logger.e("startMonth=" + startMonth);
//        Logger.e("endMonth=" + endMonth);
        return startMonth.equals(endMonth);

    }


    public static int getMonthDays(String startDateStr, String endDateStr) {
        String startMonth = startDateStr.substring(5, 7);
        String endMonth = endDateStr.substring(5, 7);
//        Logger.e("startMonth=" + startMonth);
//        Logger.e("endMonth=" + endMonth);

        //如果是同一个月, 就按正常的
        if (startMonth.equals(endMonth)) {
            String yearMonthStr = startDateStr.substring(0, 7);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(sdf.parse(yearMonthStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else {
            //如果不是同一个月,就按30天算,
            return 30;
        }
    }

    public static long DaysOfTwoMonth(String startDateStr, String endDateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startDateStr);
            endDate = simpleDateFormat.parse(endDateStr);
        } catch (ParseException e) {
//				e.printStackTrace();
        }

        long diff = endDate.getTime() - startDate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = (hours / 24) + 1;
        Logger.e("days", "" + days);
        return days;
    }

    public static void initCalendarDefaultDate(int dateFlag, String startDateDay, String endDateDay) {
        //区分一下开始 时间和结束时间
        if (dateFlag == 0) {
            if (!startDateDay.isEmpty()) {
//                Logger.e("startDateDay!=空", "startDateDay!=空");
                String[] split = startDateDay.split("\\-");
                int year = Integer.parseInt(split[0]);
                int month;
                if (split[1].startsWith("0")) {
                    month = Integer.parseInt(split[1]) - 1;
                } else {
                    month = Integer.parseInt(split[1]) - 1;
                }

                int day;
                if (split[2].startsWith("0")) {
                    day = Integer.parseInt(split[2]);
                } else {
                    day = Integer.parseInt(split[2]);
                }
                MonthDateView.setCurrentDay(year, month, day);
            } else {
//                Logger.e("startDateDay==空", "startDateDay==空");
            }
        } else {
            if (!endDateDay.isEmpty()) {
//                Logger.e("endDateDay!=空", "endDateDay!=空");
                String[] split = endDateDay.split("\\-");
                int year = Integer.parseInt(split[0]);
                int month;
                if (split[1].startsWith("0")) {
                    month = Integer.parseInt(split[1]) - 1;
                } else {
                    month = Integer.parseInt(split[1]) - 1;
                }

                int day;
                if (split[2].startsWith("0")) {
                    day = Integer.parseInt(split[2]);
                } else {
                    day = Integer.parseInt(split[2]);
                }
                MonthDateView.setCurrentDay(year, month, day);
            } else {
//                Logger.e("endDateDay==空", "endDateDay==空");
            }
        }
    }

    public static String getCurrentYearMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String dateStr = sdf.format(calendar.getTime());
        Date date = DateFormatUtils.CN2DateYYMM(dateStr);
        return DateFormatUtils.DatetoCnDateYYMM(date);
    }

    public static String getCurrentDateAndTimeNoss() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = sdf.format(calendar.getTime());
        Date date = DateFormatUtils.CN2DateNoSS(dateStr);
        String endateStr = DateFormatUtils.DatetoEnDateNoSS(date);
        return endateStr;
    }


    /**
     * 月份相差六个月
     *
     * @return
     */
    public static int TheMonthsDifferBySixMonths(
            String startMonthStr, String endMonthStr
    ) {
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));

            Date startMonthDate = simpleDateFormat.parse(startMonthStr);
            Date endMonthDate = simpleDateFormat.parse(endMonthStr);

            Calendar startDateCal = Calendar.getInstance();
            Calendar endDateCal = Calendar.getInstance();
            startDateCal.setTime(startMonthDate);
            endDateCal.setTime(endMonthDate);

            int startYear = startDateCal.get(Calendar.YEAR);
            int endYear = endDateCal.get((Calendar.YEAR));
            if (startYear == endYear) {
                //计算同一年的时间, 开始年份 小于结束年份 返回负值,    否则返回正数
                return endDateCal.get(Calendar.MONTH) - startDateCal.get(Calendar.MONTH) + 1;
            } else {
                if (endYear > startYear) {
                    Logger.e("endYear > startYear");
                    //2022.04  -   2023.01
                    //如果 结束年份 大于  开始年份 应该返回正数
                    int endMonth = 12 - (startDateCal.get(Calendar.MONTH) + 1);
                    int startMonth = endDateCal.get(Calendar.MONTH) + 1;
                    return Math.abs(endYear - startYear - 1) * 12 + startMonth + endMonth + 1;//加1  是为了加上当前月
                } else if (endYear < startYear) {
                    Logger.e("endYear < startYear");
                    //如果 结束年份 小于  开始年份, 直接返回-1, 说明结束年份不能比开始年份大
                    //  2022.05   - 2022.04
                    return -1;
                } else {
                    //如果  结束年份,开始年份, 一样大 则返回1
                    return 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * 返回double 值
     *
     * @return
     */
    public static String doubleValueStr(float value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    /**
     * @return 0 等于 1 大于 2小于
     */
    public static int comparePercent(String value1, String value2) {
        String coverdata1 = value1.replace("%", "");
        String checkdata2 = value2.replace("%", "");
        Logger.e("------------------------------");
        Logger.e("coverdata1====" + coverdata1);
        Logger.e("checkdata2====" + checkdata2);
        Logger.e("------------------------------");
        if (!coverdata1.equals("∞") && !checkdata2.equals("∞")) {
            double data1 = Double.parseDouble(coverdata1);
            double data2 = Double.parseDouble(checkdata2);
            if (data1 == data2) {
                return 0;
            } else {
                if (data1 > data2) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } else {
            return 0;
        }

    }


    public static String getCurrentDateNOTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        String dateStr = DateFormatUtils.DatetoCNDate(date);
        return dateStr.substring(0, 10);
    }

    /**
     * 当前时间和选择时间比较.
     */
    public static boolean judgeSelectDate(String selectionDay) {
        //判断 当前时间和 结束时间比较
        String currentDateTime = Utils.getCurrentDate();
        Logger.e("------------------------------");
        Logger.e("currentDateTime====" + currentDateTime);
        Logger.e("------------------------------");
        //先判断是昨天还是今天的 考勤
        Date currentDate = DateFormatUtils.CN2DateNo(currentDateTime);
        Date selectDate = DateFormatUtils.CN2DateNo(selectionDay);

        Logger.e("------------------------------");
        Logger.e("currentDate====" + currentDate);
        Logger.e("selectDate====" + selectDate);
        Logger.e("------------------------------");

        boolean dateResult = currentDate.before(selectDate);
        Logger.e("------------------------------");
        Logger.e("dateResult====" + dateResult);
        Logger.e("------------------------------");

        return dateResult;
    }


    public static void setLinearLayoutViewWidthAndHeight(AppCompatActivity activity,
                                                         View view){

        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
//        Logger.e("(int) (width * size)=="+((int) (width * size))+"");

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, (int) (height * 0.5));
        view.setLayoutParams(layoutParams);
    }


    public static boolean judgeCurrentDate(String selectionDay) {

        //判断 当前时间和 结束时间比较
        String currentDateTime = Utils.getCurrentDate();
        Logger.e("------------------------------");
        Logger.e("currentDateTime====" + currentDateTime);
        Logger.e("------------------------------");
        //先判断是昨天还是今天的 考勤
        Date currentDate = DateFormatUtils.CN2DateNo(currentDateTime);
        Date selectDate = DateFormatUtils.CN2DateNo(selectionDay);

        Logger.e("------------------------------");
        Logger.e("currentDate====" + currentDate);
        Logger.e("selectDate====" + selectDate);
        Logger.e("------------------------------");

        boolean dateResult = selectDate.before(currentDate);
        Logger.e("------------------------------");
        Logger.e("dateResult====" + dateResult);
        Logger.e("------------------------------");

        return dateResult;
    }


    public static boolean judgeCurrentDate(String beforeDate,String afterDate) {

        Date currentDate = DateFormatUtils.CN2DateNo(beforeDate);
        Date selectDate = DateFormatUtils.CN2DateNo(afterDate);

        Logger.e("------------------------------");
        Logger.e("currentDate====" + currentDate);
        Logger.e("selectDate====" + selectDate);
        Logger.e("------------------------------");

        boolean dateResult = currentDate.before(selectDate);
        Logger.e("------------------------------");
        Logger.e("dateResult====" + dateResult);
        Logger.e("------------------------------");

        return dateResult;
    }

}
