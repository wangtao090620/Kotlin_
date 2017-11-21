package com.xm.kotlin.gank.data

/**
 * Created by wangtao on 2017/11/20.
 */
class GoodsResult<out T>(val error: Boolean, val results: T) {
}