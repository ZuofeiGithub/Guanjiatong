package com.huiketong.guanjiatong.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.adapter.BannerAdapter;
import com.huiketong.guanjiatong.adapter.ModuleAdapter;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.bean.BannerByUserCodeBean;
import com.huiketong.guanjiatong.bean.CaseListBean;
import com.huiketong.guanjiatong.bean.ModuleBean;
import com.huiketong.guanjiatong.bean.ProjectInfoBean;
import com.huiketong.guanjiatong.bean.ProjectTeamUserBean;
import com.huiketong.guanjiatong.myview.RoundCornerImageView;
import com.huiketong.guanjiatong.presenter.ProjectPresenter;
import com.huiketong.guanjiatong.utils.EZUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utility;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.ProjectView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.videogo.constant.IntentConsts;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.exception.ErrorCode;
import com.videogo.openapi.EZGlobalSDK;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.openapi.bean.EZHiddnsDeviceInfo;
import com.videogo.util.ConnectionDetector;
import com.videogo.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.tools)
    RecyclerView tools;
    @BindView(R.id.select_case)
    ListView mSelectView;

    private String projectcode, projectname, housenumber;
    private int projectstatus;
    private EZDeviceInfo deviceInfo;
    private List<EZDeviceInfo> deviceInfoList;
    private String userCode;
    private Handler bannerHandler;
    private int currBannerIndex = 0;
    private int code_result = 0;
    private ProjectTeamUserBean projectTeamUserBean;
    public AppInfo appInfo;
    private String[] modules = {
            "760748040ad343b79d19c63c9b7556e4",     //基础信息
            "c4e67adb-183a-41b8-a0e4-6c93ef88b388", //每日签到
            "4f0006a7-5a6d-4024-93ec-8ddcb8682ae6", //设计档案
            "24e1df93-aef8-4661-bf5a-8da65e60ee3e", //施工任务
            "d01507f3-b7a3-4759-bac4-c000a8176bc4", //工程材料
            "90ea86e8-802c-4144-915f-838c3cc40bb6", //项目修改
            "eeee0b7c-4ed8-4423-b889-8a6108399249", //导入模板
            "f9ea2f70-c950-4951-9548-5914143c6976", //家装商城
            "0363d3ee-01fe-4d72-8e3f-9b6ed059f12d", //我的收藏
            "63a726a8-25d1-4fd5-87f6-5e25011c61cf", //添加案例
            "872e9cb2-cbbf-4c68-9786-6167b1709a83", //我的收入
            "92b1259f-1b4c-48ea-8bd7-c822c71167dc", //申请延期
            "2cbad1a3-7ea5-408c-9321-20fac2e029d4", //延期审核
              "65582869dece4dad8f1ce280be867467",     //工地直播
            "da41230e-b26f-4e63-907b-63f2b28a279c", //处罚
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        try {
            appInfo = AppInfo.getAppInfo(this.getPackageManager().
                    getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).
                    metaData.getString("TencentMapSDK"));
        } catch (Exception e) {
            // TODO: handle exception
            Toast toast = Toast.makeText(this, "未找到key", Toast.LENGTH_LONG);
            toast.show();
        }
        Bundle bundle = getIntent().getExtras();
        projectcode = bundle.getString("projectcode");
        projectname = bundle.getString("projectname");
        housenumber = bundle.getString("housenumber");
        projectstatus = bundle.getInt("projectstatus");
        if (TextUtils.isEmpty(projectcode)) {
            Utils.Toast(this, "请选择工地项目");
            finish();
        }
        initView();
        initData();



    }

    private void initView() {
        setToolBar(toolbar, tbTitle, projectname + housenumber);
        tvProjectName.setText(projectname + housenumber);
        String statusName = "意向";
        switch (projectstatus) {
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

    private void initData() {
        userCode = (String) Utils.getShared(this, "usercode", "");
        // 获取banner图
        getPresenter().getBannerByUserCode(userCode);
        // 获得项目-详情
        getPresenter().getProjectInfo(projectcode);
        // 获取团队列表
        getPresenter().getTeamList(projectcode, 1, 100);
        // 获取功能列表
        getPresenter().getModule(userCode);
        //获得精选案例列表
        getPresenter().getCaseList(projectcode,userCode,1,100);
        //获取设备信息
        getPresenter().getDeviceInfo(projectcode);
        getDDNSDeviceListInfoList(true);
        deviceInfoList = new ArrayList<>();
        Utility.setListViewHeightBasedOnChildren(mSelectView);
    }


    //获取key
    public static class AppInfo {
        private final String USER_KEY;
        private static AppInfo appInfo;

        private AppInfo(String key) {
            USER_KEY = key;
        }
        public static AppInfo getAppInfo(String key) {
            if (appInfo == null) {
                appInfo = new AppInfo(key);
            }
            return appInfo;
        }
        public static String getAppkey() {
            return appInfo.USER_KEY;
        }
    }

    @Override
    protected ProjectView createView() {
        return this;
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter(this);
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

    @Override
    public void getProjectInfoSuccess(ProjectInfoBean bean) {
        tvProjectTime.setText("施工时间：" + bean.getStartdate().substring(0, 10) + "至" + bean.getEnddate().substring(0, 10));
        tvProjectCompany.setText("施工单位：" + bean.getCompanyname());
    }

    @Override
    public void getDeviceInfoSuccess(EZDeviceInfo bean) {
        deviceInfo = bean;
        for (EZDeviceInfo info : deviceInfoList) {
            if (deviceInfo.getDeviceSerial().equals(info.getDeviceSerial())) {
                deviceInfo = info;
            }
        }
    }

    @Override
    public void getCaseInfoSuccess(CaseListBean bean) {
        Log.i("zuofei",bean.getRows().toString());
        List<Map<String,Object>> mapList = new ArrayList<>();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,mapList,R.layout.select_case_item,new String[]{"","","",""},new int[]{R.id.select_case,1,1,1});
        mSelectView.setAdapter(simpleAdapter);
    }

    @Override
    public void getTeamList(ProjectTeamUserBean bean) {
        if (bean.getRows().size() == 0) {
            return;
        }
        projectTeamUserBean = bean;
        for (int i = 0; i < bean.getRows().size(); i++) {
            if (i == 5) {
                break;
            }
            RoundCornerImageView imageView = new RoundCornerImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dpToPx(this, 40), Utils.dpToPx(this, 40));
            params.setMargins(Utils.dpToPx(this, 3), 0, 0, 0);
            imageView.setLayoutParams(params);
            imageView.setRound(Utils.dpToPx(this, 20), 0);
            if (TextUtils.isEmpty(bean.getRows().get(i).getHeadimg())) {
                imageView.setImageResource(R.drawable.default_avatar);
            } else {
                Picasso.with(this).load(bean.getRows().get(i).getHeadimg()).into(imageView);
            }
            llTeam.addView(imageView);
        }
    }

    @Override
    public void getModuleSuccess(final ModuleBean bean) {
        List list = Arrays.asList(modules);
        //数据过滤
        for (int i = bean.getRows().size() - 1; i >= 0; i--) {
            if (!list.contains(bean.getRows().get(i).getFrontmodulecode())) {
                bean.getRows().remove(i);
            }
        }

        //创建网格布局
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSmoothScrollbarEnabled(false);
        tools.setLayoutManager(manager);
        ModuleAdapter adapter = new ModuleAdapter(bean, this);
        adapter.setOnItemClickListener(new ModuleAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = null;
                switch (bean.getRows().get(position).getFrontmodulecode()) {
                    case "760748040ad343b79d19c63c9b7556e4":    //TODO 基础信息

                        break;
                    case "c4e67adb-183a-41b8-a0e4-6c93ef88b388":    //TODO 每日签到
                        intent = new Intent(ProjectActivity.this,SignInActivity.class);
                        intent.putExtra("projectcode",projectcode);
                        break;
                    case "4f0006a7-5a6d-4024-93ec-8ddcb8682ae6":    //TODO 设计档案

                        break;
                    case "24e1df93-aef8-4661-bf5a-8da65e60ee3e":    //TODO 施工任务

                        break;
                    case "d01507f3-b7a3-4759-bac4-c000a8176bc4":    //TODO 工程材料

                        break;
                    case "90ea86e8-802c-4144-915f-838c3cc40bb6":    //TODO 项目修改

                        break;
                    case "eeee0b7c-4ed8-4423-b889-8a6108399249":    //TODO 导入模板

                        break;
                    case "f9ea2f70-c950-4951-9548-5914143c6976":    //TODO 家装商城

                        break;
                    case "0363d3ee-01fe-4d72-8e3f-9b6ed059f12d":    //TODO 我的收藏

                        break;
                    case "63a726a8-25d1-4fd5-87f6-5e25011c61cf":    //TODO 添加案例

                        break;
                    case "872e9cb2-cbbf-4c68-9786-6167b1709a83":    //TODO 我的收入

                        break;
                    case "92b1259f-1b4c-48ea-8bd7-c822c71167dc":    //TODO 申请延期

                        break;
                    case "2cbad1a3-7ea5-408c-9321-20fac2e029d4":    //TODO 延期审核

                        break;
                    case "65582869dece4dad8f1ce280be867467":    //工地直播

                        if(deviceInfo != null){
                            intent = new Intent(ProjectActivity.this,SiteLiveActivity.class);
                            if (deviceInfo.getCameraNum() <= 0 || deviceInfo.getCameraInfoList() == null || deviceInfo.getCameraInfoList().size() <= 0) {
                                LogUtil.d("zuofei", "cameralist is null or cameralist size is 0");
                                return;
                            }
                            if (deviceInfo.getCameraNum() == 1 && deviceInfo.getCameraInfoList() != null && deviceInfo.getCameraInfoList().size() == 1) {
                                LogUtil.d("zuofei", "cameralist have one camera");
                                final EZCameraInfo cameraInfo = EZUtils.getCameraInfoFromDevice(deviceInfo, 0);
                                if (cameraInfo == null) {
                                    return;
                                }
                                intent.putExtra("projectname",projectname);
                                intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                                intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
                            }
                            code_result = 300;
                        }else{
                            com.videogo.util.Utils.showToast(ProjectActivity.this, "该项目没有视频直播");
                            return;
                        }

                        break;
                    case "da41230e-b26f-4e63-907b-63f2b28a279c":    //TODO 处罚

                        break;
                }
                if (intent != null) {
                    startActivityForResult(intent, code_result);
                }
            }
        });
        tools.setAdapter(adapter);
    }

    private void getDDNSDeviceListInfoList(boolean headerOrFooter) {
        if (this.isFinishing()) {
            return;
        }
        new GetDDNSDeviceListInfoListTask(headerOrFooter).execute();


    }


    private class GetDDNSDeviceListInfoListTask extends AsyncTask<Void, Void, List<EZDeviceInfo>> {
        private boolean mHeaderOrFooter;
        private int mErrorCode = 0;

        public GetDDNSDeviceListInfoListTask(boolean headerOrFooter) {
            mHeaderOrFooter = headerOrFooter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mListView.setFooterRefreshEnabled(true);
        }

        @Override
        protected List<EZDeviceInfo> doInBackground(Void... params) {
            if (ProjectActivity.this.isFinishing()) {
                return null;
            }
            if (!ConnectionDetector.isNetworkAvailable(ProjectActivity.this)) {
                mErrorCode = ErrorCode.ERROR_WEB_NET_EXCEPTION;
                return null;
            }

            if (EZOpenSDK.getInstance() != null) {
                try {
                    List<EZDeviceInfo> result = null;
                    result = EZOpenSDK.getInstance().getDeviceList(0, 10);
                    if (result != null) {
                        deviceInfoList.clear();
                        if (result.size() > 0) {
                            for (EZDeviceInfo info : result) {
                                deviceInfoList.add(info);
                            }
                        }
                        if(deviceInfoList.size() > 0){
                            for (EZDeviceInfo info : deviceInfoList) {
                                if (deviceInfo.getDeviceSerial().equals(info.getDeviceSerial())) {
                                    deviceInfo = info;
                                }
                            }
                        }
                        return result;
                    }
                } catch (BaseException e) {
                    ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                    mErrorCode = errorInfo.errorCode;
                    LogUtil.debugLog("zuofei", errorInfo.toString());

                    return null;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EZDeviceInfo> result) {
            super.onPostExecute(result);
            if (ProjectActivity.this.isFinishing()) {
                return;
            }
            if (mErrorCode != 0) {
                onError(mErrorCode);
            }
        }

        protected void onError(int errorCode) {
            switch (errorCode) {
                case ErrorCode.ERROR_WEB_SESSION_ERROR:
                case ErrorCode.ERROR_WEB_SESSION_EXPIRE:
                default:
                    break;
            }
        }
    }

}
