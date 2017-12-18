package com.junge.mvs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jimo.mvs.DArrDataAdapter;
import com.jimo.mvs.DArrDataBean;
import com.jimo.mvs.DriverActivity;
import com.jimo.mvs.R;
import com.jimo.mvs.StaffActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by dell on 2016/9/3.
 */
public class CarRecord extends AppCompatActivity implements OnDateSelectedListener{

    //页面控件
    private TextView tv_date;
    private MaterialCalendarView mcv_date;
    ListView lv_arr;
    //加载班次数据
    ArrayList<RecordBean> data = null;
    RecordAdapter adapter = null;
    private TextView title;
    private Button back;
    private Button startroute;
    private String[] SiteArry={"线路1","线路1","线路1","线路2","线路2","线路2","线路1","线路1","线路1","线路1"};
    private String[] orderArry={"班次1","班次2","班次2","班次1","班次2","班次2","班次1","班次1","班次1","班次1"};
    private String[] SiteArry2={"天府广场","春熙路","人民北路","盐井市","百草路","交大路","九里堤","盐道街","人民公园","校园路"};
    private Boolean isDS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_record);
        /// View view = inflater.inflate(R.layout.fragment_d_arr, container, false);
        initViews();
        initEvents();
    }

    private void initEvents() {
        mcv_date.setOnDateChangedListener(this);
    }

    private void initViews() {
        tv_date = (TextView)findViewById(R.id.tv_d_arr_date);
        title= (TextView) findViewById(R.id.title_info);
        startroute= (Button) findViewById(R.id.order);
        Intent ii=getIntent();
        Bundle bundle = ii.getExtras();

        if(bundle != null){
            isDS=bundle.getBoolean("DS");
        }
        tv_date.setText(new SimpleDateFormat().format(new Date()));
        mcv_date = (MaterialCalendarView)findViewById(R.id.mcv_arr);
        mcv_date.setSelectedDate(new Date());
        lv_arr = (ListView)findViewById(R.id.lv_arr);
        data = new ArrayList<>();
        if(!isDS){
            title.setText("乘车纪录");
            for(int i=0;i<10;i++){
                int num=20+(int)(Math.random()*10);
                data.add(new RecordBean(SiteArry[i],SiteArry2[i],orderArry[i]));
            }
        }else{
            title.setText("驾驶记录");
            for(int i=0;i<10;i++){
                int num=20+(int)(Math.random()*10);
                data.add(new RecordBean(SiteArry[i],String.valueOf(num),orderArry[i]));
            }
        }
        adapter = new RecordAdapter(data,this,isDS);
        lv_arr.setAdapter(adapter);
        back= (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        startroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDS) {
                    Intent i = new Intent(CarRecord.this, DriverActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(CarRecord.this, StaffActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //Toast.makeText(getActivity(),date.getDate().toString(),Toast.LENGTH_SHORT).show();
        tv_date.setText(new SimpleDateFormat().format(date.getDate()));
    }

}

