package swjtu.park.util;

import java.util.ArrayList;
import java.util.List;

import swjtu.park.R;
import swjtu.park.ui.AddParkListItem;
import swjtu.park.ui.InfoListItemBean;

import android.os.Handler;
import android.os.Message;

public class LoadParkRunnable implements Runnable{

	private Handler handler;
	private String uri;//地址
	
	public LoadParkRunnable(Handler h,String uri){
		this.handler = h;
		this.uri = uri;
	}
	@Override
	public void run() {
		Message ms = new Message();
		ms.obj = getParks();
		handler.sendMessage(ms);
	}
	
	private List<AddParkListItem> getParks(){
		List<AddParkListItem> list = new ArrayList<AddParkListItem>();
		for(int i=0;i<2;i++){
			list.add(new AddParkListItem("成都市郫县西南交大17栋", "待审核"));
		}
		list.add(new AddParkListItem("成都市郫县西南交大15栋", "已审核"));
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}
}
