package com.huiketong.guanjiatong.model;

import com.google.gson.JsonObject;
import com.huiketong.guanjiatong.base.BaseModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;

import java.util.Map;

/**
 * 客户模型
 */
public class ClientModel extends BaseModel {

    /**
     * 报备客户
     * @param params
     * @param callback
     */
    public void AddPotential(JsonObject params, HttpCallback callback){
        HttpUtils.postJsonRequest(UrlUtils.AddPotential,params,callback);
    }

}
