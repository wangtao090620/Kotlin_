package com.xm.kotlin.gank.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.text.method.ArrowKeyMovementMethod
import android.util.DisplayMetrics
import android.view.View
import android.view.View.MeasureSpec
import android.view.WindowManager
import android.widget.TextView
import android.widget.TextView.BufferType

@SuppressLint("StaticFieldLeak")
object UIUtil {

    internal val TAG = "UIUtil"

    internal var sContext: Context? = null

    val displayWidth: Int
        get() = getDisplayWidth(sContext)

    val displayHeight: Int
        get() = getDisplayHeight(sContext)

    val screenWidth: Int
        get() = getScreenWidth(sContext)

    val screenHeight: Int
        get() = getScreenHeight(sContext)

    fun init(ctx: Context) {
        sContext = ctx
    }

    fun getDisplayWidth(ctx: Context?): Int {
        val wm = ctx!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun getDisplayHeight(ctx: Context?): Int {
        val wm = ctx!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }

    fun getScreenWidth(ctx: Context?): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val wm = ctx!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getRealMetrics(dm)
            return dm.widthPixels
        } else {
            return getDisplayWidth(ctx)
        }
    }

    fun getScreenHeight(ctx: Context?): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val wm = ctx!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getRealMetrics(dm)
            return dm.heightPixels
        } else {
            return getDisplayHeight(ctx)
        }
    }

    fun getStatusBarHeight(ctx: Context): Int {
        var result = 0
        try {
            val resourceId = ctx.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = ctx.resources.getDimensionPixelSize(resourceId)
            }
        } catch (e: Exception) {
        }

        return result
    }

    fun dip2px(ctx: Context?, dip: Float): Int {
        val scale = ctx!!.resources.displayMetrics.density
        return (dip * scale + 0.5f).toInt()
    }

    fun dip2px(dip: Float): Int {
        return dip2px(sContext, dip)
    }

    fun px2dip(ctx: Context?, px: Float): Int {
        val scale = ctx!!.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun px2dip(px: Float): Int {
        return px2dip(sContext, px)
    }

    fun px2sp(context: Context?, pxValue: Float): Int {
        val fontScale = context!!.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun sp2px(context: Context?, spValue: Float): Int {
        val fontScale = context!!.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float): Int {
        return px2sp(sContext, pxValue)
    }

    fun sp2px(spValue: Float): Int {
        return sp2px(sContext, spValue)
    }

    fun convertViewToBitmap(view: View, bitmapWidth: Int, bitmapHeight: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
        view.draw(Canvas(bitmap))

        return bitmap
    }

    fun convertViewToBitmap(view: View): Bitmap {
        view.buildDrawingCache()
        return view.drawingCache
    }

    fun convertViewToBitmapWithMeasure(view: View): Bitmap {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.buildDrawingCache()

        return view.drawingCache
    }

    fun overridePendingTransition(activity: Context, animEnterId: Int, animExitId: Int) {
        (activity as? Activity)?.overridePendingTransition(animEnterId, animExitId)
    }

    @SuppressLint("NewApi", "ObsoleteSdkInt")
    fun setTextViewSelectable(tv: TextView, enable: Boolean) {
        if (Build.VERSION.SDK_INT >= 11) {
            tv.setTextIsSelectable(enable)
        } else {
            tv.isFocusableInTouchMode = true
            tv.isFocusable = true
            tv.isClickable = true
            tv.isLongClickable = true
            tv.movementMethod = ArrowKeyMovementMethod.getInstance()
            tv.setText(tv.text, BufferType.SPANNABLE)
        }
    }
}
