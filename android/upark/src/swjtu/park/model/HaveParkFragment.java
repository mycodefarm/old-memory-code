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
	 ��ӳ�λ���
	 */
	private LinearLayout ll_addpark;
	private Button btn_location,btn_place,btn_submit;
	private TextView tv_place,tv_show_park;
	private ImageView iv_addpark,iv_park_pic;
	private boolean isAddOpen = false;//�Ƿ��������
	private List<Integer> isCanSubmit;//�ж��Ƿ��ܹ��ύ
	
	//����Ƭ
	private final int PHOTO_FROM_CAMERA = 0;//�����
	private final int PHOTO_FROM_ALBUM = 1;//�����
	private Uri photoUri;
	
	//�б����
	private ListView lv_show_park;
	private List<AddParkListItem> myParkList;
	private ParkListAdapter adapter;
	private ProgressBar pb_bar;
	private String location_str;
	/*
	 * ��λ���
	 */
	private LocationClient mLocationClient;
	private MyLocationListener mListener = new MyLocationListener();
	
	//GPS��λ
//	private LocationManager locationManager;
//	private String provider;
	
	// ʵ����PoiSearch����  
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
	 * ��ʼ���ؼ�
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
			handlePhoto("��λ��Ƭ");
			break;
		case R.id.btn_addpark_location:
			handleLocation();
			break;
		case R.id.btn_addpark_place:
			break;
		case R.id.btn_addpark_submit:
			//�رն�λ����
//			if(null!=locationManager){
//				locationManager.removeUpdates(locationListener);
//			}
			
			if(isCanSubmit.contains(1)&&isCanSubmit.contains(2)){
				handleFinalSubmit();
				Toast.makeText(getContext(), "��ӳɹ������ύ���", Toast.LENGTH_SHORT).show();
				handleSubmit();
			}else{
				Toast.makeText(getContext(), "���ʧ�ܣ���������Ϣ", Toast.LENGTH_SHORT).show();				
			}
			
			break;
		case R.id.tv_blow_park:
			handleParkList();
			break;
		default:break;
		}
	}
	private void handleFinalSubmit() {
		
		myParkList.add(new AddParkListItem( location_str,"�����"));
		adapter = new ParkListAdapter(myParkList, getContext()); 
		lv_show_park.setAdapter(adapter);
	}
	private void handleAddPark() {
		if(!isAddOpen){
			AlertDialog.Builder d = new AlertDialog.Builder(getContext());
			d.setCancelable(false);//�������ط�����ȡ��
			d.setTitle("��Ҫ��ʾ");
			d.setMessage("�����Ե��ȷ��������ӳ�λ��Ϣ��" +
					"Ҳ����ȡ��������Ϊ�˱�֤��λ��Ϣ��׼ȷ��" +
					"����ɺ������ĳ�λ֤�鵽����˾�˶�У��");
			d.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
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
			d.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
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
	 * ����λ�б����
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			myParkList = (List<AddParkListItem>) msg.obj;
			adapter = new ParkListAdapter(myParkList, getContext());
			lv_show_park.setAdapter(adapter);
			pb_bar.setVisibility(View.GONE);
			tv_show_park.setText("����������ӵĳ�λ,���ˢ��");
		};
	};
	private void handleParkList() {
		pb_bar.setVisibility(View.VISIBLE);
		tv_show_park.setText("���ڼ��أ����Ժ�...");
		new Thread(new LoadParkRunnable(handler, null)).start();
	}
	
	/*
	 * �����ύ
	 */
	private void handleSubmit() {
		ll_addpark.setVisibility(View.GONE);
		if(mLocationClient.isStarted()){
			mLocationClient.stop();//ֹͣ��λ
		}
		iv_addpark.setImageResource(R.drawable.add_park);
		isAddOpen = false;
	}
	private void handleLocation(){
		//GPS��λ
		//��ʼ��GPS��λ
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
//			Toast.makeText(getContext(), "���GPS", Toast.LENGTH_SHORT).show();
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
//		tv_place.setText("���ȣ�"+location.getLatitude()+"   γ�ȣ�"+location.getLongitude());
//		
//	}
	
//	LocationListener locationListener = new LocationListener() {
//		
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//			String TAG = "==================";
//			 switch (status) {
//	            //GPS״̬Ϊ�ɼ�ʱ
//	            case LocationProvider.AVAILABLE:
//	                Log.i(TAG, "��ǰGPS״̬Ϊ�ɼ�״̬");
//	                break;
//	            //GPS״̬Ϊ��������ʱ
//	            case LocationProvider.OUT_OF_SERVICE:
//	                Log.i(TAG, "��ǰGPS״̬Ϊ��������״̬");
//	                break;
//	            //GPS״̬Ϊ��ͣ����ʱ
//	            case LocationProvider.TEMPORARILY_UNAVAILABLE:
//	                Log.i(TAG, "��ǰGPS״̬Ϊ��ͣ����״̬");
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
//			Log.i("-===========","���ȣ�"+location.getLatitude()+"   γ�ȣ�"+location.getLongitude() );
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
//            //��һ�ζ�λ
//            case GpsStatus.GPS_EVENT_FIRST_FIX:
//                Log.i(TAG, "��һ�ζ�λ");
//                break;
//            //����״̬�ı�
//            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                Log.i(TAG, "����״̬�ı�");
//                //��ȡ��ǰ״̬
//                GpsStatus gpsStatus=locationManager.getGpsStatus(null);
//                //��ȡ���ǿ�����Ĭ�����ֵ
//                int maxSatellites = gpsStatus.getMaxSatellites();
//                //����һ�������������������� 
//                Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
//                int count = 0;     
//                while (iters.hasNext() && count <= maxSatellites) {     
//                    GpsSatellite s = iters.next();     
//                    count++;     
//                }   
//                System.out.println("��������"+count+"������");
//                tv_show_park.setText("��������"+count+"������");
//                break;
//            //��λ����
//            case GpsStatus.GPS_EVENT_STARTED:
//                Log.i(TAG, "��λ����");
//                break;
//            //��λ����
//            case GpsStatus.GPS_EVENT_STOPPED:
//                Log.i(TAG, "��λ����");
//                break;
//            }
//		}
//	};
	
	///////////////////////�����ǰٶȶ�λ////////////////////////
	/*
	 * �ٶȶ�λ�ļ�����
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
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// ��λ������ÿСʱ
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// ��λ����
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// ��λ��
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps��λ�ɹ�");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //��Ӫ����Ϣ
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("���綨λ�ɹ�");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
                sb.append("\ndescribe : ");
                sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
                location_str = "���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��";
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
                location_str = "��λʧ�ܣ����������Ƿ�ͨ��";
            }
sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
            Log.i("-------BaiduLocationApiDem", sb.toString());
            
            if(null==location.getAddrStr()){
            	location_str = "û�������޷���ȡλ��";
            }else{
            	location_str = location.getAddrStr();
            	isCanSubmit.add(1);
            }
            tv_place.setText(location_str+"\n���ȣ�"+location.getLatitude()+
            		"  γ�ȣ�"+location.getLongitude());
		}
	}
	///////////////////////////////////////////////////////
	/*
	 * ����ͷ�񣬷�Ϊ�ϴ�������
	 */
	private void handlePhoto(String title) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle(title);
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton("�ֳ�����", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				getThePhoto(PHOTO_FROM_CAMERA);
			}
		});
		dialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog.setNeutralButton("����ȡ", new DialogInterface.OnClickListener() {
			
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
			Log.i("--------get the photo file", "��Ƭ�ļ���ȡʧ��");
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
