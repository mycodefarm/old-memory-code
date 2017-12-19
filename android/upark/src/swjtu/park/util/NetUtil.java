package swjtu.park.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetUtil {
	public static boolean isNetWorkAvailable(Context context){
		ConnectivityManager cm = (ConnectivityManager)context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm.getActiveNetworkInfo()!=null){
			return cm.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
}
