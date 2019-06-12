package com.huiketong.guanjiatong.activity;

import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.List;

public interface OnDataFinishedListener {
    void onDataSuccessfully(List<EZDeviceInfo> data);
    void onDataFailed();
}
