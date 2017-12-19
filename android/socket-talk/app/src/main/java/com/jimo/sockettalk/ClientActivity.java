package com.jimo.sockettalk;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ClientActivity extends AppCompatActivity {

    private boolean stop = false;//线程是否终止
    private boolean connected = false;
    private Receiver receiver = null;
    private StringBuilder sb_server,sb_me;

    private Handler handler = null;
    private final int CONNECT_ERROR = 0;
    private final int CONNECT_OK = 1;
    private final int GET_MSG = 2;

    //views
    private Button btn_connect;
    private Button btn_send;
    private EditText edt_ip;
    private EditText edt_msg;
    private TextView tv_client_record_server;
    private TextView tv_client_record_me;

    //socket相关
    private Socket clientSocket = null;
    private OutputStream out = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("SocketTalk-客户端");

        initViews();

        initEvents();

        //handler
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CONNECT_ERROR:
                        myToast("连接失败");
                        btn_connect.setEnabled(true);
                        break;
                    case GET_MSG:
                        //将消息输出到界面
                        String s = (String) msg.obj;
                        sb_server.append(s);
                        sb_server.append('\n');
                        sb_me.append('\n');
                        tv_client_record_server.setText(sb_server.toString());
                        break;
                    case CONNECT_OK:
                        myToast("连接成功");
                        btn_connect.setEnabled(false);
                        btn_send.setEnabled(true);
                        stop = false;
                        receiver = new Receiver(clientSocket);
                        receiver.start();
                        break;
                }
            }
        };
    }

    private void initEvents() {
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = String.valueOf(edt_ip.getText());
                if (TextUtils.isEmpty(ip)) {
                    myToast("ip不能为空");
                    return;
                }

                new Connector(ip).start();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = String.valueOf(edt_msg.getText());
                if (TextUtils.isEmpty(msg)) {
                    myToast("消息为哦嗯");
                    return;
                }
                byte[] buf = null;

                try {
                    buf = msg.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    myToast("编码错误");
                    e.printStackTrace();
                }
                try {
                    out = clientSocket.getOutputStream();
                    out.write(buf);
                    out.flush();
                    sb_me.append(msg);
                    sb_me.append('\n');
                    tv_client_record_me.setText(sb_me.toString());
                    sb_server.append('\n');
                    //清空消息
                    edt_msg.setText("");
                    myToast("发送成功");
                } catch (IOException e) {
                    myToast("输出流bug");
                    e.printStackTrace();
                }
            }
        });
    }


    private void initViews() {
        btn_connect = (Button) findViewById(R.id.btn_connect);
        btn_send = (Button) findViewById(R.id.btn_client_send);
        edt_ip = (EditText) findViewById(R.id.edt_sever_ip);
        edt_msg = (EditText) findViewById(R.id.edt_msg);
        tv_client_record_server = (TextView) findViewById(R.id.tv_client_record_server);
        tv_client_record_me = (TextView) findViewById(R.id.tv_client_record_me);

        btn_send.setEnabled(false);
        sb_server = new StringBuilder("服务器");
        sb_me = new StringBuilder("我");
        sb_me.append('\n');
        sb_server.append('\n');

        String add = MyUtil.getLocalAddress();
        if (add != null) {
            edt_ip.setText(add.substring(0, 8));
        }
    }

//    private check

    private void myToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 用于连接socket
     */
    private class Connector extends Thread {

        private String ip;

        public Connector(String ip) {
            this.ip = ip;
        }

        @Override
        public void run() {
            try {
                clientSocket = new Socket(ip, 5678);
            } catch (IOException e) {
                handler.sendEmptyMessage(CONNECT_ERROR);
                e.printStackTrace();
                connected = false;
                return;
            }
            connected = true;
            handler.sendEmptyMessage(CONNECT_OK);
        }
    }

    private class Receiver extends Thread {

        private InputStream in;
        private String str = null;
        private byte[] buf = null;

        public Receiver(Socket s) {
            try {
                this.in = s.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!stop) {
                this.buf = new byte[512];
                try {
                    this.in.read(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    this.str = new String(buf, "utf-8").trim();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //去更新主线程界面
                Log.i("info",str);
                Message msg = handler.obtainMessage(GET_MSG);
                msg.obj = str;
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            stop = true;
            receiver.interrupt();
        }
    }
}
