package com.huiketong.guanjiatong.model;

import com.google.gson.JsonObject;
import com.huiketong.guanjiatong.base.BaseModel;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目模型
 */
public class ProjectModel extends BaseModel {
    /**
     * 获取项目统计
     *
     * @param usercode
     * @param companycode
     */
    public void GetReport(String usercode, String companycode, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("usercode", usercode);
        params.put("companycode", companycode);

        HttpUtils.getRequest(UrlUtils.GetReport, params, callback);
    }

    /**
     * 获得项目状态
     * @param usercode
     * @param callback
     */
   public void GetProjectCate(String usercode,HttpCallback callback){
        Map<String,String> params = new HashMap<>();
        params.put("usercode",usercode);
        HttpUtils.getRequest(UrlUtils.GetProjectCate,params,callback);
   }


   public void GetTaskTemplate(String companyCode,HttpCallback callback){
       Map<String,String> params = new HashMap<>();
       params.put("companyCode",companyCode);
       HttpUtils.getRequest(UrlUtils.GetTaskTemplate,params,callback);
   }

    /**
     * 获取项目列表
     *
     * @param usercode
     * @param companyCode
     * @param projectstatus 项目状态，0意向1进行中2延期3已完成
     * @param p             当前页数
     * @param ps            每页数量
     * @param callback
     */
    public void GetProject(String usercode, String companyCode, String projectstatus, String p, String ps, HttpCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("usercode", usercode);
        params.put("companyCode", companyCode);
        params.put("projectstatus", projectstatus);
        params.put("p", p);
        params.put("ps", ps);

        HttpUtils.getRequest(UrlUtils.GetProject, params, callback);
    }

    /**
     * 获得精选案例列表
     * @param projectcode 项目ID
     * @param userCode 用户
     * @param p 当前页数
     * @param ps 每页数量
     * @param callback
     */
    public void GetCase(String projectcode,String userCode,String p,String ps,HttpCallback callback){
        Map<String, String> params = new HashMap<>();
        params.put("projectcode", projectcode);
        params.put("userCode", userCode);
        params.put("p", p);
        params.put("ps", ps);

        HttpUtils.getRequest(UrlUtils.GetCase, params, callback);
    }

    /**
     * 获取项目详情
     *
     * @param projectcode
     * @param callback
     */
    public void GetProjectInfo(String projectcode, HttpCallback callback) {
        Map<String,String> params = new HashMap<>();
        params.put("projectcode", projectcode);
        HttpUtils.getRequest(UrlUtils.GetProjectInfo, params, callback);
    }

    /**
     * 获取项目团队用户
     * @param projectcode 项目code
     * @param p 页数
     * @param ps ，每页个数
     */
    public void GetProjectTeamUser(String projectcode,String p,String ps,HttpCallback callback){
        Map<String,String> params = new HashMap<>();
        params.put("projectcode", projectcode);
        params.put("p", p);
        params.put("ps", ps);
        HttpUtils.getRequest(UrlUtils.GetProjectTeamUser, params, callback);
    }
}
