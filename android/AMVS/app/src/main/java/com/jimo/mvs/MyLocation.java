package com.jimo.mvs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by dell on 2016/8/1.
 */
public class MyLocation implements LocationListener {

    private Context mContext = null;
    private LocationProvider netProvider = null;
    private LocationManager manager = null;
    private String loca = null;

    public MyLocation(Context context) {
        this.mContext = context;
        this.manager = ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE));
        this.netProvider = manager.getProvider(LocationManager.NETWORK_PROVIDER);

        begin();
    }

    public String getLocation() {
        //Toast.makeText(mContext, "loca="+loca, Toast.LENGTH_SHORT).show();
        if(null==this.loca){
            return "30.765207,103.989339";
        }
        return this.loca;
    }

    public void closeLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        manager.removeUpdates(this);
    }

    public void begin() {
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
             /*
             * 进行定位
             * provider:用于定位的locationProvider字符串:LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
             * minTime:时间更新间隔，单位：ms
             * minDistance:位置刷新距离，单位：m
             * listener:用于定位更新的监听者locationListener
             */
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 20f, this);
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            Toast.makeText(mContext, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(i);
        }
    }

    public String getLocationInTime() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(null!=location){
            return location.getLatitude()+","+location.getLongitude();
        }else{
            return "30.765207,103.989339";
        }
    }
    @Override
    public void onLocationChanged(Location location) {

        loca = location.getLatitude()+","+location.getLongitude();
       // Toast.makeText(mContext, "loca="+loca, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private static final double EARTH_RADIUS = 6378137.0;
    // 返回单位是米
    public double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
