package com.huiketong.guanjiatong;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.videogo.openapi.EZGlobalSDK;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EzvizAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainApplication extends Application {
    //是否是调试模式
    private boolean isDebug = true;
    public static MainApplication mainApplication;

    public static String AppKey = "9e53b2561f314f00ba68b5d58d66e4c0";
    public static String API_URL;
    public static String WEB_URL;
    public static String APP_PUSH_SECRETE;
    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //bugly start
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "ccced40fbb", isDebug, strategy);
        // bugly end

        // logger
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        mainApplication = this;
        initSDK();
        EzvizAPI.getInstance().setServerUrl(API_URL, WEB_URL);
        /**
         * 如果需要推送服务，需要再初始化EZOpenSDK后，调用以下方法初始化推送服务
         * APP_PUSH_SECRETE 推送服务secret_key需要单独申请
         */
        //{
        //  getOpenSDK().initPushService(this.getApplicationContext(), APP_PUSH_SECRETE, pushServerListener);
        //}
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void initSDK() {
        /**********国内版本初始化EZOpenSDK**************/
        {
            initParam();
            /**
             * sdk日志开关，正式发布需要去掉
             */
            EZOpenSDK.showSDKLog(true);

            /**
             * 设置是否支持P2P取流,详见api
             */
            EZOpenSDK.enableP2P(true);

            /**
             * APP_KEY请替换成自己申请的
             */
            EZOpenSDK.initLib(this, AppKey);


            //EZOpenSDK.initLib(this, "fecf30f4a8754e8bbe4fbcfab2011802");
            //
            //EZOpenSDK.getInstance().setAccessToken("at.8x4g5b7v1s96dnmd0gs2ulf77ap2o8c1-2kpax44vvt-1r3swy6-qc54sro0d");
        }

    }

    private void initParam() {
        //线上测试推送
        AppKey = "26810f3acd794862b608b6cfbc32a6b8";
        API_URL = "https://open.ys7.com";
        WEB_URL = "https://auth.ys7.com";
        APP_PUSH_SECRETE = "a88256b1-65d4-4cb3-9ac4-8b08a56ad07d";

        //AppKey = "81372ec4f0a4483ea39620c88512ea9c";

        //线上测试推送
//        AppKey = "252217943f7d462e9c28507b85903068";
//        API_URL = "https://open.ys7.com";
//        WEB_URL = "https://auth.ys7.com";
//        APP_PUSH_SECRETE = "a88256b1-65d4-4cb3-9ac4-8b08a56ad07d";

        //pb平台
//        AppKey = "bd9731ed82d6413daeecd9841c54a1cc";
//        APP_PUSH_SECRETE = "a88256b1-65d4-4cb3-9ac4-8b08a56ad07d";
//        API_URL = "https://pbopen.ys7.com";
//        WEB_URL = "https://pbauth.ys7.com";


        // pb
        //AppKey = "7cec7c4f5bcf4bfd92bd020dffc632da";
        //API_URL = "https://pbopen.ys7.com";
        //WEB_URL = "https://pbauth.ys7.com";
        //APP_PUSH_SECRETE = "464b677e-a0ef-4d48-916d-4ceab81a986e";

        //test2
//      	AppKey = "bd2c26fe7450441ea962161fa6eacb79";
//        API_URL = "https://test2.ys7.com:9000";
//        WEB_URL = "https://test2auth.ys7.com:8643";
//        APP_PUSH_SECRETE = "4e71ab44-f9ad-4c6e-9f6a-031f11a5b12d";

        //test
        //AppKey = "ae1b9af9dcac4caeb88da6dbbf2dd8d5";
        //API_URL = "http://test.ys7.com:66";
        //WEB_URL = "https://testopenauth.ys7.com:8447";
        ////WEB_URL = "https://test.ys7.com:8447";
        ////WEB_URL = "http://testopenauth.ys7.com:8081";
        //APP_PUSH_SECRETE = "4282d71e351f0083b0c61efcd5091ba5";
        //https://testopenauth.ys7.com:8447


        //贺州电信
//        AppKey = "f8386cd63a494fcd89c5a5b640e1737a";

        //海康测试
//        AppKey = "15b06a07a2e04a3193b1eb6daa30231c";

//        AppKey = "00d317f1511e41f985f2c34b9c057e00";
//        API_URL = "https://test.ys7.com:65";
//        WEB_URL = "http://testopenauth.ys7.com:8080";
        //AppKey="8a305c11dde84260ad4cccabd5fc6b84";
    }

}
