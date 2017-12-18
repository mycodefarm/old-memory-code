package com.jimo.mvs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.junge.mvs.NavActivity;
import com.junge.mvs.NavRoute;
import com.junge.mvs.Routeinfo;
import com.junge.mvs.TestNav;

/**
 * Created by TR on 2016/8/2.
 */
public class Welcome extends AppCompatActivity {
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg){
            final VideoView vv = (VideoView) findViewById(R.id.videoView1);
            vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcomevideo));
            vv.start();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Mythread mythread=new Mythread();
        mythread.start();
    }
    public class Mythread extends Thread {
        public void run(){
            try {
                sleep(20);
                Message msg=new Message();
                msg.obj =0;
                handler.sendMessage(msg);
                sleep(2500);
                Intent intent =new Intent(Welcome.this,LoginActivity.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
