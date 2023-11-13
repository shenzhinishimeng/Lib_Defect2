package com.catail.lib_commons.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.catail.lib_commons.R;

/**
 * Created by sam on 17/1/13.
 */

public class GlideUtils {

    public static void init(final Context context) {


    }

    /**
     * 加载普通图片
     */
    public static void load(Context context, ImageView imageView, String url) {
        // 不能崩
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)
                || url.length() < 5) {
            return;
        }
        Context viewContext = imageView.getContext();
        Activity activity = null;

        // View你还活着吗？
        if (viewContext instanceof Activity) {
            activity = (Activity) viewContext;
            if (activity.isFinishing()) {
                return;
            }
        }

        if (null != activity) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
//                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(activity)
                    .load(url)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    //                    .error(R.mipmap.empty_picture)
                    //.placeholder(R.mipmap.empty_picture)
                    //.crossFade(1000)//设置动画时间
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    //.override(80,80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    //                    .error(R.mipmap.empty_picture)
                    //.placeholder(R.mipmap.empty_picture)
                    //.crossFade(1000)
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        }
    }


    /**
     * 加载普通图片
     */
    public static void loader(Application context, ImageView imageView, String url) {
        // 不能崩
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)
                || url.length() < 5) {
            return;
        }
        Context viewContext = imageView.getContext();
        Activity activity = null;

        // View你还活着吗？
        if (viewContext instanceof Activity) {
            activity = (Activity) viewContext;
            if (activity.isFinishing()) {
                return;
            }
        }

        if (null != activity) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像

            Glide.with(activity)
                    .load(url)
                    .apply(options)
                    //                    .error(R.mipmap.empty_picture)
                    //.placeholder(R.mipmap.empty_picture)
                    //.crossFade(1000)//设置动画时间
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    //.override(80,80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    //                    .error(R.mipmap.empty_picture)
                    //.placeholder(R.mipmap.empty_picture)
                    //.crossFade(1000)
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        }

    }

    /**
     * 加载本地图片
     */
    public static void load(Context context, ImageView imageView, int resoure) {
        // 不能崩
        if (imageView == null) {
            return;
        }

        Context viewContext = imageView.getContext();
        Activity activity = null;

        // View你还活着吗？
        if (viewContext instanceof Activity) {
            activity = (Activity) viewContext;
            if (activity.isFinishing()) {
                return;
            }
        }

        if (null != activity) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
            Glide.with(activity)
                    .load(resoure)
                    .apply(options)
                    //                    .error(R.mipmap.empty_picture)
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
            Glide.with(context)
                    .load(resoure)
                    .apply(options)
                    //                    .error(R.mipmap.empty_picture)
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        }

    }


    /**
     * 加载普通图片
     * 何兆鸿
     */
    public static void load(Context context, ImageView imageView,
                            int defaultdrawable, String url) {
        // 不能崩
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)
                || url.length() < 5) {
            return;
        }
        Context viewContext = imageView.getContext();
        Activity activity = null;

        // View你还活着吗？
        if (viewContext instanceof Activity) {
            activity = (Activity) viewContext;
            if (activity.isFinishing()) {
                return;
            }
        }

        if (null != activity) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
            Glide.with(activity)
                    .load(url)
                    .apply(options)
                    //                    .error(defaultdrawable)
                    //.placeholder(R.mipmap.empty_picture)
                    //.crossFade(1000)//设置动画时间
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    //.override(80,80)//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                    .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                    .skipMemoryCache(false) //跳过内存缓存
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    //                    .error(defaultdrawable)
                    //.placeholder(R.mipmap.empty_picture)
                    //.crossFade(1000)
                    //                    .dontAnimate()//禁止动画，防止图片变形
                    //                    .priority(Priority.NORMAL) //下载的优先级
                    //                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略-磁盘
                    .into(imageView);
        }

    }


    /**
     * 图片宽度占满，高度自适应
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl,
                                           final ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.empty_picture)//加载成功之前占位图
                .error(R.mipmap.empty_picture) //加载错误之后的错误图
//                    .dontAnimate()
                .skipMemoryCache(false) //跳过内存缓存
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
        Glide.with(context)
                .load(imageUrl)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        BitmapDrawable bd = (BitmapDrawable) resource;
                        Bitmap bitmap = bd.getBitmap();
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.width = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        params.height = bitmap.getHeight();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .into(imageView);
    }

    /**
     * //原图 -> 圆图
     * Glide.with(conx).load(url).bitmapTransform(new CropCircleTransformation(conx)).crossFade(500).into(imageView);
     * <p>
     * //原图的毛玻璃、高斯模糊效果
     * Glide.with(conx).load(url).bitmapTransform(new BlurTransformation(conx, 25)).crossFade(1000).into(imageView);
     * <p>
     * //原图基础上复合变换成圆图 +毛玻璃（高斯模糊）
     * Glide.with(conx).load(url).bitmapTransform(new BlurTransformation(conx, 25),
     * new CropCircleTransformation(conx)).crossFade(1000).into(imageView);
     * <p>
     * //原图处理成圆角，如果是四周都是圆角则是RoundedCornersTransformation.CornerType.ALL
     * Glide.with(conx).load(url).bitmapTransform(new RoundedCornersTransformation(conx, 30, 0,
     * RoundedCornersTransformation.CornerType.ALL)).crossFade(1000).into(imageView);
     */

    /*====================================== 加载圆形图片 ==========================================*/
    public static void loadCirclePic(Object obj, ImageView iv) {
        if (obj.toString().length() < 5) {
            return;
        }
//        loadCirclePic(obj, iv,R.color.pic_default_bg);
        loadCirclePic(obj, iv, R.mipmap.empty_head_picture);
    }

    public static void loadCirclePic(Object obj, ImageView iv, int defaultPic) {
        loadCirclePic(obj, iv, defaultPic, defaultPic);
    }

    public static void loadCirclePic(Object obj, ImageView iv, int placeholder, int error) {

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .placeholder(placeholder)
                .error(error)
                .transform(new CircleCrop())
                .placeholder(R.mipmap.empty_head_picture)//加载成功之前占位图
                .skipMemoryCache(false) //跳过内存缓存
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);   //缓存所有版本的图像
        Glide.with(iv.getContext())
                .load(obj)
                .apply(requestOptions)
                .into(iv);
    }


}
