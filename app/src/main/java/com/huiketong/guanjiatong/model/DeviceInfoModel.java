package com.huiketong.guanjiatong.model;

import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

public class DeviceInfoModel {

    /**
     * 获取项目详情
     *
     * @param projectcode
     * @param callback
     */
    public void getDeviceInfo(String projectcode, HttpCallback callback) {
        Map<String,String> params = new HashMap<>();
        params.put("projectcode", projectcode);
        HttpUtils.getRequest(UrlUtils.LiveAddress, params, callback);
    }
}
