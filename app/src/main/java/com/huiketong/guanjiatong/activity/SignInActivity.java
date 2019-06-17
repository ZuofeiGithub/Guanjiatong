package com.huiketong.guanjiatong.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.huiketong.guanjiatong.R;
import com.huiketong.guanjiatong.utils.HttpCallback;
import com.huiketong.guanjiatong.utils.HttpUtils;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 签到
 */
public class SignInActivity extends AppCompatActivity implements TencentLocationListener {

    MapView mapView = null;
    TencentMap tencentMap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
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
            tencentMap.setCenter(new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()));
            Marker marker = tencentMap.addMarker(new MarkerOptions()
                    .position(new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()))
                    .title(tencentLocation.getAddress())
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker())
                    .draggable(true));
            marker.showInfoWindow();// 设置默认显示一个infoWindow
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
