package com.huiketong.guanjiatong.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.huiketong.guanjiatong.base.BasePresenter;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.model.BannerModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.view.ProjectView;

import java.io.IOException;

import okhttp3.Request;

/**
 * 项目
 */
public class ProjectPresenter extends BasePresenter<ProjectView> {
    private BannerModel bannerModel;

    public ProjectPresenter(Context context) {
        this.context = context;
        bannerModel = new BannerModel();
    }

    /**
     * 获取轮播图
     * @param usercode
     */
    public void getBannerByUserCode(String usercode){
        bannerModel.GetBannerByUserCode(usercode, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                if (unifySuccessDispose(result)) {
                    BannerByUserCodeBean bean = new Gson().fromJson(result, BannerByUserCodeBean.class);
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

    /**
     * 获取项目详情
     * @param projectcode
     */
    public void getProjectInfo(String projectcode){

    }
}
