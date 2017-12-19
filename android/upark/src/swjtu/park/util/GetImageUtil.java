package swjtu.park.util;

import java.io.File;
import java.io.FileNotFoundException;

import swjtu.park.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class GetImageUtil {
	
	private final int PHOTO_FROM_CAMERA = 0;//从相机
	private final int PHOTO_FROM_ALBUM = 1;//从相册
	private Uri photoUri;
	private Context mContext;
	private Activity mActivity;
	
	public GetImageUtil(Context context,Activity activity){
		this.mContext = context;
		this.mActivity = activity;
	}
	
	/*
	 * 处理头像，分为上传和现拍
	 */
	public void handlePhoto(String title) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
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
	
	public void getThePhoto(int flag){
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
				mActivity.startActivityForResult(intent, PHOTO_FROM_ALBUM);
			}else
			{
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				mActivity.startActivityForResult(cameraIntent, PHOTO_FROM_CAMERA);
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
	
	public void cropImageUriByTakePhoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		setIntentParams(intent);
		mActivity.startActivityForResult(intent, PHOTO_FROM_ALBUM);
	}
	
	public Bitmap decodeUriAsBitmap()
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(photoUri));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
