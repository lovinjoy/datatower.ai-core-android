package com.roiquery.demo

import android.app.Application

import com.roiquery.analytics.ROIQuery


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * 初始化,唯一入口
         *
         * @param context 上下文
         * @param appId  应用id，后台分配，具体
         * @param channel 渠道，可用 ROIQueryChannel.GP, 默认为空 ；如果没有推广渠道，可不传（如果有渠道推广，具体请联系技术对接）
         * @param isDebug 是否打开调试，调试模式下将打印log,默认关闭
         * @param logLevel log 的级别，默认为 LogUtils.V，仅在 isDebug = true 有效
         */
        ROIQuery.initSDK(this, "rq_74f0a427d1e068bf",true)

//        ROIQuery.initSDK(this, "appId", ROIQueryChannel.GP, true)


    }
}