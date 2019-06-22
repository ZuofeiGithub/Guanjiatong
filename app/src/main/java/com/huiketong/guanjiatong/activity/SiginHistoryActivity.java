package com.huiketong.guanjiatong.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.adapter.SiginListAdapter;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.bean.SiginListBean;
import com.huiketong.guanjiatong.bean.SigninBean;
import com.huiketong.guanjiatong.myview.SiginHistoryRecycleView;
import com.huiketong.guanjiatong.presenter.SiginHistoryPresenter;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.ProgressDialog;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.SiginHistoryView;
import com.zly.www.easyrecyclerview.footer.ErvLoadUIHandle;
import com.zly.www.easyrecyclerview.listener.OnLoadListener;
import com.zly.www.easyrecyclerview.listener.OnRefreshListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 签到历史记录
 */
public class SiginHistoryActivity extends BaseActivity<SiginHistoryView, SiginHistoryPresenter> implements SiginHistoryView,OnLoadListener, OnRefreshListener{

    SiginListAdapter siginListAdapter;

    private Handler handler = new Handler();
    private boolean isFail = false;
    String usercode = null;
    String projectCode = null;
    String projectName = null;
    Integer mTotalCount = 0;
    Integer page = 1;

    @BindView(R.id.erv)
    SiginHistoryRecycleView siginHistoryRecycleView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;

    final static Integer COUNTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_history);
        ButterKnife.bind(this);
        siginHistoryRecycleView.setAdapter(siginListAdapter = new SiginListAdapter());
        siginHistoryRecycleView.setLastUpdateTimeRelateObject(this);
        siginHistoryRecycleView.setOnLoadListener(this);
        initData();
    }

    @Override
    protected SiginHistoryView createView() {
        return this;
    }

    @Override
    protected SiginHistoryPresenter createPresenter() {
        return new SiginHistoryPresenter(this);
    }

    void initData(){
         usercode = Utils.getShared(this, "usercode", "").toString();
         projectCode = this.getIntent().getStringExtra("projectcode");
         projectName = this.getIntent().getStringExtra("projectname");
         setToolBar(toolbar,tbTitle,"签到历史");
        getSiginHistoryList(usercode,projectCode,page,COUNTS);
    }

    void getSiginHistoryList(String usercode,String projectCode,Integer p,Integer ps){
        Map<String,String> map = new HashMap<>();
        map.put("usercode",usercode);
        map.put("projectcode",projectCode);
        map.put("p",String.valueOf(p));
        map.put("ps",String.valueOf(ps));
        ProgressDialog.getInstance().showDialog(this,"正在加载");
        HttpUtils.getRequest(UrlUtils.GetSignIn, map, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
               SiginListBean beanList =  new Gson().fromJson(result, SiginListBean.class);
                mTotalCount = beanList.getTotal();
               if(beanList.getTotal() > 0) {
                   for (SiginListBean.RowsBean bean : beanList.getRows()) {
                       SigninBean signinBean = new SigninBean();
                       signinBean.setUsername(bean.getUsername());
                       signinBean.setSiginTime(bean.getSignintime());
                       signinBean.setProjectName(projectName);
                       signinBean.setAddress(bean.getSigninaddress());
                       siginListAdapter.add(signinBean);
                   }
               }else{

               }
            }

            @Override
            public void requestFaild(Request request, IOException io) {

            }

            @Override
            public void complete() {
                siginHistoryRecycleView.removeFooter();
                ProgressDialog.getInstance().closeDialog();
            }
        });
    }

    @Override
    public void onLoadListener() {
        siginHistoryRecycleView.setFooterView(LayoutInflater.from(this).inflate(R.layout.foot_layout,null));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (siginListAdapter.getItemCount() >= mTotalCount) {
                    siginHistoryRecycleView.noMore();
                } else {
                    page++;
                    getSiginHistoryList(usercode,projectCode,page,COUNTS);
                }
            }
        }, 3000);
    }

    @Override
    public void onRefreshListener() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (siginListAdapter.getItemCount() >= mTotalCount) {
                    siginHistoryRecycleView.noMore();
                } else {
                    page++;
                    getSiginHistoryList(usercode,projectCode,page,COUNTS);
                }
            }
        }, 3000);
    }

}
