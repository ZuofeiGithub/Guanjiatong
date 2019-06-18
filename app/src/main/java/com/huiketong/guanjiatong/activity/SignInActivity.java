package com.huiketong.guanjiatong.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezviz.stream.LogUtil;
import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
import com.huiketong.guanjiatong.utils.UrlUtils;
import com.huiketong.guanjiatong.utils.Utils;
import com.kongzue.dialog.v2.SelectDialog;
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
import com.videogo.widget.TitleBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * 签到
 */
public class SignInActivity extends AppCompatActivity implements TencentLocationListener {


    @BindView(R.id.title_bar_portrait)
    TitleBar mPortraitTitleBar;
    @BindView(R.id.sigin)
    ImageView siginView;
    @BindView(R.id.currentAddress)
    TextView currentAddressView;
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
        initTitleBar();
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

            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions, 0);
            }
        }
        initTencentLocationRequest();
    }

    void initData(){
        mPortraitTitleBar.setTitle("每日签到");

        siginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDialog.show(SignInActivity.this, "确定签到吗？", "请确认地址，一旦签到不可更改", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(SignInActivity.this, "您点击了确定按钮", Toast.LENGTH_SHORT).show();

                        String usercode = Utils.getShared(SignInActivity.this,"usercode","").toString();
                        Intent intent = SignInActivity.this.getIntent();
                        String projectcode = intent.getStringExtra("projectcode");
                        signIn(usercode,""+mLatitude+","+mLongitude,mAddress,projectcode);
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SignInActivity.this, "您点击了取消按钮", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    void signIn(String usercode,String gps,String signinaddress,String projectcode){
        Map<String,String> map = new HashMap<>();
        map.put("usercode",usercode);
        map.put("gps",gps);
        map.put("signinaddress",signinaddress);
        map.put("projectcode",projectcode);
        HttpUtils.postFormRequest(UrlUtils.SignIn, map, new HttpCallback() {
            @Override
            public void requestSuccess(String result) throws Exception {
                LogUtil.d("zuofei",result);
            }

            @Override
            public void requestFaild(Request request, IOException io) {

            }

            @Override
            public void complete() {

            }
        });
    }

    // 初始化标题栏
    private void initTitleBar() {
        // 返回事件
        mPortraitTitleBar.addBackButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
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
            tencentMap.setCenter(new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()));
            Marker marker = tencentMap.addMarker(new MarkerOptions()
                    .position(new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()))
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker())
                    .draggable(true));
            marker.showInfoWindow();// 设置默认显示一个infoWindow
            currentAddressView.setText("当前位置:"+tencentLocation.getAddress());
            Log.i("zuofei","定位成功");
        } else {
            // 定位失败
            Log.i("zuofei","定位失败");
        }

    }

    private void initTencentLocationRequest() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(30000).setRequestLevel(1).setAllowCache(true);
        TencentLocationManager locationManager = TencentLocationManager.getInstance(this);
        int error = locationManager.requestLocationUpdates(request, this);
        if (error == 0)
            Log.i("zuofei","注册位置监听器成功！");
        else
            Log.i("zuofei","注册位置监听器失败！");
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.i("zuofei",s);
    }

}
