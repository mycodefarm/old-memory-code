package com.jimo.mvs;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_per_info;
    /**
     * 布局切换控件
     */
    ViewPager vp_main;
    FragmentPagerAdapter fAdapter;
    List<Fragment> mFrags;
    LinearLayout ll_arr;
    LinearLayout ll_lost;
    ImageView iv_arr;
    ImageView iv_lost;
    ImageView iv_sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        APPConst.addOneActivity(this);

        initViews();
        initEvents();
        initDatas();
    }

    private void initEvents() {
        btn_per_info.setOnClickListener(this);
        //布局控件点击事件
        ll_arr.setOnClickListener(this);
        ll_lost.setOnClickListener(this);
        iv_sign.setOnClickListener(this);
    }

    private void initViews() {
        btn_per_info = (Button)findViewById(R.id.btn_per_info);
        //布局控件
        vp_main = (ViewPager) findViewById(R.id.vp_s_main);
        ll_arr = (LinearLayout) findViewById(R.id.ll_s_arr);
        ll_lost = (LinearLayout) findViewById(R.id.ll_s_lost);
        iv_arr = (ImageView) findViewById(R.id.iv_s_arr);
        iv_lost = (ImageView) findViewById(R.id.iv_s_lost);
        iv_sign= (ImageView) findViewById(R.id.sign);
    }
    /**
     * 对布局的fragment进行初始化
     */
    private void initDatas() {
        mFrags = new ArrayList<>();
        mFrags.add(new SArrFragment());
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

    /**
     //     * 签到验证
     //     *
     //     * @param view
     //     */
    public void sign() {
            iv_sign.setImageResource(R.drawable.sign_on);
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(StaffActivity.this);
        alertDialog2.setTitle("核对信息");

        alertDialog2.setMessage("请您核对您乘坐的车辆和班次是否正常，核对成功后点击确认键签到提交");
        alertDialog2.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(StaffActivity.this,"签到成功",Toast.LENGTH_LONG).show();
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
//        final ImageButton ib_sign = (ImageButton) view;
//        if (!tv_sign.getText().toString().equals("签到成功")) {
//
//
//            ib_sign.setBackgroundResource(R.drawable.round_shape_on);
//            tv_sign.setText("正在验证地理位置...");
//            final MyLocation location = new MyLocation(this);
//            final String myloca = location.getLocationInTime();
//            SharedPreferences sharedPre = getSharedPreferences("userinfo", MODE_PRIVATE);
//            String name = sharedPre.getString("username", "");
//            String url = APPConst.URL + "MVS/servlet/APPLocationServlet?type=3&name=" + name
//                    +"&coordi="+myloca;
//            RequestQueue queue = Volley.newRequestQueue(this);
//
//            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    if (response.equals("no")) {
//                        tv_sign.setText("签到失败");
//                        ib_sign.setBackgroundResource(R.drawable.round_shape);
//                    } else {
////                        String[] lo = response.split(",");
////                        double lati = Double.parseDouble(lo[0]);
////                        double longti = Double.parseDouble(lo[1]);
////                        String[] mylo = myloca.split(",");
////                        double myla = Double.parseDouble(mylo[0]);
////                        double mylong = Double.parseDouble(mylo[1]);
////
////                        double dis = location.getDistance(longti, lati, mylong, myla);
//
//                        double dis = Double.parseDouble(response);
//
//                        if (dis < 50) {
//                            tv_sign.setText("签到成功");
//                            ib_sign.setEnabled(false);//这里设置为不可点后，这一天都应该不可点
//                            SharedPreferences sha = getSharedPreferences("signinfo", MODE_PRIVATE);
//                            Time t = new Time();
//                            t.setToNow();
//                            SharedPreferences.Editor editor2 = sha.edit();
//                            editor2.putInt("sign", 1);
//                            editor2.putInt("day", t.monthDay);
//                            editor2.putInt("year", t.year);
//                            editor2.putInt("day", t.month);
//                        } else {
//                            tv_sign.setText("您距离站点有" + dis + "米，不能签到");
//                            ib_sign.setBackgroundResource(R.drawable.round_shape);
//                        }
//                    }
//
//                    location.closeLocation();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    //Snackbar.make(null,"连接错误")
//                    tv_sign.setText("连接错误，请检查设置");
//                    ib_sign.setBackgroundResource(R.drawable.round_shape);
//                    location.closeLocation();
//                }
//            });
//
//            queue.add(request);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }

    @Override
    public void onClick(View view) {
       // resetImg();
        switch (view.getId()) {
            case R.id.ll_s_arr:
                selectFragment(0);
                break;
            case R.id.ll_s_lost:
                selectFragment(1);
                break;
            case R.id.sign:
                sign();
                break;
            case R.id.btn_per_info:
                Intent i = new Intent(StaffActivity.this,SSettingActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        APPConst.removeOneActivity(this);
    }

    /**
     * 设置图片未被选中
     */
    public void resetImg() {
        iv_arr.setImageResource(R.drawable.order_on);
        iv_lost.setImageResource(R.drawable.lost_off);
    }

    /**
     * 选择页面,图片设为高亮
     */
    public void selectFragment(int i) {
        switch (i) {
            case 0:
                iv_arr.setImageResource(R.drawable.order_on);
                iv_lost.setImageResource(R.drawable.lost_off);
                break;
            case 1:
                iv_arr.setImageResource(R.drawable.order_off);
                iv_lost.setImageResource(R.drawable.lost_on);
                break;
        }
        vp_main.setCurrentItem(i);
    }
}
