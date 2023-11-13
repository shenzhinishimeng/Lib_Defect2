package com.catail.lib_commons.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import com.catail.lib_commons.R
import com.catail.lib_commons.base.BaseActivity
import com.catail.lib_commons.utils.Logger
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import java.text.DecimalFormat


class PieChartView : PieChart {
    private var mChart: PieChart? = null

    constructor(context: Context?, chart: PieChart) : super(context) {
        initPieChart(chart);
    }

    constructor(context: Context?, attrs: AttributeSet?, chart: PieChart) : super(context, attrs) {
        initPieChart(chart);
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int, chart: PieChart) : super(
        context,
        attrs,
        defStyle
    ) {
        initPieChart(chart);
    }

    fun initPieChart(chart: PieChart) {
        this.mChart = chart;
        chart.setUsePercentValues(false)
        chart.getDescription().setEnabled(false)
        chart.setExtraOffsets(5.0f, 10.0f, 5.0f, 5.0f)
        chart.setDragDecelerationFrictionCoef(0.95f)

//        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText())
        chart.setDrawHoleEnabled(true)
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.setHoleRadius(58f)
        chart.setTransparentCircleRadius(61f)
        chart.setDrawCenterText(true)
        chart.setRotationAngle(0.0f)
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true)
        chart.setHighlightPerTapEnabled(true)

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener

//        chart.animateY(1400, Easing.EaseInOutQuad)//动画效果
//          chart.spin(2000, 0, 360);
        val l: Legend = chart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.isWordWrapEnabled = true
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        //        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f)

    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }


    //    private fun setData(count: Int, range: Float, result: ResultBean) {
    fun setData(
        activity: BaseActivity,
        count: Int,
        range: Float,
        result: ArrayList<Int>,
        typeNameLists: ArrayList<String>,
        centerText: String,
        type: String
    ) {
        val entries: ArrayList<PieEntry> = ArrayList<PieEntry>()
        for (i in 0 until count) {
            Logger.e("result.get(i).toFloat(),==" + result.get(i).toFloat())
            entries.add(
                PieEntry(
//                    result.get(i) * range / result.size,
                    result.get(i).toFloat(),
                    typeNameLists.get(i),
                    activity.resources.getDrawable(R.mipmap.ic_launcher)
                )
            )
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.setSliceSpace(3f)
        dataSet.setIconsOffset(MPPointF(0f, 40f))
        dataSet.setSelectionShift(5f)

        // add a lot of colors
        val colors = ArrayList<Int>()
        if (type.equals("Checklist")) {
            for (c in ColorTemplate.CHECKLIST_STATUS_COLORS) colors.add(c)
        }else if(type.equals("Inspection_status")){
            for (c in ColorTemplate.INSPECTION_STATUS_COLORS) colors.add(c)
//            for (c in ColorTemplate.INSPECTION_STATUS_COLORS) colors.add(c)
        }else if(type.equals("Inspection_type")){
            for (c in ColorTemplate.INSPECTION_CHECKLIST_TYPE_COLORS) colors.add(c)
        }
//        for (i in typeNameLists.indices) {
//            if (typeNameLists.get(i)
//                    .equals(activity.getResources().getString(R.string.ins_created))
//            ) {
//                colors.add(ColorTemplate.CHECKLIST_STATUS_0)
//            } else if (typeNameLists.get(i)
//                    .equals(activity.getResources().getString(R.string.ins_ongoing))
//            ) {
//                colors.add(ColorTemplate.CHECKLIST_STATUS_1)
//            } else if (typeNameLists.get(i)
//                    .equals(activity.getResources().getString(R.string.ins_completed))
//            ) {
//                colors.add(ColorTemplate.CHECKLIST_STATUS_3)
//            }
//        }
//        CHECKLIST_STATUS_0

//        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
//        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
//        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
//        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
//        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
//        colors.add(ColorTemplate.getHoloBlue())

        dataSet.setColors(colors)
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(DecimalFormat("###,###,##")))//显示整数
//        data.setValueFormatter(DefaultValueFormatter(1)) //自己修改的方法
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //        data.setValueTypeface(tfLight);
        mChart!!.data = data

        Logger.e("centerText==" + centerText)
        // undo all highlights
        mChart!!.highlightValues(null)
        mChart!!.setCenterTextSize(20.0f)
        mChart!!.centerText = centerText
        mChart!!.invalidate()
    }
}