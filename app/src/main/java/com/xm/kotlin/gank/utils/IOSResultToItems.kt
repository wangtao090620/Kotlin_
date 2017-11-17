package com.xm.kotlin.gank.utils

import com.xm.kotlin.gank.data.ios.IOSData
import com.xm.kotlin.gank.data.ios.IOSItem
import com.xm.kotlin.gank.data.ios.IOSResult
import io.reactivex.functions.Function
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by wangtao on 2017/11/15.
 */
class IOSResultToItems private constructor() : Function<IOSResult<List<IOSData>>, MutableList<IOSItem>> {

    companion object {

        val instance: IOSResultToItems = IOSResultToItems()

    }

    override fun apply(t: IOSResult<List<IOSData>>): MutableList<IOSItem> {

        val gankIOS = t.results

        val items = arrayListOf<IOSItem>()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

        val outputFormat = SimpleDateFormat("yy/MM/dd HH:mm:ss")

        for (iosData in gankIOS) {
            val item = IOSItem()
            try {
                val date = inputFormat.parse(iosData.createdAt)
                item.time = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            if (iosData.images == null) {
                item.hasImage = false
            } else {
                item.hasImage = true
                item.url = iosData.images[0]
            }
            item.contentUrl = iosData.url
            item.who = iosData.who
            item.description = iosData.desc
            items.add(item)
        }

        return items
    }
}