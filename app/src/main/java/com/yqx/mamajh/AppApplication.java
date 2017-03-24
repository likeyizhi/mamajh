package com.yqx.mamajh;

import android.app.Application;
import android.app.Service;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Vibrator;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.github.obsessive.library.base.BaseAppManager;
import com.github.obsessive.library.utils.DensityUtils;
import com.github.obsessive.library.utils.TLog;
import com.github.obsessive.library.utils.Utils;
import com.umeng.analytics.MobclickAgent;
import com.yqx.mamajh.bean.MemeberIndex;
import com.yqx.mamajh.location.LocationService;
import com.yqx.mamajh.utils.AppConstant;
import com.yqx.mamajh.utils.SPUtils;

import org.xutils.x;

import java.io.File;

/**
 * Created by young on 2016/11/06.
 */
public class AppApplication extends Application {

    public static  int            screenwidth;
    public static  int            screenheight;
    public static  boolean        activity_on;
    private static AppApplication instance;
    public         boolean        is_login;
    private        String         versionName;

    public static String TOKEN = null;
    public static MemeberIndex memeberIndex;

    public LocationService locationService;
    public Vibrator mVibrator;

    /**
     * @return Application 实例
     */
    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();x.Ext.init(this);

        TLog.i("Application", "App onCreate");
        instance = this;
        Utils.init(instance);

        SPUtils.init(getString(R.string.app_name));
        TypefaceProvider.registerDefaultIconSets(); //AndroidBootstrap Typeface 初始化

        versionName = getVersionName(this);

        /***
         * 初始化定位sdk
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

        MobclickAgent.setDebugMode(true);
//        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.openActivityDurationTrack(false);

//        JPushInterface.setDebugMode(TLog.isLogEnable); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush

//        boolean push = (boolean) SaveSerializable.getInstance(this).get(Constants.PUSH, true);
//        if(!push){
//            JPushInterface.stopPush(this);
//        }

        screenwidth = DensityUtils.getDisplayWidth(this);
        screenheight = DensityUtils.getDisplayHeight(this);

        String token = SPUtils.getString(getApplicationContext(), AppConstant.SP_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            TOKEN = token;
        }
    }


    private String getVersionName(AppApplication context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
//        MobclickAgent.onKillProcess(this);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public String getVersionName() {
        return versionName;
    }

    public String getToken() {
        return "97ef896b5f4d4519b52379a65b272d21";
    }

    /**
     * 图片缓存文件夹路径
     *
     * @return
     */
    public String getImgCacheFolder() {
        String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File   destDir    = new File(storageDir + "/" + getPackageName() + "/ImgCache/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return storageDir + "/" + getPackageName() + "/ImgCache/";
    }
}
