package com.jimo.mvs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.junge.mvs.CarRecord;
import com.junge.mvs.ComplyInfo;

public class SSettingActivity extends AppCompatActivity {
    private TextView cmpInfo;
    private TextView site;
    private TextView record;
    private TextView reset;
    private TextView quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.LEFT).setContentView(R.layout.activity_s_setting)
                .setSwipeBackView(R.layout.swipeback_default);
        init();
        initView();
    }
    public void init(){
        cmpInfo= (TextView) findViewById(R.id.tv_cmpinfo);
        site= (TextView) findViewById(R.id.reset_site);
        record= (TextView) findViewById(R.id.record);
        reset= (TextView) findViewById(R.id.reset);
        quit= (TextView) findViewById(R.id.quit);
    }
    public void initView(){
        cmpInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SSettingActivity.this, ComplyInfo.class);
                startActivity(i);
                finish();
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(SSettingActivity.this,CarRecord.class);
                i.putExtra("DS",false);
                startActivity(i);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SSettingActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                APPConst.exitApp();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
