package com.huiketong.guanjiatong.fragment;

import com.huiketong.guanjiatong.base.BaseFragment;
import com.huiketong.guanjiatong.presenter.ClientPresenter;
import com.huiketong.guanjiatong.view.ClientView;

/**
 * 客户
 */
public class ClientFragment extends BaseFragment<ClientView, ClientPresenter> implements ClientView {
    @Override
    protected ClientView createView() {
        return this;
    }

    @Override
    protected ClientPresenter createPresenter() {
        return new ClientPresenter(getContext());
    }
}
