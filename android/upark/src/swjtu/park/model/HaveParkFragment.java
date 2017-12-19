package swjtu.park.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import swjtu.park.R;
import swjtu.park.ui.AddParkListItem;
import swjtu.park.ui.InfoListItemBean;
import swjtu.park.ui.ParkListAdapter;
import swjtu.park.util.GetImageUtil;
import swjtu.park.util.LoadParkRunnable;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HaveParkFragment extends Fragment implements OnClickListener{
	
	/*
	 添加车位相关
	 */
	private LinearLayout ll_addpark;
	private Button btn_location,btn_place,btn_submit;
	private TextView tv_place,tv_show_park;
	private ImageView iv_addpark,iv_park_pic;
	private boolean isAddOpen = false;//是否正在添加
	private List<Integer> isCanSubmit;//判断是否能够提交
	
	//传照片
	private final int PHOTO_FROM_CAMERA = 0;//从相机
	private final int PHOTO_FROM_ALBUM = 1;//从相册
	private Uri photoUri;
	
	//列表加载
	private ListView lv_show_park;
	private List<AddParkListItem> myParkList;
	private ParkListAdapter adapter;
	private ProgressBar pb_bar;
	private String location_str;
	/*
	 * 定位相关
	 */
	private LocationClient mLocationClient;
	private MyLocationListener mListener = new MyLocationListener();
	
	//GPS定位
//	private LocationManager locationManager;
//	private String provider;
	
	// 实例化PoiSearch对象  
    private PoiSearch poiSearch = PoiSearch.newInstance();  
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_havepark, null,false);
		
		initView(view);
		
		initEvent();
		return view;
	}
	/*
	 * 初始化控件
	 */
	private void initView(View view) {
		ll_addpark = (LinearLayout)view.findViewById(R.id.ll_add_park);
		btn_location = (Button)view.findViewById(R.id.btn_addpark_location);
		btn_place = (Button)view.findViewById(R.id.btn_addpark_place);
		btn_submit = (Button)view.findViewById(R.id.btn_addpark_submit);
		tv_place = (TextView)view.findViewById(R.id.tv_addpark_showplace);
		tv_show_park = (TextView)view.findViewById(R.id.tv_blow_park);
		iv_addpark = (ImageView)view.findViewById(R.id.iv_add_park);
		iv_park_pic = (ImageView)view.findViewById(R.id.iv_add_park_pic);
		lv_show_park = (ListView)view.findViewById(R.id.lv_addpark_data);
		pb_bar = (ProgressBar)view.findViewById(R.id.pb_loadpark);
		
		myParkList = new ArrayList<AddParkListItem>();
		adapter = new ParkListAdapter(myParkList, getContext()); 
		lv_show_park.setAdapter(adapter);
		
		isCanSubmit = new ArrayList<Integer>();
	}

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setScanSpan(3000);
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setLocationNotify(true);
		
		mLocationClient.setLocOption(option);
	}
	
	private void initEvent(){
		iv_addpark.setOnClickListener(this);
		btn_location.setOnClickListener(this);
		btn_place.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		iv_park_pic.setOnClickListener(this);
		tv_show_park.setOnClickListener(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv_add_park:
			
			handleAddPark();
			
			break;
		case R.id.iv_add_park_pic:
			handlePhoto("车位照片");
			break;
		case R.id.btn_addpark_location:
			handleLocation();
			break;
		case R.id.btn_addpark_place:
			break;
		case R.id.btn_addpark_submit:
			//关闭定位监听
//			if(null!=locationManager){
//				locationManager.removeUpdates(locationListener);
//			}
			
			if(isCanSubmit.contains(1)&&isCanSubmit.contains(2)){
				handleFinalSubmit();
				Toast.makeText(getContext(), "添加成功，已提交审核", Toast.LENGTH_SHORT).show();
				handleSubmit();
			}else{
				Toast.makeText(getContext(), "添加失败，请完善信息", Toast.LENGTH_SHORT).show();				
			}
			
			break;
		case R.id.tv_blow_park:
			handleParkList();
			break;
		default:break;
		}
	}
	private void handleFinalSubmit() {
		
		myParkList.add(new AddParkListItem( location_str,"待审核"));
		adapter = new ParkListAdapter(myParkList, getContext()); 
		lv_show_park.setAdapter(adapter);
	}
	private void handleAddPark() {
		if(!isAddOpen){
			AlertDialog.Builder d = new AlertDialog.Builder(getContext());
			d.setCancelable(false);//点其他地方不可取消
			d.setTitle("重要提示");
			d.setMessage("您可以点击确定继续添加车位信息，" +
					"也可以取消，但是为了保证车位信息的准确，" +
					"请完成后带上你的车位证书到本公司核对校验");
			d.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(!isAddOpen){
						mLocationClient = new LocationClient(getContext());
						mLocationClient.registerLocationListener(mListener);
						
						ll_addpark.setVisibility(View.VISIBLE);
						iv_addpark.setImageResource(R.drawable.chacha);
						isAddOpen = true;
					}else{
						handleSubmit();
					}
				}
			});
			d.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			d.create().show();
		}else{
			handleSubmit();
		}
	}
	/*
	 * 处理车位列表加载
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			myParkList = (List<AddParkListItem>) msg.obj;
			adapter = new ParkListAdapter(myParkList, getContext());
			lv_show_park.setAdapter(adapter);
			pb_bar.setVisibility(View.GONE);
			tv_show_park.setText("下面是您添加的车位,点击刷新");
		};
	};
	private void handleParkList() {
		pb_bar.setVisibility(View.VISIBLE);
		tv_show_park.setText("正在加载，请稍后...");
		new Thread(new LoadParkRunnable(handler, null)).start();
	}
	
	/*
	 * 处理提交
	 */
	private void handleSubmit() {
		ll_addpark.setVisibility(View.GONE);
		if(mLocationClient.isStarted()){
			mLocationClient.stop();//停止定位
		}
		iv_addpark.setImageResource(R.drawable.add_park);
		isAddOpen = false;
	}
	private void handleLocation(){
		//GPS定位
		//初始化GPS定位
//		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//		
//		Log.i("---------------1", ""+(locationManager==null));
//		
//		List<String> providerList = locationManager.getProviders(true);
//		if(providerList.contains(LocationManager.GPS_PROVIDER)){
//			provider = LocationManager.GPS_PROVIDER;
//			Log.i("---------------2", "------------");
//		}else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
//			provider = LocationManager.NETWORK_PROVIDER;
//		}else{
//			Toast.makeText(getContext(), "请打开GPS", Toast.LENGTH_SHORT).show();
//			Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//			startActivity(it);	
//			return;
//		}
//		Location location = locationManager.getLastKnownLocation(provider);
//		if(null!=location){
//			showLocation(location);
//		}
//		
//		locationManager.addGpsStatusListener(listener);
//		
//		locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
//		
		
		initLocation();
		
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
		}
	}
//	private void showLocation(Location location) {
//		tv_place.setText("经度："+location.getLatitude()+"   纬度："+location.getLongitude());
//		
//	}
	
//	LocationListener locationListener = new LocationListener() {
//		
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//			String TAG = "==================";
//			 switch (status) {
//	            //GPS状态为可见时
//	            case LocationProvider.AVAILABLE:
//	                Log.i(TAG, "当前GPS状态为可见状态");
//	                break;
//	            //GPS状态为服务区外时
//	            case LocationProvider.OUT_OF_SERVICE:
//	                Log.i(TAG, "当前GPS状态为服务区外状态");
//	                break;
//	            //GPS状态为暂停服务时
//	            case LocationProvider.TEMPORARILY_UNAVAILABLE:
//	                Log.i(TAG, "当前GPS状态为暂停服务状态");
//	                break;
//	            }
//		}
//		
//		@Override
//		public void onProviderEnabled(String provider) {
//			Location location = locationManager.getLastKnownLocation(provider);
//			showLocation(location);
//		}
//		
//		@Override
//		public void onProviderDisabled(String provider) {
//			
//		}
//		
//		@Override
//		public void onLocationChanged(Location location) {
//			showLocation(location);
//			Log.i("-===========","经度："+location.getLatitude()+"   纬度："+location.getLongitude() );
//		}
//	};
//	
//	GpsStatus.Listener listener = new GpsStatus.Listener() {
//		
//		String TAG = "00000000000000";
//		
//		@Override
//		public void onGpsStatusChanged(int event) {
//			switch (event) {
//            //第一次定位
//            case GpsStatus.GPS_EVENT_FIRST_FIX:
//                Log.i(TAG, "第一次定位");
//                break;
//            //卫星状态改变
//            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                Log.i(TAG, "卫星状态改变");
//                //获取当前状态
//                GpsStatus gpsStatus=locationManager.getGpsStatus(null);
//                //获取卫星颗数的默认最大值
//                int maxSatellites = gpsStatus.getMaxSatellites();
//                //创建一个迭代器保存所有卫星 
//                Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
//                int count = 0;     
//                while (iters.hasNext() && count <= maxSatellites) {     
//                    GpsSatellite s = iters.next();     
//                    count++;     
//                }   
//                System.out.println("搜索到："+count+"颗卫星");
//                tv_show_park.setText("搜索到："+count+"颗卫星");
//                break;
//            //定位启动
//            case GpsStatus.GPS_EVENT_STARTED:
//                Log.i(TAG, "定位启动");
//                break;
//            //定位结束
//            case GpsStatus.GPS_EVENT_STOPPED:
//                Log.i(TAG, "定位结束");
//                break;
//            }
//		}
//	};
	
	///////////////////////下面是百度定位////////////////////////
	/*
	 * 百度定位的监听类
	 */
	class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			 //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
                location_str = "网络不同导致定位失败，请检查网络是否通畅";
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                location_str = "定位失败，请检查网络是否通畅";
            }
sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
            Log.i("-------BaiduLocationApiDem", sb.toString());
            
            if(null==location.getAddrStr()){
            	location_str = "没有网络无法获取位置";
            }else{
            	location_str = location.getAddrStr();
            	isCanSubmit.add(1);
            }
            tv_place.setText(location_str+"\n经度："+location.getLatitude()+
            		"  纬度："+location.getLongitude());
		}
	}
	///////////////////////////////////////////////////////
	/*
	 * 处理头像，分为上传和现拍
	 */
	private void handlePhoto(String title) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle(title);
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton("现场拍照", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getThePhoto(PHOTO_FROM_CAMERA);
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog.setNeutralButton("相册获取", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getThePhoto(PHOTO_FROM_ALBUM);
			}
		});
		dialog.show();
	}
	
	private void getThePhoto(int flag){
		try{
			File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			File picFile = new File(pictureFileDir, "upload_photo.jpeg");
			if (!picFile.exists()) {
				picFile.createNewFile();
			}
			photoUri = Uri.fromFile(picFile);
			
			if (flag==PHOTO_FROM_ALBUM)
			{
				Intent intent = getCropImageIntent();
				startActivityForResult(intent, PHOTO_FROM_ALBUM);
			}else
			{
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(cameraIntent, PHOTO_FROM_CAMERA);
			}
		}catch(Exception e){
			Log.i("--------get the photo file", "照片文件获取失败");
		}
	}
	/*
	 * 
	 */
	public Intent getCropImageIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		setIntentParams(intent);
		return intent;
	}
	/*
	 * 
	 */
	private void setIntentParams(Intent intent)
	{
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 600);
		intent.putExtra("noFaceDetection", true); // no face detection
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case PHOTO_FROM_ALBUM:
			try
			{
				if (photoUri != null) 
				{
					Bitmap bitmap = decodeUriAsBitmap(photoUri);
					iv_park_pic.setImageBitmap(bitmap);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		case PHOTO_FROM_CAMERA:
			try 
			{
				cropImageUriByTakePhoto();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		}
		isCanSubmit.add(2);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void cropImageUriByTakePhoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		setIntentParams(intent);
		startActivityForResult(intent, PHOTO_FROM_ALBUM);
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri)
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
