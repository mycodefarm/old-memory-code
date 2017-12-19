package com.jimo.jimoqx;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.MyMessage;
import myutil.HttpUtil;

public class MainActivity extends AppCompatActivity {

    private Button btn_send;
    private EditText edt_msg;
    private ListView lv_msg;
    private List<MyMessage> mData;
    private MessageAdapter mAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MyMessage getMsg = (MyMessage)msg.obj;
            mData.add(getMsg);
            mAdapter.notifyDataSetChanged();
            lv_msg.setSelection(mData.size()-1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        lv_msg = (ListView)findViewById(R.id.lv_msg);
        btn_send = (Button)findViewById(R.id.btn_send);
        edt_msg = (EditText)findViewById(R.id.edt_send_msg);
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add(new MyMessage("hello,I am JimoQX",new Date(), MyMessage.Type.INCOMING));
        mData.add(new MyMessage("hello",new Date(), MyMessage.Type.OUTCOMING));
        mAdapter = new MessageAdapter(this,mData);

        lv_msg.setAdapter(mAdapter);
    }
    private void initEvent(){

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String sendMsg = edt_msg.getText().toString();
                if(TextUtils.isEmpty(sendMsg)){
                    Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                final MyMessage sendMM = new MyMessage();
                sendMM.setDate(new Date());
                sendMM.setType(MyMessage.Type.OUTCOMING);
                sendMM.setmsg(sendMsg);
                mData.add(sendMM);
                mAdapter.notifyDataSetChanged();
                lv_msg.setSelection(mData.size()-1);

                edt_msg.setText("");

                //网络任务放在线程里
                new Thread(){
                    @Override
                    public void run() {
                        MyMessage getMsg = HttpUtil.sendMessage(sendMsg);
                        Message msg = Message.obtain();
                        msg.obj = getMsg;
                        handler.sendMessage(msg);
                    }
                }.start();


            }
        });
    }
}
