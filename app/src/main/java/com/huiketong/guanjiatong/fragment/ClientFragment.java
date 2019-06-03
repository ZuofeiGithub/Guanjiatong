package com.huiketong.guanjiatong.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseFragment;
import com.huiketong.guanjiatong.presenter.ClientPresenter;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.ClientView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 客户
 */
public class ClientFragment extends BaseFragment<ClientView, ClientPresenter> implements ClientView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.btn_select_style)
    TextView btnSelectStyle;
    @BindView(R.id.btn_select_scheme)
    TextView btnSelectScheme;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.et_budget)
    EditText etBudget;
    @BindView(R.id.et_remark)
    EditText etRemark;
    Unbinder unbinder;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tb_title)
    TextView tbTitle;

    private OptionsPickerView styleView, schemeView;
    private List<String> styles, schemes;
    private String usercode, companycode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_client, null);
        unbinder = ButterKnife.bind(this, view);
        tbTitle.setText("报备客户");
        initView();
        return view;
    }

    private void initView() {
        usercode = (String) Utils.getShared(getContext(), "usercode", "");
        companycode = (String) Utils.getShared(getContext(), "companycode", "");

        styleView = Utils.pickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                btnSelectStyle.setText(styles.get(options1));
            }
        }).setTitleText("装修风格").build();

        styles = new ArrayList<>();
        styles.add("现代");
        styles.add("欧式");
        styles.add("美式");
        styles.add("中式");
        styles.add("日式");
        styles.add("北欧");
        styles.add("其他");

        styleView.setPicker(styles);

        schemeView = Utils.pickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                btnSelectScheme.setText(styles.get(options1));

            }
        }).setTitleText("装修方案").build();
        schemes = new ArrayList<>();
        schemes.add("全包");
        schemes.add("半包");
        schemes.add("其他");
        schemeView.setPicker(schemes);
    }

    @Override
    protected ClientView createView() {
        return this;
    }

    @Override
    protected ClientPresenter createPresenter() {
        return new ClientPresenter(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_select_style, R.id.btn_select_scheme, R.id.btn_submit, R.id.btn_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select_style: //选择风格
                styleView.show();
                break;
            case R.id.btn_select_scheme:    // 选择方案
                schemeView.show();
                break;
            case R.id.btn_submit:   // 报名
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String style = btnSelectStyle.getText().toString().trim();
                String scheme = btnSelectScheme.getText().toString().trim();
                String area = etArea.getText().toString().trim();
                String budget = etBudget.getText().toString().trim();
                String remark = etRemark.getText().toString().trim();
                getPresenter().addPotential(usercode, companycode, name, phone, address, style, scheme, area, budget, remark);
                break;
            case R.id.btn_rule: // TODO 佣金规则

                break;
        }
    }

    @Override
    public void onSuccess() {
        Utils.Toast(getContext(), "报备成功");
        // 重置表单
        etName.setText("");
        etPhone.setText("");
        etAddress.setText("");
        etRemark.setText("");
        etBudget.setText("");
        etArea.setText("");
        btnSelectScheme.setText("");
        btnSelectStyle.setText("");
    }
}
