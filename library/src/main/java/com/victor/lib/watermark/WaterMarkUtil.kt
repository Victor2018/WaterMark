package com.victor.lib.watermark

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WaterMarkUtil
 * Author: Victor
 * Date: 2023/10/17 17:48
 * Description: 
 * -----------------------------------------------------------------
 */

object WaterMarkUtil {
    fun getWaterMarkText(): String {
        return "423099"
    }

    fun getDefaultWaterMark(isBgTransparent: Boolean): WaterMarkDrawable {
        var bgColor = Color.parseColor("#F9F9F9")
        if (isBgTransparent) {
            bgColor = Color.TRANSPARENT
        }

        var mWaterMarkDrawable = WaterMarkDrawable()
        mWaterMarkDrawable.setParams(
            getWaterMarkText(),28f,Color.parseColor("#80E6E6E6"),80f,
            100f, 80f , bgColor)

        return mWaterMarkDrawable
    }

    fun addWaterMark(activity: AppCompatActivity) {
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
        var waterMarkView = getWaterMarkView(activity)
        rootView.addView(waterMarkView)
    }

    fun addWaterMark(activity: AppCompatActivity,waterMarkView: WaterMarkView) {
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
        rootView.addView(waterMarkView)
    }

    fun addWaterMark(activity: AppCompatActivity,drawable: WaterMarkDrawable?) {
        val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
        rootView.background = drawable
        /*val layout = FrameLayout(activity)
        layout.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.background = drawable ?: getDefaultWaterMark(true)
        rootView.addView(layout)*/
    }

    fun getWaterMarkView(context: Context): WaterMarkView {
        var waterMarkView = WaterMarkView(context)

        var bgColor = Color.TRANSPARENT
        var textColor = Color.parseColor("#80E6E6E6")
        var textSize = 50f
        var hSize = 100f
        var vSize = 100f

        waterMarkView.setParams(getWaterMarkText(),textSize,textColor,-30f,hSize,vSize,bgColor)

        return waterMarkView
    }

}