package com.xm.kotlin.gank.data.girl

/**
 * Created by wangtao on 2017/11/15.
 */

data class GirlData(
        val _id: String,
        val createdAt: String,
        val desc: String,
        val images: Array<String>,
        val publishedAt: String,
        val source: String,
        val type: String,
        val url: String,
        val used: Boolean,
        val who: String
)
