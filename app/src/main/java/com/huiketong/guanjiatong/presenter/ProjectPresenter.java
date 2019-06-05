package com.huiketong.guanjiatong.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.huiketong.guanjiatong.base.BasePresenter;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.bean.ModuleBean;
import com.huiketong.guanjiatong.bean.ProjectInfoBean;
import com.huiketong.guanjiatong.bean.ProjectTeamUserBean;
import com.huiketong.guanjiatong.model.BannerModel;
import com.huiketong.guanjiatong.model.OtherModel;
import com.huiketong.guanjiatong.model.ProjectModel;
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
    private ProjectModel projectModel;
    private OtherModel otherModel;
    public ProjectPresenter(Context context) {
        this.context = context;
        bannerModel = new BannerModel();
        projectModel = new ProjectModel();
        otherModel = new OtherModel();
    }

    /**
     * 获取轮播图
     *
     * @param usercode
     */
    public void getBannerByUserCode(String usercode) {
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
     *
     * @param projectcode
     */
    public void getProjectInfo(String projectcode) {
        projectModel.GetProjectInfo(projectcode, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                ProjectInfoBean bean = new Gson().fromJson(result, ProjectInfoBean.class);
                getView().getProjectInfoSuccess(bean);
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
     * 获取项目团队列表
     * @param projectcode 项目code
     * @param p 第几页
     * @param ps 取几个
     */
    public void getTeamList(String projectcode, int p, int ps) {
        projectModel.GetProjectTeamUser(projectcode, String.valueOf(p), String.valueOf(ps), new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                if(unifySuccessDispose(result)){
                    ProjectTeamUserBean bean = new Gson().fromJson(result,ProjectTeamUserBean.class);
                    getView().getTeamList(bean);
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
     * 获取功能模块
     * @param usercode
     */
    public void getModule(String usercode){
        otherModel.GetModule(usercode, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                if(unifySuccessDispose(result)){
                    ModuleBean bean = new Gson().fromJson(result,ModuleBean.class);
                    getView().getModuleSuccess(bean);
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
