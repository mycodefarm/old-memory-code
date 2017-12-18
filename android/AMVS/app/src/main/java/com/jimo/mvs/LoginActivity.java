package com.jimo.mvs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    private RadioButton rdo_driver = null;
    private RadioButton rdo_staff = null;
    private CheckBox rmbu=null;
    private CheckBox autologin=null;
    private EditText edit=null;
    private Mydata mydata=new Mydata(LoginActivity.this);
    Handler handler = new Handler();
    private boolean debugsign=true;
    RequestQueue queue;
    Runnable getname;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sha=getSharedPreferences("signinfo", MODE_PRIVATE);
        Time t=new Time();
        t.setToNow();
        if(debugsign) {
            mydata.insert(t.year, t.month + 1, t.monthDay, "signinfo");
            mydata.insert(2016, 8, 2, "signinfo");
            mydata.insert(2016, 8, 1, "signinfo");
            mydata.insert(2016, 7, 2, "signinfo");
            mydata.insert(2016, 7, 5, "signinfo");
        }

        int day=t.monthDay;
        if(sha.getInt("day",0)!=day){
            SharedPreferences.Editor editor2 = sha.edit();
            editor2.putInt("sign", 0);
            editor2.putInt("day",t.monthDay);
            editor2.putInt("year",t.year);
            editor2.putInt("day",t.month);
            editor2.commit();
        }
        initViews();
    }

    private void initViews() {

        rdo_driver = (RadioButton)findViewById(R.id.rdo_driver);
        rdo_staff = (RadioButton)findViewById(R.id.rdo_staff);
        rmbu=(CheckBox)findViewById(R.id.rmbuser);
        autologin=(CheckBox)findViewById(R.id.autologin);
        edit=(EditText)findViewById(R.id.name);
        SharedPreferences sharedPre=getSharedPreferences("userinfo", MODE_PRIVATE);
        //sharedPre.edit().clear().commit();
        String username=sharedPre.getString("username", "");
        String atlogin=sharedPre.getString("auotlogin", "");
        if(atlogin.equals("ok")){
            autologin.setChecked(true);
            if(sharedPre.getString("type", "").equals("driver")){
                Intent intent = new Intent(this,DriverActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                this.finish();
            }
            else{
                Intent intent = new Intent(this,StaffActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                this.finish();
            }
        }
        if(!username.equals("")){
            edit.setText(username);
            rmbu.setChecked(true);
            String type=sharedPre.getString("type", "");
            if(type.equals("driver")){
                rdo_driver.setChecked(true);
            }
            if(type.equals("staff")){
                rdo_staff.setChecked(true);
            }
        }
    }

    public void login(View view) {
        if (edit.getText().toString().equals("")) {
            Toast.makeText(this, "请输入工号或姓名", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences shared = getSharedPreferences("userinfo", MODE_PRIVATE);
            shared.edit().clear().commit();
            SharedPreferences.Editor editor = shared.edit();
            if (rmbu.isChecked()) {
                editor.putString("username", edit.getText().toString());
            } else {
                editor.putString("username", "");
            }
            if (autologin.isChecked()) {
                editor.putString("auotlogin", "ok");
            } else {
                editor.putString("autologin", "no");
            }

            if (rdo_driver.isChecked()) {
                if(debugsign){
                    Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                editor.putString("type", "driver");
                editor.commit();
                queue = Volley.newRequestQueue(LoginActivity.this);
                String url = APPConst.URL+"MVS/servlet/APPLoginServlet?" +
                        "type=2&name=" + edit.getText();
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("yes")) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
                            startActivity(intent);
                            intent.putExtra("username", edit.getText().toString());
                            LoginActivity.this.finish();
                        } else {
                            edit.setText("");
                            Toast.makeText(LoginActivity.this, "查询无果，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "连接错误，请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

            } else {
                //进入员工界面
                if(debugsign){
                    Intent intent = new Intent(LoginActivity.this, StaffActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                editor.putString("type", "staff");
                editor.commit();
                queue = Volley.newRequestQueue(LoginActivity.this);
                String url = APPConst.URL+"MVS/servlet/APPLoginServlet?" +
                        "type=1&name=" + edit.getText();
                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("no")) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            String[] info=response.split(",");
                            SharedPreferences shared = getSharedPreferences("userinfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("name",info[0]);
                            editor.putString("department",info[1]);
                            editor.putString("site",info[2]);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, StaffActivity.class);
                            startActivity(intent);
                            intent.putExtra("username", edit.getText().toString());
                            LoginActivity.this.finish();
                        } else {
                            edit.setText("");
                            Toast.makeText(LoginActivity.this, "查询无果，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "连接错误，请检查网络是否连接", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
