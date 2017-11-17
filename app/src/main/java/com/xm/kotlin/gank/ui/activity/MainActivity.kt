package com.xm.kotlin.gank.ui.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.common.Constants
import com.xm.kotlin.gank.ui.fragment.AndroidFragment
import com.xm.kotlin.gank.ui.fragment.GirlFragment
import com.xm.kotlin.gank.ui.fragment.IOSFragment
import com.xm.kotlin.gank.utils.UIUtil


class MainActivity : AppCompatActivity() {

    @BindView(R.id.tabs)
    lateinit var mTabLayout: TabLayout

    @BindView(R.id.viewPager)

    lateinit var mViewPage: ViewPager

    lateinit var mFragments: MutableList<Fragment>

    private lateinit var mMenuPopup: PopupWindow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        init()

    }


    private fun init() {

        initFragment()

        mViewPage.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: kotlin.Int) = mFragments[position]
            override fun getCount() = mFragments.size

            override fun getPageTitle(position: Int): CharSequence {

                var title = ""

                when (position) {
                    0 -> title = GirlFragment.GIRL
                    1 -> title = AndroidFragment.Android
                    2 -> title = IOSFragment.IOS
                }

                return title
            }
        }

        mTabLayout.setupWithViewPager(mViewPage)

        initMenu()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments.add(GirlFragment.newInstance())
        mFragments.add(AndroidFragment.newInstance())
        mFragments.add(IOSFragment.newInstance())

    }

    @OnClick(R.id.img_menu)

    fun click(v: View) {
        clickMenu(v)

    }

    private fun onClickLikeGithub() {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.DETAIL_UTL, "https://github.com/wangtao090620")

        startActivity(intent)

    }

    private fun dismissMenuPopup() {

        if (!mMenuPopup.isShowing)
            return

        mMenuPopup.dismiss()

    }

    private fun onClickSetting() {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.DETAIL_UTL, "https://xmblog.cc/")

        startActivity(intent)
    }

    private fun clickMenu(v: View) {
        if (mMenuPopup.isShowing) {
            mMenuPopup.dismiss()
        } else {
            try {
                val location = IntArray(2)
                v.getLocationOnScreen(location)
                mMenuPopup.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1])
            } catch (e: Exception) {
                e.message
            }

        }
    }

    fun initMenu() {

        val popupView = layoutInflater.inflate(R.layout.layout_menu, null)

        popupView.findViewById<LinearLayout>(R.id.layout_setting).setOnClickListener {
            onClickSetting()
            dismissMenuPopup()
        }

        popupView.findViewById<LinearLayout>(R.id.layout_like).setOnClickListener {
            onClickLikeGithub()
            dismissMenuPopup()
        }

        popupView.setOnClickListener { dismissMenuPopup() }

        mMenuPopup = PopupWindow(popupView, UIUtil.getDisplayWidth(this), UIUtil.getDisplayHeight(this))

        mMenuPopup.isFocusable = true

        mMenuPopup.isOutsideTouchable = true

        val dw = ColorDrawable(0x00000000)

        mMenuPopup.setBackgroundDrawable(dw)


    }


}
