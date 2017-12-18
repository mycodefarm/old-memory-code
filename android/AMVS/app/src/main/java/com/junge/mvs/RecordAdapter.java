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

import static java.lang.Integer.parseInt;

/**
 * Created by dell on 2016/8/2.
 * list的适配器
 */
public class RecordAdapter extends BaseAdapter {

    private ArrayList<RecordBean> list = null;
    private Context mContext = null;
    private LayoutInflater inflater = null;
    private  Boolean isDS;

    public RecordAdapter(ArrayList<RecordBean> list, Context context,boolean isDS){
        this.list = list;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.isDS=isDS;
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
            view = inflater.inflate(R.layout.record_item,viewGroup,false);
            holder = new ViewHolder();
            holder.arr_name = (TextView)view.findViewById(R.id.tv_arr_name);
            holder.arr_num = (TextView)view.findViewById(R.id.tv_arr_num);
            holder.arr_order=(TextView)view.findViewById(R.id.tv_arr_order);
            holder.text=(TextView)view.findViewById(R.id.peonum);
            if(isDS)
                holder.text.setText("人数：");
            else
                holder.text.setText("站点：");
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final RecordBean bean = list.get(i);

        holder.arr_name.setText(bean.stationsName);
        if(isDS)
            holder.arr_num.setText(bean.peoNum+"/"+(parseInt(bean.peoNum)+2));
        else
            holder.arr_num.setText(bean.peoNum);
        holder.arr_order.setText(bean.order);
        return view;
    }

    class ViewHolder{
        public TextView arr_name;
        public TextView arr_num;
        public TextView arr_order;
        public TextView text;
    }
}
