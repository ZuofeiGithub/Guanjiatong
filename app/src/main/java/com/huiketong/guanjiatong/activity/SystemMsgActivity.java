package com.huiketong.guanjiatong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.presenter.SystemMsgPresenter;
import com.huiketong.guanjiatong.view.SystemMsgView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemMsgActivity extends BaseActivity<SystemMsgView, SystemMsgPresenter> implements SystemMsgView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg);
        ButterKnife.bind(this);
        setToolBar(toolbar, tbTitle, "系统消息");
    }

    @Override
    protected SystemMsgView createView() {
        return this;
    }

    @Override
    protected SystemMsgPresenter createPresenter() {
        return new SystemMsgPresenter(this);
    }
}
