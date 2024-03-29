package com.huiketong.guanjiatong.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.base.BaseActivity;
import com.huiketong.guanjiatong.presenter.SignInPresenter;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.huiketong.guanjiatong.view.SignInView;
import com.kongzue.dialog.v2.SelectDialog;
import com.orhanobut.logger.Logger;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 签到
 */
public class SignInActivity extends BaseActivity<SignInView, SignInPresenter> implements SignInView,TencentLocationListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.sigin)
    ImageView siginView;
    @BindView(R.id.currentAddress)
    TextView currentAddressView;
    @BindView(R.id.signTextView)
    TextView signTextView;
    @BindView(R.id.checkHistoryBtn)
    Button checkHistoryBtn;
    MapView mapView = null;
    TencentMap tencentMap = null;
    private double mLatitude = 0d;
    private double mLongitude = 0d;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        initData();
        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        tencentMap = mapView.getMap();
        //设置卫星底图
        tencentMap.setSatelliteEnabled(false);
        //设置实时路况开启
        tencentMap.setTrafficEnabled(true);
        //设置缩放级别
        tencentMap.setZoom(20);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 0);
            }
        }
        initTencentLocationRequest();
    }

    @Override
    protected SignInView createView() {
        return this;
    }

    @Override
    protected SignInPresenter createPresenter() {
        return new SignInPresenter(this);
    }

    void initData() {
        setToolBar(toolbar,tbTitle,"每日签到");
        siginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDialog.show(SignInActivity.this, "确定签到吗？", "请确认地址，一旦签到不可更改", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(SignInActivity.this, "您点击了确定按钮", Toast.LENGTH_SHORT).show();

                        String usercode = Utils.getShared(SignInActivity.this, "usercode", "").toString();
                        Intent intent = SignInActivity.this.getIntent();
                        String projectcode = intent.getStringExtra("projectcode");
                        try {
                            signIn(usercode, "" + mLatitude + "," + mLongitude, mAddress, projectcode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SignInActivity.this, "您点击了取消按钮", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        checkHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(SignInActivity.this,SiginHistoryActivity.class);
               intent.putExtra("projectcode",SignInActivity.this.getIntent().getStringExtra("projectcode"));
               intent.putExtra("projectname",SignInActivity.this.getIntent().getStringExtra("projectname"));
               startActivity(intent);
            }
        });
    }


    void signIn(String usercode, String gps, String signinaddress, String projectcode) throws JSONException {
        JsonObject map = new JsonObject();
        map.addProperty("usercode", usercode);
        map.addProperty("gps", gps);
        map.addProperty("signinaddress", signinaddress);
        map.addProperty("projectcode", projectcode);
        HttpUtils.postJsonRequest(UrlUtils.SignIn, map, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                result = HttpUtils.getJson(result);
                Logger.d(result);
                JsonObject object = new JsonParser().parse(result).getAsJsonObject();
                if (object.get("retStatus").getAsInt() == 100) {
                    signTextView.setText("签到成功");
                    Toast.makeText(SignInActivity.this,"签到成功",Toast.LENGTH_SHORT).show();
                    signTextView.setTextSize(30);
                }else{
                    signTextView.setText("签到失败");
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


    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
        //删除位置监听器
        TencentLocationManager locationManager = TencentLocationManager.getInstance(this);
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initTencentLocationRequest();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
        if (TencentLocation.ERROR_OK == error) {
            // 定位成功
            //设置地图中心点
            mLatitude = tencentLocation.getLatitude();
            mLongitude = tencentLocation.getLongitude();
            mAddress = tencentLocation.getAddress();
            tencentMap.setCenter(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()));
            Marker marker = tencentMap.addMarker(new MarkerOptions()
                    .position(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()))
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker())
                    .draggable(true));
            marker.showInfoWindow();// 设置默认显示一个infoWindow
            currentAddressView.setText("当前位置:" + tencentLocation.getAddress());
            Log.i("zuofei", "定位成功");
        } else {
            // 定位失败
            Log.i("zuofei", "定位失败");
        }

    }

    private void initTencentLocationRequest() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(30000).setRequestLevel(1).setAllowCache(true);
        TencentLocationManager locationManager = TencentLocationManager.getInstance(this);
        int error = locationManager.requestLocationUpdates(request, this);
        if (error == 0)
            Log.i("zuofei", "注册位置监听器成功！");
        else
            Log.i("zuofei", "注册位置监听器失败！");
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.i("zuofei", s);
    }

}
