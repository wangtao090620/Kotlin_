package com.xm.kotlin.gank.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.common.Constants
import com.xm.kotlin.gank.data.ios.IOSItem
import com.xm.kotlin.gank.item.GoodsItemClickListener
import com.xm.kotlin.gank.ui.activity.DetailActivity

/**
 * Created by wangtao on 2017/11/15.
 */
class IOSAdapter(val context: Context, val data: ArrayList<IOSItem>, private val itemClickListener: GoodsItemClickListener) : RecyclerView.Adapter<IOSAdapter.IOSHolder>(), AdapterView.OnItemClickListener {

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val intent = Intent(context, DetailActivity::class.java)

        intent.putExtra(Constants.DETAIL_UTL, data.get(position).contentUrl)
        context.startActivity(intent)
    }

    override fun onBindViewHolder(holder: IOSHolder?, position: Int) {

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


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IOSHolder? {

        val view = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false)

        return IOSHolder(view, itemClickListener)
    }

    override fun getItemCount(): Int {

        return data.size
    }


    class IOSHolder(itemView: View, private val itemClickListener: GoodsItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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