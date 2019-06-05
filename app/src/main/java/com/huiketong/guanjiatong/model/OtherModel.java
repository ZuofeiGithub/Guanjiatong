package com.huiketong.guanjiatong.model;

import com.huiketong.guanjiatong.base.BaseModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 其他模块
 */
public class OtherModel extends BaseModel {
    /**
     * 获取功能模块
     *
     * @param usercode 会员code
     * @param callback
     */
    public void GetModule(String usercode, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("usercode", usercode);
        HttpUtils.getRequest(UrlUtils.GetModule, params, callback);
    }
}
