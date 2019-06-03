package com.huiketong.guanjiatong.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.adapter.BannerAdapter;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.presenter.ProjectPresenter;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.ProjectView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目
 */
public class ProjectActivity extends BaseActivity<ProjectView, ProjectPresenter> implements ProjectView {

    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    ViewPager banner;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_project_time)
    TextView tvProjectTime;
    @BindView(R.id.tv_project_company)
    TextView tvProjectCompany;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.ll_team)
    LinearLayout llTeam;

    private String projectcode,projectname,housenumber;
    private int projectstatus;

    private String userCode;
    private Handler bannerHandler;
    private int currBannerIndex = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        projectcode = bundle.getString("projectcode");
        projectname = bundle.getString("projectname");
        housenumber = bundle.getString("housenumber");
        projectstatus = bundle.getInt("projectstatus");
        if(TextUtils.isEmpty(projectcode)){
            Utils.Toast(this,"请选择工地项目");
            finish();
        }
        initView();
        initDate();
        
    }
    private void initView(){
        setToolBar(toolbar,tbTitle,projectname + housenumber);
        tvProjectName.setText(projectname + housenumber);
        String statusName = "意向";
        switch (projectstatus){
            case 0:
                statusName = "意向";
                break;
            case 1:
                statusName = "进行中";
                break;
            case 2:
                statusName = "延期";
                break;
            case 3:
                statusName = "已完成";
                break;
        }
        tvState.setText(statusName);
    }
    private void initDate() {
        userCode = (String) Utils.getShared(this,"usercode","");
        // 获取banner图
        getPresenter().getBannerByUserCode(userCode);
        // 获得项目-详情

        // 获取团队列表

    }

    @Override
    protected ProjectView createView() {
        return null;
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.btn_team)
    public void onViewClicked() {
    }

    @Override
    public void onBannerSuccess(BannerByUserCodeBean bean) {
        if (bean.getRows().size() <= 0) {
            return;
        }
        final List<View> list = new ArrayList<>();
        for (int i = 0; i < bean.getRows().size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(this).load(UrlUtils.buildUrl(bean.getRows().get(i).getBannerimg())).into(imageView);
            list.add(imageView);
        }
        banner.setAdapter(new BannerAdapter(list));
        bannerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2: //切换banner
                        currBannerIndex += 1;
                        if (currBannerIndex >= list.size()) {
                            currBannerIndex = 0;
                        }
                        banner.setCurrentItem(currBannerIndex, true);
                        break;
                }
            }
        };
        bannerHandler.sendEmptyMessageDelayed(2, 2500);
        banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i != 0 && bannerHandler.hasMessages(2)) {
                    bannerHandler.removeMessages(2);
                }
            }

            @Override
            public void onPageSelected(int i) {
                currBannerIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == 0) {
                    bannerHandler.sendEmptyMessageDelayed(2, 2500);
                }
            }
        });
    }
}
