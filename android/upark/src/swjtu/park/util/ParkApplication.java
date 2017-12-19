package swjtu.park.util;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class ParkApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(this);
	}
}
