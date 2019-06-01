package com.huiketong.guanjiatong.view;

import com.huiketong.guanjiatong.base.BaseView;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;

/**
 * 首页
 */
public interface HomeView extends BaseView {
    /**
     * 获取banner成功
     * @param bean
     */
    void onBannerSuccess(BannerByUserCodeBean bean);

}
