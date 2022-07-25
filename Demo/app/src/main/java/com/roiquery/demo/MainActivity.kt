package com.roiquery.demo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.roiquery.ad.*

import com.roiquery.analytics.ROIQueryAnalytics


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_report)
        val properties = mutableMapOf<String,Any>()

        properties["sd3"] = "p3"
        properties["sd4"] = "p4"
        ROIQueryAnalytics.track("test_event")
        ROIQueryAnalytics.track("test_event_2", properties)

        val seq = ROIQueryAdReport.generateUUID()

        findViewById<View>(R.id.button_track_to_show).setOnClickListener {
            ROIQueryAnalytics.setKochavaId("sdf23r243r")
            ROIQueryAnalytics.setAppSetId("121388-0329-4")
            val p = mutableMapOf<String,Any>()
            p["sd3"] = "p3"
            p["sd4"] = "p4"
            ROIQueryAdReport.reportToShow(
                "",
                AdType.REWARDED_INTERSTITIAL,
                AdPlatform.ADX,
                "user",
                seq,
                p,
                "main"
            )

        }
        findViewById<View>(R.id.button_track_show).setOnClickListener {
            ROIQueryAdReport.reportShow(
                "",
                AdType.REWARDED_INTERSTITIAL,
                AdPlatform.ADX,
                "car",
                seq
            )
        }

        findViewById<View>(R.id.button_track_show_failed).setOnClickListener {
            ROIQueryAdReport.reportShowFailed(
                "",
                AdType.REWARDED_INTERSTITIAL,
                AdPlatform.ADX,
                "car",
                seq,
                12,
                "network"
            )
        }

        findViewById<View>(R.id.button_track_impression).setOnClickListener {
            ROIQueryAdReport.reportImpression(
                "4",
                AdType.REWARDED_INTERSTITIAL,
                AdPlatform.ADX,
                "home",
                seq
            )
        }
            findViewById<View>(R.id.button_track_click).setOnClickListener {
                ROIQueryAdReport.reportClick(
                    "5",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq
                )

            }

            findViewById<View>(R.id.button_track_close).setOnClickListener {
                ROIQueryAdReport.reportClose(
                    "4",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq
                )
            }



            findViewById<View>(R.id.button_track_paid).setOnClickListener {
                ROIQueryAdReport.reportPaid(
                    "",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq,
                    "5000",
                    "01",
                    "1"
                )
            }

            findViewById<View>(R.id.button_track_conversion_click).setOnClickListener {
                ROIQueryAdReport.reportConversionByClick(
                    "4",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq
                )
            }

            findViewById<View>(R.id.button_track_conversion_left_app).setOnClickListener {
                ROIQueryAdReport.reportConversionByLeftApp(
                    "4",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq
                )
            }

            findViewById<View>(R.id.button_track_conversion_impression).setOnClickListener {
                ROIQueryAdReport.reportConversionByImpression(
                    "4",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq
                )
                ROIQueryAdReport.reportConversionByRewarded(
                    "4",
                    AdType.REWARDED_INTERSTITIAL,
                    AdPlatform.ADX,
                    "home",
                    seq
                )
            }
        }

    }