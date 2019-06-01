package com.huiketong.guanjiatong.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huiketong.guanjiatong.base.BasePresenter;
import com.huiketong.guanjiatong.model.LoginModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.ProgressDialog;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.LoginView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Request;

/**
 * 登录
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel loginModel;

    public LoginPresenter(Context context) {
        this.context = context;
        loginModel = new LoginModel();
    }

    /**
     * 登录
     *
     * @param phone 账号
     * @param pword 密码
     * @param check 是否记住密码
     */
    public void login(final String phone, String pword, boolean check) {
        if (TextUtils.isEmpty(phone)) {
            Utils.Toast(context, "请输入账号");
            return;
        } else if (TextUtils.isEmpty(pword)) {
            Utils.Toast(context, "请输入密码");
            return;
        }
        // 记住密码
        if (check) {
            Utils.setShared(context, "login_phone", phone);
            Utils.setShared(context, "login_pword", pword);
        }
        ProgressDialog.getInstance().showDialog(context, "正在登陆");
        loginModel.CheckLogin(phone, pword, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                if (unifySuccessDispose(result)) {
                    // 获得对应的企业用户的信息-企业信息包括企业基础资料
                    GetCompanyUserInfo(phone);
                }
            }

            @Override
            public void requestFaild(Request request, IOException io) {

            }

            @Override
            public void complete() {
            }
        });
    }

    /**
     * 获得对应的企业用户的信息-企业信息包括企业基础资料
     * @param uid
     */
    public void GetCompanyUserInfo(String uid){
        loginModel.GetCompanyUserInfo(uid, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                try{
                    //存储基本信息
                    Map<String,Object> params = new HashMap<>();
                    JsonObject object = new JsonParser().parse(HttpUtils.getJson(result)).getAsJsonObject();
                    Iterator iterator = object.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) iterator.next();
                        if(entry.getKey().toString().equals("userpassword")){
                            continue;
                        }
                        params.put( entry.getKey().toString(),entry.getValue().toString().replace("\"",""));
                    }
                    Utils.setShared(context,params);
                    getView().onSuccess();

                }catch (Exception e){
                    Utils.Toast(context,"获取信息失败");
                }

            }

            @Override
            public void requestFaild(Request request, IOException io) {

            }

            @Override
            public void complete() {
                ProgressDialog.getInstance().closeDialog();
            }
        });
    }
}
