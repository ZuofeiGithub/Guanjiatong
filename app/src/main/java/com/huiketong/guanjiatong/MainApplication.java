package com.huiketong.guanjiatong;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.videogo.openapi.EZGlobalSDK;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EzvizAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.Request;

public class MainApplication extends Application {
    //是否是调试模式
    private boolean isDebug = true;
    public static MainApplication mainApplication;
    private final static int TYPE_OPENSDK = 1;
    private final static int TYPE_GLOABL_OPENSDK = 2;

    private final static int TYPE = TYPE_OPENSDK;
    public static String AppKey = "9e53b2561f314f00ba68b5d58d66e4c0";
    public static String ACCESS_TOKEN = null;
    public static String API_URL;
    public static String WEB_URL;
    public static String APP_SECRETE;
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
        if (TYPE == TYPE_GLOABL_OPENSDK) {
            initGlobalSDK();
        } else if (TYPE == TYPE_OPENSDK) {
            initSDK();
        }
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

            HttpUtils.getRequest(UrlUtils.GetToken, null, new HttpCallback() {
                @Override
                public void requestSuccess(String result) throws Exception {
                    ACCESS_TOKEN = result;
                    EZOpenSDK.getInstance().setAccessToken(ACCESS_TOKEN);
                }

                @Override
                public void requestFaild(Request request, IOException io) {

                }

                @Override
                public void complete() {

                }
            });
            //EZOpenSDK.initLib(this, "fecf30f4a8754e8bbe4fbcfab2011802");
            //

        }

    }

    private void initGlobalSDK() {
        /**********海外版本初始化EZGlobalSDK**************/
        {
            initGlobalParam();
            /**
             * sdk日志开关，正式发布需要去掉
             */
            EZGlobalSDK.showSDKLog(true);

            /**
             * 设置是否支持P2P取流,详见api
             */
            EZGlobalSDK.enableP2P(true);
            /**
             * APP_KEY请替换成自己申请的
             */
            EZGlobalSDK.initLib(this, AppKey, "");
        }
    }

    private void initGlobalParam() {
        //海外测试
        AppKey = "f23c52801b504675881c0db0efa456d8";
        APP_SECRETE = "51e4d82aa48fd4af979bf47d8c63af84";
        API_URL = "https://itcnopen.ezvizlife.com:9443";
        WEB_URL = "https://itcnopenauth.ezvizlife.com:9543";
        APP_PUSH_SECRETE = "090f0094-1d46-4862-8017-89a40188ce32";

        //海外正式平台
        AppKey = "6394bd89638111e6876d0cc47a41de4e";
        APP_SECRETE = "a7b31531638111e6876d0cc47a41de4e";
        API_URL = "https://iusopen.ezvizlife.com";
        WEB_URL = "https://iusopenauth.ezvizlife.com";
        APP_PUSH_SECRETE = "311979bb-6d89-490c-b89b-ca7669ab8fba";

        // 北美海外正式平台
        //    AppKey = "8c2f7387ad7a11e6876d0cc47a41de4e";
        //    AppKey = "bd8f6f049ca049c0ad32776e7f745180";
        //    APP_SECRETE = "a7b31531638111e6876d0cc47a41de4e";
        //    API_URL = "https://iusopen.ezvizlife.com";
        //    WEB_URL = "https://iusopenauth.ezvizlife.com";
        //    APP_PUSH_SECRETE = "05bb7777-025a-4a81-9302-9954344b971f";

        //新加坡海外正式平台
        //AppKey = "f3bbdc3115f311e7adacfa163eedb34f";
//        APP_SECRETE = "a7b31531638111e6876d0cc47a41de4e";
//        API_URL = "https://iusopen.ezvizlife.com";
//        WEB_URL = "https://iusopenauth.ezvizlife.com";
//        APP_PUSH_SECRETE = "05bb7777-025a-4a81-9302-9954344b971f";

        //AppKey = "0506e44cb1ef11e690d952540059e058";

        //伏羲
        //AppKey = "ae1b9af9dcac4caeb88da6dbbf2dd8d5";
        //APP_SECRETE = "a7b31531638111e6876d0cc47a41de4e";
        //API_URL = "http://ifuxi.open.ezvizlife.com:8080";
        //WEB_URL = "http://ifuxi.oauth.ezvizlife.com:8080";
        //APP_PUSH_SECRETE = "311979bb-6d89-490c-b89b-ca7669ab8fba";

        AppKey = "52966fc7efe84fa8ad3c0b2b2e3effdd";
    }

    private void initParam() {
        //线上测试推送
        AppKey = "9e53b2561f314f00ba68b5d58d66e4c0";
        API_URL = "https://open.ys7.com";
        WEB_URL = "https://auth.ys7.com";
        APP_PUSH_SECRETE = "34874f1bdd964b7fa822c2117f3b451d";

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
