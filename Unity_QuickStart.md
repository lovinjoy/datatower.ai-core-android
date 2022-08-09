# 注册应用

### 开通账号

LovinJoy给您开通产品服务后，会提供一个超级管理员账号，登录https://datatower.ai/ 可以创建项目

### 创建项目

登录后，进入**项目设置**

![image-image-project-setting](https://github.com/lovinjoy/datatower.ai-core-android/blob/main/resurce/image-project-setting.png)

切换到**系统设置**，点击**创建项目**

![image-create-project](https://github.com/lovinjoy/datatower.ai-core-android/blob/main/resurce/image-create-project.png)

输入项目名称，如：simple_android

![image-setting-name](https://github.com/lovinjoy/datatower.ai-core-android/blob/main/resurce/image-setting-name.png)

创建成功后可以看到APP_ID

![image-appid-view](https://github.com/lovinjoy/datatower.ai-core-android/blob/main/resurce/image-appid-view.png)

记下APP_ID，初始化SDK会用到

### 

# 初始化SDK

### 集成

1.下载 Unity SDK 资源包 

2.将上述文件通过 Assets → Import Package → Custom Package 添加到项目中

3.将 ROIQuerySDK/ROIQuerySDK/ 目录下的 ROIQuerySDK.prefab 预制体拖到需要加载的位置(一般放在第一个场景)

 ![image-appid-view](https://github.com/lovinjoy/datatower.ai-core-android/blob/main/resurce/unity_1.png)
 
4.配置SDK

 ![image-appid-view](https://github.com/lovinjoy/datatower.ai-core-android/blob/main/resurce/unity_2.png)
 
- App Id：项目Id，即上一步申请到的 APP_ID，必须填
- Channel：渠道名称，打多渠道包时需要用到，如gp、app_store，默认为“”，可不填
- Is Debug：是否打开调试，调试模式下将打印log, 默认为false
- Log Level：log 的级别，默认为 LogUtils.V，仅在 isDebug = true 有效
	


### 初始化

- 拖入场景自动初始化


# 自定义事件

调用track()方法来自定义一个事件，并自动上报

```c#
Dictionary<string, object> properties = Dictionary<string, object>();
properties.Add("test_property_3", false);
properties.Add("test_property_4", 2.3);

ROIQueryAnalytics.Track("test_track", properties);
// or 不传属性
ROIQueryAnalytics.Track("test_track");
```



# 广告事件（可选）

SDK  内置了一些有关广告相关的行为事件，可供开发者在收到广告SDK回调时，通过调用相关的接口进行上报，用于分析广告相关的信息

### 独立广告平台

插页广告的过程作为演示

```c#
 /*
 * 一次展示Admob插页广告的过程
 */
 
//广告位，比如这页面是主页
string location = "main"
 
//Admob 广告单元
string adUnit = "ca-app-pub-3940256099942544/1033173712"
 
//整个过程的行为系列标识
string seq = ROIQueryAdReport.GenerateUUID()
 
//1.Admob 开始加载广告
AdManager.loadInterstitialAd(this, adUnit); 

//2.即将展示广告
ROIQueryAdReport.ReportToShow(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
//3.展示广告
AdManager.showInterstitialAd(object: OnAdShowCallback(){
   	override void onAdShowed(){
        	//4. 广告展示成功
   		ROIQueryAdReport.ReportShow(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location,seq)
    	}
   	override void onAdFailedToShow(adError: AdError){
      		//5. 广告展示失败
    		ROIQueryAdReport.ReportShowFailed(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq, adError.code, adError.msg)
    	}
   	override void onAdClicked(){
        	//6. 广告被点击
     		ROIQueryAdReport.ReportClick(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
      		ROIQueryAdReport.ReportConversionByClick(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
    	}
   		
   	override void onAdClosed(){
        	//7. 广告关闭
     		ROIQueryAdReport.ReportClose(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
    	}
	
	
	override void onAdRewared(){
		//对于激励广告，会有获得激励回调
    		 ROIQueryAdReport.ReportRewared(adUnit, AdType.REWARDED, AdPlatform.ADMOB, location, seq)
    		 ROIQueryAdReport.ReportConversionByRewared(adUnit, AdType.REWARDED, AdPlatform.ADMOB, location, seq)
	}
   
   	override void onAdRevenuePaid(AdInfo ad){
		//8. 广告用户层级展示数据
        	string value = ad.getValue() //广告的价值
        	string currency = ad.getCurrency() //货币
        	string precision = ad.getPrecision() // 精确度
        	ROIQueryAdReport.ReportPaid(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq, value, currency, precision)
      }
 })
    

```





### 聚合广告平台

由于聚合广告平台展示广告的时候，没有返回具体是哪个广告平台的广告，所以需要在回调中判断，如onAdShowed回调

```c#
//聚合广告平台一般会返回广告相关的信息 AdInfo
override void onAdShowed(AdInfo ad){
  	//需自行实现 getAdPlatform() 、getAdUnit()方法
  	AdPlatform adPlatform = getAdPlatform(ad)
  	string adUnit = getAdUnit(ad)
        //4. 广告展示成功
   	ROIQueryAdReport.ReportShow(adUnit, AdType.INTERSTITIAL,adPlatform,location, seq)
      }
```

其他回调类似
