package com.huiketong.guanjiatong.view;

import com.huiketong.guanjiatong.base.BaseView;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;

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
}
