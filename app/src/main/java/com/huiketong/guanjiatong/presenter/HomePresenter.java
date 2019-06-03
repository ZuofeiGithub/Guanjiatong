package com.huiketong.guanjiatong.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huiketong.guanjiatong.base.BasePresenter;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.bean.ProjectCountBean;
import com.huiketong.guanjiatong.bean.ProjectListItemBean;
import com.huiketong.guanjiatong.model.BannerModel;
import com.huiketong.guanjiatong.model.ProjectModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.ProgressDialog;
import com.huiketong.guanjiatong.view.HomeView;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 首页
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private BannerModel bannerModel;
    private ProjectModel projectModel;

    public HomePresenter(Context context) {
        this.context = context;
        bannerModel = new BannerModel();
        projectModel = new ProjectModel();
    }

    /**
     * 获取首页banner
     *
     * @param userCode
     */
    public void getBanner(String userCode) {

        bannerModel.GetBannerByUserCode(userCode, new HttpCallback() {
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
     * 获取项目统计
     *
     * @param usercode    会员code
     * @param companycode 公司code
     */
    public void getReport(String usercode, String companycode) {
        projectModel.GetReport(usercode, companycode, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                ProjectCountBean bean = new Gson().fromJson(result, ProjectCountBean.class);
                getView().onProjectCountSuccess(bean);
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
     * 获取项目列表
     *
     * @param usercode
     * @param companyCode
     * @param projectstatus 项目状态，0意向1进行中2延期3已完成
     * @param p             当前页数
     * @param ps            每页数量
     */
    public void getProjectList(String usercode, String companyCode, int projectstatus, int p, int ps) {
        ProgressDialog.getInstance().showDialog(context,"正在加载");
        projectModel.GetProject(usercode, companyCode, String.valueOf(projectstatus), String.valueOf(p), String.valueOf(ps), new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                Logger.d(result);
                JsonObject object = new JsonParser().parse(result).getAsJsonObject();
                Iterator iterator = object.entrySet().iterator();
                Gson gson = new Gson();
                ProjectListItemBean beanList = null;
                List<String> _groupName = new ArrayList<>();
                List<ProjectListItemBean> _groupItem = new ArrayList<>();
                while (iterator.hasNext()){
                    Map.Entry m = (Map.Entry) iterator.next();
                    beanList = gson.fromJson((JsonElement) m.getValue(),ProjectListItemBean.class);
                    if(beanList.getTotal() > 0){
                        _groupName.add((String) m.getKey());
                        _groupItem.add(beanList);
                    }
                }
                getView().onGetProjectListSuccess(_groupName,_groupItem);
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
