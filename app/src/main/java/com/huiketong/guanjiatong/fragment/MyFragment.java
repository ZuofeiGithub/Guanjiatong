package com.huiketong.guanjiatong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.activity.AboutUsActivity;
import com.huiketong.guanjiatong.activity.ActivitiesActivity;
import com.huiketong.guanjiatong.activity.LoginActivity;
import com.huiketong.guanjiatong.activity.SystemMsgActivity;
import com.huiketong.guanjiatong.base.BaseFragment;
import com.huiketong.guanjiatong.myview.RoundCornerImageView;
import com.huiketong.guanjiatong.presenter.MyPresenter;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.MyView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的
 */
public class MyFragment extends BaseFragment<MyView, MyPresenter> implements MyView {

    @BindView(R.id.iv_avatar)
    RoundCornerImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my, null);
        unbinder = ButterKnife.bind(this, view);
        String avatar = (String) Utils.getShared(getContext(), "headimg", "");
        if (!TextUtils.isEmpty(avatar)) {
            Picasso.with(getContext()).load(UrlUtils.buildUrl(avatar)).into(ivAvatar);
        }
        String name = (String) Utils.getShared(getContext(), "username", "");
        String phone = (String) Utils.getShared(getContext(), "cellphone", "");
        tvName.setText(name);
        tvPhone.setText(phone);
        return view;
    }

    @Override
    protected MyView createView() {
        return this;
    }

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_new_activity, R.id.btn_sys_message, R.id.btn_about, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_new_activity:
                startActivity(new Intent(getActivity(), ActivitiesActivity.class));
                break;
            case R.id.btn_sys_message:
                startActivity(new Intent(getActivity(),SystemMsgActivity.class));
                break;
            case R.id.btn_about:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.btn_logout:   //退出登录
                Utils.clearShare(getContext());
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

}
