package com.huiketong.guanjiatong.fragment;

import com.huiketong.guanjiatong.base.BaseFragment;
import com.huiketong.guanjiatong.presenter.MyPresenter;
import com.huiketong.guanjiatong.view.MyView;

/**
 * 我的
 */
public class MyFragment extends BaseFragment<MyView, MyPresenter> implements MyView {
    @Override
    protected MyView createView() {
        return this;
    }

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(getContext());
    }
}
