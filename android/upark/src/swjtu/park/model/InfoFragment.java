package swjtu.park.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import swjtu.park.R;
import swjtu.park.activity.myupark.AboutSoftwareActivity;
import swjtu.park.activity.myupark.HelpAndFeedbackActivity;
import swjtu.park.activity.myupark.MyFavoriteActivity;
import swjtu.park.activity.myupark.OrderingItemActivity;
import swjtu.park.activity.myupark.RemindSettingActivity;
import swjtu.park.ui.InfoListItemBean;
import swjtu.park.ui.MyAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class InfoFragment extends Fragment implements OnClickListener{
	
	private ListView lv_info;
	private final String[]info_data={"订单详情","提醒设置","我的收藏","帮助与反馈","关于软件"};
	private final int[]imgMenuId={R.drawable.order_detail,R.drawable.remind_me,R.drawable.my_favorite,
			R.drawable.help_feedback,R.drawable.about_us};
	private List<InfoListItemBean> list;
	private MyAdapter adapter;
	
	private Button btn_quit;
	
	//头像相关
	private ImageView iv_user_photo;
	private TextView tv_user_name;
	private final int PHOTO_FROM_CAMERA = 0;//从相机
	private final int PHOTO_FROM_ALBUM = 1;//从相册
	private Uri photoUri;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_info, null,false);
		lv_info = (ListView)view.findViewById(R.id.lv_info);
		btn_quit = (Button)view.findViewById(R.id.btn_quit);
		iv_user_photo = (ImageView)view.findViewById(R.id.iv_user_photo);
		
		loadData();
		
		initEvent();
		return view;
	}
	
	private void loadData(){
		list = new ArrayList<InfoListItemBean>();
		for(int i=0;i<info_data.length;i++){
			InfoListItemBean bean = new InfoListItemBean(imgMenuId[i], info_data[i]);
			list.add(bean);
		}
		adapter = new MyAdapter(getContext(), list);
		lv_info.setAdapter(adapter);
	}
	
	private void initEvent(){
		btn_quit.setOnClickListener(this);
		iv_user_photo.setOnClickListener(this);
		
		lv_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Intent intent00 = new Intent(getContext(),
							OrderingItemActivity.class);
					startActivity(intent00);
					break;
				case 1:
//					Intent intent01 = new Intent(getContext(),
//							RemindSettingActivity.class);
//					startActivity(intent01);
					Toast.makeText(getContext(), "待完成...", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(getContext(), "待完成...", Toast.LENGTH_SHORT).show();
//					Intent intent02 = new Intent(getContext(),
//							MyFavoriteActivity.class);
//					startActivity(intent02);
					break;
				case 3:
					Intent intent03 = new Intent(getContext(),
							HelpAndFeedbackActivity.class);
					startActivity(intent03);
					break;
				case 4:
					Intent intent04 = new Intent(getContext(),
							AboutSoftwareActivity.class);
					startActivity(intent04);
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_quit:
			getActivity().finish();
			break;
		case R.id.iv_user_photo:
			handlePhoto("头像");
			break;
		default:break;
		}
	}

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
					iv_user_photo.setImageBitmap(bitmap);
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
