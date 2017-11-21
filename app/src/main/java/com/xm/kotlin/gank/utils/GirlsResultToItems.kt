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
class GirlsResultToItems private constructor() : Function<GoodsResult<List<GoodsData>>, MutableList<GoodsItem>> {

    companion object {

        val instance: GirlsResultToItems = GirlsResultToItems()

    }

    @SuppressLint("SimpleDateFormat")
    override fun apply(t: GoodsResult<List<GoodsData>>): MutableList<GoodsItem> {

        val list = t.results

        val items = ArrayList<GoodsItem>()

        val urls = ArrayList<String>()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

        val outputFormat = SimpleDateFormat("yy/MM/dd HH:mm:ss")

        if (list != null) {
            for (gankBeauty in list) {
                val item = GoodsItem()
                try {
                    val date = inputFormat.parse(gankBeauty.createdAt)
                    item.description = outputFormat.format(date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    item.description = "unknown date"
                }
                item.url = gankBeauty.url

                if (!urls.contains(gankBeauty.url)) {
                    items.add(item)
                    urls.add(gankBeauty.url)
                }

            }
        }


        urls.clear()
        return items
    }
}