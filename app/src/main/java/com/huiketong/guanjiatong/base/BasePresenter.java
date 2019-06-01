package com.huiketong.guanjiatong.base;

import android.app.Activity;
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
            if(object.has("code")){
                String code = object.get("code").getAsString();
                if(code.equals("500")){
                    Logger.e("超时");
//                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((Activity)context).finish();
                    return false;
                }else if(!code.equals("1")){
                    Logger.e("请求失败");
                    if(object.has("msg")){
                        Utils.Toast(context,object.get("msg").getAsString());
                    }else{
                        Utils.Toast(context,"系统繁忙");
                    }
                    return false;
                }
                return true;
            }else{
                Utils.Toast(context,"请求错误");
                return false;
            }
        }catch (Exception e){
            Utils.Toast(context,"请求错误");
            return false;
        }

    }
}
