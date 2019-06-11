package com.huiketong.guanjiatong.view;

import com.huiketong.guanjiatong.base.BaseView;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.bean.ModuleBean;
import com.huiketong.guanjiatong.bean.ProjectInfoBean;
import com.huiketong.guanjiatong.bean.ProjectTeamUserBean;
import com.videogo.openapi.bean.EZDeviceInfo;

/**
 * 项目详情
 */
public interface ProjectView extends BaseView {
    /**
     * 获取轮播图成功
     *
     * @param bean
     */
    void onBannerSuccess(BannerByUserCodeBean bean);

    /**
     * 获取项目详情
     * @param bean
     */
    void getProjectInfoSuccess(ProjectInfoBean bean);

    /**
     * 获取团队用户列表
     * @param bean
     */
    void getTeamList(ProjectTeamUserBean bean);

    /**
     * 获取功能模块
     * @param bean
     */
    void getModuleSuccess(ModuleBean bean);

    /**
     * 获取设备信息
     */
    void getDeviceInfoSuccess(EZDeviceInfo bean);
}
