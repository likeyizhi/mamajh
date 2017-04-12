#项目说明
更新日期 2017-03-08

###开发环境
Windows/Mac<br/>
IDE：Android Studio

###目录说明
所有文件的命名规则都顾名思义。不知道？拿字典查
>###root
>>####app\src\main\java
>>>#####activity：
* 每个Activity上都有注释说明，没有的话说明太简单，看名知意
* 所有Activity需继承[com.yqx.mamajh.base.BaseActivity](https://git.oschina.net/DragonsQC/Mamajh/blob/master/app/src/main/java/com/yqx/mamajh/base/BaseActivity.java)并实现方法，去掉原有onCreate方法<br/>
* 将LayoutId放入getContentViewLayoutID实现的方法中，例：
```
@Override
protected int getContentViewLayoutID() {
    return R.layout.activity_classify;
}
```
* getBundleExtras 方法可以获取bundle数据
* initViewsAndEvents 所有初始化视图和事件添加都在这里添加
* 更多方法说明点进类中方法有注释说明，都是顾名思义的命名方法，不在多余复述
* 其中BaseActivity还封装了startActivity方法，可以直接调用readyGo方法，传入对应参数，可实现各种startActivity。

>>>#####adapter
* 各种适配器

>>>#####base
* 各种基础类

>>>#####bean
* 各种实体类

>>>#####dbcity
* 城市选择相关类，用于地址选择

>>>#####fragment
* Fragment 用法和 Activity 差不多，使用时继承[com.yqx.mamajh.base.BaseFragment](https://git.oschina.net/DragonsQC/Mamajh/blob/master/app/src/main/java/com/yqx/mamajh/base/BaseFragment.java)，方法也和BaseActivity相同

>>>#####interactor

>>>#####interfaces

>>>#####listener

>>>#####location
* 百度定位

>>>######network
* 网络接口，使用Retrofit2进行网络数据的传输，使用教程：各种搜索引擎“Retrofit2”

>>>######Presenter

>>>#####services

>>>#####update

>>>#####utils
* 各种工具类，看名知意

>>>#####view

>>>#####widget
* 相关控件，看名知意

>>>#####wxapi
* 微信支付相关

>>####app\src\main\res
* Android资源文件

>>####library\src\main\java
* 自定义控件和第三方控件工具类<br/>
若需要添加第三方依赖或工具库请放置library/build.gradle，方便后期维护，而app/build.gradle主要用于项目编译相关配置
