package com.huiketong.guanjiatong.model;

import com.google.gson.JsonObject;
import com.huiketong.guanjiatong.base.BaseModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 登录
 */
public class LoginModel extends BaseModel {

    /**
     * 登陆
     *
     * @param uid          账号
     * @param userpassword 密码
     * @param callback     回调
     */
    public void CheckLogin(String uid, String userpassword, HttpCallback callback) {
        JsonObject object = new JsonObject();
        object.addProperty("uid", uid);
        object.addProperty("userpassword", userpassword);
        HttpUtils.postJsonRequest(UrlUtils.CheckLogin, object, callback);
    }

    /**
     * 获得对应的企业用户的信息-企业信息包括企业基础资料
     */
    public void GetCompanyUserInfo(String uid,HttpCallback callback){
        Map<String,String> params = new HashMap<>();
        params.put("uid",uid);
        HttpUtils.getRequest(UrlUtils.GetCompanyUserInfo, params, callback);
    }

}
