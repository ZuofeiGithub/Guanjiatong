package com.huiketong.guanjiatong.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.presenter.ActivitiesPresenter;
import com.huiketong.guanjiatong.presenter.SiteLivePresenter;
import com.huiketong.guanjiatong.view.ActivitiesView;
import com.huiketong.guanjiatong.view.SiteLiveView;
import com.videogo.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 最新活动
 */
public class ActivitiesActivity  extends BaseActivity<ActivitiesView, ActivitiesPresenter> implements ActivitiesView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ButterKnife.bind(this);
        setToolBar(toolbar, tbTitle, "最新活动");
    }

    @Override
    protected ActivitiesView createView() {
        return this;
    }

    @Override
    protected ActivitiesPresenter createPresenter() {
        return new ActivitiesPresenter(this);
    }



}
