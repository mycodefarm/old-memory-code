package com.jimo.mvs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

import calendar.MainActivity;

public class SettingActivity extends AppCompatActivity {
    private TextView set_sign;
    private TextView sign_history;
    private TextView reset;
    private TextView quit;
    private TextView name;
    private TextView department;
    private TextView num;
    private TextView site;
    private Mydata mydata=new Mydata(SettingActivity.this);
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.LEFT).setContentView(R.layout.activity_setting)
                .setSwipeBackView(R.layout.swipeback_default);


        initEvent();
        inithandler();
        getInfo();
    }

    private void getInfo() {
        SharedPreferences sharedPre=getSharedPreferences("userinfo", MODE_PRIVATE);
        //sharedPre.edit().clear().commit();
        String username=sharedPre.getString("username", "");
    }

    private void initEvent() {



        name= (TextView) findViewById(R.id.set_name);
        department= (TextView) findViewById(R.id.set_department);
        num= (TextView) findViewById(R.id.set_num);
        site= (TextView) findViewById(R.id.set_site);
        set_sign= (TextView) findViewById(R.id.set_sign);
        sign_history= (TextView) findViewById(R.id.signhistory);
        reset= (TextView) findViewById(R.id.reset);
        quit= (TextView) findViewById(R.id.quit);
        SharedPreferences shared = getSharedPreferences("userinfo", MODE_PRIVATE);
        name.setText(shared.getString("name", ""));
        department.setText(shared.getString("department", ""));
        site.setText(shared.getString("username", ""));
        site.setText(shared.getString("site", ""));
    }
    private void inithandler() {

        reset.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shared=getSharedPreferences("userinfo", MODE_PRIVATE);
                shared.edit().clear().commit();
                SharedPreferences sha=getSharedPreferences("signinfo", MODE_PRIVATE);
                sha.edit().clear().commit();
                mydata.clearsql("signinfo");
                Intent i = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        sign_history.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingActivity.this, MainActivity.class);
                i.putExtra("signinfo",mydata.getData("signinfo"));
                startActivity(i);
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                APPConst.exitApp();
            }
        });
        SharedPreferences sha=getSharedPreferences("signinfo", MODE_PRIVATE);
        if(sha.getInt("sign",0)==1){
            set_sign.setText("已签到");
        }else {
            set_sign.setText("未签到");
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
