package com.catail.lib_commons.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import com.catail.lib_commons.R
import com.github.aachartmodel.aainfographics.aachartcreator.*


object AAChartUtils {

    /*---------------------------------柱状图------------------------------------------------------*/
    @SuppressLint("ResourceType")
    fun initBarChartModel(context: Context): AAChartModel {
        //动态设置统计图的背景颜色
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.card_bg_color, typedValue, true)

        val aaChartModel = AAChartModel.Builder(context)
            .setChartType(AAChartType.Bar)
            .setBackgroundColor(typedValue.resourceId)
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)
            .setXAxisLabelsEnabled(true)
            .setZoomType(AAChartZoomType.XY)
            .setXAxisVisible(true)
            .setYAxisVisible(true)
            .setYAxisTitle("")
            .build()
        return aaChartModel
    }

    @SuppressLint("ResourceType")
    fun initBarChartModel1(context: Context): AAChartModel {
        val aaChartModel = AAChartModel.Builder(context)
            .setChartType(AAChartType.Bar)
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)
            .setXAxisLabelsEnabled(true)
            .setZoomType(AAChartZoomType.XY)
            .setXAxisVisible(true)
            .setYAxisVisible(true)
            .setYAxisTitle("")
            .build()
        return aaChartModel
    }

    fun setBarChartName(aaChartModel: AAChartModel, chartDatas: Array<String?>) {
        aaChartModel
            .categories(
                chartDatas
            )
    }

    fun setBarChartValue(aaChartModel: AAChartModel, chartValueDatas: Array<Any?>) {
        aaChartModel.series(
            arrayOf(
                AASeriesElement()
                    .name("")
                    .data(
                        chartValueDatas
                    )
                    .colorByPoint(true)
            )
        )
    }

    fun setBarChartColor(aaChartModel: AAChartModel) {
        aaChartModel.colorsTheme(
            arrayOf(
                "#FE2944", "#5663FF", "#FCD44F", "#15DCC7", "#18BCFF", "#2EFF5B",
            )
        )
    }

    fun setBarChartColor(aaChartModel: AAChartModel, array: Array<Any>) {
        aaChartModel.colorsTheme(
            array
        )
    }

    fun setBarChartStyle(aaChartModel: AAChartModel) {
        aaChartModel.animationType(AAChartAnimationType.EaseInCubic)
            .animationDuration(1200)
    }

    /*---------------------------------条形图,柱状图------------------------------------------------------*/
    @SuppressLint("ResourceType")
    fun initColumChartModel(context: Context): AAChartModel {
        //动态设置统计图的背景颜色
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.card_bg_color, typedValue, true)
        val aaChartModel = AAChartModel.Builder(context)
            .setChartType(AAChartType.Column)
            .setBackgroundColor(typedValue.resourceId)
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)
            .setXAxisLabelsEnabled(true)
            .setZoomType(AAChartZoomType.XY)
            .setXAxisVisible(true)
            .setYAxisVisible(true)
            .setYAxisTitle("")
            .build()
        return aaChartModel
    }

    fun setColumChartName(aaChartModel: AAChartModel, chartDatas: Array<String?>) {
        aaChartModel
            .categories(
                chartDatas
            )
    }

    fun setColumChartValue(aaChartModel: AAChartModel, chartValueDatas: Array<Any?>) {
        aaChartModel.series(
            arrayOf(
                AASeriesElement()
                    .name("")
                    .data(
                        chartValueDatas
                    )
                    .colorByPoint(true)
            )
        )
    }

    fun setColumChartColor(aaChartModel: AAChartModel) {
        aaChartModel.colorsTheme(
            arrayOf(
                "#FE2944", "#5663FF", "#FCD44F", "#15DCC7", "#18BCFF", "#2EFF5B",
            )
        )
    }

    fun setColumChartColor(aaChartModel: AAChartModel, array: Array<Any>) {
        aaChartModel.colorsTheme(
            array
        )
    }

    fun setColumChartStyle(aaChartModel: AAChartModel) {
        aaChartModel.animationType(AAChartAnimationType.EaseInCubic)
            .animationDuration(1200)
    }


    /*----------------------------饼状图-----------------------------------------------------------*/
    fun initPieChartModel(context: Context): AAChartModel {
        //默认显示图例
        //动态设置统计图的背景颜色
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.card_bg_color, typedValue, true)
        return AAChartModel()
            .chartType(AAChartType.Pie)
            .backgroundColor(typedValue.resourceId)
            .title("")
            .subtitle("")
            .dataLabelsEnabled(false)//是否直接显示扇形图数据
            .xAxisLabelsEnabled(false)
            .yAxisLabelsEnabled(false)
            .yAxisVisible(false)
            .xAxisVisible(false)
            .yAxisTitle("")
    }

    fun setPieChartLegend(aaChartModel: AAChartModel) {
        aaChartModel.legendEnabled(false)
    }

    fun setPieChartcColor(aaChartModel: AAChartModel) {
        aaChartModel.colorsTheme(
            arrayOf(
                "#FE2944", "#5663FF", "#FCD44F", "#15DCC7", "#18BCFF", "#2EFF5B",
            )
        )
    }

    fun setPieChartcValue(aaChartModel: AAChartModel, pieChartValueDatas: Array<Any?>) {
        aaChartModel.series(
            arrayOf(
                AASeriesElement()
                    .name("")
                    .data(
                        pieChartValueDatas
                    )
            )
        )
    }

    /*----------------------------箱线图-----------------------------------------------------------*/

    fun initBoxplotChartModel(context: Context): AAChartModel {
        //动态设置统计图的背景颜色
//        val typedValue = TypedValue()
//        context.theme.resolveAttribute(R.attr.card_bg_color, typedValue, true)
        val aaChartModel = AAChartModel.Builder(context)
            .setChartType(AAChartType.Boxplot)
            .setXAxisVisible(true)
//            .setBackgroundColor(typedValue.resourceId)
            .setDataLabelsEnabled(true)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)
            .setXAxisLabelsEnabled(true)
            .setZoomType(AAChartZoomType.XY)
            .setYAxisVisible(true)
            .setYAxisTitle("")
            .build()
        return aaChartModel
    }


    fun setBoxplotChartValue(aaChartModel: AAChartModel, chartValueDatas: Array<Any?>) {
        aaChartModel.series(
            arrayOf(
                AASeriesElement()
                    .name("")
                    .color("#FF884C")
                    .fillColor("#FFFFFF")
                    .data(
                        chartValueDatas
                    )
            )
        )
    }


    /*----------------------------折线图-----------------------------------------------------------*/
    fun initLineChartModel(context: Context): AAChartModel {
        //动态设置统计图的背景颜色
//        val typedValue = TypedValue()
//        context.theme.resolveAttribute(R.attr.card_bg_color, typedValue, true)
        val aaChartModel = AAChartModel.Builder(context)
            .setChartType(AAChartType.Line)
            .setDataLabelsEnabled(false)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(true)
            .setTouchEventEnabled(false)
//            .setBackgroundColor(typedValue.resourceId)
            .setXAxisLabelsEnabled(true)
            .setYAxisTitle("")
            .setMarkerSymbolStyle(AAChartSymbolStyleType.Normal)//设置折线连接点样式为:边缘白色
            .setMarkerRadius(6f)
            .build()
        return aaChartModel
    }

    fun setLineChartName(aaChartModel: AAChartModel, chartDatas: Array<String?>) {
        aaChartModel
            .categories(
                chartDatas
            )
    }

    fun setLineChartValue(aaChartModel: AAChartModel, chartValueDatas: Array<AASeriesElement>) {
        aaChartModel.series(
            chartValueDatas
        )
    }

    fun setLineColor(aaChartModel: AAChartModel, array: Array<Any>) {
        aaChartModel.colorsTheme(
            array
        )
    }
}