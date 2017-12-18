package com.jimo.mvs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by dell on 2016/9/3.
 */
public class SArrFragment extends Fragment implements View.OnClickListener{

    TextView tv_sign;
    private TextView tv_site_dis;
    private TextView tv_drive_dis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_s_arr, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initEvents() {
    }

    private void initViews(View view) {
        tv_drive_dis = (TextView) view.findViewById(R.id.tv_drive_dis);
        tv_site_dis = (TextView) view.findViewById(R.id.tv_site_dis);
        tv_sign = (TextView) view.findViewById(R.id.tv_sign);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


     /**
      * 计算距离
     */
    public void cacudis() {
//        tv_drive.setText("正在计算距离...");
        final MyLocation location = new MyLocation(getContext());
        final String myloca = location.getLocationInTime();
        SharedPreferences sharedPre = getContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String name = sharedPre.getString("username", "");
        String url = APPConst.URL + "MVS/servlet/APPLocationServlet?type=1&name=" + name;
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] mylo = myloca.split(",");
                double myla = Double.parseDouble(mylo[0]);
                double mylong = Double.parseDouble(mylo[1]);

                String[] lo = response.split(",");
                if (lo[0].equals("no")) {
//                    tv_drive.setText("司机没有开定位");
                    tv_drive_dis.setText("未知");
                    if (lo[1].equals("no")) {
                        tv_site_dis.setText("未知");
                    } else {
                        double slati = Double.parseDouble(lo[1]);
                        double slongti = Double.parseDouble(lo[2]);
                        double sdis = location.getDistance(slongti, slati, mylong, myla);
                        tv_site_dis.setText(sdis + "米");
                    }
                } else {
                    if (lo[2].equals("no")) {
//                        tv_drive.setText("司机没有开定位");
                        tv_site_dis.setText("未知");
                        tv_drive_dis.setText("未知");
                    } else {
                        //人离站点距离
                        double slati = Double.parseDouble(lo[2]);
                        double slongti = Double.parseDouble(lo[3]);
                        double sdis = location.getDistance(slongti, slati, mylong, myla);
                        tv_site_dis.setText(sdis + "米");

                        //车离站点距离
                        double dlati = Double.parseDouble(lo[0]);
                        double dlongti = Double.parseDouble(lo[1]);
                        double ddis = location.getDistance(dlongti, dlati, mylong, myla);
                        double sddis = location.getDistance(slongti, slati, dlongti, dlati);
                        tv_drive_dis.setText(sddis + "米");
                    }
                }

                location.closeLocation();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Snackbar.make(null,"连接错误")
//                tv_drive.setText("连接错误，请检查设置");
            }
        });

        queue.add(request);
    }
}

