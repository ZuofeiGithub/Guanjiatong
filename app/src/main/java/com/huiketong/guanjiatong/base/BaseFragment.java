package com.huiketong.guanjiatong.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter>  extends Fragment {
    private P presenter;
    private V view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
    public void onDestroy() {
        super.onDestroy();
        // 解绑视图
        if (this.presenter != null) {
            this.presenter.detachView();
        }
    }

}
