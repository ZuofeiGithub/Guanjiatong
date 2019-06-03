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
import com.huiketong.guanjiatong.view.ClientView;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * 客户
 */
public class ClientPresenter extends BasePresenter<ClientView> {

    private ClientModel clientModel;

    public ClientPresenter(Context context) {
        this.context = context;
        clientModel = new ClientModel();
    }

    /**
     * 报备客户
     *
     * @param createusercode   用户code
     * @param companycode      企业code
     * @param potentialname    客户姓名
     * @param potentialphone   手机号
     * @param potentialaddress 地址
     * @param potentialstyle   风格
     * @param potentialsln     方案
     * @param potentialarea    面积
     * @param potentialbudget  预算
     * @param potentialremark  备注
     */
    public void addPotential(String createusercode, String companycode,
                             String potentialname, String potentialphone,
                             String potentialaddress, String potentialstyle,
                             String potentialsln, String potentialarea, String potentialbudget, String potentialremark) {
        if (TextUtils.isEmpty(potentialname)) {
            Utils.Toast(context, "请输入客户姓名");
            return;
        } else if (TextUtils.isEmpty(potentialphone)) {
            Utils.Toast(context, "请输入手机号码");
            return;
        } else if (!Pattern.matches("^1[0-9]{10}$", potentialphone)) {
            Utils.Toast(context, "请输入正确的手机号");
            return;
        } else if (TextUtils.isEmpty(potentialaddress)) {
            Utils.Toast(context, "请输入小区、楼号");
            return;
        } else if (TextUtils.isEmpty(potentialstyle)) {
            Utils.Toast(context, "请选择装修风格");
            return;
        } else if (TextUtils.isEmpty(potentialsln)) {
            Utils.Toast(context, "请选择装修方案");
            return;
        } else if (TextUtils.isEmpty(potentialarea)) {
            Utils.Toast(context, "请输入房屋面积（m²）");
            return;
        } else if (TextUtils.isEmpty(potentialbudget)) {
            Utils.Toast(context, "请输入装修预算（万）");
            return;
        }

        JsonObject params = new JsonObject();
        params.addProperty("createusercode", createusercode);
        params.addProperty("companycode", companycode);
        params.addProperty("potentialname", potentialname);
        params.addProperty("potentialphone", potentialphone);
        params.addProperty("potentialaddress", potentialaddress);
        params.addProperty("potentialstyle", potentialstyle);
        params.addProperty("potentialsln", potentialsln);
        params.addProperty("potentialarea", potentialarea);
        params.addProperty("potentialbudget", potentialbudget);
        params.addProperty("potentialremark", potentialremark);
        params.addProperty("potentialusercode", createusercode);
        params.addProperty("potentialdate", "www");
        ProgressDialog.getInstance().showDialog(context, "正在报备客户");
        clientModel.AddPotential(params, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                if (unifySuccessDispose(result)) {
                    getView().onSuccess();
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
