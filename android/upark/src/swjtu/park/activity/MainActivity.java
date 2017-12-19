package swjtu.park.activity;

import swjtu.park.R;
import swjtu.park.model.HaveParkFragment;
import swjtu.park.model.InfoFragment;
import swjtu.park.model.NeedParkFragment;
import swjtu.park.util.MyApplication;
import swjtu.park.util.NetUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private LinearLayout ll_need,ll_have,ll_info;
	
	private ImageView iv_need,iv_have,iv_info;
	
	private Fragment fm_need,fm_have,fm_info;
	
//	private MapView mMapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		MyApplication.setInstance(this);
		
		//网络判断
		if(!NetUtil.isNetWorkAvailable(this)){
			Toast.makeText(this, "网络不可用", Toast.LENGTH_LONG).show();
		}
		
		initView();
		initEvent();
		setSelect(0);
		
		/*
		 * 
		 */
//		setContentView(R.layout.fragment_needpark);
//		mMapView = (MapView)findViewById(R.id.bmapView);
	}

//	@Override
//	public void onDestroy() {  
//        super.onDestroy();  
//        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
//        mMapView.onDestroy();  
//    }  
//    @Override
//	public void onResume() {  
//        super.onResume();  
//        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
//        mMapView.onResume();  
//        }  
//    @Override
//	public void onPause() {  
//        super.onPause();  
//        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
//        mMapView.onPause();  
//        }  
	////////////////////////////
	private void initEvent() {
		ll_have.setOnClickListener(this);
		ll_need.setOnClickListener(this);
		ll_info.setOnClickListener(this);
	}

	private void initView() {
		
		
		
		ll_have = (LinearLayout)findViewById(R.id.ll_havepark);
		ll_need = (LinearLayout)findViewById(R.id.ll_needpark);
		ll_info = (LinearLayout)findViewById(R.id.ll_info);
		
		iv_have = (ImageView)findViewById(R.id.iv_havepark);
		iv_need = (ImageView)findViewById(R.id.iv_needpark);
		iv_info = (ImageView)findViewById(R.id.iv_info);
	}

	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId()) {
		case R.id.ll_needpark:
			setSelect(0);
			break;
		case R.id.ll_havepark:
			setSelect(1);
			break;
		case R.id.ll_info:
			setSelect(2);
			break;
		default:
			break;
		}
	}
	
	private void setSelect(int i)
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
		case 0:
			if (fm_need == null)
			{
				fm_need = new NeedParkFragment();
				transaction.add(R.id.fl_content, fm_need);
			} else
			{
				transaction.show(fm_need);
			}
			iv_need.setImageResource(R.drawable.needpark_press);
			break;
		case 1:
			if (fm_have == null)
			{
				fm_have = new HaveParkFragment();
				transaction.add(R.id.fl_content, fm_have);
			} else
			{
				transaction.show(fm_have);
				
			}
			iv_have.setImageResource(R.drawable.havapark_press);
			break;
		case 2:
			if (fm_info == null)
			{
				fm_info = new InfoFragment();
				transaction.add(R.id.fl_content, fm_info);
			} else
			{
				transaction.show(fm_info);
			}
			iv_info.setImageResource(R.drawable.info_press);
			break;
			default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (fm_need != null)
		{
			transaction.hide(fm_need);
		}
		if (fm_have != null)
		{
			transaction.hide(fm_have);
		}
		if (fm_info != null)
		{
			transaction.hide(fm_info);
		}
	}
	
	/**
	 * 切换图片至暗色
	 */
	private void resetImgs()
	{
		iv_have.setImageResource(R.drawable.havapark_normal);
		iv_need.setImageResource(R.drawable.needpark_normal);
		iv_info.setImageResource(R.drawable.info_normal);
	}
}
