package com.huiketong.guanjiatong.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.huiketong.guanjiatong.base.BasePresenter;
import com.huiketong.guanjiatong.model.ClientModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.ProgressDialog;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.SiteLiveView;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * 工地直播
 */
public class SiteLivePresenter extends BasePresenter<SiteLiveView> {


    public SiteLivePresenter(Context context) {
        this.context = context;
    }

}
