package com.victor.lib.watermark

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import kotlin.math.abs

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MarkDrawable
 * Author: Victor
 * Date: 2023/10/17 16:54
 * Description: 
 * -----------------------------------------------------------------
 */

class MarkDrawable: Drawable {
    private val TAG = "MarkDrawable"

    private var mPaint: TextPaint? = null
    private var mTextColor = Color.BLACK
    private var mBackgroundColor = Color.TRANSPARENT
    private var mBoundRect: RectF? = null
    private var mMarkStr: String? = null
    private var mHSpace = 80f //水平间距
    private var mVSpace = 80f //垂直间距
    private var mTextSize = 40f
    private var mDegree = -30f

    constructor() {
        mPaint = TextPaint()
        mPaint?.isAntiAlias = true
        mBoundRect = RectF()
    }

    private fun initPaint() {
        mPaint?.color = mTextColor
        mPaint?.textSize = mTextSize

        val width = mPaint?.measureText(mMarkStr, 0, mMarkStr?.length ?: 0) ?: 0
        val rect = Rect()
        mPaint?.getTextBounds(mMarkStr, 0, mMarkStr?.length ?: 0, rect)

        val x = (width.toFloat() * Math.cos(Math.toRadians(mDegree.toDouble()))).toFloat()+ mHSpace
        val y = (width.toFloat() * Math.cos(Math.toRadians(mDegree.toDouble()))).toFloat() + mVSpace
        mBoundRect!![0f, 0f, x] = y

    }

    override fun draw(canvas: Canvas) {
        if (TextUtils.isEmpty(mMarkStr)) return
        canvas.save()
        canvas.drawColor(mBackgroundColor)
        canvas.translate(mBoundRect!!.width() / 2, mBoundRect!!.height() / 2)
        canvas.rotate(mDegree)
        canvas.drawText(mMarkStr ?: "", mHSpace / 2 - mBoundRect!!.width() / 2, 0f, mPaint!!)
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {
        mPaint?.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint?.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicHeight(): Int {
        return abs(mBoundRect?.height()?.toInt() ?: 0)
    }

    override fun getIntrinsicWidth(): Int {
        return abs(mBoundRect?.width()?.toInt() ?: 0)
    }

    fun setParams(markStr: String?) {
        setParams(markStr,mTextSize,mTextColor,  mHSpace, mVSpace,mDegree, mBackgroundColor)
    }

    fun setParams(markStr: String?,textSize: Float) {
        setParams(markStr,textSize,mTextColor,mDegree, mHSpace, mVSpace, mBackgroundColor)
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int) {
        setParams(markStr,textSize,textColor,mDegree, mHSpace, mVSpace, mBackgroundColor)
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,degree: Float) {
        setParams(markStr,textSize,textColor,degree, mHSpace, mVSpace, mBackgroundColor)
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,hSpace: Float,vSpace: Float) {
        setParams(markStr,textSize,textColor,mDegree, hSpace, vSpace, mBackgroundColor)
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,degree: Float,hSpace: Float,vSpace: Float) {
        setParams(markStr,textSize,textColor,degree, hSpace, vSpace, mBackgroundColor)
    }

    fun setParams(markStr: String?,textSize: Float,textColor: Int,degree: Float,hSpace: Float,vSpace: Float,backgroundColor: Int) {
        mMarkStr = markStr
        mTextSize = textSize
        mTextColor = textColor
        mDegree = degree
        mHSpace = hSpace
        mVSpace = vSpace
        mBackgroundColor = backgroundColor

        initPaint()
        invalidateSelf()
    }

}