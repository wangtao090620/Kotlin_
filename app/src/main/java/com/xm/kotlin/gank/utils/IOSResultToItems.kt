package com.xm.kotlin.gank.utils

import android.annotation.SuppressLint
import com.xm.kotlin.gank.data.GoodsData
import com.xm.kotlin.gank.data.GoodsItem
import com.xm.kotlin.gank.data.GoodsResult
import io.reactivex.functions.Function
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by wangtao on 2017/11/15.
 */
class IOSResultToItems private constructor() : Function<GoodsResult<List<GoodsData>>, MutableList<GoodsItem>> {

    companion object {

        val instance: IOSResultToItems = IOSResultToItems()

    }

    @SuppressLint("SimpleDateFormat")
    override fun apply(t: GoodsResult<List<GoodsData>>): MutableList<GoodsItem> {

        val list = t.results

        val items = arrayListOf<GoodsItem>()

        val urls = ArrayList<String>()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

        val outputFormat = SimpleDateFormat("yy/MM/dd HH:mm:ss")

        if (list != null) {
            for (data in list) {
                val item = GoodsItem()
                try {
                    val date = inputFormat.parse(data.createdAt)
                    item.time = outputFormat.format(date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                if (data.images == null) {
                    item.hasImage = false
                } else {
                    item.hasImage = true
                    item.url = data.images[0]
                }
                item.contentUrl = data.url
                item.who = data.who
                item.description = data.desc

                if (!urls.contains(data.url)) {
                    urls.add(data.url)
                    items.add(item)
                }
            }
        }
        urls.clear()
        return items
    }
}