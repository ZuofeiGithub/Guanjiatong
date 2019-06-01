package com.huiketong.guanjiatong.utils;

import java.io.IOException;

import okhttp3.Request;

public interface HttpCallback {
    void requestSuccess(String result) throws Exception;
    void requestFaild(Request request, IOException io);
    void complete();
}
