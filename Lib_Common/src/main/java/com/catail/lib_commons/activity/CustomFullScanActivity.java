package com.catail.lib_commons.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.catail.lib_commons.R;
import com.catail.lib_commons.bean.QRCodeResultBean;
import com.catail.lib_commons.utils.Base64;
import com.catail.lib_commons.utils.ConstantValue;
import com.catail.lib_commons.utils.GsonUtil;
import com.catail.lib_commons.utils.Logger;
import com.catail.lib_commons.utils.StatusBarUtils;
import com.google.zxing.Result;
import com.king.zxing.CameraScan;
import com.king.zxing.CaptureActivity;

public class CustomFullScanActivity extends CaptureActivity implements CameraScan.OnScanResultCallback, View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_full_scan;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        StatusBarUtils.immersiveStatusBar(this, toolbar, 0.2f);

        ImageView ivLeft = findViewById(R.id.ivLeft);
        ivLeft.setOnClickListener(this);
        ivLeft.setVisibility(View.VISIBLE);
    }


    @Override
    public void initCameraScan() {
        super.initCameraScan();
        getCameraScan()
                .setPlayBeep(true)
                .setVibrate(true);
//        //初始化解码配置
//        DecodeConfig decodeConfig = new DecodeConfig();
//        decodeConfig.setHints(DecodeFormatManager.ALL_HINTS)////设置解码
//                .setSupportVerticalCode(true)//设置是否支持扫垂直的条码 （增强识别率，相应的也会增加性能消耗）
//                .setSupportLuminanceInvert(true)//设置是否支持识别反色码，黑白颜色反转（增强识别率，相应的也会增加性能消耗）
//                .setAreaRectRatio(0.8f)//设置识别区域比例，默认0.8，设置的比例最终会在预览区域裁剪基于此比例的一个矩形进行扫码识别
////                .setAreaRectVerticalOffset(0)//设置识别区域垂直方向偏移量，默认为0，为0表示居中，可以为负数
////                .setAreaRectHorizontalOffset(0)//设置识别区域水平方向偏移量，默认为0，为0表示居中，可以为负数
//                .setFullAreaScan(false);//设置是否全区域识别，默认false
//
//        //获取CameraScan，里面有扫码相关的配置设置。CameraScan里面包含部分支持链式调用的方法，即调用返回是CameraScan本身的一些配置建议在startCamera之前调用。
//        getCameraScan().setPlayBeep(true)//设置是否播放音效，默认为false
//                .setVibrate(true)//设置是否震动，默认为false
////                .setCameraConfig(new CameraConfig())//设置相机配置信息，CameraConfig可覆写options方法自定义配置
//                .setCameraConfig(new ResolutionCameraConfig(this))//设置CameraConfig，可以根据自己的需求去自定义配置
//                .setNeedAutoZoom(false)//二维码太小时可自动缩放，默认为false
//                .setNeedTouchZoom(true)//支持多指触摸捏合缩放，默认为true
//                .setDarkLightLux(45f)//设置光线足够暗的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
//                .setBrightLightLux(100f)//设置光线足够明亮的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
//                .bindFlashlightView(ivFlashlight)//绑定手电筒，绑定后可根据光线传感器，动态显示或隐藏手电筒按钮
//                .setOnScanResultCallback(this)//设置扫码结果回调，需要自己处理或者需要连扫时，可设置回调，自己去处理相关逻辑
//                .setAnalyzer(new MultiFormatAnalyzer(decodeConfig))//设置分析器,DecodeConfig可以配置一些解码时的配置信息，如果内置的不满足您的需求，你也可以自定义实现，
//                .setAnalyzeImage(true);//设置是否分析图片，默认为true。如果设置为false，相当于关闭了扫码识别功能
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivLeft) {
            finish();
        }
    }


    /**
     * 扫码结果回调
     *
     * @param result 扫码结果
     */
    @Override
    public boolean onScanResultCallback(Result result) {
        //  这里是扫描到二维码之后的逻辑   扫描出的字符串
        String text = result.getText();
        Logger.e("result-->", text + "");

        String base64Str = Base64.decode(text);
        Logger.e("base64Str==", base64Str);

        String flagStr = getIntent().getStringExtra("flag");
        Logger.e("flagStr", flagStr);
        if (flagStr.equals("task_home")) {
            String projectId = getIntent().getStringExtra("projectId");
            QRCodeDealMethod(base64Str, flagStr, projectId);
        } else if (flagStr.equals("task_apply")) {
            String projectId = getIntent().getStringExtra("projectId");
            QRCodeDealMethod(base64Str, flagStr, projectId);
        }else if(flagStr.equals("defect")){
            String projectId = getIntent().getStringExtra("projectId");
            QRCodeDealMethod(base64Str, flagStr, projectId);
        }

        //如果需支持连扫，返回true即可
        return false;
    }

    private void QRCodeDealMethod(String qrCodeStr, String flagStr, String projectId) {
        if (!TextUtils.isEmpty(qrCodeStr)) {
            try {
                QRCodeResultBean qrCodeResultBean = GsonUtil.GsonToBean(qrCodeStr,
                        QRCodeResultBean.class);
                Logger.e("qrCodeStr="+qrCodeStr);
                Logger.e("projectId==", "projectId==" + projectId + "AAA");
                Logger.e("qrCodeResultBean=="+qrCodeResultBean.toString());
                if (!projectId.equals(qrCodeResultBean.getProgram_id())) {
                    Toast.makeText(CustomFullScanActivity.this, R.string.task_qr_project_not_same,
                                    Toast.LENGTH_SHORT)
                            .show();
                    CustomFullScanActivity.this.finish();
                    return;
                }

                if (flagStr.equals("task_home")) {
                    //TODO 打开task界面
//                    Intent intent = new Intent(CustomFullScanActivity.this, TaskDetailsListActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("open_flag", "qrcode");
//                    bundle.putSerializable("qrcodebean", qrCodeResultBean);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    CustomFullScanActivity.this.finish();
                } else if (flagStr.equals("task_apply")) {
                    Logger.e("qrCodeResultBean.toString()==", qrCodeResultBean.toString());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("qrcodebean", qrCodeResultBean);
                    intent.putExtras(bundle);
                    setResult(ConstantValue.TaskApplyQrCode, intent);
                    CustomFullScanActivity.this.finish();
                    //                    if (modelists.size() == 0) {
//                        modelists.add(qrCodeResultBean);
//                    } else {
//                        boolean isIn = false;
//                        for (int i = 0; i < modelists.size(); i++) {
//                            if (modelists.get(i).getUuid().equals(qrCodeResultBean.getUuid())) {
//                                isIn = true;
//                            }
//                        }
//                        if (isIn == false) {
//                            modelists.add(qrCodeResultBean);
//                        }
//                    }
//                    Logger.e("modelists.size()==", "modelists.size()==" + modelists.size());
//                    if (handler != null) {
//                        handler.restartPreviewAndDecode();
//                    }

                }else if(flagStr.equals("defect")){
                    Logger.e("qrCodeResultBean.toString()==", qrCodeResultBean.toString());
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("qrcodebean", qrCodeResultBean);
                    intent.putExtras(bundle);
                    setResult(ConstantValue.TaskApplyQrCode, intent);
                    CustomFullScanActivity.this.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 二维码不支持
                Toast.makeText(CustomFullScanActivity.this, R.string.this_type_of_qr_code_is_not_supported,
                                Toast.LENGTH_SHORT)
                        .show();
                CustomFullScanActivity.this.finish();
            }
        } else {
            // 扫描二维码失败
            Toast.makeText(CustomFullScanActivity.this, R.string.identify_a_qr_code_failure,
                    Toast.LENGTH_SHORT).show();
            CustomFullScanActivity.this.finish();
        }
    }





//    @Override
//    protected void onDestroy() {
//        mCameraScan.release();
//        super.onDestroy();
//    }
}