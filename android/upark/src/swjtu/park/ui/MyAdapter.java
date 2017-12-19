package swjtu.park.ui;

import java.util.List;

import swjtu.park.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private List<InfoListItemBean> list;
	private LayoutInflater inflater = null;
	
	public MyAdapter(Context context,List<InfoListItemBean> list){
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if(null==convertView){
			convertView = inflater.inflate(R.layout.list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.img = (ImageView)convertView.findViewById(R.id.iv_list_item);
			viewHolder.text = (TextView)convertView.findViewById(R.id.tv_list_item);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		InfoListItemBean item = list.get(position);
		viewHolder.img.setBackgroundResource(item.imgId);
		viewHolder.text.setText(item.text);
		
		return convertView;
	}

	class ViewHolder{
		public ImageView img;
		public TextView text;
	}
}
