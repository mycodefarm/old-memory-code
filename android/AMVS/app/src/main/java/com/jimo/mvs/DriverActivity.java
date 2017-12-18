package com.jimo.mvs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity implements View.OnClickListener {

    //页面控件
    private Button btn_per_info;
    //获取数据
    private Button btn_location = null;
    MyLocation location = null;
    Handler handler = new Handler();
    RequestQueue queue;
    Runnable sendLocationRun;

    /**
     * 布局切换控件
     *
     * @param savedInstanceState
     */
    ViewPager vp_main;
    FragmentPagerAdapter fAdapter;
    List<Fragment> mFrags;
    LinearLayout ll_arr;
    LinearLayout ll_lost;
    ImageView iv_arr;
    ImageView iv_lost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        APPConst.addOneActivity(this);

        initViews();
        initEvents();
        initDatas();
    }

    /**
     * 对布局的fragment进行初始化
     */
    private void initDatas() {
        mFrags = new ArrayList<>();
        mFrags.add(new DArrFragment());
        mFrags.add(new LostFragment());

        fAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFrags.get(position);
            }

            @Override
            public int getCount() {
                return mFrags.size();
            }
        };

        vp_main.setAdapter(fAdapter);

        //切换监听
        vp_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vp_main.setCurrentItem(position);
                resetImg();
                selectFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {

//        tv_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*
//                注销司机
//                 */
//                SharedPreferences shared = getSharedPreferences("userinfo", MODE_PRIVATE);
//                shared.edit().clear().commit();
//                SharedPreferences sha = getSharedPreferences("signinfo", MODE_PRIVATE);
//                sha.edit().clear().commit();
//                Intent i = new Intent(DriverActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
        //布局控件点击事件
        ll_arr.setOnClickListener(this);
        ll_lost.setOnClickListener(this);
        btn_per_info.setOnClickListener(this);
    }

    private void initViews() {
        btn_per_info = (Button)findViewById(R.id.btn_per_info);
        btn_location = (Button) findViewById(R.id.btn_location);
//        tv_setting = (TextView) findViewById(R.id.tv_setting);
//        tv_setting.setText("注销");
        //布局控件
        vp_main = (ViewPager) findViewById(R.id.vp_d_main);
        ll_arr = (LinearLayout) findViewById(R.id.ll_driver_arrange);
        ll_lost = (LinearLayout) findViewById(R.id.ll_driver_lost);
        iv_arr = (ImageView) findViewById(R.id.iv_d_arr);
        iv_lost = (ImageView) findViewById(R.id.iv_d_lost);
    }

    /**
     * 发送定位信息
     *
     * @param view
     */
    public void sendLocation(View view) {

        queue = Volley.newRequestQueue(DriverActivity.this);

        location = new MyLocation(this);


        if (btn_location.getText().equals("连接")) {

            btn_location.setBackgroundResource(R.drawable.round_shape_on);

            btn_location.setText("正在连接...");
            sendLocationRun = new Runnable() {
                //http://192.168.1.108:8080/MVS/servlet/APPLoginServlet?type=1&name=2014100001
//http://192.168.1.108:8080/MVS/servlet/APPLoginServlet?type=2&name=
//http://192.168.1.108:8080/MVS/servlet/APPLocationServlet?type=2&location=11
                @Override
                public void run() {
                    final String url = APPConst.URL + "MVS/servlet/APPLocationServlet?" +
                            "type=2&location=" + location.getLocation();
                    StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            btn_location.setText("连接成功，正在提交地址，点击可取消");
                            handler.postDelayed(sendLocationRun, 3000);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // String url1 = ""+location.getLocation();
                            //Toast.makeText(DriverActivity.this, "连接错误+url="+url, Toast.LENGTH_SHORT).show();
                            btn_location.setText("连接出错，请检查网络和定位");
                            location.closeLocation();
                        }
                    });

                    queue.add(request);

                }
            };
            handler.postDelayed(sendLocationRun, 3000);

        } else {
            btn_location.setBackgroundResource(R.drawable.round_shape);
            handler.removeCallbacks(sendLocationRun);
            btn_location.setText("连接");
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendLocationRun);
        APPConst.removeOneActivity(this);
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.ll_driver_arrange:
                selectFragment(0);
                break;
            case R.id.ll_driver_lost:
                selectFragment(1);
                break;
            case R.id.btn_per_info:
                Intent i = new Intent(DriverActivity.this,DSettingActivity.class);
                startActivity(i);
                break;
        }
    }

    /**
     * 设置图片未被选中
     */
    public void resetImg() {
        iv_arr.setImageResource(R.drawable.schedule_on);
        iv_lost.setImageResource(R.drawable.lost_off);
    }

    /**
     * 选择页面,图片设为高亮
     */
    public void selectFragment(int i) {
        switch (i) {
            case 0:
                iv_arr.setImageResource(R.drawable.schedule_on);
                iv_lost.setImageResource(R.drawable.lost_off);
                break;
            case 1:
                iv_arr.setImageResource(R.drawable.schedule_off);
                iv_lost.setImageResource(R.drawable.lost_on);
                break;
        }
        vp_main.setCurrentItem(i);
    }
}
