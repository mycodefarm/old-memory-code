package swjtu.park.ui;

import java.util.List;

import swjtu.park.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkListAdapter extends BaseAdapter{

	private List<AddParkListItem> list;
	private LayoutInflater inflater = null;
	
	public ParkListAdapter(List<AddParkListItem> list,Context context){
		this.list = list;
		this.inflater = LayoutInflater.from(context);
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
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.park_list_item, null);
			viewHolder.tv_left = (TextView)convertView.findViewById(R.id.tv_park_left);
			viewHolder.tv_right = (TextView)convertView.findViewById(R.id.tv_park_right);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		AddParkListItem item = list.get(position);
		viewHolder.tv_left.setText(item.text_left);
		viewHolder.tv_right.setText(item.text_right);
		if(viewHolder.tv_right.getText().equals("´ýÉóºË")){
			viewHolder.tv_right.setTextColor(Color.parseColor("#cccccc"));
		}else{
			viewHolder.tv_right.setTextColor(Color.parseColor("#33ccff"));
		}
		
//		convertView.setMinimumHeight(50);
		return convertView;
	}

	class ViewHolder{
		public TextView tv_left;
		public TextView tv_right;
	}
}
