package com.xm.kotlin.gank.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.data.android.AndroidItem
import com.xm.kotlin.gank.item.GoodsItemClickListener

/**
 * Created by wangtao on 2017/11/15.
 */
class AndroidAdapter(val context: Context, val data: MutableList<AndroidItem>,val itemClickListener: GoodsItemClickListener) : RecyclerView.Adapter<AndroidAdapter.AndroidHolder>(){

    override fun onBindViewHolder(holder: AndroidHolder?, position: Int) {

        if (data.get(position).hasImage!!) {
            holder?.mImageView?.visibility = View.VISIBLE
            Glide.with(context).load(data.get(position).url).into(holder?.mImageView)
        } else {
            holder?.mImageView?.visibility = View.GONE
        }

        holder?.mDescText?.text = data.get(position).description
        holder?.mTimeText?.text = data.get(position).time
        holder?.mWhoText?.text = data.get(position).who ?: "匿名"
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AndroidHolder? {

        val view = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false)


        view.setOnClickListener {

        }
        return AndroidHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int {

        return data.size
    }


    class AndroidHolder(itemView: View, private val itemClickListener: GoodsItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View) {

            itemClickListener.itemClick(v, adapterPosition)

        }

        @BindView(R.id.iv_pic)
        lateinit var mImageView: ImageView

        @BindView(R.id.tv_desc)
        lateinit var mDescText: TextView

        @BindView(R.id.tv_sho)
        lateinit var mWhoText: TextView

        @BindView(R.id.tv_time)
        lateinit var mTimeText: TextView

        init {
            ButterKnife.bind(this, itemView)

            itemView.setOnClickListener(this)
        }


    }

}