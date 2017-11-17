package com.xm.kotlin.gank.utils

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager

object NetworkUtil {

    fun getNetworkType(ctx: Context): Int {
        try {
            val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            return info.type
        } catch (e: Exception) {
            return ConnectivityManager.TYPE_WIFI
        }

    }

    fun isNetworkUseful(ctx: Context): Boolean {
        val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = mgr.activeNetworkInfo
        return info != null && info.isAvailable
    }

    fun isMobileNetwork(ctx: Context): Boolean {
        return isNetworkUseful(ctx) && getNetworkType(ctx) != ConnectivityManager.TYPE_WIFI
    }

    fun isNetworkActive(ctx: Context): Boolean {
        try {
            val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            return info != null
        } catch (e: Exception) {
            return false
        }

    }

    fun isWifiActive(ctx: Context): Boolean {
        try {
            val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            return if (info != null) info.type == ConnectivityManager.TYPE_WIFI else false
        } catch (e: Exception) {
            return false
        }

    }

    fun is4GActive(ctx: Context): Boolean {
        try {
            val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            return if (info == null || info.type != ConnectivityManager.TYPE_MOBILE) false else info.subtype == TelephonyManager.NETWORK_TYPE_LTE

        } catch (e: Exception) {
            return false
        }

    }

    fun getActiveMobileNetworkClass(context: Context): String {
        try {
            val mgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            if (info == null || info.type != ConnectivityManager.TYPE_MOBILE)
                return ""

            when (info.subtype) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> return "2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> return "3G"
                TelephonyManager.NETWORK_TYPE_LTE -> return "4G"
                else -> return "Unknown"
            }
        } catch (e: Exception) {
            return "Unknown"
        }

    }

    fun checkNetAvailable(ctx: Context): Boolean {
        try {
            val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            return info != null
        } catch (e: Exception) {
            return true
        }

    }


    fun getActiveNetworkName(ctx: Context): String? {
        try {
            val mgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.activeNetworkInfo
            return info?.typeName
        } catch (e: Exception) {
            return null
        }

    }

}
