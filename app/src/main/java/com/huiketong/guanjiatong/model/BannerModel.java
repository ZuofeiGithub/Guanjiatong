package com.huiketong.guanjiatong.model;

import com.huiketong.guanjiatong.base.BaseModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 轮播图模块
 */
public class BannerModel extends BaseModel {
    /**
     * 据帐号获得对应的企业用户所属的所有角色信息获取企业轮播图信息
     * @param userCode
     * @param callback
     */
    public void GetBannerByUserCode(String userCode, HttpCallback callback){
        Map<String ,String> map = new HashMap<>();
        map.put("userCode",userCode);
        HttpUtils.getRequest(UrlUtils.GetBannerByUserCode,map,callback);
    }

    /**
     * 根据帐号获得对应的企业用户的信息-企业信息包括企业基础资料
     * @param companyCode
     * @param callback
     */
    public void GetBanner(String companyCode, HttpCallback callback){
        Map<String ,String> map = new HashMap<>();
        map.put("companyCode",companyCode);
        HttpUtils.getRequest(UrlUtils.GetBanner,map,callback);
    }

}
