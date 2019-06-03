package com.huiketong.guanjiatong.view;

import com.huiketong.guanjiatong.base.BaseView;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.bean.ProjectCountBean;
import com.huiketong.guanjiatong.bean.ProjectListItemBean;

import java.util.List;

/**
 * 首页
 */
public interface HomeView extends BaseView {
    /**
     * 获取banner成功
     * @param bean
     */
    void onBannerSuccess(BannerByUserCodeBean bean);

    /**
     * 获取项目统计成功
     * @param bean
     */
    void onProjectCountSuccess(ProjectCountBean bean);

    /**
     * 获取项目列表成功
     * @param groupName 分组名
     * @param groupItem 分组项
     */
    void onGetProjectListSuccess(List<String> groupName, List<ProjectListItemBean> groupItem);
}
