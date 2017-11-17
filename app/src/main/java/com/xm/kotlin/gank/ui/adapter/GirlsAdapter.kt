package com.xm.kotlin.gank.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.common.Constants
import com.xm.kotlin.gank.data.girl.GirlItem
import com.xm.kotlin.gank.item.GoodsItemClickListener
import com.xm.kotlin.gank.ui.activity.GirlDetailActivity

/**
 * Created by wangtao on 2017/11/15.
 */
class GirlsAdapter(val context: Context, val data: MutableList<GirlItem>, private val itemClickListener: GoodsItemClickListener) : RecyclerView.Adapter<GirlsAdapter.GirlViewHolder>(), AdapterView.OnItemClickListener {

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val intent = Intent(context, GirlDetailActivity::class.java)
        intent.putExtra(Constants.DETAIL_UTL, data.get(position).imageUrl)
        context.startActivity(intent)
    }


    override fun onBindViewHolder(holder: GirlViewHolder?, position: Int) {


        Glide.with(context).load(data.get(position).imageUrl).into(holder?.mImageView)

    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GirlViewHolder? {

        val view = LayoutInflater.from(context).inflate(R.layout.item_girl, parent, false)

        return GirlViewHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int {

        return data.size
    }


    class GirlViewHolder(itemView: View, private val itemClickListener: GoodsItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View) {
            itemClickListener.itemClick(v, adapterPosition)
        }

        @BindView(R.id.iv_girl)

        lateinit var mImageView: ImageView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener(this)
        }


    }

}