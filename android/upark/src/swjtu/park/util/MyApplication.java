package swjtu.park.util;

import android.app.Activity;
import android.content.Context;

public class MyApplication {
	static Context mactivity;
	public static void setInstance(Context activity){
		mactivity = activity;
	}
	public static Context getInstance(){
		return mactivity;
	}
}
