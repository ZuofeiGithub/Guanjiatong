package com.huiketong.guanjiatong.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.huiketong.jiketong.utils.ProgressDialog;

/**
 * 基础Acitivty
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter> extends AppCompatActivity {
    private P presenter;
    private V view;
    protected Dialog mDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null) {
            this.presenter = createPresenter();
        }

        if (this.view == null) {
            this.view = createView();
        }

        if (this.presenter != null && this.view != null) {
            // 绑定视图
            this.presenter.attachView(this.view);
        }
    }

    public P getPresenter() {
        return presenter;
    }

    /**
     * 创建 View
     *
     * @return BaseView
     */
    protected abstract V createView();

    /**
     * 创建 Presenter
     *
     * @return BasePresenter
     */
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑视图
        if (this.presenter != null) {
            this.presenter.detachView();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ProgressDialog.getInstance().closeDialog();
    }

    /**
     * 设置导航栏
     * @param toolbar   Toolbar
     * @param tbTitle   标题
     */
    protected void setToolBar(Toolbar toolbar, TextView tbTitle){
        this.setSupportActionBar(toolbar);
        tbTitle.setText(getTitle().toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
