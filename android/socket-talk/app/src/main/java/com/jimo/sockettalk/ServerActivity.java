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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class ServerActivity extends AppCompatActivity {

    //views
    private TextView tv_server_ip;
    private Button btn_start_server;
    private TextView tv_client;
    private Button btn_send;
    private EditText edt_msg;
    private TextView tv_server_record_client;
    private TextView tv_server_record_me;

    //handler
    private Handler handler = null;
    private final int IP_MSG = 0;
    private final int RECEV_MSG = 1;
    private final int CLIENTIP_MSG = 2;

    //socket work
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private OutputStream out = null;
    private Receiver receiver = null;
    private ListenThread listener = null;

    private boolean stop = true;
    private StringBuilder sb_client;
    private StringBuilder sb_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        setTitle("SocketTalk-服务端");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case IP_MSG:
                        String ip = (String) msg.obj;
                        tv_server_ip.setText(tv_server_ip.getText() + " 公网IP：" + ip);
//                        btn_start_server.setEnabled(true);//获取到ip后可以开启服务了
                        break;
                    case RECEV_MSG:
                        //收到消息了,更新界面
                        String s = (String) msg.obj;
                        sb_client.append(s);
                        sb_client.append('\n');
                        sb_me.append('\n');
                        tv_server_record_client.setText(sb_client.toString());
                        break;
                    case CLIENTIP_MSG:
                        String cip = (String) msg.obj;
                        tv_client.setText("客户端IP：" + cip);
                        btn_send.setEnabled(true);
                        break;
                }
            }
        };

        initViews();

        initEvents();
    }

    private void myToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean closeServer() {
        if (listener != null) {
            listener.interrupt();
            if (receiver != null) {
                receiver.interrupt();
            }
            stop = true;
            return true;
        }
        return false;
    }

    private void initEvents() {
        btn_start_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("开启服务".equals(btn_start_server.getText())) {
                    if (listener == null) {
                        listener = new ListenThread();
                        listener.start();
                        myToast("开启成功");
                        btn_start_server.setText("关闭服务");
                    }
                } else {
                    closeServer();
                    myToast("已关闭");
                    btn_start_server.setText("开启服务");
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = String.valueOf(edt_msg.getText());
                if (TextUtils.isEmpty(msg)) {
                    myToast("发送消息为空");
                    return;
                }
                byte[] buf = null;
                try {
                    buf = msg.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    myToast("server编码bug");
                    e.printStackTrace();
                }
                try {
                    out = clientSocket.getOutputStream();
                } catch (IOException e) {
                    myToast("out bug");
                    e.printStackTrace();
                }
                try {
                    out.write(buf);
                    out.flush();
                } catch (IOException e) {
                    myToast("out write bug");
                    e.printStackTrace();
                }
                sb_me.append(msg);
                sb_me.append('\n');
                sb_client.append('\n');
                tv_server_record_me.setText(sb_me.toString());
                edt_msg.setText("");
                myToast("发送成功");
            }
        });
    }

    private void initViews() {
        tv_client = (TextView) findViewById(R.id.tv_client);
        tv_server_ip = (TextView) findViewById(R.id.tv_myip);
        btn_start_server = (Button) findViewById(R.id.btn_start_server);
        btn_send = (Button) findViewById(R.id.btn_server_send);
        edt_msg = (EditText) findViewById(R.id.edt_server_msg);
        tv_server_record_client = (TextView) findViewById(R.id.tv_server_record_client);
        tv_server_record_me = (TextView) findViewById(R.id.tv_server_record_me);
        sb_client = new StringBuilder("客户端");
        sb_me = new StringBuilder("我");
        sb_me.append('\n');
        sb_client.append('\n');

        btn_send.setEnabled(false);
        btn_start_server.setEnabled(false);

        tv_server_ip.setText("正在获取您的IP.....");
        initServerIP();
        tv_server_ip.setText("内网IP:" + MyUtil.getLocalAddress());
        btn_start_server.setEnabled(true);
    }

    /**
     * 得到公网ip，用于客户端连接用
     */
    private void initServerIP() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                URL infoUrl = null;
                InputStream inStream = null;
                String line = "";
                try {
                    infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        while ((line = reader.readLine()) != null)
                            strber.append(line + "\n");
                        inStream.close();
                        // 从反馈的结果中提取出IP地址
                        int start = strber.indexOf("{");
                        int end = strber.indexOf("}");
                        String json = strber.substring(start, end + 1);
                        if (json != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                line = jsonObject.optString("cip");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Message msg = handler.obtainMessage(IP_MSG);
                        msg.obj = line;
                        handler.sendMessage(msg);
                        return;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage(IP_MSG);
                msg.obj = "获取失败";
                handler.sendMessage(msg);
            }
        };
        thread.start();
    }

    /**
     * 监听客户端请求
     */
    private class ListenThread extends Thread {

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(5678);//端口号为5678
//                serverSocket.setSoTimeout(4000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stop = false;
            receiver = new Receiver(clientSocket);
            //开始接收
            receiver.start();
            //获得客户端ip
            Message msg = handler.obtainMessage(CLIENTIP_MSG);
            msg.obj = clientSocket.getInetAddress().getHostAddress();
            handler.sendMessage(msg);
        }
    }

    /**
     * 接受消息线程
     */
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
                Log.i("info", str);
                Message msg = handler.obtainMessage(RECEV_MSG);
                msg.obj = str;
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeServer();
    }
}
