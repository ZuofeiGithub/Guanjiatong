package com.huiketong.guanjiatong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.Utils;

/**
 * 启动初始化
 */
public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpUtils.setContext(getApplicationContext());

        //判断uid是否存在
        if(TextUtils.isEmpty((String) Utils.getShared(this,"uid",""))){
            // 跳转登录界面
            startActivity(new Intent(this , LoginActivity.class));
        }else{
            // 跳转首页
            startActivity(new Intent(this , MainActivity.class));
        }
        finish();
    }
}
