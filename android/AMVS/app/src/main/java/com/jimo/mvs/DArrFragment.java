package com.jimo.mvs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dell on 2016/9/3.
 */
public class DArrFragment extends Fragment implements OnDateSelectedListener,View.OnClickListener{

    //页面控件
    private TextView tv_date;
    private TextView tv_select_date;
    private MaterialCalendarView mcv_date;
    ListView lv_arr;
    //加载班次数据
    ArrayList<DArrDataBean> data = null;
    DArrDataAdapter adapter = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_arr, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initEvents() {
        mcv_date.setOnDateChangedListener(this);
        tv_select_date.setOnClickListener(this);
    }

    private void initViews(View view) {
        tv_date = (TextView)view.findViewById(R.id.tv_d_arr_date);
        tv_date.setText(new SimpleDateFormat().format(new Date()));
        tv_select_date = (TextView)view.findViewById(R.id.tv_d_arr_se_date);
        mcv_date = (MaterialCalendarView)view.findViewById(R.id.mcv_arr);
        mcv_date.setSelectedDate(new Date());
        lv_arr = (ListView)view.findViewById(R.id.lv_arr);
        data = new ArrayList<>();
        for(int i=0;i<10;i++){
            data.add(new DArrDataBean("班次"+i,"线路"+i,20+i,"12:00"));
        }
        adapter = new DArrDataAdapter(data,getActivity());
        lv_arr.setAdapter(adapter);
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //Toast.makeText(getActivity(),date.getDate().toString(),Toast.LENGTH_SHORT).show();
        tv_date.setText(new SimpleDateFormat().format(date.getDate()));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_d_arr_se_date:
                controlCalendar();
                break;
        }
    }

    /**
     * 控制日期显示隐藏
     */
    private void controlCalendar() {
        if(mcv_date.getVisibility()==View.VISIBLE){
            mcv_date.setVisibility(View.GONE);
            tv_select_date.setText("选择日期");
        }else{
            mcv_date.setVisibility(View.VISIBLE);
            tv_select_date.setText("关闭日期");
        }
    }
}

