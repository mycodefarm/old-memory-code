package com.junge.mvs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jimo.mvs.R;
import com.junge.mvs.Stations;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/2.
 * list的适配器
 */
public class StationsAdapter extends BaseAdapter {

    private ArrayList<StationsBean> list = null;
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public StationsAdapter(ArrayList<StationsBean> list, Context context){
        this.list = list;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(null==view){
            view = inflater.inflate(R.layout.stations_item,viewGroup,false);
            holder = new ViewHolder();
            holder.arr_name = (TextView)view.findViewById(R.id.tv_arr_name);
            holder.arr_num = (TextView)view.findViewById(R.id.tv_arr_num);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final StationsBean bean = list.get(i);
        holder.arr_name.setText(bean.stationsName);
        holder.arr_num.setText(bean.peoNum+"");

        return view;
    }

    class ViewHolder{
        public TextView arr_name;
        public TextView arr_num;
    }
}
