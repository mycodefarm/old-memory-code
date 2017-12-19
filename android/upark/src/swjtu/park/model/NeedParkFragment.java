package swjtu.park.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;

import swjtu.park.R;
import swjtu.park.activity.ParkGuideActivity;
import swjtu.park.util.Info;
import swjtu.park.util.MyApplication;
import swjtu.park.util.MyOrientationListener;
import swjtu.park.util.MyOrientationListener.OnOrientationListener;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NeedParkFragment extends Fragment implements OnInfoWindowClickListener,
	OnClickListener,OnGetSuggestionResultListener{
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	
	private static final String APP_FOLDER_NAME = "Park";
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	private String mSDCardPath = null;
	
	//定位相关变量
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude,mLongititude;//经纬度
	private float mCurrentX;
	
	//定位图片相关
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener mOrientationListener;
	private LocationMode mLocationMode;
	
	//覆盖物相关
	private BitmapDescriptor mMaker;
	private LinearLayout ll_overlay;
	
	private List<Info> info_list;
	
	//付款选时间相关
	private Button btn_start_date,btn_start_time,btn_end_date,
			btn_sure_order,btn_navigation,btn_end_time;
	private TextView tv_money,tv_canbeuse_time;
	private LinearLayout ll_hide;
	
	//搜索相关变量
	private AutoCompleteTextView act_city;
	private SuggestionSearch mSuggestionSearch;
	private ArrayAdapter<String> sugAdapter;
	private Button btn_search_place;
	
	//菜单栏展开动画相关
	private int[] menu_img = {R.id.iv_menu_location,R.id.iv_menu_navigation,
			R.id.iv_menu};
	private List<ImageView> menu_list;
	private boolean isMenuOpen = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view =  inflater.inflate(R.layout.fragment_needpark, null,false);
		
		initView(view);
		
		//初始化标记
		initMaker();
		
		//初始化定位
		initLocation();
		
		//
		
		
		//初始化导航
		initNavigation();
		
		//初始化事件
		initEvent();
		
//		addOverlays(info_list);
//		myAddOverlay(0.5);
//		myAddOverlay(1.0);
		
		return view;
	}
	
	private void initInfoList() {
		info_list.add(new Info(mLatitude+0.01,mLongititude+0.01,R.drawable.a01,"park1","距离200米",1450));
		info_list.add(new Info(mLatitude+0.02,mLongititude+0.02,R.drawable.a02,"park2","距离500米",450));
		info_list.add(new Info(mLatitude-0.02,mLongititude+0.01,R.drawable.a03,"park3","距离400米",150));
		info_list.add(new Info(mLatitude+0.01,mLongititude-0.02,R.drawable.a04,"park4","距离300米",145));
	}
	private void initView(View view){
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		
		ll_overlay = (LinearLayout)view.findViewById(R.id.ll_overlay_layout);
		btn_start_date = (Button)view.findViewById(R.id.btn_start_date);
		btn_start_time = (Button)view.findViewById(R.id.btn_start_time);
		btn_end_date = (Button)view.findViewById(R.id.btn_end_date);
		btn_end_time = (Button)view.findViewById(R.id.btn_end_time);
		btn_sure_order = (Button)view.findViewById(R.id.btn_sure_order);
		btn_navigation = (Button)view.findViewById(R.id.btn_navigation_now);		
		tv_canbeuse_time = (TextView)view.findViewById(R.id.tv_can_be_used_time);
		tv_money = (TextView)view.findViewById(R.id.tv_total_money);
		ll_hide = (LinearLayout)view.findViewById(R.id.ll_hide_window);
		btn_search_place = (Button)view.findViewById(R.id.btn_search_place);
		
		info_list = new ArrayList<Info>();
		
		//搜索
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		act_city = (AutoCompleteTextView)view.findViewById(R.id.act_city);
		sugAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line);
		act_city.setAdapter(sugAdapter);
		
		//菜单
		menu_list = new ArrayList<ImageView>();
		for(int i=0;i<menu_img.length;i++){
			ImageView ib = (ImageView)view.findViewById(menu_img[i]);
			ib.setOnClickListener(this);
			menu_list.add(ib);
		}
	}
	
	private void initNavigation() {
		if(initDirs()){
			initNavi();
		}
	}

	private void initMaker() {
		mMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
	}

	private void initLocation() {
		mLocationMode = LocationMode.NORMAL;
		
		mLocationClient = new LocationClient(getContext());
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		
		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd0911");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		
		//初始化图标
		mIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.map_gps_locked);
		mOrientationListener = new MyOrientationListener(getContext());
		
		mOrientationListener
			.setmOnOrientationListener(new OnOrientationListener() {
				
				@Override
				public void onOrientationChanged(float x) {
					mCurrentX = x;
				}
			});
//		addOverlays(info_list);
		
//		myAddOverlay(0.5);
//		myAddOverlay(1.0);
	}

	@Override
	public void onStart() {
		super.onStart();
		//开启定位
		mBaiduMap.setMyLocationEnabled(true);////不要忘了开启
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
		}
		//开启方向传感器
		mOrientationListener.start();
	}
	@Override
	public void onStop() {
		super.onStop();
		//停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		//停止方向传感器
		mOrientationListener.stop();
	}
	@Override
	public void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override
	public void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override
	public void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
     }
    
    private class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			MyLocationData data = new MyLocationData.Builder()//
				.direction(mCurrentX)//
				.accuracy(location.getRadius())//
				.latitude(location.getLatitude())//
				.longitude(location.getLongitude())//
				.build();
			mBaiduMap.setMyLocationData(data);
			
			//设置自定义图标,模式切换
			MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode,true , mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);
			
			
			//更新经纬度
			mLatitude = location.getLatitude();
			mLongititude = location.getLongitude();
			
			Log.i("============", "===========");
			//第一次进入
			if(isFirstIn){
				
				//初始化标记数据
				initInfoList();
				
				addOverlays(info_list);
				
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;
				
				Toast.makeText(getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
			}
		}
    }
    
    
    
    
   @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		   inflater.inflate(R.menu.main, menu);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.item_location:
    		getMyLocation();
    		break;
//    	case R.id.item_mode_normal:
//    		mLocationMode = LocationMode.NORMAL;
//    		break;
//    	case R.id.item_mode_follow:
//    		mLocationMode = LocationMode.FOLLOWING;
//    		break;
//    	case R.id.item_mode_compass:
//    		mLocationMode = LocationMode.COMPASS;
//    		break;
    	case R.id.item_baidu_cordi:
    		if(BaiduNaviManager.isNaviInited()){
    			routeplanToNavi(CoordinateType.BD09LL);
    		}
    		break;
    	}
    	return true;
    }

    /*
     * 添加覆盖物
     * 有服务器传值回来后加个参数，List<Info> infos,先获得info的值
     */
    private void addOverlays(List<Info> infos) {
//    	mBaiduMap.clear();//清楚图层
    	
    	LatLng latLng = null;
    	Marker marker = null;
    	OverlayOptions options;
    	for(Info info:infos){
    		//经纬度
    		latLng = new LatLng(info.getLatitude(), info.getLongitude());
    		//图标
    		options = new MarkerOptions().
    				position(latLng).
    				icon(mMaker)
    				.zIndex(9);
    		//zIndex:值越大，层越高
    		marker = (Marker)( mBaiduMap.addOverlay(options));
    		Bundle bundle = new Bundle();
    		bundle.putSerializable("info", info);
    		marker.setExtraInfo(bundle);
    		
    	}
    	
    	//移动位置让我们看得见
//    	MapStatusUpdate msu = MapStatusUpdateFactory
//    			.newLatLng(latLng);
//    	mBaiduMap.setMapStatus(msu);
	}

	//返回到我的位置
	private void getMyLocation() {
		LatLng latLng = new LatLng(mLatitude, mLongititude);
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.animateMapStatus(msu);
	}
	
	/*
	 * 覆盖物点击事件
	 */
	private void initEvent(){
		
		btn_search_place.setOnClickListener(this);
		btn_end_date.setOnClickListener(this);
		btn_end_time.setOnClickListener(this);
		btn_start_date.setOnClickListener(this);
		btn_start_time.setOnClickListener(this);
		btn_sure_order.setOnClickListener(this);
		btn_navigation.setOnClickListener(this);
		ll_hide.setOnClickListener(this);
		
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				Bundle extraInfo = marker.getExtraInfo();
				Info info = (Info)extraInfo.getSerializable("info");
				ImageView iv = (ImageView) ll_overlay.findViewById(R.id.iv_img);
				iv.setImageResource(info.getImgId());
				
				/*
				 * 点击覆盖物显示文字在上面
				 */
				InfoWindow infoWindow;
				TextView tv = new TextView(getContext());
				tv.setBackground(getResources().getDrawable(R.drawable.location_tips));
				tv.setPadding(30, 20, 30, 30);
				tv.setText(info.getName());
				tv.setTextColor(Color.WHITE);
				
				final LatLng latLng = marker.getPosition();
				//将经纬度坐标转化成屏幕坐标
				Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
				p.y -= 100;//设置偏移量,为了不把覆盖物遮住
				//再把屏幕坐标转化成经纬度
				LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);
				
				//点击信息隐藏
				infoWindow = new InfoWindow(tv, ll, 0);
				
				mBaiduMap.showInfoWindow(infoWindow);
				
				ll_overlay.setVisibility(View.VISIBLE);
				
				return true;
			}
		});
		
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}
			
			@Override
			public void onMapClick(LatLng arg0) {
				ll_overlay.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();
				
				if(isMenuOpen){
					closeMenu();
				}
			}
		});
		
		//搜索
		act_city.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(0>=s.length()){
					return ;
				}
//				String city = act_city.getText().toString();
				mSuggestionSearch.
					requestSuggestion(new SuggestionSearchOption()
							.keyword(s.toString())
							.city("成都"));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	
	/*
	 * 导航
	 */
	private void initNavi() {
//		StoragePathManager maStoragePathManager = new StoragePathManager(getContext());
//		String mSDCardPath = maStoragePathManager.getExternalStoragePath();
//		String APP_FOLDER_NAME = maStoragePathManager.getAppStoragePath();
		
		
		BaiduNaviManager.getInstance().init(getActivity(), mSDCardPath, APP_FOLDER_NAME,
		new NaviInitListener() {
	        @Override
	        public void onAuthResult(int status, String msg) {
	        	final String authinfo;
	                if (0 == status) {
	                    authinfo = "key校验成功!";
	                } else {
	                    authinfo = "key校验失败, " + msg;
	                }
	                getActivity().runOnUiThread(new Runnable() {
	 
	                    @Override
	                    public void run() {
//	                        Toast.makeText(getContext(), authinfo, Toast.LENGTH_LONG).show();
	                    }
	                });
	        }
	 
	        public void initSuccess() {
//			Toast.makeText(getContext(), "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
		}
	 
		public void initStart() {
//			Toast.makeText(getContext(), "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
		}
	 
		public void initFailed() {
			Toast.makeText(getContext(), "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
			}
		}, null /*mTTSCallback*/);
	}

	@Override
	public void onInfoWindowClick() {
		mBaiduMap.hideInfoWindow();
	}
	
	/*
	 * 跳转到导航
	 */
	private void routeplanToNavi(CoordinateType coType) {
		BNRoutePlanNode sNode = null;
		BNRoutePlanNode eNode = null;
		if(coType==CoordinateType.BD09LL){
			sNode = new BNRoutePlanNode(126.30784537597782, 50.057009624099436, "百度大厦", null, coType);
			eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", null, coType);
		}
		if (sNode != null && eNode != null) {
			List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
			list.add(sNode);
			list.add(eNode);
			BaiduNaviManager.getInstance().launchNavigator(getActivity(), list, 1, true, new MyRoutePlanListener(sNode));
		}
	}
	
	public class MyRoutePlanListener implements RoutePlanListener{

		private BNRoutePlanNode node = null;
		
		public MyRoutePlanListener(BNRoutePlanNode node){
			this.node = node;
		}
		
		@Override
		public void onJumpToNavigator() {
			Intent intent = new Intent(getContext(), ParkGuideActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) node);
			intent.putExtras(bundle);
			startActivity(intent);
			
			Toast.makeText(getContext(), "路径规划成功",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onRoutePlanFailed() {
			Toast.makeText(getContext(), "路径规划失败",Toast.LENGTH_SHORT).show();
		}
	}
	/*
	 * 获取SD卡根目录
	 */
	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	/*
	 * 初始化文件目录
	 */
	private boolean initDirs() {
		mSDCardPath = getSdcardDir();
		if (mSDCardPath == null) {
			return false;
		}
		File f = new File(mSDCardPath, APP_FOLDER_NAME);
		if (!f.exists()) {
			try {
				f.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_start_date:
			handleDate(btn_start_date,1);
			break;
		case R.id.btn_start_time:
			handleTime(btn_start_time,1);
			break;
		case R.id.btn_end_date:
			handleDate(btn_end_date,2);
			break;
		case R.id.btn_end_time:
			handleTime(btn_end_time,2);
			break;
		case R.id.btn_navigation_now:
			break;
		case R.id.btn_sure_order:
			handleMoney();
			break;
		case R.id.ll_hide_window:
			ll_overlay.setVisibility(View.GONE);
			break;
		case R.id.btn_search_place:
			Toast.makeText(getContext(), "待完成...", Toast.LENGTH_SHORT).show();
			break;
		case R.id.iv_menu:
			if(isMenuOpen){
				closeMenu();
			}else{
				openMenu();
			}
			break;
		case R.id.iv_menu_location:
			Toast.makeText(getContext(), "定位成功", Toast.LENGTH_SHORT).show();
			getMyLocation();
    		break;
    	case R.id.iv_menu_navigation:
    		Toast.makeText(getContext(), "导航中", Toast.LENGTH_SHORT).show();
    		if(BaiduNaviManager.isNaviInited()){
    			routeplanToNavi(CoordinateType.BD09LL);
    		}
			break;
		default:break;
		}
	}
	
	//菜单打开动画
	private void openMenu() {
		for(int i=0;i<menu_list.size()-1;i++){
			
			Log.i("ccccccccccc", menu_list.size()+menu_list.get(i).toString());
			
			ObjectAnimator a = ObjectAnimator.ofFloat(menu_list.get(i), "translationY",0f,(i+1)*100f);
			a.setDuration(300);
			a.setStartDelay(i*200);//层次感
			a.start();
		}
		isMenuOpen = true;
	}

	private void closeMenu() {
		for(int i=0;i<menu_list.size()-1;i++){
			
			Log.i("ccccccccccc", menu_list.size()+menu_list.get(i).toString());
			
			ObjectAnimator a = ObjectAnimator.ofFloat(menu_list.get(i), "translationY",(i+1)*100f,0f);
			a.setDuration(300);
			a.setStartDelay(i*200);//层次感
			a.start();
		}
		isMenuOpen = false;
	}

	/*
	 * 获取日期
	 */
	private int sYear,sMonth,sDay=-1,sHour=-1,sMinute;
	private int eYear,eMonth,eDay=-1,eHour=-1,eMinute;
	
	private void handleDate(final Button btn,final int flag){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int monthOfYear = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		
		new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				btn.setText(year+"-"+(1+monthOfYear)+"-"+dayOfMonth);
				if(1==flag){
					sYear = year;
					sMonth = monthOfYear+1;
					sDay = dayOfMonth;
				}else{
					eYear = year;
					eMonth = monthOfYear;
					eDay = dayOfMonth;
				}
				handleMoney();
			}
		}, year, monthOfYear, dayOfMonth).show();
	}
	
	private void handleTime(final Button btn,final int flag){
		Calendar c = Calendar.getInstance();
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				btn.setText(hourOfDay+":"+minute);
				if(1==flag){
					sHour = hourOfDay;
					sMinute = minute;
				}else{
					eHour = hourOfDay;
					eMinute = minute;
				}
				handleMoney();
			}
		}, hourOfDay, minute, true).show();
	}
	
	/*
	 * 处理金额
	 */
	private void handleMoney(){
		if(sDay!=-1&&sHour!=-1&&eDay!=-1&&eHour!=-1){
			tv_money.setText("你猜多少钱");
		}
	}

	
	/*
	 * (non-Javadoc)
	 * 搜索
	 */
	
	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if(null==res||null==res.getAllSuggestions()){
			return ;
		}
		sugAdapter.clear();
		for(SuggestionResult.SuggestionInfo info:res.getAllSuggestions()){
			if(null!=info.key){
				sugAdapter.add(info.key);
			}
		}
		sugAdapter.notifyDataSetChanged();
	}
}








