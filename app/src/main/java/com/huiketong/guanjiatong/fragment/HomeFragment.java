package com.huiketong.guanjiatong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.activity.ProjectActivity;
import com.huiketong.guanjiatong.adapter.BannerAdapter;
import com.huiketong.guanjiatong.adapter.ProjectListAdapter;
import com.huiketong.guanjiatong.base.BaseFragment;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.bean.ProjectCountBean;
import com.huiketong.guanjiatong.bean.ProjectListItemBean;
import com.huiketong.guanjiatong.presenter.HomePresenter;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.HomeView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView {
    // TODO 上拉加载
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
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String userCode;
    private String companyCode;
    private Handler bannerHandler;
    private int currBannerIndex = 0;

    private int p;
    private int ps = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        tbTitle.setText("装企管家通");
        projectList.setGroupIndicator(null);    //取消箭头，设置父节点不可展开
        //设置展开不可以点击
        projectList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;    //返回ture表示不可以点击
            }
        });
        userCode = (String) Utils.getShared(getContext(), "usercode", "");
        companyCode = (String) Utils.getShared(getContext(), "companycode", "");
        // 获取banner
        getPresenter().getBanner(userCode);

        // 获取项目统计
        getPresenter().getReport(userCode, companyCode);

        //获取项目列表
        changeProjectState(0);

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
                changeProjectState(0);
                resetColor();
                projectCate0.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.project_cate1:
                changeProjectState(1);
                resetColor();
                projectCate1.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.project_cate2:
                changeProjectState(2);
                resetColor();
                projectCate2.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.project_cate3:
                changeProjectState(3);
                resetColor();
                projectCate3.setBackgroundResource(R.color.homeToolsSelect);
                break;
            case R.id.create_project:
                break;
        }
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

    /**
     * 修改项目状态
     *
     * @param state 状态0意向，1进行中，2延期，3已完成
     */
    private void changeProjectState(int state) {
        p = 1;
        getPresenter().getProjectList(userCode, companyCode, state, p, ps);
    }

    @Override
    public void onBannerSuccess(final BannerByUserCodeBean bean) {
        if (bean.getRows().size() <= 0) {
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
        if(banner != null) {
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

    @Override
    public void onProjectCountSuccess(ProjectCountBean bean) {
        projectCount0.setText(bean.get_$0());
        projectCount1.setText(bean.get_$1());
        projectCount2.setText(bean.get_$2());
        projectCount3.setText(bean.get_$3());
    }

    @Override
    public void onGetProjectListSuccess(List<String> groupName, final List<ProjectListItemBean> groupItem) {
        ProjectListAdapter adapter = new ProjectListAdapter(getContext());
        adapter.group = groupName;
        adapter.item = groupItem;
        projectList.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            projectList.expandGroup(i);
        }
        /**
         * 选中项目
         */
        projectList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ProjectActivity.class);
                intent.putExtra("projectcode",groupItem.get(groupPosition).getRows().get(childPosition).getProjectcode());
                intent.putExtra("projectname",groupItem.get(groupPosition).getRows().get(childPosition).getProjectname());
                intent.putExtra("housenumber",groupItem.get(groupPosition).getRows().get(childPosition).getHousenumber());
                intent.putExtra("projectstatus",groupItem.get(groupPosition).getRows().get(childPosition).getProjectstatus());
                startActivity(intent);
                return false;
            }
        });
    }
}
