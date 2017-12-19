package com.jimo.jimoqx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import bean.MyMessage;

/**
 * Created by dell on 2016/5/1.
 */
public class MessageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<MyMessage> list;

    public MessageAdapter(Context context,List<MyMessage> list){
        inflater = LayoutInflater.from(context);
        this.list = list;
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
    public int getItemViewType(int position) {

        if(list.get(position).getType()== MyMessage.Type.INCOMING){
            return 0;//收到的消息
        }else{
            return 1;//发送的消息
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;//总共两种布局情况
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        MyMessage myMessage = list.get(i);
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();

            if(0==getItemViewType(i)){
                view = inflater.inflate(R.layout.item_get_msg,viewGroup,false);
                viewHolder.tv_date = (TextView)view.findViewById(R.id.tv_get_date);
                viewHolder.tv_msg = (TextView)view.findViewById(R.id.tv_get_msg);
            }else{
                view = inflater.inflate(R.layout.item_send_msg,viewGroup,false);
                viewHolder.tv_date = (TextView)view.findViewById(R.id.tv_send_date);
                viewHolder.tv_msg = (TextView)view.findViewById(R.id.tv_send_msg);
            }
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.tv_date.setText(sdf.format(myMessage.getDate()));
        viewHolder.tv_msg.setText(myMessage.getmsg());
        return view;
    }

    private final class ViewHolder{

        TextView tv_date;
        TextView tv_msg;
    }
}
