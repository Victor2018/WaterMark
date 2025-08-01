package com.victor.watermark

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.victor.lib.watermark.WaterMarkDrawable
import com.victor.lib.watermark.WaterMarkUtil
import com.victor.lib.watermark.WaterMarkView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTextWaterMark()
//        addDrawableWaterMark()
    }

    private fun addTextWaterMark() {
        var waterMarkView = WaterMarkView(this)

        var bgColor = Color.TRANSPARENT
        var textSize = 60f
        var textColor = Color.parseColor("#80E6E6E6")
        var hSpace = 100f
        var vSpace = 100f
        var degree = -45f

        waterMarkView.setParams("423098",textSize,textColor,degree,hSpace,vSpace,bgColor)
        WaterMarkUtil.addWaterMark(this,waterMarkView)
    }

    private fun addDrawableWaterMark() {

        var bgColor = Color.TRANSPARENT
        var textSize = 60f
        var textColor = Color.parseColor("#80E6E6E6")
        var hSpace = 100f
        var vSpace = 100f
        var degree = -45f

        var waterMarkDrawable = WaterMarkDrawable()
        waterMarkDrawable.setParams("423099",textSize,textColor,degree,hSpace,vSpace,bgColor)

        WaterMarkUtil.addWaterMark(this,waterMarkDrawable)
    }
}