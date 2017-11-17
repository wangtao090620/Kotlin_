package com.xm.kotlin.gank.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.xm.kotlin.gank.R
import com.xm.kotlin.gank.common.Constants
import com.xm.kotlin.gank.utils.NetworkUtil

class DetailActivity : AppCompatActivity() {

    @BindView(R.id.webview)
    lateinit var mWebView: WebView

    @BindView(R.id.progressBar)
    lateinit var mWebProgressBar: ProgressBar

    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
        openSearchWebpage()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openSearchWebpage() {

        mWebProgressBar.max = 100

        val intent = intent

        mUrl = intent.getStringExtra(Constants.DETAIL_UTL)


        val webSettings = mWebView.settings

        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.setSupportMultipleWindows(true)
        //        webSettings.setUseWideViewPort(true);
        //        webSettings.setDefaultTextEncodingName("utf-8");
        //        webSettings.setLoadsImagesAutomatically(true);
        //        webSettings.setDisplayZoomControls(true);
        if (NetworkUtil.isNetworkUseful(this)) {
            webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        //        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {

            }
        }

        val webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    mWebProgressBar.visibility = View.GONE
                } else {
                    if (mWebProgressBar.visibility == View.GONE)
                        mWebProgressBar.visibility = View.VISIBLE
                    mWebProgressBar.progress = newProgress
                }
            }
        }

        mWebView.webChromeClient = webChromeClient

        mWebView.loadUrl(mUrl)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()

        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mWebView.clearHistory()

        (mWebView.parent as ViewGroup).removeView(mWebView)
        mWebView.destroy()

    }

}
