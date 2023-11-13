package com.catail.lib_commons.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {

    /**
     * 英文字符串转日期
     */
    public static Date En2Date(String EnStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date;
        if (EnStr != null) {
            try {
                date = simpleDateFormat.parse(EnStr);
            } catch (ParseException e) {
                date = CN2Date(EnStr);
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }


        return date;
    }

    /**
     * 英文字符串转日期
     */
    public static Date En2DateNo(String EnStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date;
        if (EnStr != null) {
            try {
                date = simpleDateFormat.parse(EnStr);
            } catch (ParseException e) {
                date = CN2Date(EnStr);
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }


        return date;
    }

    /**
     * 中文字符串转日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date CN2Date(String CnStr) {
        //2017-06-01 04:15:00
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date = null;
        if (CnStr != null) {
            try {
                date = simpleDateFormat.parse(CnStr);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return date;
    }

    /**
     * 中文字符串转日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date CN2DateNo(String CnStr) {
        //2017-06-01
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date = null;
        if (CnStr != null) {
            try {
                date = simpleDateFormat.parse(CnStr);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return date;
    }

    /**
     * 转中文日期
     */
    @SuppressLint("SimpleDateFormat")
    public static String DatetoCNDate(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;

    }

    /**
     * 转中文日期
     */
    @SuppressLint("SimpleDateFormat")
    public static String DatetoCNStrNo(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;

    }

    @SuppressLint("SimpleDateFormat")
    public static String DatetoCNStrYY(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;

    }

    /**
     * 英文格式的字符串转中文格式字符串时间
     */
    public static String EnStr2CnStr(String EnStr) {
        Date en2Date = En2Date(EnStr);
        return DatetoCNDate(en2Date);
    }

    /**
     * 转英文日期
     */
    public static String DatetoEnDateNo(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;
    }

    /**
     * 转英文日期
     */
    public static String DatetoEnDate(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;
    }

    /**
     * 转英文日期
     */
    public static String DatetoEnDateNoSS(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;
    }


    public static String CnStr2EnStr(String CnStr) {
        Date en2Date = En2Date(CnStr);
        return DatetoEnDate(en2Date);
    }

    /**
     * 英文字符串转日期
     */
    public static Date En2DateNoSS(String EnStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MMM-yyyy", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date;
        if (EnStr != null) {
            try {
                date = simpleDateFormat.parse(EnStr);
            } catch (ParseException e) {
                date = CN2DateNoSS(EnStr);
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }


        return date;
    }

    /**
     * 中文字符串转日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date CN2DateNoSS(String CnStr) {
        //2017-06-01 04:15:00
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date = null;
        if (CnStr != null) {
            try {
                date = simpleDateFormat.parse(CnStr);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return date;
    }


    public static String CnStr2EnStrNoSS(String CnStr) {
        Date en2Date = En2DateNoSS(CnStr);
        return DatetoEnDateNoSS(en2Date);
    }

    public static Date CN2DateYYMM(String CnStr) {
        //2017-06-01 04:15:00
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date = null;
        if (CnStr != null) {
            try {
                date = simpleDateFormat.parse(CnStr);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return date;
    }

    public static String DatetoEnDateYYMM(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;
    }

    /**
     * 将2016-12-22 时间转换为英文格式时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String FormatDate(String str) {
        String EnStr = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        try {
            Date parse = simpleDateFormat.parse(str);
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            EnStr = simpleDateFormat.format(parse);
        } catch (ParseException e1) {
//			e1.printStackTrace();
        }
        return EnStr;
    }

    /**
     * 将 06-DEc-2016类型的时间转换为 06 Dec 2016
     */
    public static String FormatDateEN(String str) {
        String EnStr = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        try {
            Date parse = simpleDateFormat.parse(str);
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            EnStr = simpleDateFormat.format(parse);
        } catch (ParseException e1) {
//			e1.printStackTrace();
        }
        return EnStr;
    }

    /**
     * 将字符串日期转换为时间日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StrToDate(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
        }
        return date;
    }

    /**
     * 将字符串日期转换为时间日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StrToDateNO(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));

            try {
                date = sdf.parse(str);
//				Log.e("AAA", date.toString());
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }

        return date;
    }

    /**
     * 将时间日期转换为英文日期 yyyy-MM-dd HH:mm:ss
     */
    public static String CNDateStrTOENDate(Date CNDate) {
        String ENDate;
        if (CNDate != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            ENDate = df.format(CNDate);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }

        return ENDate;
    }

    /**
     * 将时间日期转换为英文日期 dd MMM yyyy
     */
    public static String CNDateStrTOENDateNO(Date CNDate) {
        String ENDate;
        if (CNDate != null) {
//			Log.e("CNDate", CNDate.toString());
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            ENDate = df.format(CNDate);
//			Log.e("ENDate", ENDate.toString());
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }

        return ENDate;
    }

    /**
     * 中文时间转换为英文时间 (没有HH:mm:ss)
     */
    public static String CNStr2ENStrNoTime(String CNStr) {
        String ENStr;
        if (CNStr != null) {
            Date strToDateNO = StrToDateNO(CNStr);
            ENStr = CNDateStrTOENDateNO(strToDateNO);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return ENStr;
    }

    /**
     * 中文时间转换为英文时间
     */
    public static String CNStr2ENStr(String CNStr) {
        String ENStr;
        if (CNStr != null) {
            Date strToDate = StrToDate(CNStr);
            ENStr = CNDateStrTOENDate(strToDate);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return ENStr;
    }

    /**
     * 英文时间转日期
     */
    public static Date ENStr2Date(String ENStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd MMM yyyy", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(ENStr);
        } catch (ParseException e) {
//			e.printStackTrace();
        }
        return date2;
    }

    /**
     * 日期转中文时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String Date2CNStr(Date date) {
        //将日期转换为字符串
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return simpleDateFormat2.format(date);
    }

    public static String Date2CNStrNoSS(Date date) {
        //将日期转换为字符串
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String format = simpleDateFormat2.format(date);

        return format;

    }

    public static String ENStr2CNStr(String ENStr) {
        String CNStr = null;
        if (ENStr != null) {
            Date enStr2Date = ENStr2Date(ENStr);
            String date2cnStr = Date2CNStr(enStr2Date);
        } else {
            Log.e("NullExcepton", "时间为空");
        }
        return CNStr;
    }


    /**
     * 将字符串日期转换为时间日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StrToDateNoss(String str) {
        Date date = null;
        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Log.e("NullExcepton", "时间为空");
        }
        return date;
    }


    public static String CNDateStrTOENDateNoss(Date CNDate) {
        String ENDate;
        if (CNDate != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
            df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            ENDate = df.format(CNDate);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }

        return ENDate;
    }

    public static String CNStr2ENStrNoss(String CNStr) {
        String ENStr;
        if (CNStr != null) {
            Date strToDate = StrToDateNoss(CNStr);
            ENStr = CNDateStrTOENDateNoss(strToDate);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return ENStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String Date2CNStrNoss(Date date) {
        //将日期转换为字符串
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return simpleDateFormat2.format(date);
    }


    public static Long between_days(String a, String b) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 自定义时间格式

        Calendar calendar_a = Calendar.getInstance();// 获取日历对象
        Calendar calendar_b = Calendar.getInstance();

        Date date_a = null;
        Date date_b = null;

        try {
            date_a = simpleDateFormat.parse(a);//字符串转Date
            date_b = simpleDateFormat.parse(b);
            calendar_a.setTime(date_a);// 设置日历
            calendar_b.setTime(date_b);
        } catch (ParseException e) {
            e.printStackTrace();//格式化异常
        }

        long time_a = calendar_a.getTimeInMillis();
        long time_b = calendar_b.getTimeInMillis();

        long between_days = (time_b - time_a) / (1000 * 3600 * 24);//计算相差天数

        return between_days;
    }

    public static String DatetoCnDateYYMM(Date date) {
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM", Locale.CHINESE);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;
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


    public static String getsTheYYMMSeveralMonthsBefore(int Xmonth) {
        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.add(Calendar.MONTH, Xmonth);
        String cnDate = DateFormatUtils.DatetoCnDateYYMM(calendarInstance.getTime());
        Logger.e("datetoCNStrNo1=" + cnDate);
        return cnDate;
    }


    public static String getCurrentYearMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String dateStr = sdf.format(calendar.getTime());
        Date date = DateFormatUtils.CN2DateYYMM(dateStr);
        return DateFormatUtils.DatetoCnDateYYMM(date);
    }

    public static Long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        Logger.e("startTime=" + startTime);
        Logger.e("endTime=" + endTime);
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            long nd = (1000 * 24 * 60 * 60); // 一天的毫秒数
            long nh = (1000 * 60 * 60); // 一小时的毫秒数
            long nm = (1000 * 60); // 一分钟的毫秒数
            long ns = 1000;// 一秒钟的毫秒数
            long diff;
            long day = 0;
            try {
                // 获得两个时间的毫秒时间差异
                diff = (sd.parse(endTime).getTime()
                        - sd.parse(startTime).getTime());
                day = diff / nd;// 计算差多少天
                long hour = diff % nd / nh;// 计算差多少小时
                long min = diff % nd % nh / nm; // 计算差多少分钟
                long sec = diff % nd % nh % nm / ns;// 计算差多少秒
                // 输出结果
                Logger.e(
                        "时间相差：" + day + "天" + hour + "小时" + min
                                + "分钟" + sec + "秒。"
                );

                if (day >= 1) {
                    return day;
                } else {
                    if (day == 0L) {
                        return 1L;
                    } else {
                        return 0L;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0L;
        } else {
            return 0L;
        }
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

    public static String getYYMMDD(String time) {
        Date date = DateFormatUtils.En2Date(time);
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;

    }


    public static String getHHMMSS(String time) {
        Date date = DateFormatUtils.En2Date(time);
        String CnStr;
        //将日期转换为字符串
        if (date != null) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        } else {
            Log.e("NullExcepton", "时间为空");
            return null;
        }

        return CnStr;

    }


    /**
     * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getISO8601Timestamp(String cnDateStr) {
        String resultStr = "";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dt1 = LocalDateTime.parse(cnDateStr, formatter);
            ZoneOffset offset = ZoneOffset.of("+08:00");
            OffsetDateTime date = OffsetDateTime.of(dt1, offset);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            resultStr = date.format(formatter2);
            Logger.e("cnDateStr==" + cnDateStr);
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
            return resultStr;
        }
    }


    public static String Date2CNStrYYMM(Date date) {
        //将日期转换为字符串
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String format = simpleDateFormat2.format(date);

        return format;

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

    public static String calcHours(String startTimeStr, String endTimeStr) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date startTime = df.parse(startTimeStr);
            Date endTime = df.parse(endTimeStr);

            long diff = endTime.getTime() - startTime.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = 0;
            if (days == 0) {
                hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            } else {
                hours = days * 24;
            }
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            Logger.e("minutes==", minutes + "");
            if (minutes == 30) {
                return hours + ".5";
            } else {
                return hours + "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String resetDate(String timeStr, String minutes) {
        try {
//            Logger.e("timeStr=","timeStr="+timeStr);
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//            df.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            Date date = df.parse(timeStr);

            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
//            Logger.e("year=", year + "");
            int month = calendar.get(Calendar.MONTH) + 1;
//            Logger.e("month=", month + "");
            int day = calendar.get(Calendar.DATE);
//            Logger.e("day=", day + "");
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
//            Logger.e("hours=", hours + "");
            String minute = minutes;
            String ss = "00";
            String dateStr = year + "-" + month + "-" + day + " " + hours + ":" + minute + ":" + ss;
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String DateformatHHmmss(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String DateformatHHmm(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 中文字符串转日期
     */
    public static Date CN2DateHHmmss(String CnStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
//        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
//        Logger.e("------------------------------");
//        Logger.e("====" + timeZone.getDisplayName());
//        Logger.e("------------------------------");

        Logger.e("------------------------------");
        String[] availableIDs = TimeZone.getAvailableIDs();
//        for (int i = 0; i < availableIDs.length; i++) {
//            Logger.e("i=" + availableIDs[i]);
//        }
        Logger.e("------------------------------");

        Date date = null;
        if (CnStr != null) {
            try {
                date = simpleDateFormat.parse(CnStr);
            } catch (ParseException e) {
//				e.printStackTrace();
            }
        } else {
            Logger.e("NullExcepton", "时间为空");
            return null;
        }
        return date;
    }

    public static boolean comparisonHHmmss(String startTimeStr, String endTimeStr) {
        Logger.e("------------------------------");
        Logger.e("startTimeStr====" + startTimeStr);
        Logger.e("endTimeStr====" + endTimeStr);
        Logger.e("------------------------------");
        try {
            DateFormat df = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            Date startTime = df.parse(startTimeStr);
            Date endTime = df.parse(endTimeStr);
            long diff = startTime.getTime() - endTime.getTime();

            if (diff > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 日期转中文时间
     *
     * @param date
     * @return
     */
    public static String Date2CNStrNoDay(Date date) {
        //将日期转换为字符串
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy MM");
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        String format = simpleDateFormat2.format(date);
        return format;
    }

    /**
     * 转中文日期
     */
    public static String  DatetoCNStrNoDay(Date date){
        String CnStr;
        //将日期转换为字符串
        if(date!=null){
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            CnStr = simpleDateFormat2.format(date);
        }else{
            Log.e("NullExcepton", "时间为空");
            return null;
        }
        return CnStr;
    }
}
