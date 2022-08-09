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

在项目根目录下 build.gradle 文件中添加 `mavenCentral`仓库

- 下载 Unity SDK 资源包  （贴入github地址）
- 将上述文件通过 Assets → Import Package → Custom Package 添加到项目中
- 将 ROIQuerySDK/ROIQuerySDK/ 目录下的 ROIQuerySDK.prefab 预制体拖到需要加载的位置(一般放在第一个场景)
 ![image-appid-view](https://internal-api-drive-stream.feishu.cn/space/api/box/stream/download/v2/cover/boxcnxkWKL9pUdE58oHBGARLtIF/?fallback_source=1&height=1280&mount_node_token=doxcn6QkqWAA2cYQ6mOeGRIQZmc&mount_point=docx_image&policy=equal&width=1280)

在项目主工程目录下 build.gradle 文件中添加  SDK 依赖

```groovy
dependencies {
    ...

    implementation 'com.lovinjoy:datatowerai-core:1.2.38'
}
```

### 初始化

在Application初始化SDK

```kotlin
//填入上一步申请到的APP_ID
ROIQuery.initSDK(this, "app id")
```

# 自定义事件

调用track()方法来自定义一个事件，并自动上报

```kotlin
HashMap<String, Object> properties = new HashMap<>();
properties.put("test_property_3", false);
properties.put("test_property_4", 2.3);

ROIQueryAnalytics.track("test_track", properties);
// or 不传属性
ROIQueryAnalytics.track("test_track");
```



# 广告事件（可选）

SDK  内置了一些有关广告相关的行为事件，可供开发者在收到广告SDK回调时，通过调用相关的接口进行上报，用于分析广告相关的信息

### 独立广告平台

插页广告的过程作为演示

```kotlin
 /*
 * 一次展示Admob插页广告的过程
 */
 
//广告位，比如这页面是主页
val location = "main"
 
//Admob 广告单元
val adUnit = "ca-app-pub-3940256099942544/1033173712"
 
//整个过程的行为系列标识
var seq = ROIQueryAdReport.generateUUID()
 
//1.Admob 开始加载广告
AdManager.loadInterstitialAd(this, adUnit); 

//2.即将展示广告
ROIQueryAdReport.reportToShow(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
//3.展示广告
AdManager.showInterstitialAd(object: OnAdShowCallback(){
   	override fun onAdShowed(){
        	//4. 广告展示成功
   		ROIQueryAdReport.reportShow(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location,seq)
    	}
   	override fun onAdFailedToShow(adError: AdError){
      		//5. 广告展示失败
    		ROIQueryAdReport.reportShowFailed(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq, adError.code, adError.msg)
    	}
   	override fun onAdClicked(){
        	//6. 广告被点击
     		ROIQueryAdReport.reportClick(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
      		ROIQueryAdReport.reportConversionByClick(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
    	}
   		
   	override fun onAdClosed(){
        	//7. 广告关闭
     		ROIQueryAdReport.reportClose(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq)
    	}
   
   	override fun onAdRevenuePaid(ad: AdInfo){
		//8. 广告用户层级展示数据
        	val value = ad.getValue() //广告的价值
        	val currency = ad.getCurrency() //货币
        	val precision = ad.getPrecision() // 精确度
        	ROIQueryAdReport.reportPaid(adUnit, AdType.INTERSTITIAL, AdPlatform.ADMOB, location, seq, value, currency, precision)
      }
 })
    

```

对于激励广告，会有获得激励回调

```kotlin
override fun onAdRewared(){
     ROIQueryAdReport.reportRewared(adUnit, AdType.REWARDED, AdPlatform.ADMOB, location, seq)
     ROIQueryAdReport.reportConversionByRewared(adUnit, AdType.REWARDED, AdPlatform.ADMOB, location, seq)
}
```



### 聚合广告平台

由于聚合广告平台展示广告的时候，没有返回具体是哪个广告平台的广告，所以需要在回调中判断，如onAdShowed回调

```kotlin
//聚合广告平台一般会返回广告相关的信息 AdInfo
override fun onAdShowed(ad: AdInfo){
  	//需自行实现 getAdPlatform() 、getAdUnit()方法
  	val adPlatform = getAdPlatform(ad)
  	val adUnit = getAdUnit(ad)
        //4. 广告展示成功
   	ROIQueryAdReport.reportShow(adUnit, AdType.INTERSTITIAL,adPlatform,location, seq)
      }
```

其他回调类似
