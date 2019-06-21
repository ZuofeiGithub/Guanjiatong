package com.huiketong.guanjiatong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.EditText;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.presenter.LoginPresenter;
import com.huiketong.guanjiatong.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pword)
    EditText etPword;
    @BindView(R.id.cb_remeb)
    CheckBox cbRemeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected LoginView createView() {
        return this;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String phone = etPhone.getText().toString().trim();
        String pword = etPword.getText().toString().trim();
        boolean check = cbRemeb.isChecked();
        getPresenter().login(phone, pword, check);
    }

    @Override
    public void onSuccess() {
        // 登录成功，跳转首页
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
