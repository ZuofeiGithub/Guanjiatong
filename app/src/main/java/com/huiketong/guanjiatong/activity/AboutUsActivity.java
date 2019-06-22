package com.huiketong.guanjiatong.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.presenter.AboutUsPresenter;
import com.huiketong.guanjiatong.view.AboutUsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity<AboutUsView, AboutUsPresenter>  implements AboutUsView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        setToolBar(toolbar, tbTitle, "关于");
    }

    @Override
    protected AboutUsView createView() {
        return this;
    }

    @Override
    protected AboutUsPresenter createPresenter() {
        return new AboutUsPresenter(this);
    }
}
