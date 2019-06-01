package com.huiketong.guanjiatong.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.huiketong.guanjiatong.base.BasePresenter;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.model.BannerModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.HomeView;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

import okhttp3.Request;

/**
 * 首页
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private BannerModel bannerModel;

    public HomePresenter(Context context) {
        this.context = context;
        bannerModel = new BannerModel();
    }

    /**
     * 获取首页banner
     * @param userCode
     */
    public void getBanner(String userCode){

        bannerModel.GetBannerByUserCode(userCode, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                if(unifySuccessDispose(result)){
                    BannerByUserCodeBean bean = new Gson().fromJson(result,BannerByUserCodeBean.class);
                    getView().onBannerSuccess(bean);
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
}
