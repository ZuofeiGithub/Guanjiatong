package com.huiketong.guanjiatong.base;

import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huiketong.guanjiatong.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class BasePresenter<V extends BaseView> {

    private V view;

    public V getView() {
        return view;
    }

    protected Context context;

    /**
     * 绑定视图
     * @param view
     */
    public void attachView(V view){
        this.view = view;
    }

    /**
     * 解绑视图
     */
    public void detachView(){
        this.view = null;
    }

    /**
     * 获得基本参数
     */
    public Map<String,String> getParams(){
        Map<String,String> params = new HashMap<>();
        params.put("user_id", (String) Utils.getShared(context,"user_id",""));
        params.put("token", (String) Utils.getShared(context,"token",""));
        return params;
    }


    /**
     * 统一结果处理
     * @param result 请求结果
     * @return
     */
    protected boolean unifySuccessDispose(String result){
        Logger.d(result);
        try{
            JsonObject object = new JsonParser().parse(result).getAsJsonObject();
            if(object.has("retStatus")){
                String retStatus = object.get("retStatus").getAsString();
                if(!retStatus.equals("100")){
                    Logger.e("请求失败");
                    if(object.has("retValue")){
                        Utils.Toast(context,object.get("retValue").getAsString());
                    }else{
                        Utils.Toast(context,"系统繁忙");
                    }
                    return false;
                }
                return true;
            }else if(object.has("success")){
                boolean success = object.get("success").getAsBoolean();
                if(!success){
                    Logger.e("请求失败");
                    if(object.has("errorMsg")){
                        Utils.Toast(context,object.get("errorMsg").getAsString());
                    }else{
                        Utils.Toast(context,"系统繁忙");
                    }
                    return false;
                }
                return true;
            }else{
                Utils.Toast(context,"请求错误10001");
                return false;
            }
        }catch (Exception e){
            Utils.Toast(context,"请求错误10000");
            return false;
        }

    }
}
