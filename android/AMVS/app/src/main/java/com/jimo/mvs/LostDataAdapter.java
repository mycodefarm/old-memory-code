package com.jimo.mvs;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/2.
 * list的适配器
 */
public class LostDataAdapter extends BaseAdapter {

    private ArrayList<LostDataBean> list = null;
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public LostDataAdapter(ArrayList<LostDataBean> list,Context context){
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
            view = inflater.inflate(R.layout.lost_item,viewGroup,false);
            holder = new ViewHolder();
            holder.tv_content = (TextView)view.findViewById(R.id.tv_lost_content);
            holder.tv_name = (TextView)view.findViewById(R.id.tv_lost_name);
            holder.tv_date = (TextView)view.findViewById(R.id.tv_lost_date);
            holder.tv_contact = (TextView)view.findViewById(R.id.tv_lost_contact);
            holder.tv_copy = (TextView)view.findViewById(R.id.tv_lost_copy);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        final LostDataBean bean = list.get(i);
        holder.tv_content.setText(bean.content);
        holder.tv_date.setText(bean.date);
        holder.tv_name.setText(bean.name);
        holder.tv_contact.setText(bean.contact);

        holder.tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将联系方式copy到剪贴板
                ClipboardManager cbm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cbm.setPrimaryClip(ClipData.newPlainText("label",bean.contact));
                Snackbar.make(view.findViewById(R.id.tv_lost_copy),"联系方式已复制到剪切板",Snackbar.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder{
        public TextView tv_name;
        public TextView tv_date;
        public TextView tv_content;
        public TextView tv_contact;
        public TextView tv_copy;
    }
}
