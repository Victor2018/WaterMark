package com.victor.lib.watermark

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WaterMarkView
 * Author: Victor
 * Date: 2023/10/18 10:46
 * Description: 
 * -----------------------------------------------------------------
 */

class WaterMarkView: View {
    private val TAG = WaterMarkView::class.java.simpleName

    private var mDrawWidth = 300 //画的宽度 为实际高度的mScaleSize倍（作用是在旋转后防止有些地方没有水印）

    private var mDrawHeight = 300 //画的高度 同上

    private var mMarkStr = " " //水印文字

    private var mTextColor = Color.BLACK //字体颜色//E6E8EC

    private var mTextSize = 13f //字体大小

    private var mHSpace = 50f //水平间距

    private var mVSpace = 20f //垂直间距

    private var mDegree = -30f //旋转角度

    private var mBackgroundColor = Color.TRANSPARENT //背景颜色//F4F4F4

    private var mScaleSize = 2 //缩放大小 固定：2

    private var mWidth = 0 //布局实际宽度

    private var mHeight = 0 //布局实际高度

    constructor(context: Context):this(context, null)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(context, attrs)
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    fun init(context: Context, attrs: AttributeSet?) {
        if (null != attrs) {
            //获取XML中的参数
            val ta = context.obtainStyledAttributes(attrs, R.styleable.WaterMarkView)
            mMarkStr = ta.getString(R.styleable.WaterMarkView_wm_text) ?: " "
            mTextSize = ta.getDimension(R.styleable.WaterMarkView_wm_textSize, 13f)
            mTextColor = ta.getColor(R.styleable.WaterMarkView_wm_textColor, Color.BLACK)
            mDegree = ta.getFloat(R.styleable.WaterMarkView_wm_degree, -30f)
            mHSpace = ta.getDimension(R.styleable.WaterMarkView_wm_hSpace, 50f)
            mVSpace = ta.getDimension(R.styleable.WaterMarkView_wm_vSpace, 20f)
            mBackgroundColor = ta.getColor(R.styleable.WaterMarkView_wm_bgColor, Color.TRANSPARENT)
            ta.recycle()
        }
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

    /**
     * 参数设置
     *
     * @param text
     * @param hSize
     * @param vSize
     * @param degrees
     * @param bgColor   背景颜色
     * @param textColor 字体颜色
     * @param textSize  字体大小
     */
    fun setParams(
        text: String?,
        textSize: Float,
        textColor: Int,
        degree: Float,
        hSpace: Float,
        vSpace: Float,
        bgColor: Int
    ) {
        mMarkStr = text ?: " "
        mTextSize = textSize
        mTextColor = textColor
        mDegree = degree
        mHSpace = hSpace
        mVSpace = vSpace
        mBackgroundColor = bgColor

        invalidate()
    }

    /**
     * 在View的源码当中并没有对AT_MOST和EXACTLY两个模式做出区分，
     * 也就是说View在wrap_content和match_parent两个模式下是完全相同的，
     * 都会是match_parent，
     * 显然这与我们平时用的View不同，
     * 所以我们要重写onMeasure方法。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDrawWidth, mDrawHeight)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDrawWidth, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mDrawHeight)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        //获取实际宽高
        mWidth = width
        mHeight = height
        //设置需要画的宽高
        mDrawWidth = mWidth * mScaleSize
        mDrawHeight = mHeight * mScaleSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画水印
        drawWaterMark(canvas)
    }

    /**
     * 绘制水印
     *
     * @param canvas
     */
    private fun drawWaterMark(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = Rect()
        paint.textSize = mTextSize
        //获取文字长度和宽度
        paint.getTextBounds(mMarkStr, 0, mMarkStr.length, rect)
        val textWidth = rect.width()
        val textHeight = rect.height()
        //获取每个单独的item的宽高
        val itemWidth = textWidth + mHSpace
        val itemHeight = textHeight + mVSpace
        //获取水平、垂直方向需要绘制的个数
        val hSize = (mDrawWidth / itemWidth + 0.5).toInt()
        val vSize = (mDrawHeight / itemHeight + 0.5).toInt()

        //X轴开始坐标
        val xStart = mHSpace / 2
        //Y轴开始坐标
        val yStart = mVSpace / 2 + textHeight

        //创建透明画布
        canvas.drawColor(mBackgroundColor)
        paint.color = mTextColor
//        paint.typeface = Typeface.DEFAULT_BOLD
        //paint.setAlpha((int) (0.1 * 255));
        // 获取跟清晰的图像采样
        paint.isDither = true
        paint.isFilterBitmap = true
        canvas.save()
        //平移
        canvas.translate(-(mDrawWidth / 4).toFloat(), -(mDrawHeight / 4).toFloat())
        //旋转对应角度
        canvas.rotate(mDegree, (mDrawWidth / 2).toFloat(), (mDrawHeight / 2).toFloat())

        //画X轴方向
        for (i in 0 until hSize) {
            val xDraw = xStart + itemWidth * i
            //画Y轴方向
            for (j in 0 until vSize) {
                val yDraw = yStart + itemHeight * j
                canvas.drawText(mMarkStr, xDraw, yDraw, paint)

                /*var spacing = 0 //间距

                for (i in 0 ..2) {
                    canvas.drawText("$str $i", xDraw, yDraw + spacing, paint)
                    spacing += textHeight
                }*/
            }
        }
        canvas.restore()
    }
}