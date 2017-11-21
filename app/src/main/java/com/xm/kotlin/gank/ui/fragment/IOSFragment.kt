package com.xm.kotlin.gank.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.api.GankApi
import com.xm.kotlin.gank.common.Constants
import com.xm.kotlin.gank.data.GoodsItem
import com.xm.kotlin.gank.listener.GoodsItemClickListener
import com.xm.kotlin.gank.ui.activity.DetailActivity
import com.xm.kotlin.gank.ui.adapter.AndroidIOSAdapter
import com.xm.kotlin.gank.utils.IOSResultToItems
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wangtao on 2017/11/16.
 */
class IOSFragment : Fragment() {

    @BindView(R.id.recyclerView)
    lateinit var mRecycleView: RecyclerView

    private var mPage = 1

    private var mData = ArrayList<GoodsItem>()

    private var mAdapter: AndroidIOSAdapter? = null

    companion object {
        val IOS = "IOS"
        fun newInstance(): IOSFragment {

            val fragment = IOSFragment()
            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.recycle_view, container, false)

        ButterKnife.bind(this, view)

        mRecycleView.layoutManager = LinearLayoutManager(context)


        mRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView?.canScrollVertically(1)!!) {
                    loadData(++mPage)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        mAdapter = AndroidIOSAdapter(context, mData,object : GoodsItemClickListener {

            override fun itemClick(view: View, position: Int) {
                val intent = Intent(context, DetailActivity::class.java)

                intent.putExtra(Constants.DETAIL_UTL, mData.get(position).contentUrl)
                context.startActivity(intent)
            }

        })

        mRecycleView.adapter = mAdapter

        loadData(mPage)


        return view
    }

    private fun loadData(page: Int) {
        val gankApi = GankApi.createContentApi()

        if (gankApi != null) {
            gankApi.getIOS(page)
                    .map(IOSResultToItems.instance)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->

                        setData(result)

                    }, { e ->
                        e.message
                    })

        }
    }

    private fun setData(result: MutableList<GoodsItem>) {
        mData.addAll(result)
        mAdapter?.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}