package com.jimo.mvs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.junge.mvs.Stations;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/2.
 * list的适配器
 */
public class DArrDataAdapter extends BaseAdapter {

    private ArrayList<DArrDataBean> list = null;
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public DArrDataAdapter(ArrayList<DArrDataBean> list, Context context){
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
            view = inflater.inflate(R.layout.d_arr_item,viewGroup,false);
            holder = new ViewHolder();
            holder.arr_name = (TextView)view.findViewById(R.id.tv_arr_name);
            holder.arr_time = (TextView)view.findViewById(R.id.tv_arr_time);
            holder.arr_num = (TextView)view.findViewById(R.id.tv_arr_num);
            holder.arr_line = (TextView)view.findViewById(R.id.tv_arr_line);
            holder.tv_detail = (TextView)view.findViewById(R.id.tv_arr_detail);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final DArrDataBean bean = list.get(i);
        holder.arr_name.setText(bean.arrName);
        holder.arr_time.setText(bean.time);
        holder.arr_num.setText(bean.peoNum+"");
        holder.arr_line.setText(bean.lineName);

        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"yes",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(mContext, Stations.class);
                mContext.startActivity(i);
            }
        });
        return view;
    }

    class ViewHolder{
        public TextView arr_name;
        public TextView arr_time;
        public TextView arr_num;
        public TextView arr_line;
        public TextView tv_detail;
    }
}
