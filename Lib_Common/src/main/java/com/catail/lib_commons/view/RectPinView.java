package com.catail.lib_commons.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.catail.lib_commons.R;
import com.catail.lib_commons.bean.QueryDMDLPDefectListDetailsResultBean;

import java.util.ArrayList;
import java.util.List;

import es.voghdev.pdfviewpager.library.subscaleview.SubsamplingScaleImageView;


public class RectPinView extends SubsamplingScaleImageView {
    private final Paint paint = new Paint();
    private final PointF vPin = new PointF();
    //    private Bitmap pin;
    private Bitmap pin1;
    private List<PointF> PointLists = new ArrayList<>();
    private List<QueryDMDLPDefectListDetailsResultBean.ResultBean> mDefectDataList = new ArrayList<>();
    private final List<Bitmap> pins = new ArrayList();

    public RectPinView(Context context) {
        this(context, null);
    }

    public RectPinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public void setPin(PointF sPin) {
        initialise();
        invalidate();
    }

    public void setPins(List<PointF> PointLists) {
        this.PointLists = PointLists;
        initialise();
        invalidate();
    }

    public void setAllTypePins(List<PointF> PointLists,
                               List<QueryDMDLPDefectListDetailsResultBean.ResultBean> mDefectDataList) {
        this.PointLists = PointLists;
        this.mDefectDataList = mDefectDataList;
        initialise();
        invalidate();


    }


    public void setBitmap() {
        for (int i = 0; i < pins.size(); i++) {
            Bitmap bitmap = pins.get(i);
            bitmap = null;
        }
//        this.pin = null;
    }


    private void initialise() {
        if (mDefectDataList.size() > 0) {
            if (pins.size() > 0) {
                pins.clear();
            }
        }

        for (int i = 0; i < PointLists.size(); i++) {
            if (!TextUtils.isEmpty(mDefectDataList.get(i).getFeature_pin_type())) {
                if (mDefectDataList.get(i).getFeature_pin_type().equals("defect")) {
                    showDefect2Pin(i);
                }

            } else {
                showInspectionPin(i);
            }
        }


//        float density = getResources().getDisplayMetrics().densityDpi;
//        pin = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_test);
//        float w = (density / 420f) * pin.getWidth();
//        float h = (density / 420f) * pin.getHeight();
//        pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);

    }

    private Canvas mCanvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        // Don't draw pin before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        paint.setAntiAlias(true);

//        Logger.e("pins=="+pins.size());
//        Logger.e("PointLists=="+PointLists.size());
        if (PointLists.size() > 0) {
            //多个的
            for (int i = 0; i < PointLists.size(); i++) {

                //画图钉
                sourceToViewCoord(PointLists.get(i), vPin);
                float vX = vPin.x - (pins.get(i).getWidth() / 2);
                float vY = vPin.y - pins.get(i).getHeight() + 50;
                canvas.drawBitmap(pins.get(i), vX, vY, paint);

                //画数字
                paint.setColor(getResources().getColor(R.color.black_textcolor_000000));
                paint.setTextSize(34.0f);
//                String text = String.valueOf(i+1);
                String text = "";

                if (mDefectDataList.size() == PointLists.size()) {
                    //TODO 2023.5.6日,先让CR202 项目跑起来, 剩下的再说,5.17日开始进行修改,显示多个.
//                    text = getResources().getString(R.string.inspection_all_level);
                    if (mDefectDataList.size() == 1) {
                        if (TextUtils.isEmpty(mDefectDataList.get(i).getZone())) {
                            if (mDefectDataList.get(i).getPin_pos().length() < 5) {
                                text = String.valueOf(mDefectDataList.get(i).getPin_pos());
                                if (text.length() == 1) {
                                    text = "L000" + text;
                                } else if (text.length() == 2) {
                                    text = "L00" + text;
                                } else if (text.length() == 3) {
                                    text = "L0" + text;
                                } else if (text.length() == 4) {
                                    text = "L" + text;
                                }
                                canvas.drawText(text, vX + 30, vY + 55, paint);//设置文本位置
                            } else {
                                text = String.valueOf(mDefectDataList.get(i).getPin_pos());
                                canvas.drawText(text, vX + 30, vY + 55, paint);//设置文本位置
                            }
                        } else {
//                            text = getResources().getString(R.string.inspection_all_level);
                            text = String.valueOf(mDefectDataList.get(i).getZone());
                            canvas.drawText(text, vX + 30, vY + 55, paint);//设置文本位置
                        }
                    } else {
                        if (!TextUtils.isEmpty(mDefectDataList.get(i).getZone())) {
                            text = String.valueOf(mDefectDataList.get(i).getZone());
                            canvas.drawText(text, vX + 20, vY + 55, paint);//设置文本位置
                        } else {
                            text = String.valueOf(mDefectDataList.get(i).getPin_pos());
                            if (text.length() < 5) {
                                if (text.length() == 1) {
                                    text = "L000" + text;
                                } else if (text.length() == 2) {
                                    text = "L00" + text;
                                } else if (text.length() == 3) {
                                    text = "L0" + text;
                                } else if (text.length() == 4) {
                                    text = "L" + text;
                                }
                            }
                            canvas.drawText(text, vX + 30, vY + 55, paint);//设置文本位置
                        }
                    }
                }
//                else {
//                    text = String.valueOf(i + 1);
//                }
//                canvas.drawText(text, vX + 30, vY + 55, paint);//设置文本位置
//                canvas.drawText(text, vX + 10, vY + 55, paint);//设置文本位置

            }

//            for (int k = 0; k < PointLists.size(); k++) {
//
//            }
//            Rect rect = new Rect();
//            paint.setStyle(Paint.Style.FILL);//实心
//            canvas.drawColor(Color.BLACK);
//            canvas.drawRect(0, 0, getHeight(), getWidth(), paint);//画矩形

//            paint.getTextBounds(text, 0, text.length(), rect);//将内容的长和宽。添加到rect矩形中
//            float width = rect.width();//获取宽
//            float height = rect.height();


        }


    }


    private void showInspectionPin(int i) {
        //0 Active 1 Onhold 2 Closed 3 Delay 9 删除
        if (mDefectDataList.get(i).getStatus() == -1) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_blue_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 0) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_orange_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 1) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_green_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 2) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_red_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 3) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_blue_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 4) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_orange_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        }
    }

    private void showDefect2Pin(int i) {
        //0 Active 1 Onhold 2 Closed 3 Delay 9 删除
        if (mDefectDataList.get(i).getStatus() == -1) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_blue_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 0
                || mDefectDataList.get(i).getStatus() == 1
                || mDefectDataList.get(i).getStatus() == 7
                || mDefectDataList.get(i).getStatus() == 12) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_orange_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 6) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_green_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 10) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_red_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        } else if (mDefectDataList.get(i).getStatus() == 9) {
            float density = getResources().getDisplayMetrics().densityDpi;
            Bitmap pin = BitmapFactory.decodeResource(this.getResources(), R.mipmap.rect_blue_pin);
            float w = (density / 420f) * pin.getWidth();
            float h = (density / 420f) * pin.getHeight();
            pin = Bitmap.createScaledBitmap(pin, (int) w, (int) h, true);
            pins.add(pin);
        }
    }
}
