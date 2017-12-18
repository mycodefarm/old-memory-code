package com.jimo.mvs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.junge.mvs.CarRecord;
import com.junge.mvs.ComplyInfo;

public class DSettingActivity extends AppCompatActivity {
    private TextView cmpInfo;
    private TextView car;
    private TextView record;
    private TextView data;
    private TextView quit;
    private TextView reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBack.attach(this, Position.LEFT).setContentView(R.layout.activity_d_setting)
                .setSwipeBackView(R.layout.swipeback_default);
        init();
        initView();

    }
    public void init(){
        car= (TextView) findViewById(R.id.car_info);
        cmpInfo= (TextView) findViewById(R.id.comply_info);
        record= (TextView) findViewById(R.id.drive_record);
        data= (TextView) findViewById(R.id.commit_data);
        reset= (TextView) findViewById(R.id.reset);
        quit= (TextView) findViewById(R.id.quit);
    }
    public void initView(){
        cmpInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DSettingActivity.this, ComplyInfo.class);
                startActivity(i);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(DSettingActivity.this,CarRecord.class);
                i.putExtra("DS",true);
                startActivity(i);
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DSettingActivity.this);
                alertDialog2.setTitle("核对信息");

                alertDialog2.setMessage("请您核对您乘坐数据是否正确，核对成功后点击确认键签到提交，提交数据为：\n线路:1, 班次:班次2, 人数:28/30");
                alertDialog2.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DSettingActivity.this,"提交成功",Toast.LENGTH_LONG).show();
                            }
                        });


                alertDialog2.setNegativeButton("关闭",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                alertDialog2.show();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DSettingActivity.this,LoginActivity.class);
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
