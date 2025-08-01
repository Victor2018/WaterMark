package com.victor.lib.watermark

import android.graphics.*
import android.graphics.drawable.Drawable
import com.victor.lib.watermark.MarkDrawable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WaterMarkDrawable
 * Author: Victor
 * Date: 2023/10/17 17:02
 * Description: 
 * -----------------------------------------------------------------
 */

class WaterMarkDrawable: Drawable {
    private var mMarkDrawable: MarkDrawable? = null
    private var mBoundRect: RectF? = null
    private var mShader: BitmapShader? = null
    private var mPaint: Paint? = null

    constructor() {
        mMarkDrawable = MarkDrawable()
        mBoundRect = RectF()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint?.style = Paint.Style.FILL
    }

    private fun drawWaterMark() {
        val width = mMarkDrawable?.intrinsicWidth ?: 200
        val height = mMarkDrawable?.intrinsicHeight ?: 200
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)

        mMarkDrawable?.setBounds(0, 0, width, height)
        mMarkDrawable?.draw(canvas)

        mShader = BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        mPaint?.shader = mShader
    }

    override fun draw(canvas: Canvas) {
        if (mBoundRect != null) {
            canvas.drawRect(0f, 0f, mBoundRect!!.right, mBoundRect!!.bottom, mPaint!!)
        }
    }

    override fun setAlpha(alpha: Int) {
        mPaint?.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint?.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        mBoundRect!![left.toFloat(), top.toFloat(), right.toFloat()] = bottom.toFloat()
    }

    fun setParams(markStr: String?) {
        mMarkDrawable?.setParams(markStr)
        drawWaterMark()
    }

    fun setParams(markStr: String?,textSize: Float) {
        mMarkDrawable?.setParams(markStr,textSize)
        drawWaterMark()
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int) {
        mMarkDrawable?.setParams(markStr,textSize,textColor)
        drawWaterMark()
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,degree: Float) {
        mMarkDrawable?.setParams(markStr,textSize,textColor,degree)
        drawWaterMark()
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,hSpace: Float,vSpace: Float) {
        mMarkDrawable?.setParams(markStr,textSize,textColor,hSpace,vSpace)
        drawWaterMark()
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,degree: Float,hSpace: Float,vSpace: Float) {
        mMarkDrawable?.setParams(markStr,textSize,textColor,degree,hSpace,vSpace)
        drawWaterMark()
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,degree: Float,hSpace: Float,vSpace: Float,backgroundColor: Int) {
        mMarkDrawable?.setParams(markStr,textSize,textColor,degree,hSpace,vSpace, backgroundColor)
        drawWaterMark()
    }
}