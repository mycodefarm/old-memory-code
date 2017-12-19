package swjtu.park.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.test.MoreAsserts;

public class MyOrientationListener implements SensorEventListener{

	private Context mContext;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private float lastX;
	
	public MyOrientationListener(Context context){
		this.mContext = context;
	}
	
	//��ʼ����
	@SuppressWarnings("deprecation")
	public void start(){
		mSensorManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);
		if(mSensorManager!=null){
			//��÷��򴫸���
			mSensor = mSensorManager
					.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		}
		if(mSensor!=null){
			mSensorManager.registerListener(this, mSensor,
					SensorManager.SENSOR_DELAY_UI);
		}
	}
	
	//��������
	public void stop(){
		mSensorManager.unregisterListener(this);
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
			float x = event.values[SensorManager.DATA_X];
			
			if(Math.abs(x - lastX)>1.0){
				if(mOnOrientationListener!=null){
					mOnOrientationListener.onOrientationChanged(x);
				}
			}
			
			lastX = x;
		}
	}

	//���ȱ仯��ʱ����
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	private OnOrientationListener mOnOrientationListener;
	
	
	public void setmOnOrientationListener(
			OnOrientationListener mOnOrientationListener) {
		this.mOnOrientationListener = mOnOrientationListener;
	}

	//�ص��ӿ�
	public interface OnOrientationListener{
		void onOrientationChanged(float x);
	}
}