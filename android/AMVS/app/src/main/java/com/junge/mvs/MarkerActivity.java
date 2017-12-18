package com.junge.mvs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jimo.mvs.APPConst;
import com.jimo.mvs.Constants;
import com.jimo.mvs.LoginActivity;
import com.jimo.mvs.LostActivity;
import com.jimo.mvs.MyLocation;
import com.jimo.mvs.R;
import com.jimo.mvs.ToastUtil;

import java.util.ArrayList;




/**
 * AMapV1地图中简单介绍一些Marker的用法.
 */
public class MarkerActivity extends AppCompatActivity implements OnMarkerClickListener,
		OnInfoWindowClickListener, OnMarkerDragListener, OnMapLoadedListener,
		OnClickListener, InfoWindowAdapter {
	private MarkerOptions markerOption;
	private TextView tv_lost;
	private TextView tv_setting;
	private Button btn_location = null;
	MyLocation location = null;
	Handler handler = new Handler();
	RequestQueue queue;
	Runnable sendLocationRun;
	private AMap aMap;
	private MapView mapView;
	private Marker marker2;// 有跳动效果的marker对象
	private LatLng latlng = new LatLng(36.061, 103.834);
	private boolean debug=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marker_activity);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState); // 此方法必须重写
		init();
		APPConst.addOneActivity(this);

		initViews();
		initEvents();
		if(debug) {
			location = new MyLocation(this);
			String[] lat = location.getLocation().split(",");
			//Toast.makeText(MarkerActivity.this, (lat[0] + lat[1]), Toast.LENGTH_SHORT).show();
			LatLng ll = new LatLng(Double.valueOf(lat[0]), Double.valueOf(lat[1]));
			marker2.setPosition(ll);
		}
	}
	private void initEvents() {


	}

	private void initViews() {

	}

	/**
	 * 发送定位信息
	 *
	 * @param view
	 */
	public void sendLocation(View view) {

	}


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.swipeback_stack_to_front,
				R.anim.swipeback_stack_right_out);
	}


	/**
	 * 初始化AMap对象
	 */
	private void init() {

		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}

	private void setUpMap() {
		aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		addMarkersToMap();// 往地图上添加marker
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
		handler.removeCallbacks(sendLocationRun);
		APPConst.removeOneActivity(this);
	}

	/**
	 * 在地图上添加marker
	 */
	private void addMarkersToMap() {

		//文字显示标注，可以设置显示内容，位置，字体大小颜色，背景色旋转角度,Z值等
		/*TextOptions textOptions = new TextOptions().position(Constants.BEIJING)
				.text("Text").fontColor(Color.BLACK)
				.backgroundColor(Color.BLUE).fontSize(30).rotate(20).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
				.zIndex(1.f).typeface(Typeface.DEFAULT_BOLD)
				;
		aMap.addText(textOptions);

		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(Constants.CHENGDU).title("成都市")
				.snippet("成都市:30.679879, 104.064855").draggable(true));*/

		markerOption = new MarkerOptions();
		//markerOption.position(Constants.XIAN);
		//markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
		markerOption.draggable(true);
	//	markerOption.icon(BitmapDescriptorFactory
			//	.fromResource(R.drawable.appicon));
		marker2 = aMap.addMarker(markerOption);
		marker2.setIcon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		//marker2.showInfoWindow();
		// marker旋转90度
		//marker2.setRotateAngle(90);
		LatLng latlng2 = new LatLng(30.689879, 104.066855);
		marker2.setPosition(latlng2);
		marker2.setDraggable(false);
		// 动画效果
		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		giflist.add(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));
		giflist.add(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
		/*aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(Constants.ZHENGZHOU).title("郑州市").icons(giflist)
				.draggable(true).period(10));*/

		//drawMarkers();// 添加10个带有系统默认icon的marker
	}

	/**
	 * 绘制系统默认的1种marker背景图片
	 */
	public void drawMarkers() {
		Marker marker = aMap.addMarker(new MarkerOptions()
				.position(latlng)
				.title("好好学习")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.showInfoWindow();// 设置默认显示一个infowinfow
	}

	/**
	 * 对marker标注点点击响应事件
	 */
	@Override
	public boolean onMarkerClick(final Marker marker) {
		if (marker.equals(marker2)) {
			if (aMap != null) {
				//jumpPoint(marker);
			}
		}

		return false;
	}

	/**
	 * marker点击时跳动一下
	 */
	public void jumpPoint(final Marker marker) {
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = aMap.getProjection();
		Point startPoint = proj.toScreenLocation(Constants.XIAN);
		startPoint.offset(0, -100);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;

		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);
				double lng = t * Constants.XIAN.longitude + (1 - t)
						* startLatLng.longitude;
				double lat = t * Constants.XIAN.latitude + (1 - t)
						* startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));
				aMap.invalidate();// 刷新地图
				if (t < 1.0) {
					handler.postDelayed(this, 16);
				}
			}
		});

	}

	/**
	 * 监听点击infowindow窗口事件回调
	 */
	@Override
	public void onInfoWindowClick(Marker marker) {
		ToastUtil.show(this, "你点击了infoWindow窗口" + marker.getTitle());
	}

	/**
	 * 监听拖动marker时事件回调
	 */
	@Override
	public void onMarkerDrag(Marker marker) {
		String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
				+ marker.getPosition().latitude + ","
				+ marker.getPosition().longitude + ")";

	}

	/**
	 * 监听拖动marker结束事件回调
	 */
	@Override
	public void onMarkerDragEnd(Marker marker) {

	}

	/**
	 * 监听开始拖动marker事件回调
	 */
	@Override
	public void onMarkerDragStart(Marker marker) {

	}

	/**
	 * 监听amap地图加载成功事件回调
	 */
	@Override
	public void onMapLoaded() {
		// 设置所有maker显示在当前可视区域地图中
		LatLngBounds bounds = new LatLngBounds.Builder()
				.include(Constants.XIAN).include(Constants.CHENGDU)
				.include(latlng).include(Constants.ZHENGZHOU).include(Constants.BEIJING).build();
		aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
	}

	/**
	 * 监听自定义infowindow窗口的infocontents事件回调
	 */
	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	/**
	 * 监听自定义infowindow窗口的infowindow事件回调
	 */
	@Override
	public View getInfoWindow(Marker marker) {

		return null;
	}

	/**
	 * 自定义infowinfow窗口
	 */
	public void render(Marker marker, View view) {

	}

	@Override
	public void onClick(View v) {

	}
}
