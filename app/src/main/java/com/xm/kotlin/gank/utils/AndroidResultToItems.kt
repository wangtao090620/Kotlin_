package com.xm.kotlin.gank.utils

import com.xm.kotlin.gank.data.android.AndroidData
import com.xm.kotlin.gank.data.android.AndroidItem
import com.xm.kotlin.gank.data.android.AndroidResult
import io.reactivex.functions.Function
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by wangtao on 2017/11/15.
 */
class AndroidResultToItems private constructor() : Function<AndroidResult<List<AndroidData>>, MutableList<AndroidItem>> {

    companion object {

        val instance: AndroidResultToItems = AndroidResultToItems()

    }

    override fun apply(t: AndroidResult<List<AndroidData>>): MutableList<AndroidItem> {

        val gankAndroid = t.results

        val items = arrayListOf<AndroidItem>()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

        val outputFormat = SimpleDateFormat("yy/MM/dd HH:mm:ss")

        for (androidData in gankAndroid) {
            val item = AndroidItem()
            try {
                val date = inputFormat.parse(androidData.createdAt)
                item.time = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            if(androidData.images==null){
                item.hasImage = false
            }else{
                item.hasImage = true
                item.url = androidData.images[0]
            }
            item.who = androidData.who
            item.description = androidData.desc
            item.contentUrl = androidData.url
            items.add(item)
        }

        return items
    }
}