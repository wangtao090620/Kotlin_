package com.xm.kotlin.gank.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.common.Constants

class GirlDetailActivity : AppCompatActivity() {

    @BindView(R.id.iv_image)
    lateinit var mGirlImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl_detail)

        ButterKnife.bind(this)
        val url = intent.getStringExtra(Constants.DETAIL_UTL)
        Glide.with(this).load(url).into(mGirlImageView)
    }
}
