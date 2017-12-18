package com.junge.mvs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.amap.api.location.DPoint;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.jimo.mvs.APPConst;
import com.jimo.mvs.DriverActivity;
import com.jimo.mvs.MyLocation;
import com.jimo.mvs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TR on 2016/9/3.
 */

public class NavRoute extends AppCompatActivity implements AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {
    private RouteSearch route;
    private AMap aMap;
    private MapView mapView;
    private MyLocation location;
    private Marker marker;
    private MarkerOptions markerOption;
    private DriveRouteResult mDriveRouteResult;
    private Button back;
    private Button nav;
    private ProgressDialog pDialog;
    private ArrayList<LatLonPoint> wayspoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navroute);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        route=new RouteSearch(this);

        init();
    }
    /**
     * 初始化AMap对象
     */
    private void init() {
        pDialog=new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setContentView(R.layout.progress_dialog);
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        back= (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NavRoute.this, DriverActivity.class);
                startActivity(i);
                finish();
            }
        });
        nav= (Button) findViewById(R.id.getnav);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(NavRoute.this, Routeinfo.class);
                startActivity(i);
                finish();
            }
        });
        route.setRouteSearchListener(this);
// fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
// 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
        LatLonPoint from=new LatLonPoint(30.671208,104.054065);
        LatLonPoint to=new LatLonPoint(30.65323,104.066853);
        wayspoints=new ArrayList<LatLonPoint>();
        wayspoints.add(new LatLonPoint(30.66807,104.055653));
        wayspoints.add(new LatLonPoint(30.664748,104.061232));
        wayspoints.add(new LatLonPoint(30.661573,104.061875));
        wayspoints.add(new LatLonPoint(30.65323,104.060245));
        RouteSearch.FromAndTo fromAndTo=new RouteSearch.FromAndTo(from,to);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, 0,null , null, "");
        route.calculateDriveRouteAsyn(query);

    }

    private void setUpMap() {
        location = new MyLocation(this);
        String[] lat = location.getLocation().split(",");
        //Toast.makeText(MarkerActivity.this, (lat[0] + lat[1]), Toast.LENGTH_SHORT).show();
        LatLng ll = new LatLng(Double.valueOf(lat[0]), Double.valueOf(lat[1]));
        markerOption = new MarkerOptions();
        marker=aMap.addMarker(markerOption);
        CoordinateConverter converter  = new CoordinateConverter();
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标点 DPoint类型
        converter.coord(ll);
       // converter.convert();
// 执行转换操作
        LatLng desLatLng = converter.convert();

        marker.setPosition(desLatLng);
       // marker.setPosition(new LatLng(36.061, 103.834));
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(desLatLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));

    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        hidePDialog();
        APPConst.removeOneActivity(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        //driveRouteResult.getPaths()
        hidePDialog();
        mDriveRouteResult = driveRouteResult;
        final DrivePath drivePath = mDriveRouteResult.getPaths()
                .get(0);
        DriveRouteColorfulOverLay drivingRouteOverlay = new DriveRouteColorfulOverLay(
                aMap, drivePath,
                mDriveRouteResult.getStartPos(),
                mDriveRouteResult.getTargetPos(),wayspoints);
        drivingRouteOverlay.removeFromMap();
        drivingRouteOverlay.addToMap();
        drivingRouteOverlay.zoomToSpan();
        List<DriveStep> drivePaths = drivePath.getSteps();

       //polylineOptions.addAll();

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }
}
