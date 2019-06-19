package com.huiketong.guanjiatong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.adapter.SiginListAdapter;
import com.huiketong.guanjiatong.bean.ProjectListItemBean;
import com.huiketong.guanjiatong.bean.SiginListBean;
import com.huiketong.guanjiatong.bean.SigninBean;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.videogo.util.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class SiginHistoryActivity extends AppCompatActivity {

    public LayoutInflater inflater;
    @BindView(R.id.sigin_history_list)
    public ListView siginHistoryListView;
    public int last_index;
    public int total_index;
    public List<SigninBean> firstList = new ArrayList<>(); //表示首次加载的list
    public List<SigninBean> nextList = new ArrayList<>(); //表示出现刷新之后需要显示的list
    public boolean isLoading = false; //表示是否正处于加载状态
    public SiginListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_history);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initList(10,10);
    }

    /**
     * 初始化我们需要加载的数据
     * @param firstCount
     * @param nextCount
     */
    public void initList(int firstCount,int nextCount)
    {
        String usercode = Utils.getShared(SiginHistoryActivity.this, "usercode", "").toString();
        String projectcode = SiginHistoryActivity.this.getIntent().getStringExtra("projectcode");
        LogUtil.d("zuofei",usercode+projectcode);
        getSiginHistory(usercode,projectcode,1,firstCount);
    }


    private void getSiginHistory(String usercode, String projectCode, Integer p, Integer ps){
        Map<String,String> map = new HashMap<>();
        map.put("usercode", usercode);
        map.put("projectCode", projectCode);
        map.put("p", String.valueOf(p));
        map.put("ps", String.valueOf(ps));
        HttpUtils.getRequest(UrlUtils.GetSignIn, map, new HttpCallback() {

            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                JsonObject object = new JsonParser().parse(result).getAsJsonObject();
                Iterator iterator = object.entrySet().iterator();
                SiginListBean listBean = null;
                Gson gson = new Gson();
                while (iterator.hasNext()){
                    Map.Entry m = (Map.Entry) iterator.next();
                    listBean =gson.fromJson((JsonElement) m.getValue(),SiginListBean.class);
                }
            }

            @Override
            public void requestFaild(Request request, IOException io) {

            }

            @Override
            public void complete() {

            }
        });
    }
}
