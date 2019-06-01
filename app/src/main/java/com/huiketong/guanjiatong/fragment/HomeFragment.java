package com.huiketong.guanjiatong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.adapter.BannerAdapter;
import com.huiketong.guanjiatong.base.BaseFragment;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.presenter.HomePresenter;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.HomeView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView {

    @BindView(R.id.banner)
    ViewPager banner;
    @BindView(R.id.project_count0)
    TextView projectCount0;
    @BindView(R.id.project_count1)
    TextView projectCount1;
    @BindView(R.id.project_count2)
    TextView projectCount2;
    @BindView(R.id.project_count3)
    TextView projectCount3;
    @BindView(R.id.project_list)
    ExpandableListView projectList;
    Unbinder unbinder;
    @BindView(R.id.project_cate0)
    RelativeLayout projectCate0;
    @BindView(R.id.project_cate1)
    RelativeLayout projectCate1;
    @BindView(R.id.project_cate2)
    RelativeLayout projectCate2;
    @BindView(R.id.project_cate3)
    RelativeLayout projectCate3;
    @BindView(R.id.create_project)
    ImageButton createProject;

    private String userCode;
    private String companyCode;
    private Handler bannerHandler;
    private int currBannerIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);

        projectList.setGroupIndicator(null);    //取消箭头，设置父节点不可展开
        //设置展开不可以点击
        projectList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;    //返回ture表示不可以点击
            }
        });
        userCode =(String) Utils.getShared(getContext(),"usercode","");
        companyCode = (String) Utils.getShared(getContext(),"companycode","");

        // 获取banner
        getPresenter().getBanner(userCode);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    protected HomeView createView() {
        return this;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.project_cate0, R.id.project_cate1, R.id.project_cate2, R.id.project_cate3, R.id.create_project})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.project_cate0:
                changeProjectCate("0");
                resetColor();
                projectCate0.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.project_cate1:
                changeProjectCate("1");
                resetColor();
                projectCate0.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.project_cate2:
                changeProjectCate("2");
                resetColor();
                projectCate0.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.project_cate3:
                changeProjectCate("3");
                resetColor();
                projectCate0.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.create_project:
                break;
        }
    }

    /**
     * 切换项目状态
     *
     * @param status
     */
    private void changeProjectCate(String status) {

        Map<String, String> params = new HashMap<>();
        params.put("userCode", userCode);
        params.put("companyCode",companyCode);
        params.put("projectstatus", status);
        params.put("p", "1");
        params.put("ps", "10000");

    }

    /**
     * 重置项目状态背景颜色
     */
    private void resetColor() {
        projectCate0.setBackgroundResource(R.color.bgColor);
        projectCate1.setBackgroundResource(R.color.bgColor);
        projectCate2.setBackgroundResource(R.color.bgColor);
        projectCate3.setBackgroundResource(R.color.bgColor);
    }

    @Override
    public void onBannerSuccess(final BannerByUserCodeBean bean) {
        if(bean.getRows().size() <= 0){
            return;
        }
        final List<View> list = new ArrayList<>();
        for (int i = 0; i < bean.getRows().size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(UrlUtils.buildUrl(bean.getRows().get(i).getBannerimg())).into(imageView);
            /*
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
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
