package com.xm.kotlin.gank.utils

import com.xm.kotlin.gank.data.girl.GirlData
import com.xm.kotlin.gank.data.girl.GirlItem
import com.xm.kotlin.gank.data.girl.GirlResults
import io.reactivex.functions.Function
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by wangtao on 2017/11/15.
 */
class GirlsResultToItems private constructor() : Function<GirlResults<List<GirlData>>, MutableList<GirlItem>> {

    companion object {

        val instance: GirlsResultToItems = GirlsResultToItems()

    }

    override fun apply(t: GirlResults<List<GirlData>>): MutableList<GirlItem> {

        println("apply haha "+t)

        val gankBeauties = t.results

        val items = arrayListOf<GirlItem>()

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")

        val outputFormat = SimpleDateFormat("yy/MM/dd HH:mm:ss")

        if (gankBeauties != null) {
            for (gankBeauty in gankBeauties) {
                val item = GirlItem()
                try {
                    val date = inputFormat.parse(gankBeauty.createdAt)
                    item.description = outputFormat.format(date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    item.description = "unknown date"
                }
                item.imageUrl = gankBeauty.url
                items.add(item)
            }
        }
        return items
    }
}